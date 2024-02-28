package org.example.gestorpedidoshibernate.domain.Pedido;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Representa la entidad Pedido en el sistema.
 * Esta clase se utiliza para mapear la tabla "pedidos" en la base de datos.
 */
@Data
@Entity
@NoArgsConstructor
public class Pedido implements Serializable {

    public Pedido(Long id, String codigo, Date fecha, Double total, Usuario usuario) {
        this.id = id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.total = total;
        this.usuario = usuario;
    }

    /**
     * Identificador único para cada pedido, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Código único asociado al pedido.
     */
    private String codigo;
    /**
     * Fecha en que se realizó el pedido.
     */
    private Date fecha;
    /**
     * Total del pedido.
     */
    private Double total;
    /**
     * Usuario asociado al pedido mediante una relación muchos a uno.
     */
    @ManyToOne
    private Usuario usuario;
    /**
     * Lista de ítems asociados al pedido mediante una relación uno a muchos.
     */
    @OneToMany(mappedBy = "codigo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
    /**
     * Representación en cadena del pedido.
     *
     * @return Una cadena que representa el pedido.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) && Objects.equals(codigo, pedido.codigo) && Objects.equals(fecha, pedido.fecha) && Objects.equals(total, pedido.total) && Objects.equals(usuario, pedido.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, fecha, total, usuario);
    }
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", fecha= " + fecha +
                ", total=" + total +
                ", usuario=" + usuario.getId() +
                ", items=" + items +
                '}';
    }

}
