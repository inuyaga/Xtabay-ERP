package Inventario;

import Clientes.Departamento;
import bd.conexion;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class DepartamentosController implements Initializable {

    @FXML
    private TreeView<Departamento> TreeviewDepartamentos;
    private String BDATOS;
    private ObservableList<TreeItem<Departamento>> ObserDepartamentos;
    @FXML
    private JFXTextField txt_nombredepo;
    @FXML
    private Button Btn_Acciones;

    private Departamento dato;
    @FXML
    private TableView<TablaProductos> TablaProductos;
    private ObservableList<TablaProductos> ObserTproductos;
    @FXML
    private TableColumn<?, ?> COL_ID;
    @FXML
    private TableColumn<?, ?> COL_INTERNO;
    @FXML
    private TableColumn<?, ?> COL_BARRAS;
    @FXML
    private TableColumn<?, ?> COL_DESCRIPCION;
    @FXML
    private TableColumn<?, ?> COL_DEPARTAMENTO;
    @FXML
    private TextField txt_busqueda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setBd(String bd) {
        BDATOS = bd;
        MuestraDepo();
    }

    public void MuestraDepo() {
        ObserDepartamentos = FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Departamentos";

        TreeItem<Departamento> rootItem = new TreeItem<>(new Departamento("", "Departamentos"));
        rootItem.setExpanded(true);

        conexion conexDB = new conexion();

        cnx = conexDB.crearConexion(BDATOS);
        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ObserDepartamentos.add(new TreeItem<>(new Departamento(rs.getString("Depto_id"), rs.getString("Depto_descripcion"))));
            }
            
            rootItem.getChildren().addAll(ObserDepartamentos);
            TreeviewDepartamentos.setRoot(rootItem);

        } catch (SQLException ex) {
            Logger.getLogger(DepartamentosController.class.getName()).log(Level.SEVERE, null, ex);
            alertas.alertas.alertaDeExcepcion("error", "La parecer hubo un problema", "", ex.getMessage());
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
    private void AccionClicEnTreeView(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int i = TreeviewDepartamentos.getSelectionModel().getSelectedIndex();
            if (i > 0) {
                dato = TreeviewDepartamentos.getRoot().getChildren().get(i - 1).getValue();

                System.out.println(dato.getNOMBRE());
                txt_nombredepo.setText(dato.getNOMBRE());
                CargaTablaProductos(dato.getID());
                Btn_Acciones.setText("Editar");
            }

        }
    }

    @FXML
    private void AccionAgregaYcambia(ActionEvent event) {
        if (Btn_Acciones.getText().equals("Editar")) {
            System.out.println("Editar");
            EditaDepartamento();
            
        } else {
            System.out.println("Nuevo");
            String nombre = txt_nombredepo.getText();

            if (nombre.isEmpty() && nombre.length() < 2) {
                alertas.alertas.alertaError("Error", "Sin datos", "Ingrese datos por favor");
            } else {

                PreparedStatement ps = null;
                Connection cnx = null;
                String sql = "INSERT INTO Ncc_Departamentos(Depto_descripcion) VALUES(?)";

                conexion conexDB = new conexion();

                cnx = conexDB.crearConexion(BDATOS);
                try {
                    ps = cnx.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.executeUpdate();
                    MuestraDepo();
                    alertas.alertas.arlertaInformacion("Creacion departamento", "Se creo Correctamente");
                } catch (SQLException ex) {
                    Logger.getLogger(DepartamentosController.class.getName()).log(Level.SEVERE, null, ex);
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

        }//fin else
    }

    private void EditaDepartamento() {

        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "UPDATE Ncc_Departamentos SET Depto_descripcion=? WHERE Depto_id=?";
        conexion conexDB = new conexion();

        cnx = conexDB.crearConexion(BDATOS);

        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, txt_nombredepo.getText());
            ps.setString(2, dato.getID());
            ps.executeUpdate();
            MuestraDepo();
            Btn_Acciones.setText("Agregar");
            txt_nombredepo.setText("");
            TablaProductos.getItems().clear();
            alertas.alertas.NotificacionInformacion("Edicion", "Departamento editado");

        } catch (SQLException e) {
            Logger.getLogger(DepartamentosController.class.getName()).log(Level.SEVERE, null, e);
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
    
    
    public void CargaTablaProductos(String ID_depo){
        ObserTproductos = FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Productos \n"
                + "INNER JOIN Ncc_Departamentos ON Ncc_Productos.Prod_idDepto = Ncc_Departamentos.Depto_id \n"
                + "WHERE Prod_idDepto = ?";
        
        conexion conexDB = new conexion();

        cnx = conexDB.crearConexion(BDATOS);
        
        try {
            ps=cnx.prepareStatement(sql);
            ps.setString(1, ID_depo);
            rs=ps.executeQuery();
            while (rs.next()) {
                ObserTproductos.add(new TablaProductos(rs.getString("Prod_Id"), rs.getString("Prod_CodInterno"), rs.getString("Prod_CodBarras"), rs.getString("Prod_Descripcion"), rs.getString("Depto_descripcion")));  
            }
            COL_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            COL_INTERNO.setCellValueFactory(new PropertyValueFactory<>("INTERNO"));
            COL_BARRAS.setCellValueFactory(new PropertyValueFactory<>("BARRAS"));
            COL_DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("DESCRIPCION"));
            COL_DEPARTAMENTO.setCellValueFactory(new PropertyValueFactory<>("DEPARTAMENTO"));
            
            TablaProductos.setItems(ObserTproductos);
        } catch (SQLException e) {
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
            }
        }
        
        
        
        
        
        
        FilteredList<TablaProductos> filteredData = new FilteredList<>(ObserTproductos, p -> true);

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
                } else if (Buscador.getINTERNO().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaProductos> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaProductos.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaProductos.setItems(sortedData);
    }

}
