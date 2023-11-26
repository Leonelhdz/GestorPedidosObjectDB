package org.example.gestorpedidoshibernate.domain.Pedido;

import jakarta.persistence.*;
import lombok.Data;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad Pedido en el sistema.
 * Esta clase se utiliza para mapear la tabla "pedidos" en la base de datos.
 */
@Data
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
    /**
     * Identificador único para cada pedido, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    /**
     * Código único asociado al pedido.
     */
    @Column(name = "Codigo")
    private String codigo;
    /**
     * Fecha en que se realizó el pedido.
     */
    @Column(name = "Fecha")
    private String fecha;
    /**
     * Total del pedido.
     */
    @Column(name = "Total")
    private Double total;
    /**
     * Usuario asociado al pedido mediante una relación muchos a uno.
     */
    @ManyToOne
    @JoinColumn(name = "Usuario", referencedColumnName = "Id")
    private Usuario usuario;
    /**
     * Lista de ítems asociados al pedido mediante una relación uno a muchos.
     */
    @OneToMany(mappedBy = "codigo", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Item> items = new ArrayList<>();
    /**
     * Representación en cadena del pedido.
     *
     * @return Una cadena que representa el pedido.
     */
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + Id +
                ", codigo='" + codigo + '\'' +
                ", fecha= " + fecha + '\'' +
                ", total=" + total +
                ", usuario=" + usuario.getId() +
                ", items=" + items +
                '}';
    }

}
