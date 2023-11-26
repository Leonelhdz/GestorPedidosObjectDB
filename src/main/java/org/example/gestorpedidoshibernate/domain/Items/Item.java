package org.example.gestorpedidoshibernate.domain.Items;

import jakarta.persistence.*;
import lombok.Data;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Productos.Productos;

import java.io.Serializable;

/**
 * Representa la entidad Item en el sistema.
 * Esta clase se utiliza para mapear la tabla "itemspedido" en la base de datos.
 */
@Data
@Entity
@Table(name = "itemspedido")
public class Item implements Serializable {
    /**
     * Identificador único para cada ítem, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Pedido asociado al ítem mediante una relación muchos a uno.
     */
    @ManyToOne
    @JoinColumn(name = "CodigoPedido", referencedColumnName = "Codigo")
    private Pedido codigo;
    /**
     * Cantidad del producto en el ítem.
     */
    @Column(name = "Cantidad")
    private Integer cantidad;
    /**
     * Producto asociado al ítem mediante una relación uno a uno.
     */
    @OneToOne
    @JoinColumn(name = "ProductoId")
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

