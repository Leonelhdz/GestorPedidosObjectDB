package org.example.gestorpedidoshibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gestorpedidoshibernate.domain.Productos.Productos;
import org.example.gestorpedidoshibernate.domain.Productos.ProductosDAO;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;
import org.example.gestorpedidoshibernate.domain.Usuario.UsuarioDAO;

import java.io.IOException;
import java.util.List;

/**
 * Clase principal de la aplicación que extiende Application de JavaFX.
 */
public class App extends Application {
    // Instancia única de la ventana principal (patrón Singleton).
    public static Stage mystage;

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * Método llamado cuando se inicia la aplicación.
     *
     * @param stage La ventana principal de la aplicación.
     * @throws IOException Si hay un error al cargar el archivo FXML de la escena inicial.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try{
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDAO.getAll();

            //Verifica si ya existen usuarios en la Base de Datos.
            if (usuarios.isEmpty()) {
                usuarios = Data.generarUsuarios();
                usuarioDAO.saveAll(usuarios);
            }

            ProductosDAO productoDAO = new ProductosDAO();
            List<Productos> productos = productoDAO.getAll();

            //Verifica si ya existen productos en la Base de Datos.
            if (productos.isEmpty()) {
                productos = Data.generarProductos();
                productoDAO.saveAll(productos);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        this.mystage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        mystage.setTitle("Login");
        mystage.setScene(scene);
        mystage.show();
    }

    public static void ventanaPrincipal(String fxml,String title){ //ventanaPrincipal
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            mystage.setTitle(title);
            mystage.setScene(scene);
            mystage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el FXML");
            throw new RuntimeException(e);
        }
    }

    public static void ventanaDatos(String fxml){ //ventana datos
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            mystage.setTitle("Ventana Pedidos");
            mystage.setScene(scene);
            mystage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el FXML");
            throw new RuntimeException(e);
        }
    }
}
