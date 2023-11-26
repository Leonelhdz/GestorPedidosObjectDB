package org.example.gestorpedidoshibernate.domain.Pedido;

import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.HibernateUtil;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz DAO para la entidad Pedido.
 * Proporciona métodos para interactuar con la base de datos relacionados con la entidad Pedido.
 */
public class PedidoDAO implements DAO<Pedido> {
    /**
     * Obtiene todos los pedidos de la base de datos.
     *
     * @return Lista de todos los pedidos en la base de datos.
     */
    @Override
    public ArrayList<Pedido> getAll() {
        var salida = new ArrayList<Pedido>(0);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pedido> query = session.createQuery("from Pedido", Pedido.class);
            salida = (ArrayList<Pedido>) query.getResultList();
        }
        return salida;
    }
    /**
     * Obtiene un pedido por su identificador único.
     *
     * @param id Identificador único del pedido.
     * @return El pedido con el identificador especificado.
     */
    @Override
    public Pedido get(Integer id) {
        var salida = new Pedido();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Pedido.class, id);
        }
        return salida;
    }
    /**
     * Guarda un nuevo pedido en la base de datos.
     *
     * @param data Datos del pedido a guardar.
     * @return El pedido guardado.
     */
    @Override
    public Pedido save(Pedido data) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(data);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return data;
    }
    /**
     * Actualiza un pedido en la base de datos.
     *
     * @param data Datos del pedido a actualizar.
     */
    @Override
    public void update(Pedido data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.update(data);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    /**
     * Elimina un pedido de la base de datos.
     *
     * @param data Datos del pedido a eliminar.
     */
    @Override
    public void delete(Pedido data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Pedido pedido = session.get(Pedido.class, data.getId());
            session.remove(pedido);
        });
    }

    /**
     * Obtiene el último código de pedido almacenado en la base de datos.
     *
     * @return El último código de pedido.
     */
    public String getUltimoCodigo() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("select max(p.codigo) from Pedido p", String.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el último código", e);
        }
    }

    /**
     * Calcula el total de un pedido sumando los precios de los ítems asociados.
     *
     * @param items Lista de ítems asociados al pedido.
     * @return El total del pedido.
     */
    public Double calcularTotalPedido(List<Item> items) {
        Double total = 0.0;

        for (Item item : items) {
            total += item.getProductos().getPrecio();
        }

        return total;
    }
}
