package ClasesObjetos.buscador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
public class BComprasPorPagar_PagosController implements Initializable {

    @FXML
    private TableView<TablaBusqCompras> TablaCompras;
    @FXML
    private TableColumn<?, ?> Col_idCompra;
    @FXML
    private TableColumn<?, ?> Colprovee;
    @FXML
    private TableColumn<?, ?> ColMonto;
    @FXML
    private TableColumn<?, ?> ColFac;

    private ObservableList<TablaBusqCompras> DataTable;
    private int IDCOMPRA=0; 
    private String BaseDatos;
    @FXML
    private JFXTextField txtbus;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataTable=FXCollections.observableArrayList();
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
    private void RellenarTabla()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Compras INNER JOIN Ncc_Proveedores ON Pro_ID=Compra_Proveedor_ID WHERE Compra_Finiquitada=0";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);
        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataTable.add(new TablaBusqCompras(
                        rs.getInt("Compra_ID"),
                        rs.getString("Pro_Proveedor"),
                        rs.getDouble("Compra_monto_total"),
                        rs.getString("Compra_Factura"),
                        rs.getInt("Compra_ID")));
            }
            Col_idCompra.setCellValueFactory(new PropertyValueFactory<>("IDCOMPRA"));
            Colprovee.setCellValueFactory(new PropertyValueFactory<>("PROVEEDOR"));
            ColMonto.setCellValueFactory(new PropertyValueFactory<>("MONTO"));
            ColFac.setCellValueFactory(new PropertyValueFactory<>("FACTURA"));
            TablaCompras.setItems(DataTable);
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

        FilteredList<TablaBusqCompras> filteredData = new FilteredList<>(DataTable, p -> true);

        txtbus.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(Buscador
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (Double.toString(Buscador.getIDCOMPRA()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                } else if (Buscador.getPROVEEDOR().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaBusqCompras> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaCompras.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaCompras.setItems(sortedData);
    }

    @FXML
    private void dobleClic(MouseEvent event)
    {
        if(event.getClickCount() == 2)
        {
            int index=TablaCompras.getSelectionModel().getSelectedIndex();
            IDCOMPRA=TablaCompras.getItems().get(index).getIDCOMPRA();
            Stage stage = (Stage) txtbus.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void pulsarenter(KeyEvent event) 
    {
        if(event.getCode()==KeyCode.ENTER)
        {
            int index=TablaCompras.getSelectionModel().getSelectedIndex();
            if(index >= 0)
            {
                IDCOMPRA=TablaCompras.getItems().get(index).getIDCOMPRA();
                Stage stage = (Stage) txtbus.getScene().getWindow();
                stage.close();
            }
        }
    }
}
