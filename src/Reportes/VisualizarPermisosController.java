package Reportes;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ClasesObjetos.buscador.TablaPermisos;
import alertas.alertas;
import bd.conexion;
import cma.carmelo.jasperviewerfx.JasperFX;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class VisualizarPermisosController implements Initializable {

    @FXML
    private JFXTextField TXTBUSQUEDA;
    @FXML
    private TableView<TablaPermisos> TABLAPERMISOS;
    @FXML
    private TableColumn<?, ?> COLPERMISOS;
    @FXML
    private Button BTNEXCEL;
    private ObservableList<TablaPermisos> OBSPermisos;
    private String BDatos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDatos(String bd)
    {
        this.BDatos = bd;
        RellenarTabla();
    }
    
    private void RellenarTabla()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Permisos";
        conexion x = new conexion();
        cnx = x.crearConexion(BDatos);
        
        OBSPermisos=FXCollections.observableArrayList();
        
        try {
            ps=cnx.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
               OBSPermisos.add(new TablaPermisos(rs.getInt("Per_ID"), rs.getString("Per_Serie"),rs.getString("Per_Descripcion")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPermisosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        COLPERMISOS.setCellValueFactory(new PropertyValueFactory<>("PERMISO"));
        
        TABLAPERMISOS.setItems(OBSPermisos);
        
         FilteredList<TablaPermisos> filteredData = new FilteredList<>(OBSPermisos, p -> true);

        TXTBUSQUEDA.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(Permisos
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (Permisos.getPERMISO().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaPermisos> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TABLAPERMISOS.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TABLAPERMISOS.setItems(sortedData);
    }
    @FXML
    private void GenerarExcel(ActionEvent event)
    {
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        try
        {
            HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
            InputStream fis2 = null;
            fis2 = this.getClass().getResourceAsStream("/Reportes/Permisos.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
            JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
            JasperFX jpFX = new JasperFX(print);
            jpFX.show();
        } catch (NumberFormatException | JRException e)
        {
            alertas.alertaDeExcepcion("Error al generar el documento", "Reporte", "Jasper", e.getMessage());
        }
    }
}
