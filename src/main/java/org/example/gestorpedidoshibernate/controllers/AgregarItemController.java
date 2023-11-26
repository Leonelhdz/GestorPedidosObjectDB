package org.example.gestorpedidoshibernate.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.gestorpedidoshibernate.App;
import org.example.gestorpedidoshibernate.Session;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.example.gestorpedidoshibernate.domain.Items.ItemDAO;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Productos.Productos;
import org.example.gestorpedidoshibernate.domain.Productos.ProductosDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Agregar item controller.
 */
public class AgregarItemController implements Initializable {

    /**
     * Spinner con la cantidad de un mismo producto que se va añadir al pedido.
     */
    @FXML
    private Spinner<Integer> spCantidad;

    /**
     * Combo Box que contiene todos los productos que se pueden agregar a un item y por consiguiente a un pedido.
     */
    @FXML
    private ComboBox<Productos> comboProducto;

    /**
     * Observable que contiene una lista de todos los productos disponibles, se usa para añadirlos al Combo Box.
     */
    private ObservableList<Productos> observableListProductos;

    /**
     * Label que muestra la cantidad disponible que hay en un producto al seleccionarlo en el Combo Box.
     */
    @FXML
    private Label lbInfoCantidad;
    @FXML
    private Button buttonAnterior;
    @FXML
    private Button buttonAceptar;
    @FXML
    private Label labeltitle;

    /**
     * Inicializa el controlador y establece los valores iniciales.
     *
     * @param url            La URL de inicialización.
     * @param resourceBundle El ResourceBundle utilizado para la inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Observable que se usará para gestionar una lista de productos disponibles en un Combo Box.
        observableListProductos = FXCollections.observableArrayList();
        //Se crea un nuevo ProductoDAO.
        ProductosDAO productoDAO = new ProductosDAO();
        //Se rellena el Observable con todos los productos.
        observableListProductos.setAll(productoDAO.getAll());
        //Se carga el Combo Box con el Observable.
        comboProducto.setItems(observableListProductos);
        //Se selecciona el primer producto del Combo Box por defecto.
        comboProducto.getSelectionModel().selectFirst();
        //Se establece el Spinner para que solo pueda llegar hasta 100 con paso 1, teniendo como predeterminado el 1.
        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1));
        //Muestra la cantidad disponible que hay de cada producto seleccionado.
        lbInfoCantidad.setText("Cantidad disponible: " + comboProducto.getSelectionModel().getSelectedItem().getCantDisponible());
        comboProducto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lbInfoCantidad.setText("Cantidad disponible: " + newValue.getCantDisponible());
            }
        });
    }

    /**
     * Método para agregar un ítem al pedido actual.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     * @throws IOException the io exception
     */
    @Deprecated
    public void aceptar(ActionEvent actionEvent) throws IOException {

        //Se crea una instancia de Pedido con el pedido actual de la sesión.
        Pedido pedido = Session.getCurrentPedido();

        //Si el pedido es distinto de nulo se crea un nuevo item para ese pedido  y se retorna a la ventana de DetallesPedidoController.
        Productos productoSeleccionado = comboProducto.getSelectionModel().getSelectedItem();
        Integer cantidadAgregada = spCantidad.getValue();
        Integer cantidadDisponible = productoSeleccionado.getCantDisponible();

        if (cantidadAgregada > cantidadDisponible) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cantidad excedida");
            alert.setHeaderText("No se puede agregar esa cantidad");
            alert.setContentText("Cantidad de producto disponible: " + productoSeleccionado.getCantDisponible());
            alert.showAndWait();
        } else {
            Item item = new Item();
            item.setCodigo(pedido);
            item.setCantidad(spCantidad.getValue());
            item.setProductos(productoSeleccionado);

            Session.setCurrentItem((new ItemDAO()).save(item));
            Session.setCurrentItem(item);

            App.changeScene("listado-items-view.fxml", "Detalle Items");
        }
    }

    /**
     * Método para cerrar la sesión actual y cargar la pantalla de inicio de sesión.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     * @throws IOException the io exception
     */
    @Deprecated
    public void logOut(ActionEvent actionEvent) throws IOException {
        //Se settea el usuario actual a null y vuelve al LoginController.
        Session.setCurrentUsuario(null);
        App.changeScene("login-view.fxml", "Login");
    }

    /**
     * Método para mostrar información "Acerca de".
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     */
    @Deprecated
    public void mostrarAcercaDe(ActionEvent actionEvent) {
        // Muestra información "Acerca de" en una ventana de diálogo.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de: ");
        alert.setHeaderText("Creado por: ");
        alert.setContentText("Francisco Leonel Soriano, 2ºDAM");
        alert.showAndWait();
    }

    /**
     * Método para volver atrás.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     * @throws IOException the io exception
     */
    @Deprecated
    public void volverAtrás(ActionEvent actionEvent) throws IOException {
        //Vuelve a la pantalla inmediatamente anterior.
        App.changeScene("listado-items-view.fxml", "Detalle Items");
    }

}

