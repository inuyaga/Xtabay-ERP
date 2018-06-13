package ClasesObjetos.buscador;

import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class BComprasConsultaController implements Initializable {

    @FXML
    private TableView<TablaPagosConsulta> TablaComprasConsulta;
    @FXML
    private TableColumn<?, ?> colcompra;
    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private TableColumn<?, ?> colpago;
    @FXML
    private TableColumn<?, ?> colproveedor;
    @FXML
    private TableColumn<?, ?> colabono;
    @FXML
    private TableColumn<?, ?> colmpago;
    @FXML
    private TableColumn<?, ?> colreferencia;
    @FXML
    private TableColumn<?, ?> colobservacion;
    
    private ObservableList<TablaPagosConsulta> ListaDatos;
    private String BaseDatos;
    private int IDCOMPRA;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }
    
    public int getIDCOMPRA()
    {
        return this.IDCOMPRA;
    }

    public void setBasedeDatos(String bd)
    {
        this.BaseDatos=bd;
        RellenarTabla();
    }
    @FXML
    private void dobleClic(MouseEvent event)
    {
        if(event.getClickCount() == 2)
        {
            int index=TablaComprasConsulta.getSelectionModel().getSelectedIndex();
            IDCOMPRA=TablaComprasConsulta.getItems().get(index).getPAGO();
            Stage stage = (Stage) TablaComprasConsulta.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void pulsarenter(KeyEvent event) 
    {
        if(event.getCode()==KeyCode.ENTER)
        {
            int index=TablaComprasConsulta.getSelectionModel().getSelectedIndex();
            if(index >= 0)
            {
                IDCOMPRA=TablaComprasConsulta.getItems().get(index).getPAGO();
                Stage stage = (Stage) TablaComprasConsulta.getScene().getWindow();
                stage.close();
            }
        }
    }
    
    private void RellenarTabla()
    {
        ListaDatos=FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Pagos INNER JOIN Ncc_Proveedores ON Pro_ID=Pagos_IDProveedor ORDER BY Pagos_ID DESC";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);
        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ListaDatos.add(new TablaPagosConsulta(
                        rs.getInt("Pagos_ID"),
                        rs.getInt("Pagos_IDCompra"),
                        rs.getString("Pro_Proveedor"),
                        rs.getDouble("Pagos_Abono"),
                        rs.getString("Pagos_IDMetodoPago"),
                        rs.getString("Pagos_Referencia"),
                        rs.getString("Pagos_Observacion")
                ));
            }
            colpago.setCellValueFactory(new PropertyValueFactory<>("PAGO"));
            colcompra.setCellValueFactory(new PropertyValueFactory<>("COMPRA"));
            colproveedor.setCellValueFactory(new PropertyValueFactory<>("PROVEEDOR"));
            colabono.setCellValueFactory(new PropertyValueFactory<>("ABONO"));
            colmpago.setCellValueFactory(new PropertyValueFactory<>("METODOPAGO"));
            colreferencia.setCellValueFactory(new PropertyValueFactory<>("REFERENCIA"));
            colobservacion.setCellValueFactory(new PropertyValueFactory<>("OBSERVACION"));
            TablaComprasConsulta.setItems(ListaDatos);
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

        FilteredList<TablaPagosConsulta> filteredData = new FilteredList<>(ListaDatos, p -> true);

        txtBusqueda.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(Buscador
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (Double.toString(Buscador.getPAGO()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                } else if (Buscador.getPROVEEDOR().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }else if (Double.toString(Buscador.getCOMPRA()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaPagosConsulta> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaComprasConsulta.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaComprasConsulta.setItems(sortedData);
    }
}
