package Marcas;

import ClasesObjetos.Marcas;
import Inventario.DepartamentosController;
import Inventario.TablaProductos;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class MarcasController implements Initializable {
private String BaseDatos;

@FXML
private TreeView<Marcas> TreeviewMarcas;
private ObservableList<TreeItem<Marcas>> ObsMarcas;
private Marcas dato;
private ObservableList<TablaProductos> ObserTproductos;
private String IdGrupoSeleccionado;
@FXML
private JFXTextField txt_nombremarca; 
    @FXML
    private TableView<TablaProductos> TablaMarcas;
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
    private TextField txtBusqueda;
    @FXML
    private Label LblResultados;
    @FXML
    private Button btnNuevo;
    @FXML
    private ContextMenu contextomenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setBasedeDatos(String bd)
    {
        this.BaseDatos = bd;
        MostrarArbol();
    }
    
    public void MostrarArbol()
    {
        ObsMarcas = FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Marca";

        TreeItem<Marcas> rootItem = new TreeItem<>(new Marcas("", "Marcas"));
        rootItem.setExpanded(true);

        conexion conexDB = new conexion();
        cnx = conexDB.crearConexion(BaseDatos);
        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
       
            while (rs.next()) {
                ObsMarcas.add(new TreeItem<>(new Marcas(rs.getString("Marca_ID"), rs.getString("Marca_Descripcion"))));
            }
           
            rootItem.getChildren().addAll(ObsMarcas);
            TreeviewMarcas.setRoot(rootItem);
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentosController.class.getName()).log(Level.SEVERE, null, ex);
            alertas.alertaDeExcepcion("error", "La parecer hubo un problema", "", ex.getMessage());
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
    private void AccionClicEnTreeView(MouseEvent event) 
    {
        if (event.getClickCount() == 2) 
        {
            int i = TreeviewMarcas.getSelectionModel().getSelectedIndex();
            if (i > 0)
            {
                dato = TreeviewMarcas.getRoot().getChildren().get(i - 1).getValue();
                txt_nombremarca.setText(dato.getDESCRIPCIONMARCA());
                LblResultados.setText("Seleccionado: "+dato.getDESCRIPCIONMARCA());
                CargarTabla(dato.getIDMARCA());
//                Btn_Acciones.setText("Editar");
            }
        }
    }
    
    private void CargarTabla(String IDMARCA)
    {
        ObserTproductos = FXCollections.observableArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Productos \n"
                + "INNER JOIN Ncc_Departamentos ON Ncc_Productos.Prod_idDepto = Ncc_Departamentos.Depto_id \n"
                + "WHERE Prod_MarcaID = ?";
        conexion conexDB = new conexion();
        cnx = conexDB.crearConexion(BaseDatos);
        
        try {
            ps=cnx.prepareStatement(sql);
            ps.setString(1, IDMARCA);
            rs=ps.executeQuery();
            while (rs.next()) {
                ObserTproductos.add(new TablaProductos(rs.getString("Prod_Id"), rs.getString("Prod_CodInterno"), rs.getString("Prod_CodBarras"), rs.getString("Prod_Descripcion"), rs.getString("Depto_descripcion")));  
            }
            COL_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            COL_INTERNO.setCellValueFactory(new PropertyValueFactory<>("INTERNO"));
            COL_BARRAS.setCellValueFactory(new PropertyValueFactory<>("BARRAS"));
            COL_DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("DESCRIPCION"));
            COL_DEPARTAMENTO.setCellValueFactory(new PropertyValueFactory<>("DEPARTAMENTO"));
            TablaMarcas.setItems(ObserTproductos);
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
        txtBusqueda.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(Buscador
                    -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Buscador.getDESCRIPCION().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Buscador.getINTERNO().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });

        });
        SortedList<TablaProductos> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TablaMarcas.comparatorProperty());
        TablaMarcas.setItems(sortedData);
    }

    @FXML
    private void AccionGuardarNuevo(ActionEvent event)
    {
        ResultSet rs = null;
        PreparedStatement ps=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sq3l = "INSERT INTO Ncc_Marca(Marca_Descripcion)\n" +
                        "SELECT ?\n" +
                        "FROM dual\n" +
                        "WHERE NOT EXISTS (SELECT Marca_Descripcion FROM Ncc_Marca WHERE Marca_Descripcion=? LIMIT 1)";
        String NombreMarca = trim(txt_nombremarca.getText());
        
        if(!NombreMarca.isEmpty())
        {
            try {
                ps = cnx.prepareStatement(sq3l);
                ps.setString(1, trim(NombreMarca));
                ps.setString(2, trim(NombreMarca));
                if(ps.executeUpdate() > 0 )
                {
                    alertas.arlertaInformacion("Información guardada", "La marca fue creada correctamente");
                    LblResultados.setText("Guardado: "+txt_nombremarca.getText());
                    txt_nombremarca.setText("");
                    MostrarArbol();
//                    TablaMarcas.getItems().clear();
                }else{
                    alertas.arlertaInformacion("Información repetida", "La marca ya existe");
                    LblResultados.setText("");
                }
            } catch (SQLException ex) {
//                Logger.getLogger(FXMLGrupoController.class.getName()).log(Level.SEVERE, null, ex);
                alertas.alertaError("Error","Error al intentar crear la marca", ex.getMessage());
                LblResultados.setText("");
            }
            finally{
                try{
                if(rs != null){
                    rs.close();
                }
                if(cnx != null){
                    cnx.close();
                }
                if(ps != null){
                    ps.close();
                }
                }catch(SQLException e){
                    alertas.alertaDeExcepcion("Error","Error al intentar cerrar conexiones","error" ,e.getMessage());
                }
            }
        }else{
            alertas.arlertaInformacion("Campo vacío", "El campo esta vacío, debe llenarlo con un nombre válido");
            LblResultados.setText("");
        }
    }

    @FXML
    private void CambiarNombreMarca(ActionEvent event)
    {
        int n = TreeviewMarcas.getSelectionModel().getSelectedIndex();
        IdGrupoSeleccionado = TreeviewMarcas.getTreeItem(n).getValue().getIDMARCA();
        String GrupoSeleccionado = TreeviewMarcas.getTreeItem(n).getValue().getDESCRIPCIONMARCA();
        TextInputDialog dialog = new TextInputDialog(GrupoSeleccionado);
        dialog.setTitle("Cambio de nombre "+GrupoSeleccionado);
        dialog.setHeaderText("Corregir nombre de la marca");
        dialog.setContentText("Ingresa el nuevo nombre:");
        System.out.println(GrupoSeleccionado+"="+IdGrupoSeleccionado);
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.toString().isEmpty()) {
                PreparedStatement ps = null;
                PreparedStatement ps2 = null;
                ResultSet x = null;
                Connection cnx = null;
                String sql = "UPDATE Ncc_Marca SET Marca_Descripcion = ? WHERE Marca_ID = ? LIMIT 1";
                String sql2="SELECT * FROM Ncc_Marca WHERE Marca_Descripcion = ?";
                conexion conexBD = new conexion();
                conexBD.crearConexion();
                cnx = conexBD.getConexion();

                try {
                        ps2=cnx.prepareStatement(sql2);
                        ps2.setString(1, result.get());
                        x=ps2.executeQuery();
                        
                        if(x.next()){
                             alertas.arlertaInformacion("Información duplicada", "Ya existe una marca con ese nombre");
                        }else{
                            ps = cnx.prepareStatement(sql);
                            ps.setString(1, trim(result.get()));
                            ps.setString(2, trim(IdGrupoSeleccionado));
                            if (ps.executeUpdate() > 0) {
                                MostrarArbol();
                                alertas.arlertaInformacion("Información guardada", "La marca fue cambiada correctamente");
                                LblResultados.setText(GrupoSeleccionado+" por "+result.get());
                            }else{
                                alertas.alertaDeExcepcion("Duplicado", "Ocurrio un error", "No se afectó ningun registro, posibles causas: ya existe un registro con ese nombre", "no ok");
                                LblResultados.setText("");
                            }
                        }
                } catch (SQLException e) {
                    alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
                    LblResultados.setText("");
                }finally{
                    try {
                        if (ps != null) {
                        ps.close();
                        }
                        if (cnx != null) {
                            cnx.close();
                        }
                    } catch (SQLException e) {
                        alertas.alertaDeExcepcion("Error", "Ocurrio un error", "No se pudo cerrar conexiones", e.getMessage());
                        LblResultados.setText("");
                    }
                }
            }
        }
    }
    
}
