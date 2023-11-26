package org.example.gestorpedidoshibernate.domain.Productos;

import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

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
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Query<Productos> query = sesion.createQuery("from Productos", Productos.class);
            salida = (ArrayList<Productos>) query.getResultList();
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
    public Productos get(Integer id) {
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
    public void update(Productos data) {
    }
    /**
     * Método de eliminación que no está implementado en este contexto.
     *
     * @param data Datos del producto a eliminar.
     */
    @Override
    public void delete(Productos data) {

    }
}
