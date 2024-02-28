package org.example.gestorpedidoshibernate.domain.Productos;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Representa la entidad Productos en el sistema.
 * Esta clase se utiliza para mapear la tabla "productos" en la base de datos.
 */
@Data
@Entity
@NoArgsConstructor
public class Productos implements Serializable {

    public Productos(String nombreProducto, Double precio, Integer cantDisponible) {
        NombreProducto = nombreProducto;
        Precio = precio;
        CantDisponible = cantDisponible;
    }

    /**
     * Identificador único para cada producto, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    /**
     * Nombre del producto.
     */
    private String NombreProducto;
    /**
     * Precio del producto.
     */
    private Double Precio;
    /**
     * Cantidad disponible del producto.
     */
    private Integer CantDisponible;


    /**
     * Devuelve una representación en cadena del producto (en este caso, el nombre del producto).
     *
     * @return Nombre del producto.
     */

    @Override
    public String toString() {
        return "Productos{" +
                "Id=" + Id +
                ", NombreProducto='" + NombreProducto + '\'' +
                ", Precio=" + Precio +
                ", CantDisponible=" + CantDisponible +
                '}';
    }
}
