package org.example.gestorpedidoshibernate.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import org.example.gestorpedidoshibernate.App;
import org.example.gestorpedidoshibernate.Session;
import org.example.gestorpedidoshibernate.domain.HibernateUtil;
import org.example.gestorpedidoshibernate.domain.Items.Item;
import org.example.gestorpedidoshibernate.domain.Items.ItemDAO;
import org.example.gestorpedidoshibernate.domain.Pedido.Pedido;
import org.example.gestorpedidoshibernate.domain.Pedido.PedidoDAO;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controlador para la pantalla de listado de ítems de un pedido.
 */
public class ListadoItemsController implements Initializable {

    @javafx.fxml.FXML
    private TableView<Item> tablaDetallespedido;
    @javafx.fxml.FXML
    private Button buttonAnterior;
    @javafx.fxml.FXML
    private TableColumn<Item, String> columnId;
    @javafx.fxml.FXML
    private TableColumn<Item, String> columnCodPedido;
    @javafx.fxml.FXML
    private TableColumn<Item, String> columnCantidad;
    @javafx.fxml.FXML
    private TableColumn<Item, String> columnIdProducto;
    @javafx.fxml.FXML
    private Button buttonBorrar;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Label labelCorreo1;
    @FXML
    private Button buttonSalir;
    @FXML
    private Label titulo3;
    @FXML
    private Button buttonAgregarItem;

    // Objeto DAO para acceder a la base de datos de ítems
    private ItemDAO itemDAO = new ItemDAO();

    // Lista observable para la tabla de ítems
    private ObservableList<Item> observableList;
    @FXML
    private Button buttonImprimir;

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
            String columnId = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(columnId);
        });

        columnCodPedido.setCellValueFactory((fila) -> {
            String columnCodPedido = String.valueOf(fila.getValue().getCodigo().getCodigo());
            return new SimpleStringProperty(columnCodPedido);
        });

        columnCantidad.setCellValueFactory((fila) -> {
            String columnCantidad = String.valueOf(fila.getValue().getCantidad());
            return new SimpleStringProperty(columnCantidad);
        });

        columnIdProducto.setCellValueFactory((fila) -> {
            String columnIdProducto = String.valueOf(fila.getValue().getProductos().getNombreProducto() + "/" + fila.getValue().getProductos().getPrecio() + "€");
            return new SimpleStringProperty(columnIdProducto);
        });

        // Inicialización de la lista observable y carga de datos
        observableList = FXCollections.observableArrayList();
        Session.setCurrentPedido(new PedidoDAO().get(Session.getCurrentPedido().getId()));
        observableList.setAll(Session.getCurrentPedido().getItems());
        tablaDetallespedido.setItems(observableList);

        // Actualización de la interfaz gráfica con información del pedido actual
        actualizarPedido();

        labelCorreo1.setText(Session.getCurrentUsuario().getEmail());

    }

    /**
     * Maneja el evento cuando se presiona el botón "Salir".
     *
     * @param actionEvent Evento de acción.
     */
    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Session.setCurrentUsuario(null);
        try {
            App.changeScene("login-view.fxml", "Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Maneja el evento cuando se presiona el botón "Retroceder".
     *
     * @param actionEvent Evento de acción.
     */
    @javafx.fxml.FXML
    public void retroceder(ActionEvent actionEvent) {
        try {
            App.changeScene("listado-pedido-view.fxml", "Listado de Pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Muestra información "Acerca de" en una ventana de diálogo.
     *
     * @param actionEvent Evento de acción.
     * @deprecated Este método está marcado como obsoleto (deprecated) y se debería evitar su uso futuro. Se recomienda utilizar métodos alternativos para mostrar información "Acerca de".
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
     * Maneja el evento cuando se presiona el botón "Añadir Item".
     *
     * @param actionEvent Evento de acción.
     * @throws IOException Si ocurre un error al cambiar la escena.
     */
    @javafx.fxml.FXML
    public void anhadirItem(ActionEvent actionEvent) throws IOException {
        // Crea un nuevo ítem.
        var item = new Item();
        // Establece el ítem recién creado en la sesión actual para su posterior uso.
        Session.setCurrentItem(item);
        //Lleva a la pantalla de AgregarItemController.
        App.changeScene("agregar-item-view.fxml", "Añadir nuevo Item");
    }

    /**
     * Maneja el evento cuando se presiona el botón "Borrar Item".
     *
     * @param actionEvent Evento de acción.
     */
    @javafx.fxml.FXML
    public void borrarItem(ActionEvent actionEvent) {
        // Obtener el ítem seleccionado desde la interfaz gráfica
        Item itemSeleccionado = tablaDetallespedido.getSelectionModel().getSelectedItem();

        if (itemSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Estas seguro de eliminar el Item: " + itemSeleccionado.getId() + ", que contiene el producto: "
                    + itemSeleccionado.getProductos().getNombreProducto() + "?");
            var result = alert.showAndWait().get();

            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                itemDAO.delete(itemSeleccionado);
                observableList.remove(itemSeleccionado);

                Pedido pedidoActual = Session.getCurrentPedido();
                Double nuevoTotal = calcularTotalPedido(pedidoActual) - (itemSeleccionado.getProductos().getPrecio() * itemSeleccionado.getCantidad());
                System.out.println(nuevoTotal);
                pedidoActual.setTotal(nuevoTotal);

                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.update(pedidoActual);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecciona un item para eliminar");
            alert.showAndWait();
        }
    }
    /**
     * Calcula el total de un pedido restando el precio de los productos eliminados.
     *
     * @param pedidoActual Pedido actual.
     * @deprecated Este método está marcado como obsoleto (deprecated) y se debería evitar su uso futuro.
     * Se recomienda utilizar métodos alternativos para calcular el total de un pedido.
     */
    @Deprecated
    private Double calcularTotalPedido(Pedido pedidoActual) {
        Double total = 0.0;

        for (Item item : pedidoActual.getItems()) {
            total += item.getProductos().getPrecio() * item.getCantidad();
        }
        return total;
    }
    /**
     * Maneja el evento cuando se presiona el botón "Actualizar Pedido".
     */
    @javafx.fxml.FXML
    private void actualizarPedido() {
        Pedido pedidoActual = Session.getCurrentPedido();
        Double nuevoTotal = calcularTotalPedido(pedidoActual);
        pedidoActual.setTotal(nuevoTotal);

        try {
            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.update(pedidoActual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera y muestra un informe en formato PDF basado en un archivo JasperReport (.jrxml y .jasper),
     * utilizando los datos de un pedido actualmente seleccionado en la sesión. El informe contiene
     * información específica de la empresa y del pedido, y se guarda en una ubicación predefinida.
     *
     * @param actionEvent Objeto que representa un evento de acción, generalmente asociado a la
     *                    interacción del usuario con la interfaz gráfica.
     * @throws SQLException Si ocurre algún error relacionado con la manipulación de la base de datos.
     */
    @FXML
    public void imprimirInforme(ActionEvent actionEvent) throws SQLException {
        Connection conexionbd = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpedidos", "root", "");
        String codpedido = Session.getCurrentPedido().getCodigo();
        System.out.println(codpedido);


        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombreEmpresa", "Thunder S.A.");
        parametros.put("codpedido", codpedido);

        try {
            String rutaJrxml = "C:\\Users\\frans\\JaspersoftWorkspace\\InformePedidos\\informepedido.jrxml";
            String rutaJasper = "C:\\Users\\frans\\JaspersoftWorkspace\\InformePedidos\\informepedido.jasper";

            if (!Files.exists(Paths.get(rutaJasper))) {
                JasperCompileManager.compileReportToFile(rutaJrxml);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, conexionbd);

            String nombreArchivoPDF = "informe_" + codpedido + ".pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\frans\\Downloads\\Informes\\" + nombreArchivoPDF);

            JasperViewer.viewReport(jasperPrint, false);


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Cerrar la conexión después de su uso
            if (conexionbd != null) {
                try {
                    conexionbd.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}




