/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos.buscador;

import alertas.alertas;
import bd.conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class BProductoController implements Initializable {

    @FXML
    private TextField txt_busqueda;
    @FXML
    private TableView<TablaBuscador> TablaBuscador;
    private ObservableList<TablaBuscador> ObservBuscador;
    @FXML
    private TableColumn<?, ?> COL_ID;
    @FXML
    private TableColumn<?, ?> COL_DESCRIPCION;
    @FXML
    private TableColumn<?, ?> COL_INTERNO;
    @FXML
    private TableColumn<?, ?> COL_BARRAS;

    private String BasedateName;
    @FXML
    private TableColumn<?, ?> COL_PRECIO;

    private List<TablaBuscador> SeleccionProductos;
    @FXML
    private TableColumn<?, ?> Col_Unidad;
    @FXML
    private TableColumn<?, ?> COL_IVA;
    @FXML
    private TableColumn<?, ?> COL_IEPS;
    @FXML
    private TableColumn<?, ?> COL_ISR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDatos(String bdName) {
        SeleccionProductos = new ArrayList<>();
        this.BasedateName = bdName;
        CargaTabla();
    }

    public List<TablaBuscador> getSeleccion() {
        return SeleccionProductos;
    }

    @FXML
    private void AccionEsc(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            final Node source = (Node) event.getSource();
            final Stage nodo = (Stage) source.getScene().getWindow();
            nodo.close();
        }
    }

    private void CargaTabla() {
        ObservBuscador = FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Productos INNER JOIN Ncc_Unidades ON Ncc_Productos.Prod_idUnidad = Ncc_Unidades.Unidad_ClaveUnidad";
        conexion x = new conexion();
        cnx = x.crearConexion(BasedateName);
        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                //ObservBuscador.add(new TablaBuscador(rs.getInt("Prod_Id"), rs.getString("Prod_Descripcion"), rs.getString("Unidad_Descripcion"), rs.getString("Prod_CodInterno"), rs.getString("Prod_CodBarras"), rs.getDouble("Prod_PrecioMostrador")));
                ObservBuscador.add(new TablaBuscador(
                        rs.getInt("Prod_Id"),
                        rs.getString("Prod_Descripcion"),
                        rs.getString("Unidad_Descripcion"),
                        rs.getString("Unidad_ClaveUnidad"),
                        rs.getString("Prod_CodInterno"),
                        rs.getInt("Prod_IVA"),
                        rs.getInt("Prod_IEPS"),
                        rs.getInt("Prod_ISR"),
                        rs.getString("Prod_CodBarras"),
                        rs.getDouble("Prod_PrecioMostrador")));
            }
            COL_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            COL_DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("DESCRIPCION"));
            Col_Unidad.setCellValueFactory(new PropertyValueFactory<>("COD_UNIDAD"));
            COL_INTERNO.setCellValueFactory(new PropertyValueFactory<>("COD_INTERNO"));
            COL_IVA.setCellValueFactory(new PropertyValueFactory<>("IVA"));
            COL_IEPS.setCellValueFactory(new PropertyValueFactory<>("IEPS"));
            COL_ISR.setCellValueFactory(new PropertyValueFactory<>("ISR"));

            COL_BARRAS.setCellValueFactory(new PropertyValueFactory<>("COD_BARRAS"));
            COL_PRECIO.setCellValueFactory(new PropertyValueFactory<>("PRECIO"));

            TablaBuscador.setItems(ObservBuscador);
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Servidor", "", e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                alertas.alertaDeExcepcion("Error", "bd", "", e.getMessage());
            }
        }

        FilteredList<TablaBuscador> filteredData = new FilteredList<>(ObservBuscador, p -> true);

        txt_busqueda.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(Buscador
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (Buscador.getDESCRIPCION().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                } else if (Buscador.getCOD_INTERNO().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaBuscador> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaBuscador.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaBuscador.setItems(sortedData);
    }
    boolean bandera = true;

    public void SeleccionaProductos(double cantidad) {

        int index = TablaBuscador.getSelectionModel().getSelectedIndex();

        int IDProDucto = TablaBuscador.getItems().get(index).getID();
        String descripcion = TablaBuscador.getItems().get(index).getDESCRIPCION();
        String unidad = TablaBuscador.getItems().get(index).getCOD_UNIDAD();
        String unidad_COD = TablaBuscador.getItems().get(index).getCOD_UNIDAD_CODIGO();
        String interno = TablaBuscador.getItems().get(index).getCOD_INTERNO();
        int iva = TablaBuscador.getItems().get(index).getIVA();
        int ieps = TablaBuscador.getItems().get(index).getIEPS();
        int isr = TablaBuscador.getItems().get(index).getISR();
        String barras = TablaBuscador.getItems().get(index).getCOD_BARRAS();
        double precio = TablaBuscador.getItems().get(index).getPRECIO();

        if (SeleccionProductos.isEmpty()) {
            SeleccionProductos.add(new TablaBuscador(
                    IDProDucto,
                    descripcion,
                    unidad,
                    unidad_COD,
                    interno,
                    iva,
                    ieps,
                    isr,
                    barras,
                    precio,
                    cantidad));
        } else {
            SeleccionProductos.forEach((SeleccionProducto) -> {
                if (SeleccionProducto.getID() == IDProDucto) {
                    double contador = SeleccionProducto.getCatidad();
                    contador += cantidad;
                    SeleccionProducto.setCatidad(contador);
                    bandera = false;
                }
            });

            if (bandera) {
                SeleccionProductos.add(new TablaBuscador(
                        IDProDucto,
                        descripcion,
                        unidad,
                        unidad_COD,
                        interno,
                        iva,
                        ieps,
                        isr,
                        barras,
                        precio,
                        cantidad));
            }

            bandera = true;
        }

    }

    @FXML
    private void AccionTeclaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
          

                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Cantidad");
                dialog.setHeaderText("Ingrese una cantidad");
                dialog.setContentText("Numeros:");

// Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    if (isDouble(result.get())) {
                        double numero = Double.parseDouble(result.get());
                        SeleccionaProductos(numero);
                    } else {
                        alertas.NotificacionError("Error", "No es un numero");
                    }
                }

            

        }
    }

    @FXML
    private void AccionDobleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
        
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Cantidad");
                dialog.setHeaderText("Ingrese una cantidad");
                dialog.setContentText("Numeros:");

// Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    if (isDouble(result.get())) {
                        double numero = Double.parseDouble(result.get());
                        SeleccionaProductos(numero);
                    } else {
                        alertas.NotificacionError("Error", "No es un numero");
                    }
                }
            

        }
    }

    public void muestraList() {

    }

    public boolean isDouble(String numer) {
        try {
            Double.parseDouble(numer);
            return true;
        } catch (NumberFormatException e) {
            return false;

        }
    }

}
