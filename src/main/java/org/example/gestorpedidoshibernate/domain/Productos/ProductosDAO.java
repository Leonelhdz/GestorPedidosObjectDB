package org.example.gestorpedidoshibernate.domain.Productos;

import org.example.gestorpedidoshibernate.ObjectDBUtil;
import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz DAO para la entidad Productos.
 * Proporciona métodos para interactuar con la base de datos relacionados con la entidad Productos.
 */
public class ProductosDAO implements DAO<Productos> {
    /**
     * Obtiene todos los productos de la base de datos.
     *
     * @return Lista de todos los productos en la base de datos.
     */
    @Override
    public ArrayList<Productos> getAll() {
        var salida = new ArrayList<Productos>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Productos> query = entityManager.createQuery("select p from Productos p", Productos.class);
            salida = (ArrayList<Productos>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return salida;
    }
    /**
     * Obtiene un producto por su identificador único (no implementado en este contexto).
     *
     * @param id Identificador único del producto.
     * @return El producto con el identificador especificado.
     */
    @Override
    public Productos get(Long id) {
        return null;
    }
    /**
     * Método de guardado que no está implementado en este contexto.
     *
     * @param data Datos del producto a guardar.
     * @return El producto guardado.
     */
    @Override
    public Productos save(Productos data) {
        return null;
    }
    /**
     * Método de actualización que no está implementado en este contexto.
     *
     * @param data Datos del producto a actualizar.
     */
    @Override
    public Pedido update(Productos data) {
        return null;
    }
    /**
     * Método de eliminación que no está implementado en este contexto.
     *
     * @param data Datos del producto a eliminar.
     */
    @Override
    public boolean delete(Productos data) {
        return false;
    }

    public void saveAll(List<Productos> data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            for (Productos pr : data) {
                entityManager.persist(pr);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
