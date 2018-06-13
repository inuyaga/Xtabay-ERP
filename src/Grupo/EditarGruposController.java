package Grupo;

import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class EditarGruposController implements Initializable {

    @FXML
    private TableView<TablaGrupos> TablaGrupo;
    @FXML
    private TableColumn<?, ?> IdColumna;
    @FXML
    private TableColumn<?, ?> ColNombre;
    @FXML
    private TableColumn<?, ?> ColFecha;
    @FXML
    private TableColumn<?, ?> colCreo;
    @FXML
    private JFXTextField TxtBuscador;
    @FXML
    private JFXTextField txtResultados;

    private ObservableList<TablaGrupos> ObListaGrupos;
    private String IdGrupoSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LlenarLista();
        
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

        IdColumna.setCellValueFactory(new PropertyValueFactory<>("IDG"));
        ColNombre.setCellValueFactory(new PropertyValueFactory<>("NOMBREGRUPO"));
        ColFecha.setCellValueFactory(new PropertyValueFactory<>("FECHA"));
        colCreo.setCellValueFactory(new PropertyValueFactory<>("LOCREO"));
        TablaGrupo.setItems(ObListaGrupos);
        
        
        
        FilteredList<TablaGrupos> filteredData = new FilteredList<>(ObListaGrupos, p -> true);

        TxtBuscador.textProperty().addListener((observable, oldValue, newValue)
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
        sortedData.comparatorProperty().bind(TablaGrupo.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaGrupo.setItems(sortedData);
    }

    @FXML
    private void CambiarNombreGrupo(ActionEvent event)
    {
        int n = TablaGrupo.getSelectionModel().getSelectedIndex();
        IdGrupoSeleccionado = TablaGrupo.getItems().get(n).getIDG();
        String GrupoSeleccionado = TablaGrupo.getItems().get(n).getNOMBREGRUPO();
        TextInputDialog dialog = new TextInputDialog(GrupoSeleccionado);
        dialog.setTitle("Cambio de nombre "+GrupoSeleccionado);
        dialog.setHeaderText("Cambiar el nombre del grupo");
        dialog.setContentText("Ingresa el nuevo nombre:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.toString().isEmpty()) {
                PreparedStatement ps = null;
                PreparedStatement ps2 = null;
                ResultSet x = null;
                Connection cnx = null;
                String sql = "UPDATE Ncc_Grupos SET Group_Name = ? WHERE Group_ID = ? LIMIT 1";
                String sql2="SELECT * FROM Ncc_Grupos WHERE Group_Name = ?";
                conexion conexBD = new conexion();
                conexBD.crearConexion();
                cnx = conexBD.getConexion();

                try {
                        ps2=cnx.prepareStatement(sql2);
                        ps2.setString(1, result.get());
                        x=ps2.executeQuery();
                        
                        if(x.next()){
                             alertas.arlertaInformacion("Información duplicada", "Ya existe un grupo con ese nombre");
                        }else{
                            ps = cnx.prepareStatement(sql);
                            ps.setString(1, trim(result.get()));
                            ps.setString(2, trim(IdGrupoSeleccionado));
                            if (ps.executeUpdate() > 0) {
                                LlenarLista();
                                alertas.arlertaInformacion("Información guardada", "El grupo fue cambiado correctamente");
                            }else{
                                alertas.alertaDeExcepcion("Duplicado", "Ocurrio un error", "No se afectó ningun registro, posibles causas: ya existe un registro con ese nombre", "no ok");
                            }
                        }
                } catch (SQLException e) {
                    alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
                }finally{
                    try {
                        if (ps != null) {
                        ps.close();
                        }
                        if (cnx != null) {
                            cnx.close();
                        }
                    } catch (Exception e) {
                        alertas.alertaDeExcepcion("Error", "Ocurrio un error", "No se pudo cerrar conexiones", e.getMessage());
                    }
                }
            }
        }
    }

    @FXML
    private void CacharDobleClic(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int n = TablaGrupo.getSelectionModel().getSelectedIndex();
            String NombreGrupo = TablaGrupo.getItems().get(n).getNOMBREGRUPO();
            IdGrupoSeleccionado = TablaGrupo.getItems().get(n).getIDG();
            txtResultados.setText(NombreGrupo);
        }
    }
}
