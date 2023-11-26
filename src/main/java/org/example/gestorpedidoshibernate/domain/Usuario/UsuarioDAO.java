package org.example.gestorpedidoshibernate.domain.Usuario;

import org.example.gestorpedidoshibernate.domain.DAO;
import org.example.gestorpedidoshibernate.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta HQL para obtener un Usuario por nombre de usuario y contraseña.
            Query<Usuario> q = session.createQuery("from Usuario where nombre=:n and contrasena=:c", Usuario.class);
            q.setParameter("n", user);
            q.setParameter("c", pass);

            try {
                // Intenta obtener un único resultado (debería ser único, ya que se espera un solo usuario).
                result = q.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta HQL para obtener todos los usuarios.
            Query<Usuario> q = s.createQuery("from Usuario", Usuario.class);
            salida = (ArrayList<Usuario>) q.getResultList();
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
    public Usuario get(Integer id) {
        var salida = new Usuario();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            // Obtiene un usuario por su identificador único.
            salida = s.get(Usuario.class, id);
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
    public void update(Usuario data) {

    }

    /**
     * Método de eliminación que no está implementado en este contexto.
     *
     * @param data Datos del usuario a eliminar.
     */
    @Override
    public void delete(Usuario data) {

    }

}
