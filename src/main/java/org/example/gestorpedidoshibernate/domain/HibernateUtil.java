package org.example.gestorpedidoshibernate.domain;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase utilitaria para gestionar la creación y obtención de la SessionFactory de Hibernate.
 */
@Log
public class HibernateUtil {
    // Se utiliza una única instancia de SessionFactory para toda la aplicación.
    private static SessionFactory sf;
    // Bloque estático que se ejecuta al cargar la clase.
    static {
        try {
            // Configuración de Hibernate desde el archivo hibernate.cfg.xml por defecto.
            Configuration cfg = new Configuration();
            cfg.configure();
            // Creación de la SessionFactory.
            sf = cfg.buildSessionFactory();
            log.info("SessionFactory creada con exito!");
        } catch (Exception e) {
            // Captura de excepciones durante la creación de la SessionFactory.
            log.severe("Error al crear SessionFactoty()");
        }
    }

    /**
     * Obtiene la instancia única de la SessionFactory.
     *
     * @return La SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sf;
    }
}
