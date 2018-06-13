package usuarios;

import ClasesObjetos.UsuariosPermisos;
import ClasesObjetos.Permisos;
import alertas.alertas;
import bd.conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.ListSelectionView;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class Permisos_adicionalesController implements Initializable {

    @FXML
    private ListSelectionView<Permisos> Selector_permisos;
    @FXML
    private TableView<UsuariosPermisos> TablaUsuarios;
    private ObservableList<UsuariosPermisos> ObsListUsuario;
    @FXML
    private TableColumn<?, ?> Col_ID;
    @FXML
    private TableColumn<?, ?> Col_user;
    @FXML
    private TableColumn<?, ?> Col_nombre;
    @FXML
    private TextField txt_nombre_completo;
    @FXML
    private TextField txt_busqueda;
    @FXML
    private TextField txt_usuario;

    private String IDUsuarioTemporal;
    @FXML
    private Button btn_aplicar_cambios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargaPermisos();
        CargaUsuarios();

        FilteredList<UsuariosPermisos> filteredData = new FilteredList<>(ObsListUsuario, p -> true);

        txt_busqueda.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(UsuariosPermisos
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (UsuariosPermisos.getUSUARIO().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                } else if (UsuariosPermisos.getNOMBRE().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<UsuariosPermisos> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaUsuarios.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaUsuarios.setItems(sortedData);
    }

    @FXML
    private void AccionAplicarCambios(ActionEvent event)
    {
        boolean bandera = alertas.alertaConfirmacion("Cambio permisos", "", "¿Desea aplicar estos cambios?");
        if (bandera) {
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            Connection cnx = null;
            String sql = "DELETE FROM Ncc_PermisoUsuario WHERE UP_IdUser=?";
            String sql2Agrega = "INSERT INTO Ncc_PermisoUsuario(UP_IdUser, UP_Id_Permiso) VALUES(?,?)";

            conexion conexBD = new conexion();
            conexBD.crearConexion();
            cnx = conexBD.getConexion();

            try {
                cnx.setAutoCommit(false);
                ps = cnx.prepareStatement(sql);
                ps.setString(1, IDUsuarioTemporal);
                ps.executeUpdate();

                ps2 = cnx.prepareStatement(sql2Agrega);

                for (Permisos targetItem : Selector_permisos.getTargetItems()) {
                    ps2.setString(1, IDUsuarioTemporal);
                    ps2.setString(2, targetItem.getID());
                    ps2.executeUpdate();
                }

                cnx.commit();
                alertas.arlertaInformacion("Permisos", "Cambios aplicados correctamente");

            } catch (SQLException e) {

                if (cnx != null) {
                    try {
                        cnx.rollback();
                        alertas.alertaError("Error", "Cambios no aplicados", "Intenta de nuevo");
                    } catch (SQLException ex) {
                        Logger.getLogger(Permisos_adicionalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                    if (cnx != null) {
                        cnx.close();
                    }
                } catch (SQLException e) {
                }
            }
        }
        
            Selector_permisos.getSourceItems().clear();
            CargaPermisos();
            Selector_permisos.getTargetItems().clear();
            txt_nombre_completo.setText("");
            txt_usuario.setText("");
            btn_aplicar_cambios.setDisable(true);
            
            
    }

    private void CargaPermisos() {
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

            while (rs.next()) {
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

    public void CargaUsuarios() {
        ObsListUsuario = FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_User";

        conexion conexDB = new conexion();
        conexDB.crearConexion();
        cnx = conexDB.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ObsListUsuario.add(new UsuariosPermisos(rs.getString("User_ID"), rs.getString("User_Usuario"), rs.getString("User_Nombre")));
            }

            Col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            Col_user.setCellValueFactory(new PropertyValueFactory<>("USUARIO"));
            Col_nombre.setCellValueFactory(new PropertyValueFactory<>("NOMBRE"));
        } catch (SQLException ex) {
            Logger.getLogger(Permisos_adicionalesController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e) {
            }
        }

        TablaUsuarios.setItems(ObsListUsuario);

    }

    @FXML
    private void AccionDobleClicTabla(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Selector_permisos.getSourceItems().clear();
            Selector_permisos.getTargetItems().clear();
            
            int IndexTabla = TablaUsuarios.getSelectionModel().getSelectedIndex();
            String user = TablaUsuarios.getItems().get(IndexTabla).getUSUARIO();
            String nombre = TablaUsuarios.getItems().get(IndexTabla).getNOMBRE();
            IDUsuarioTemporal = TablaUsuarios.getItems().get(IndexTabla).getID();

            txt_nombre_completo.setText(nombre);
            txt_usuario.setText(user);

            
            CargaPermisos();
            busquedaPermisoUsuario(IDUsuarioTemporal);
            btn_aplicar_cambios.setDisable(false);
            
            
            
        }
    }

    public void busquedaPermisoUsuario(String Id) {
        ResultSet rs = null;
        int i = 0;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT\n"
                + "Ncc_User.User_ID,\n"
                + "Ncc_User.User_Nombre,\n"
                + "Ncc_PermisoUsuario.UP_Id,\n"
                + "Ncc_Permisos.Per_ID,\n"
                + "Ncc_Permisos.Per_Serie,\n"
                + "Ncc_Permisos.Per_Descripcion\n"
                + "FROM\n"
                + "Ncc_PermisoUsuario\n"
                + "INNER JOIN Ncc_Permisos ON Ncc_PermisoUsuario.UP_Id_Permiso = Ncc_Permisos.Per_ID\n"
                + "INNER JOIN Ncc_User ON Ncc_User.User_ID = Ncc_PermisoUsuario.UP_IdUser\n"
                + "WHERE\n"
                + "Ncc_User.User_ID = ?";

        conexion conexDB = new conexion();
        conexDB.crearConexion();
        cnx = conexDB.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Selector_permisos.getTargetItems().add(new Permisos(rs.getString("Per_ID"), rs.getString("Per_Serie"), rs.getString("Per_Descripcion")));

            }

            busquedaItemseleccion();

        } catch (SQLException e) {
        } finally {
            try {
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
    
    private void busquedaItemseleccion()
    {
    ObservableList<Permisos> PermisosArriba= Selector_permisos.getSourceItems();
    ObservableList<Permisos> PermisosAbajo=Selector_permisos.getTargetItems();
    
        for (Permisos PermisosAbajo1 : PermisosAbajo) {
            
            PermisosArriba.removeIf((Permisos em) -> em.getID().equals(PermisosAbajo1.getID()));
        }
    }
}
