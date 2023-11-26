package org.example.gestorpedidoshibernate.domain.Usuario;

import jakarta.persistence.*;
import lombok.Data;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
// La anotación @Data de Lombok genera automáticamente los métodos equals, hashCode, toString, getters y setters.

/**
 * Representa la entidad Usuario en el sistema.
 * Esta clase se utiliza para mapear la tabla "usuarios" en la base de datos.
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    /**
     * Identificador único para cada usuario, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del usuario.
     */
    @Column(name = "Nombre")
    private String nombre;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "Contraseña")
    private String contrasena;

    /**
     * Dirección de correo electrónico del usuario.
     */
    @Column(name = "Email")
    private String email;

    /**
     * Variable transitoria que no será persistida en la base de datos.
     */
    @Transient
    private Integer pedidosQuantity;

    /**
     * Relación uno a muchos con la entidad Pedido, mapeada por el campo "usuario".
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>(0);

    /**
     * Calcula la cantidad de pedidos asociados al usuario.
     *
     * @return La cantidad de pedidos asociados al usuario.
     */
    public Integer getPedidosQuantity() {
        pedidosQuantity = (int) pedidos.size();
        return pedidosQuantity;
    }
}
