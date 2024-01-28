module com.example.GestorPedidosHibernate {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jasperreports;


    opens org.example.gestorpedidoshibernate.domain.Usuario;
    opens org.example.gestorpedidoshibernate.domain.Pedido;
    opens org.example.gestorpedidoshibernate.domain.Productos;
    opens org.example.gestorpedidoshibernate.domain.Items;

    opens org.example.gestorpedidoshibernate to javafx.fxml;
    opens org.example.gestorpedidoshibernate.controllers to javafx.fxml;

    exports org.example.gestorpedidoshibernate;
    exports org.example.gestorpedidoshibernate.controllers;

}