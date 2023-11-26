package org.example.gestorpedidoshibernate.domain.Items;

import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

/**
 * Implementación de la interfaz DAO para la entidad Item.
 * Proporciona métodos para interactuar con la base de datos relacionados con la entidad Item.
 */
public class ItemDAO implements DAO<Item> {
    /**
     * Constructor de la clase ItemDAO.
     */
    public ItemDAO() {
    }
    /**
     * Obtiene todos los ítems de la base de datos.
     *
     * @return Lista de todos los ítems en la base de datos.
     */
    @Override
    public ArrayList<Item> getAll() {
        var salida = new ArrayList<Item>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Item> q = s.createQuery("from Item", Item.class);
            salida = (ArrayList<Item>) q.getResultList();
        }
        return salida;
    }
    /**
     * Obtiene un ítem por su identificador único (no implementado en este contexto).
     *
     * @param id Identificador único del ítem.
     * @return El ítem con el identificador especificado.
     */
    @Override
    public Item get(Integer id) {
        return null;
    }

    /**
     * Guarda un nuevo ítem en la base de datos.
     *
     * @param data Datos del ítem a guardar.
     * @return El ítem guardado.
     */
    @Override
    public Item save(Item data) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(data);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return data;
        }
    }
    /**
     * Actualiza un ítem en la base de datos (no implementado en este contexto).
     *
     * @param data Datos del ítem a actualizar.
     */
    @Override
    public void update(Item data) {

    }
    /**
     * Elimina un ítem de la base de datos.
     *
     * @param data Datos del ítem a eliminar.
     */
    @Override
    public void delete(Item data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Item item = session.get(Item.class, data.getId());
            session.remove(item);
        });
    }
}
