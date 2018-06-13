package Proveedores;

import bd.conexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.sql.SQLException;
import alertas.alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import static jdk.nashorn.internal.objects.NativeString.trim;
import ClasesObjetos.MetodosPago;

/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class NuevoProveedorController implements Initializable {

    @FXML
    private JFXTextField BusquedaTxt;
    @FXML
    private TableView<RellenaTablaProoveedores> TablaProveedores;
    @FXML
    private TableColumn<?, ?> IdCol;
    @FXML
    private TableColumn<?, ?> RFCCol;
    @FXML
    private TableColumn<?, ?> ProveedorCol;
    @FXML
    private TableColumn<?, ?> DiasCol;
    @FXML
    private TableColumn<?, ?> MpagoCol;
    @FXML
    private TableColumn<?, ?> CorreoCol;
    @FXML
    private TableColumn<?, ?> TelefonoCol;
    @FXML
    private TableColumn<?, ?> CalleCol;
    @FXML
    private TableColumn<?, ?> cpCol;
    @FXML
    private TableColumn<?, ?> ColoniaCol;
    @FXML
    private TableColumn<?, ?> CiudadCol;
    @FXML
    private TableColumn<?, ?> EstadoCol;
    @FXML
    private JFXTextField RFC_TXT;
    @FXML
    private JFXTextField proveedorTXT;
    @FXML
    private JFXTextField diasTXT;
    @FXML
    private ComboBox<MetodosPago> pagoCombo;
    @FXML
    private JFXTextField correoTXT;
    @FXML
    private JFXTextField telTXT;
    @FXML
    private JFXTextField CalleTXT;
    @FXML
    private JFXTextField CpTXT;
    @FXML
    private JFXTextField ColoniaTXT;
    @FXML
    private JFXTextField CiudadTXT;
    @FXML
    private ComboBox<String> EstadoCombo;
    @FXML
    private JFXButton nuevoBtn;
    @FXML
    private JFXButton EditarBtn;
    @FXML
    private TableColumn<?, ?> limiteCredCol;
    @FXML
    private TableColumn<?, ?> t_entregaCol;

    private String BaseDatos;
    private ObservableList<RellenaTablaProoveedores> ObservableProveedores;
    @FXML
    private JFXTextField txtLimiteCred;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EstadoCombo.getItems().add("Aguascalientes");
        EstadoCombo.getItems().add("Baja California");
        EstadoCombo.getItems().add("Baja California Sur");
        EstadoCombo.getItems().add("Campeche");
        EstadoCombo.getItems().add("Chiapas");
        EstadoCombo.getItems().add("Chihuahua");
        EstadoCombo.getItems().add("Ciudad de México");
        EstadoCombo.getItems().add("Coahuila");
        EstadoCombo.getItems().add("Colima");
        EstadoCombo.getItems().add("Durango");
        EstadoCombo.getItems().add("Guanajuato");
        EstadoCombo.getItems().add("Guerrero");
        EstadoCombo.getItems().add("Hidalgo");
        EstadoCombo.getItems().add("Jalisco");
        EstadoCombo.getItems().add("México");
        EstadoCombo.getItems().add("Michoacán");
        EstadoCombo.getItems().add("Morelos");
        EstadoCombo.getItems().add("Nayarit");
        EstadoCombo.getItems().add("Nuevo León");
        EstadoCombo.getItems().add("Oaxaca");
        EstadoCombo.getItems().add("Puebla");
        EstadoCombo.getItems().add("Querétaro");
        EstadoCombo.getItems().add("Quintana Roo");
        EstadoCombo.getItems().add("San Luis Potosí");
        EstadoCombo.getItems().add("Sinaloa");
        EstadoCombo.getItems().add("Sonora");
        EstadoCombo.getItems().add("Tabasco");
        EstadoCombo.getItems().add("Tamaulipas");
        EstadoCombo.getItems().add("Tlaxcala");
        EstadoCombo.getItems().add("Veracruz");
        EstadoCombo.getItems().add("Yucatán");
        EstadoCombo.getItems().add("Zacatecas");
        EstadoCombo.getSelectionModel().selectFirst();
        
    }
    private void LlenaMPago()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_MetodosPago";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);
        
        try 
        {
            ps=cnx.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next())
            {
                pagoCombo.getItems().add(new MetodosPago(rs.getString("MPag_Codigo"), rs.getString("MPag_Descripcion")));
            }
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Error de conexión", e.getLocalizedMessage(), e.getMessage());
        }finally {
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
        pagoCombo.getSelectionModel().selectFirst();
    }
    
    private void RellenarTablaProveedores()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Proveedores LEFT JOIN Ncc_MetodosPago ON Ncc_MetodosPago.MPag_Codigo=Ncc_Proveedores.Pro_IDMetodoPago order by Pro_RFC asc";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);
        ObservableProveedores=FXCollections.observableArrayList();
        try 
        {
            ps=cnx.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next())
            {
                ObservableProveedores.add(new RellenaTablaProoveedores(rs.getString("Pro_ID"), rs.getString("Pro_RFC"),rs.getString("Pro_Proveedor"), rs.getString("Pro_DiasCredito"), 
                        rs.getString("MPag_Descripcion"), rs.getString("Pro_Correo"), rs.getString("Pro_TelefonoContacto"), rs.getString("Pro_Calle"), rs.getString("Pro_CP"), rs.getString("Pro_Colonia"), 
                        rs.getString("Pro_Ciudad"), rs.getString("Pro_Estado"), rs.getString("Pro_TiempoEntrega"), rs.getString("Pro_LimiteCredito")));
            }
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Error de conexión", e.getLocalizedMessage(), e.getMessage());
        }finally {
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
        
        IdCol.setCellValueFactory(new PropertyValueFactory<>("IDProveedor"));
        RFCCol.setCellValueFactory(new PropertyValueFactory<>("RFC"));
        ProveedorCol.setCellValueFactory(new PropertyValueFactory<>("NOMBREPROVEEDOR"));
        DiasCol.setCellValueFactory(new PropertyValueFactory<>("DIASCREDITO"));
        MpagoCol.setCellValueFactory(new PropertyValueFactory<>("METODOPAGO"));
        CorreoCol.setCellValueFactory(new PropertyValueFactory<>("CORREO"));
        TelefonoCol.setCellValueFactory(new PropertyValueFactory<>("TELEFONO"));
        CalleCol.setCellValueFactory(new PropertyValueFactory<>("CALLE"));
        cpCol.setCellValueFactory(new PropertyValueFactory<>("CP"));
        ColoniaCol.setCellValueFactory(new PropertyValueFactory<>("COLONIA"));
        CiudadCol.setCellValueFactory(new PropertyValueFactory<>("CIUDAD"));
        EstadoCol.setCellValueFactory(new PropertyValueFactory<>("ESTADO"));
        limiteCredCol.setCellValueFactory(new PropertyValueFactory<>("LIMITECREDITO"));
        t_entregaCol.setCellValueFactory(new PropertyValueFactory<>("TIEMPOENTREFA"));
        
        TablaProveedores.setItems(ObservableProveedores);
        FilteredList<RellenaTablaProoveedores> filteredData = new FilteredList<>(ObservableProveedores, p -> true);

        BusquedaTxt.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(RellenaTablaProoveedores
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (RellenaTablaProoveedores.getRFC().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el RFC.
                } else if (RellenaTablaProoveedores.getNOMBREPROVEEDOR().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con EL NOMBRE DE PROVEERDOR.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<RellenaTablaProoveedores> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaProveedores.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaProveedores.setItems(sortedData);
    }
    
    //recibir del controlador principal padre-- el nombre de la base de datos
    public void setBasedeDatos(String bd)
    {
        this.BaseDatos=bd;
        LlenaMPago();
        RellenarTablaProveedores();
    }
    
    @FXML
    private void GuardarNuevo(ActionEvent event)
    {
        ResultSet rs = null;
        PreparedStatement ps=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sq3l = "INSERT INTO Ncc_Proveedores(Pro_Proveedor,Pro_RFC,Pro_Calle,Pro_IDMetodoPago,Pro_DiasCredito,Pro_CP,Pro_Correo,"
                + "Pro_TelefonoContacto,Pro_Colonia,Pro_Ciudad,Pro_Estado,Pro_LimiteCredito)\n" +
                        "SELECT ?,?,?,?,?,?,?,?,?,?,?,?\n" +
                        "FROM dual\n" +
                        "WHERE NOT EXISTS (SELECT Pro_RFC FROM Ncc_Proveedores WHERE Pro_RFC=? LIMIT 1)";
        
        if(!proveedorTXT.getText().isEmpty() && 
                !RFC_TXT.getText().isEmpty() && 
                !CalleTXT.getText().isEmpty() && 
                !CpTXT.getText().isEmpty() && 
                !diasTXT.getText().isEmpty() && 
                !correoTXT.getText().isEmpty() && 
                !telTXT.getText().isEmpty() && 
                !ColoniaTXT.getText().isEmpty() && 
                !CiudadTXT.getText().isEmpty() && 
                !txtLimiteCred.getText().isEmpty())
        {
            try {
                ps = cnx.prepareStatement(sq3l);
                ps.setString(1, trim(proveedorTXT.getText()));
                ps.setString(2, trim(RFC_TXT.getText()));
                ps.setString(3, trim(CalleTXT.getText()));
                ps.setString(4, trim(pagoCombo.getValue().getCODIGO()));
                ps.setString(5, trim(diasTXT.getText()));
                ps.setString(6, trim(CpTXT.getText()));
                ps.setString(7, trim(correoTXT.getText()));
                ps.setString(8, trim(telTXT.getText()));
                ps.setString(9, trim(ColoniaTXT.getText()));
                ps.setString(10, trim(CiudadTXT.getText()));
                ps.setString(11, trim(EstadoCombo.getValue()));
                ps.setString(12, trim(txtLimiteCred.getText()));
                ps.setString(13, trim(RFC_TXT.getText()));
                
                if(ps.executeUpdate() > 0 )
                {
                    RellenarTablaProveedores();
                    alertas.arlertaInformacion("Información guardada", "El proveedor fue creado correctamente");
                }else{
                    alertas.arlertaInformacion("Información repetida", "El proveedor ya existe");
                }
            } catch (SQLException ex) {
//                Logger.getLogger(FXMLGrupoController.class.getName()).log(Level.SEVERE, null, ex);
                alertas.alertaError("Error","Error al intentar dar de alta al nuevo proveedor", ex.getMessage());
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
            alertas.arlertaInformacion("Campo vacío", "El campo esta vacío, debe llenarlo con datos válidos");
        }
    }

    @FXML
    private void GuardarCambios(ActionEvent event)
    {
        ResultSet rs = null;
        PreparedStatement ps=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sql = "UPDATE Ncc_Proveedores SET Pro_Proveedor=?,Pro_RFC=?,Pro_Calle=?,Pro_IDMetodoPago=?,Pro_DiasCredito=?,Pro_CP=?,Pro_Correo=?,"
                + "Pro_TelefonoContacto=?,Pro_Colonia=?,Pro_Ciudad=?,Pro_Estado=?,Pro_LimiteCredito=? WHERE Pro_RFC=? LIMIT 1";
        
        if(!proveedorTXT.getText().isEmpty() && 
                !RFC_TXT.getText().isEmpty() && 
                !CalleTXT.getText().isEmpty() && 
                !CpTXT.getText().isEmpty() && 
                !diasTXT.getText().isEmpty() && 
                !correoTXT.getText().isEmpty() && 
                !telTXT.getText().isEmpty() && 
                !ColoniaTXT.getText().isEmpty() && 
                !CiudadTXT.getText().isEmpty() && 
                !txtLimiteCred.getText().isEmpty())
        {
            try {
                ps = cnx.prepareStatement(sql);
                ps.setString(1, trim(proveedorTXT.getText()));
                ps.setString(2, trim(RFC_TXT.getText()));
                ps.setString(3, trim(CalleTXT.getText()));
                ps.setString(4, trim(pagoCombo.getValue().getCODIGO()));
                ps.setString(5, trim(diasTXT.getText()));
                ps.setString(6, trim(CpTXT.getText()));
                ps.setString(7, trim(correoTXT.getText()));
                ps.setString(8, trim(telTXT.getText()));
                ps.setString(9, trim(ColoniaTXT.getText()));
                ps.setString(10, trim(CiudadTXT.getText()));
                ps.setString(11, trim(EstadoCombo.getValue()));
                ps.setString(12, trim(txtLimiteCred.getText()));
                ps.setString(13, trim(RFC_TXT.getText()));
                
                if(ps.executeUpdate() > 0 )
                {
                    alertas.arlertaInformacion("Información actualizada", "El proveedor fue actualizado correctamente");
                    RellenarTablaProveedores();
                }else{
                    alertas.arlertaInformacion("Información", "El no se actualizó ningún registro");
                }
            } catch (SQLException ex) {
//                Logger.getLogger(FXMLGrupoController.class.getName()).log(Level.SEVERE, null, ex);
                alertas.alertaError("Error","Error al intentar dar de alta al nuevo proveedor", ex.getMessage());
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
            alertas.arlertaInformacion("Campo vacío", "El campo esta vacío, debe llenarlo con datos válidos");
        }
    }

    @FXML
    private void DobleClic(MouseEvent event)
    {
        if(event.getClickCount() == 2)
        {
            int x=TablaProveedores.getSelectionModel().getSelectedIndex();
            String Mpag=TablaProveedores.getItems().get(x).getMETODOPAGO();
            RFC_TXT.setText(TablaProveedores.getItems().get(x).getRFC());
            proveedorTXT.setText(TablaProveedores.getItems().get(x).getNOMBREPROVEEDOR());
            diasTXT.setText(TablaProveedores.getItems().get(x).getDIASCREDITO());
            correoTXT.setText(TablaProveedores.getItems().get(x).getCORREO());
            telTXT.setText(TablaProveedores.getItems().get(x).getTELEFONO());
            CalleTXT.setText(TablaProveedores.getItems().get(x).getCALLE());
            CpTXT.setText(TablaProveedores.getItems().get(x).getCP());
            ColoniaTXT.setText(TablaProveedores.getItems().get(x).getCOLONIA());
            CiudadTXT.setText(TablaProveedores.getItems().get(x).getCIUDAD());
            txtLimiteCred.setText(TablaProveedores.getItems().get(x).getLIMITECREDITO());
            
            EstadoCombo.getSelectionModel().select(TablaProveedores.getItems().get(x).getESTADO());
            pagoCombo.getItems().forEach((pagos) -> {
                if(Mpag.equals(pagos.getDESCRIPCION())){
                    pagoCombo.getSelectionModel().select(new MetodosPago(pagos.getCODIGO(), pagos.getDESCRIPCION()));
                }
            });
            
        }
    }
    
}
