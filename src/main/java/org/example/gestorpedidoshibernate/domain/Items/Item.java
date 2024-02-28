package org.example.gestorpedidoshibernate.domain.Items;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Productos.Productos;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Representa la entidad Item en el sistema.
 * Esta clase se utiliza para mapear la tabla "itemspedido" en la base de datos.
 */
@Data
@Entity
@NoArgsConstructor
public class Item implements Serializable {
    /**
     * Identificador único para cada ítem, generado automáticamente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Pedido asociado al ítem mediante una relación muchos a uno.
     */
    @ManyToOne
    private Pedido codigo;
    /**
     * Cantidad del producto en el ítem.
     */

    private int cantidad;
    /**
     * Producto asociado al ítem mediante una relación uno a uno.
     */
    @OneToOne
    private Productos productos;
    /**
     * Representación en cadena del ítem.
     *
     * @return Una cadena que representa el ítem.
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", codigoPedido='" + codigo.getCodigo() + '\'' +
                ", cantidad=" + cantidad + '\'' +
                ", ProductoId=" + productos +
                '}';
    }
}

