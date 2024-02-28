package org.example.gestorpedidoshibernate;

import lombok.Getter;
import lombok.Setter;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Productos.Productos;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;

/**
 * Clase de sesión que almacena información sobre la sesión actual.
 */
public class Session {
    // Usuario actual en la sesión.
    @Getter
    @Setter
    private static Usuario currentUsuario;
    // Pedido actual en la sesión.
    @Getter
    @Setter
    private static Pedido currentPedido;
    // Ítem actual en la sesión.
    @Getter
    @Setter
    private static Item currentItem;

    @Getter
    @Setter
    private static Productos currentProducto;


}
