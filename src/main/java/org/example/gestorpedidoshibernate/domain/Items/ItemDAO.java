package org.example.gestorpedidoshibernate.domain.Items;

import org.example.gestorpedidoshibernate.ObjectDBUtil;
import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

/**
 * Implementación de la interfaz DAO para la entidad Item.
 * Proporciona métodos para interactuar con la base de datos relacionados con la entidad Item.
 */
public class ItemDAO implements DAO<Item> {

    /**
     * Obtiene todos los ítems de la base de datos.
     *
     * @return Lista de todos los ítems en la base de datos.
     */
    @Override
    public ArrayList<Item> getAll() {
        var salida = new ArrayList<Item>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Item> query = entityManager.createQuery("from Item", Item.class);
            salida = (ArrayList<Item>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public Item get(Long id) {
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
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            try {
                // Comenzar la transacción
                entityManager.getTransaction().begin();

                // Guardar el nuevo pedido en la base de datos
                entityManager.persist(data);

                // Commit de la transacción
                entityManager.getTransaction().commit();
            } finally {
                entityManager.close();
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Actualiza un ítem en la base de datos (no implementado en este contexto).
     *
     * @param data Datos del ítem a actualizar.
     */
    @Override
    public Pedido update(Item data) {
        return null;

    }
    /**
     * Elimina un ítem de la base de datos.
     *
     * @param data Datos del ítem a eliminar.
     */
    @Override
    public boolean delete(Item data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {

            try {
                entityManager.getTransaction().begin();

                if(!entityManager.contains(data)){
                    data = entityManager.merge(data);
                }
                entityManager.remove(data);
                entityManager.getTransaction().commit();

            } catch (Exception e) {
                // En caso de error, realiza un rollback
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
            }finally {
                entityManager.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
