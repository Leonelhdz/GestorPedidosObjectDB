package org.example.gestorpedidoshibernate.domain.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// La anotación @Data de Lombok genera automáticamente los métodos equals, hashCode, toString, getters y setters.

/**
 * Representa la entidad Usuario en el sistema.
 * Esta clase se utiliza para mapear la tabla "usuarios" en la base de datos.
 */
@Data
@Entity
@NoArgsConstructor
public class Usuario implements Serializable {

    public Usuario(String nombre, String contrasena, String email, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.pedidos = pedidos;
    }

    /**
     * Identificador único para cada usuario, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Contraseña del usuario.
     */
    private String contrasena;

    /**
     * Dirección de correo electrónico del usuario.
     */
    private String email;

    /**
     * Variable transitoria que no será persistida en la base de datos.
     */
    @Transient
    private Long pedidosQuantity;

    /**
     * Relación uno a muchos con la entidad Pedido, mapeada por el campo "usuario".
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>();

    /**
     * Calcula la cantidad de pedidos asociados al usuario.
     *
     * @return La cantidad de pedidos asociados al usuario.
     */
    public Long getPedidosQuantity() {
        pedidosQuantity = (long) pedidos.size();
        return pedidosQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nombre, usuario.nombre) && Objects.equals(contrasena, usuario.contrasena) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, contrasena, email);
    }

}
