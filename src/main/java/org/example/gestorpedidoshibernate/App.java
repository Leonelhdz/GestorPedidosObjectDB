package org.example.gestorpedidoshibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación que extiende Application de JavaFX.
 */
public class App extends Application {
    // Instancia única de la ventana principal (patrón Singleton).
    private static Stage stage;

    /**
     * Método estático para cambiar entre escenas.
     *
     * @param fxml   El archivo FXML de la nueva escena.
     * @param tittle El título de la ventana.
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    public static void changeScene(String fxml, String tittle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/" + fxml));
        Scene scene = new Scene(fxmlLoader.load(), 823, 605);
        stage.setScene(scene);
        stage.setTitle(tittle);
        stage.show();
    }

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
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }
}
