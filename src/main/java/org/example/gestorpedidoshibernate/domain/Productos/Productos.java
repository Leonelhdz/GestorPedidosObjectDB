package org.example.gestorpedidoshibernate.domain.Productos;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Representa la entidad Productos en el sistema.
 * Esta clase se utiliza para mapear la tabla "productos" en la base de datos.
 */
@Data
@Entity
@Table(name = "productos")
public class Productos implements Serializable {
    /**
     * Identificador único para cada producto, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    /**
     * Nombre del producto.
     */
    @Column(name = "NombreProducto")
    private String NombreProducto;
    /**
     * Precio del producto.
     */
    @Column(name = "Precio")
    private Double Precio;
    /**
     * Cantidad disponible del producto.
     */
    @Column(name = "CantDisponible")
    private Integer CantDisponible;
    /**
     * Devuelve una representación en cadena del producto (en este caso, el nombre del producto).
     *
     * @return Nombre del producto.
     */
    @Override
    public String toString() {
        return NombreProducto;
    }

}
