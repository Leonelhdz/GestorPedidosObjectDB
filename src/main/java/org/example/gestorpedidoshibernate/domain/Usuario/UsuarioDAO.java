package org.example.gestorpedidoshibernate.domain.Usuario;

import org.example.gestorpedidoshibernate.ObjectDBUtil;
import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz DAO para la entidad Usuario.
 * Proporciona métodos para interactuar con la base de datos relacionados con la entidad Usuario.
 */
public class UsuarioDAO implements DAO<Usuario> {

    /**
     * Valida las credenciales de un usuario en la base de datos.
     *
     * @param user Nombre de usuario.
     * @param pass Contraseña del usuario.
     * @return El usuario validado o null si no se encuentra.
     */
    public Usuario validateUser(String user, String pass) {
        Usuario result = null;
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Usuario> q = entityManager.createQuery("select u from Usuario u where u.nombre=:u and u.contrasena=:p", Usuario.class);
            q.setParameter("u", user);
            q.setParameter("p", pass);

            try {
                result = q.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } finally {

        }
        return result;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return Lista de todos los usuarios en la base de datos.
     */
    @Override
    public ArrayList<Usuario> getAll() {
        var salida = new ArrayList<Usuario>(0);
        EntityManager s = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Usuario> query = s.createQuery("Select u from Usuario u", Usuario.class);
            salida = (ArrayList<Usuario>) query.getResultList();
        }finally {

        }
        return salida;
    }

    /**
     * Obtiene un usuario por su identificador único.
     *
     * @param id Identificador único del usuario.
     * @return El usuario con el identificador especificado.
     */
    @Override
    public Usuario get(Long id) {
        Usuario salida = null;
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u where u.id = :id", Usuario.class);
            query.setParameter("id", id);
            var resultado = query.getResultList();
            if (resultado.size() > 0) {
                salida = resultado.get(0);
            }
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * Método de guardado que no está implementado en este contexto.
     *
     * @param data Datos del usuario a guardar.
     * @return El usuario guardado.
     */
    @Override
    public Usuario save(Usuario data) {
        return null;
    }

    /**
     * Método de actualización que no está implementado en este contexto.
     *
     * @param data Datos del usuario a actualizar.
     */
    @Override
    public Pedido update(Usuario data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Utiliza el método merge para actualizar la entidad en la base de datos.
            data = entityManager.merge(data);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // Maneja la excepción adecuadamente (puede imprimir o lanzar una excepción personalizada).
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return null;
    }

    /**
     * Método de eliminación que no está implementado en este contexto.
     *
     * @param data Datos del usuario a eliminar.
     */
    @Override
    public boolean delete(Usuario data) {
        return false;
    }

    public Usuario getUserByEmail(String email) {
        Usuario result = null;
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try  {
            TypedQuery<Usuario> query = entityManager.createQuery("Select u from Usuario u where u.email=:e", Usuario.class);
            query.setParameter("e", email);

            try {
                result = query.getSingleResult();
            } catch (NoResultException e) {
                // No se encontró ningún usuario con ese correo electrónico
                System.out.println("Usuario no encontrado para el correo electrónico: " + email);
            } catch (Exception e) {
                // Otra excepción
                System.out.println("Error al buscar usuario por correo electrónico: " + e.getMessage());
            }
        }finally {

        }
        return result;
    }

    public void saveAll(List<Usuario> data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            for (Usuario u : data) {
                entityManager.persist(u);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

}
