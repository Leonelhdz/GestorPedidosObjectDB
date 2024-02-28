package org.example.gestorpedidoshibernate.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.example.gestorpedidoshibernate.App;
import org.example.gestorpedidoshibernate.Session;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Pedido.PedidoDAO;
import org.example.gestorpedidoshibernate.domain.Usuario.Usuario;
import org.example.gestorpedidoshibernate.domain.Usuario.UsuarioDAO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controlador para la pantalla de listado de pedidos.
 */
public class ListadoPedidosController implements Initializable {
    // Elementos de la interfaz de usuario
    @javafx.fxml.FXML
    private Label labelTotal;
    @javafx.fxml.FXML
    private Label Info;
    @javafx.fxml.FXML
    private TableView<Pedido> tabla;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> columnId;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> columnCodigo;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> columnFecha;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> columnUsuarioId;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> columnTotal;
    @javafx.fxml.FXML
    private Label labelCorreo;
    @javafx.fxml.FXML
    private Button buttonSalir;
    @javafx.fxml.FXML
    private Button buttonNuevoPedido;
    @javafx.fxml.FXML
    private Button buttonBorrarPedido;
    @javafx.fxml.FXML
    private Label titulo2;
    @javafx.fxml.FXML
    private ImageView informacion;

    // Lista observable para la tabla
    private ObservableList<Pedido> observableList;

    // Objeto DAO para acceder a la base de datos de pedidos
    private PedidoDAO pedidoDAO = new PedidoDAO();


    /**
     * Inicializa el controlador después de que se ha cargado el archivo FXML.
     *
     * @param url            Ubicación utilizada para resolver rutas relativas para el objeto root o null si no se conoce.
     * @param resourceBundle El recurso de localización utilizado para localizar el objeto root o null si no se necesita.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuración de las columnas de la tabla
        columnId.setCellValueFactory((fila) -> {
            String id = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(id);
        });

        columnCodigo.setCellValueFactory((fila) -> {
            String cPedido = fila.getValue().getCodigo();
            return new SimpleStringProperty(cPedido);
        });

        columnFecha.setCellValueFactory((fila) -> {
            String cFecha = String.valueOf(fila.getValue().getFecha());
            return new SimpleStringProperty(cFecha);
        });

        columnUsuarioId.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getUsuario().getNombre() + "");
        });

        columnTotal.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getTotal() + "");
        });

        // Inicialización de la lista observable y carga de datos
        observableList = FXCollections.observableArrayList();

        Session.setCurrentUsuario((new UsuarioDAO().get(Session.getCurrentUsuario().getId())));

        cargarLista();

        // Configuración de escuchadores de eventos en la tabla
        tabla.getSelectionModel().selectedItemProperty().addListener((observableValue, pedido, t1) -> {
            Session.setCurrentPedido(t1);
        });

        tabla.setOnMouseClicked(event -> {
            // Manejo de eventos al hacer clic en un pedido
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Pedido selectedPedido = tabla.getSelectionModel().getSelectedItem();
                if (selectedPedido != null) {
                    Session.setCurrentPedido(selectedPedido);
                    App.ventanaDatos("/views/listado-items-view.fxml");
                }

            }
        });
        // Actualización de etiquetas con información del usuario
        labelTotal.setText("El total de tus pedidos es : " + Session.getCurrentUsuario().getPedidosQuantity() + " pedidos");
        Info.setText("Bienvenido a tu lista de pedidos " + Session.getCurrentUsuario().getNombre());
        labelCorreo.setText("Correo de usuario : " + Session.getCurrentUsuario().getEmail());

    }

    /**
     * Carga la lista de pedidos del usuario actual en la tabla.
     */
    private void cargarLista() {
        observableList.setAll(Session.getCurrentUsuario().getPedidos());

        for (Pedido pedido : observableList) {
            Double totalPedido = calcularTotalPedido(pedido);
            pedido.setTotal(totalPedido);
        }
        tabla.setItems(observableList);
    }
    /**
     * Calcula el total de un pedido sumando los precios de los productos en los ítems.
     *
     * @param pedido Pedido para el cual calcular el total.
     * @return Total del pedido.
     */
    private Double calcularTotalPedido(Pedido pedido) {
        Double total = 0.0;
        for (Item item : pedido.getItems()) {
            total += item.getProductos().getPrecio() * item.getCantidad();
        }
        return total;
    }

    /**
     * Maneja el evento cuando se presiona el botón "Salir".
     *
     * @param actionEvent Evento de acción.
     */
    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonSalir.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja el evento cuando se presiona el botón "Nuevo Pedido".
     *
     * @param actionEvent Evento de acción.
     * @throws IOException Si ocurre un error al cambiar la escena.
     */
    @javafx.fxml.FXML
    public void nuevoPedidoVacio(ActionEvent actionEvent){
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setCodigo(pedidoDAO.getUltimoCodigo());
        nuevoPedido.setFecha(new Date());
        nuevoPedido.setUsuario(Session.getCurrentUsuario());
        nuevoPedido.setItems(new ArrayList<>());
        nuevoPedido.setTotal(0.0);

        // Obtener la lista actual de pedidos de la tabla
        ObservableList<Pedido> pedidosActuales = tabla.getItems();

        // Agregar el nuevo pedido a la lista
        pedidosActuales.add(nuevoPedido);

        // Establecer la lista actualizada de pedidos en la tabla
        tabla.setItems(pedidosActuales);

        // Guardar el nuevo pedido en la base de datos
        PedidoDAO pedidoDAO = new PedidoDAO();
        pedidoDAO.save(nuevoPedido);

        // Actualizar el usuario actual en SessionData
        Session.setCurrentUsuario((new UsuarioDAO().get(Session.getCurrentUsuario().getId())));
    }

    /**
     * Maneja el evento cuando se presiona el botón "Borrar Pedido".
     *
     * @param actionEvent Evento de acción.
     */
    @javafx.fxml.FXML
    public void borrarPedido(ActionEvent actionEvent) {
        //Se coge el pedido seleccionado.
        Pedido pedidoSeleccionado = tabla.getSelectionModel().getSelectedItem();

        //Confirmación de eliminación mediante un diálogo de confirmación.
        if (pedidoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Deseas borrar el pedido: " + pedidoSeleccionado.getCodigo() + "?");
            var result = alert.showAndWait().get();

            //Si se confirma la eliminación, se borra el ítem seleccionado de la lista y de la base de datos.
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                pedidoDAO.delete(pedidoSeleccionado);
                observableList.remove(pedidoSeleccionado);
            }
        } else {
            //Muestra un mensaje de error o advertencia al usuario si no se ha seleccionado ningún pedido para eliminar.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un pedido para eliminar.");
            alert.showAndWait();
        }
    }

    /**
     * Muestra una ventana de información.
     *
     * @param event Evento.
     */
    @javafx.fxml.FXML
    public void mostrarventana(Event event) {
        // Lógica para mostrar la ventana emergente
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("La ventana muestra el listado de todos tus pedidos");
        alert.showAndWait();
    }
}

