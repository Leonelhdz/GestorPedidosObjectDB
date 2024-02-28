package org.example.gestorpedidoshibernate;

import org.example.gestorpedidoshibernate.domain.Productos.Productos;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<Productos> generarProductos(){
        List<Productos> productos = new ArrayList<>();
        productos.add(new Productos("Smartphone", 299.00, 50));
        productos.add(new Productos("Portátil", 799.00, 30));
        productos.add(new Productos("Auriculares", 79.00, 100));
        productos.add(new Productos("Televisor", 599.00, 20));
        productos.add(new Productos("Tablet", 199.00, 40));
        productos.add(new Productos("PC Sobremesa",950.00,65));
        return productos;
    }

    public static List<Usuario> generarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Carlos","12345","carlos@cesurformacion.com",new ArrayList<>()));
        return usuarios;
    }
}
