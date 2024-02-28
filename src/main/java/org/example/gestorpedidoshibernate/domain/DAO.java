package org.example.gestorpedidoshibernate.domain;

import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;

import java.util.ArrayList;

/**
 * Interfaz genérica para un objeto de acceso a datos (DAO) que proporciona operaciones CRUD básicas.
 *
 * @param <T> Tipo de entidad que manejará el DAO.
 */
public interface DAO<T> {
    /**
     * Recupera todos los elementos de la entidad almacenados en la base de datos.
     *
     * @return Lista de todos los elementos de la entidad.
     */
    public ArrayList<T> getAll();

    /**
     * Recupera un elemento de la entidad por su identificador único.
     *
     * @param id Identificador único del elemento.
     * @return El elemento de la entidad correspondiente al identificador proporcionado, o null si no se encuentra.
     */
    public T get(Long id);

    /**
     * Guarda un nuevo elemento de la entidad en la base de datos.
     *
     * @param data El elemento de la entidad que se va a guardar.
     * @return El elemento de la entidad después de ser guardado, incluido su posible identificador único asignado por la base de datos.
     */
    public T save(T data);

    /**
     * Actualiza un elemento existente de la entidad en la base de datos.
     *
     * @param data El elemento de la entidad que se va a actualizar.
     */
    public Pedido update(T data);

    /**
     * Elimina un elemento existente de la entidad de la base de datos.
     *
     * @param data El elemento de la entidad que se va a eliminar.
     */
    public boolean delete(T data);
}
