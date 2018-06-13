package Grupo;

import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.ListSelectionView;
import ClasesObjetos.Permisos;
import usuarios.Permisos_adicionalesController;
/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class PermisosGruposController implements Initializable {

    @FXML
    private TableView<TablaGrupos> TablaPermisosGrupo;
    @FXML
    private TableColumn<?, ?> ColIdPer;
    @FXML
    private TableColumn<?, ?> ColNombreGrupo;
    @FXML
    private TableColumn<?, ?> ColFechaGrupo;
    @FXML
    private JFXTextField TxtBusquedaGrupo;

    private ObservableList<TablaGrupos> ObListaGrupos;
    private ObservableList<TablaGrupos> ObListaPermisosIndividuales;
    private String IDUsuarioTemporal;
    
    
    @FXML
    private ListSelectionView<Permisos> Selector_permisos;
    @FXML
    private Text TextResultado;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LlenarLista();
//        CargaPermisos();
        FilteredList<TablaGrupos> filteredData = new FilteredList<>(ObListaGrupos, p -> true);
        TxtBusquedaGrupo.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(TablaGrupo
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (TablaGrupo.getNOMBREGRUPO().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                }
                return false; // No existe ninguna coincidencia.
            });
        });
        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaGrupos> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaPermisosGrupo.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaPermisosGrupo.setItems(sortedData);
    }    
    
    public void LlenarLista()
    {
        ObListaGrupos = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Grupos LEFT JOIN Ncc_User ON Ncc_Grupos.Group_UserAlta = Ncc_User.User_ID";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ObListaGrupos.add(new TablaGrupos(rs.getString("Group_ID"), rs.getString("Group_Name"), rs.getString("Group_FechaAlta"), rs.getString("User_Nombre")));
            }

        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", "No se pudo establecer una conexion con el servidor", e.getMessage());
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
                alertas.alertaDeExcepcion("Error", "Ocurrio un error", "No se pudo cerrar conexiones", e.getMessage());
            }
        }
        ColIdPer.setCellValueFactory(new PropertyValueFactory<>("IDG"));
        ColNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("NOMBREGRUPO"));
        ColFechaGrupo.setCellValueFactory(new PropertyValueFactory<>("FECHA"));
        TablaPermisosGrupo.setItems(ObListaGrupos);
    }
    private void CargaPermisos()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Permisos";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next())
            {
                Selector_permisos.getSourceItems().add(new Permisos(rs.getString("Per_ID"), rs.getString("Per_Serie"), rs.getString("Per_Descripcion")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Permisos_adicionalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    @FXML
    private void dobleclicT(MouseEvent event)
    {
        if (event.getClickCount() == 2) {
            Selector_permisos.getSourceItems().clear();
            Selector_permisos.getTargetItems().clear();
            int IndexTabla = TablaPermisosGrupo.getSelectionModel().getSelectedIndex();
            String grupo = TablaPermisosGrupo.getItems().get(IndexTabla).getNOMBREGRUPO();
            IDUsuarioTemporal = TablaPermisosGrupo.getItems().get(IndexTabla).getIDG();
            TextResultado.setText(grupo);
            CargaPermisos();
            busquedaPermisogrupo(IDUsuarioTemporal);
            comparacionYmuestra();
        }
    }
    
    private void busquedaPermisogrupo (String idgrupo)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Administracion_EditarPermisosGrupos WHERE GP_IdGroup = '"+idgrupo+"'";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next())
            {
                Selector_permisos.getTargetItems().add(new Permisos(rs.getString("GP_IdPermiso"), rs.getString("Per_Serie"), rs.getString("Per_Descripcion")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Permisos_adicionalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
            }
        }
    }
   
    private void comparacionYmuestra()
    {
        ObservableList<Permisos> PermisosArriba= Selector_permisos.getSourceItems();
        ObservableList<Permisos> PermisosAbajo=Selector_permisos.getTargetItems();
    
        for (Permisos PermisosAbajo1 : PermisosAbajo)
        {    
            PermisosArriba.removeIf((Permisos em) -> em.getID().equals(PermisosAbajo1.getID()));
        }
     
    }

    @FXML
    private void GuardarCambios(ActionEvent event)
    {
        boolean bandera = alertas.alertaConfirmacion("Cambio permisos", "", "¿Desea aplicar estos cambios?");
        if (bandera)
        {
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            Connection cnx = null;
            String sql = "DELETE FROM Ncc_GroupPermisos WHERE GP_IdGroup=?";
            String sql2Agrega = "INSERT INTO Ncc_GroupPermisos(GP_IdGroup, GP_IdPermiso) VALUES(?,?)";

            conexion conexBD = new conexion();
            conexBD.crearConexion();
            cnx = conexBD.getConexion();

            try
            {
                cnx.setAutoCommit(false);
                ps = cnx.prepareStatement(sql);
                ps.setString(1, IDUsuarioTemporal);
                ps.executeUpdate();
                ps2 = cnx.prepareStatement(sql2Agrega);

                for (Permisos targetItem : Selector_permisos.getTargetItems()) 
                {
                    ps2.setString(1, IDUsuarioTemporal);
                    ps2.setString(2, targetItem.getID());
                    ps2.executeUpdate();
                }

                cnx.commit();
                alertas.arlertaInformacion("Permisos", "Cambios aplicados correctamente");
            } catch (SQLException e) {
                if (cnx != null)
                {
                    try
                    {
                        cnx.rollback();
                        alertas.alertaError("Error", "Cambios no aplicados", "Intenta de nuevo");
                    } catch (SQLException ex) {
                        Logger.getLogger(Permisos_adicionalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } finally 
                    {
                        try
                        {
                            if (ps != null)
                            {
                                ps.close();
                            }
                            if (ps2 != null)
                            {
                                ps2.close();
                            }
                            if (cnx != null)
                            {
                                cnx.close();
                            }
                        } catch (SQLException e) {
                        }
                    }
        }
            Selector_permisos.getSourceItems().clear();
            CargaPermisos();
            Selector_permisos.getTargetItems().clear();   
    }
}
