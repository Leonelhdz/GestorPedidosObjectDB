package org.example.gestorpedidoshibernate;

import lombok.Getter;
import lombok.extern.java.Log;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
@Log
public class ObjectDBUtil {
    @Getter
    private final static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("data.odb");
    }
}
