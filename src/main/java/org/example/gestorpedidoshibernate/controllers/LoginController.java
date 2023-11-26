package org.example.gestorpedidoshibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.gestorpedidoshibernate.App;
import org.example.gestorpedidoshibernate.Session;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;
import org.example.gestorpedidoshibernate.domain.Usuario.UsuarioDAO;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la pantalla de inicio de sesión.
 */
public class LoginController implements Initializable {
    // Elementos de la interfaz de usuario
    @FXML
    private TextField nombreUsuarioField;
    @FXML
    private Button buttonIngresar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Label infoLabel;
    @FXML
    private Label titulo1;
    @FXML
    private Label titulo;
    /**
     * Inicializa el controlador después de que se ha cargado el archivo FXML.
     *
     * @param url            Ubicación utilizada para resolver rutas relativas para el objeto root o null si no se conoce.
     * @param resourceBundle El recurso de localización utilizado para localizar el objeto root o null si no se necesita.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Imprime información sobre los campos y anotaciones de la clase Pedido
        for (Field f : Pedido.class.getDeclaredFields()) {
            System.out.println(f.getName() + ":" + f.getType().getName());
            for (var a : f.getDeclaredAnnotations()) {
                System.out.println(a.toString());
            }
        }

    }

    /**
     * Maneja el evento cuando se presiona el botón "Cancelar".
     *
     * @param event Evento de acción.
     */
    @FXML
    public void buttonCancelarOnAction(ActionEvent event) {
        // Cierra la ventana
        Stage stage = (Stage) buttonCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja el evento cuando se presiona el botón "Ingresar".
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void buttonIngresarOnAction(ActionEvent actionEvent) {
        // Obtiene el nombre de usuario y la contraseña de los campos de texto
        String user = nombreUsuarioField.getText();
        String pass = contrasenaField.getText();

        // Validación de longitud mínima de nombre de usuario y contraseña
        if (user.length() < 4 || pass.length() < 4) {
            infoLabel.setText("Introduce los datos de usuario");
            infoLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        } else {
            // Intenta validar al usuario utilizando la base de datos
            Usuario u = (new UsuarioDAO()).validateUser(user, pass);
            if (u == null) {
                infoLabel.setText("Uusuario no encontrado");
                infoLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            } else {
                infoLabel.setText("Uusuario " + user + "(" + pass + ") correcto");
                infoLabel.setStyle("-fx-background-color:green; -fx-text-fill: white;");
                // Establece el usuario actual en la sesión
                Session.setCurrentUsuario(u);

                try {
                    // Cambia a la escena de listado de pedidos
                    App.changeScene("listado-pedido-view.fxml", "Listado Pedidos");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
