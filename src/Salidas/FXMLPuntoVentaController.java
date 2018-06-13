package Salidas;

import ClasesObjetos.CobroDatos;
import ClasesObjetos.MetodosPago;
import ClasesObjetos.buscador.TablaBuscador;
import ClasesObjetos.buscador.BProductoController;
import ClasesObjetos.TABLA_COMPRA;
import ClasesObjetos.Usuario;
import ClasesObjetos.buscador.BClientesController;
import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class FXMLPuntoVentaController implements Initializable {

    private String DATABASE_NAME;
  
    @FXML
    private Label LabelSumaTotal;

    private ObservableList<Usuario> DatosUsuarios;
    @FXML
    private Button Boton_Cobrar;
    @FXML
    private Label LabelUsuarioAtiende;
    @FXML
    private TextField txt_cliente;
    @FXML
    private TextField txt_rfc_cliente;
    @FXML
    private Label Label_NoVenta;

    boolean band = true;
    @FXML
    private TableView<TABLA_COMPRA> TABLA_COMPRA;
    @FXML
    private TableColumn<?, ?> COL_CANTIDAD;
    @FXML
    private TableColumn<?, ?> COL_UNIDAD;
    @FXML
    private TableColumn<?, ?> COL_DESCRIPCION;
    @FXML
    private TableColumn<?, ?> COL_PRECIO;
    @FXML
    private TableColumn<?, ?> COL_IVA;
    @FXML
    private TableColumn<?, ?> COL_IEPS;
    @FXML
    private TableColumn<?, ?> COL_ISR;
    @FXML
    private TableColumn<?, ?> COL_TOTAL;
    
    private ObservableList OBserbaleTablaVenta;
    @FXML
    private TextField txt_codigo;
    private double sumatotal;
    @FXML
    private Button Btn_Imprime_Venta;
    @FXML
    private TextField txt_n_cliente;
    @FXML
    private Label LabelClienteCorreo;
    @FXML
    private Button btn_buscar_articulos;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setBd(String bd, ObservableList<Usuario> DatosUsuarios) {
        this.DATABASE_NAME = bd;
        this.DatosUsuarios = DatosUsuarios;

        LabelUsuarioAtiende.setText(DatosUsuarios.get(0).getUSUARIO());
    }

  
    int entero;

   
    
    private void NuevaVenta(){
     ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "INSERT INTO Ncc_Venta(Venta_monto_total) VALUES(?)";
        conexion x = new conexion();
        cnx = x.crearConexion(DATABASE_NAME);
        
        try {
            ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, 0.00);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            Label_NoVenta.setText(rs.getString(1));
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        
    }


    @FXML
    private void AccionBotonCobrar(ActionEvent event) {
        
        String venta=Label_NoVenta.getText();
        
        if (isInteger(venta)) {
            CobrarVentaImpresa();
        }else{
        alertas.NotificacionError("Sin venta", "No es una venta");
        }
        
        
    }
    
       public boolean isInteger(String numer) {
        try {
            Integer.parseInt(numer);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String numer) {
        try {
            Double.parseDouble(numer);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void CobrarVentaImpresa() {
         FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Salidas/Cobro.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(CobroController.class.getName()).log(Level.SEVERE, null, e);
        }
        CobroController principal = Loader.getController();
        principal.setDatos(DATABASE_NAME,LabelSumaTotal.getText());

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("Cobro");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        ObservableList<CobroDatos> tem=principal.getDatos_Cobro();
        if (!tem.isEmpty()) {
            // ActualizaCobroCaja(metodo, null, pago, ActualizaStado);
            double temporl = Double.parseDouble(tem.get(0).getPAGO_CLIENTE());
            double temporl2 = Double.parseDouble(LabelSumaTotal.getText());
            String ActualizaStado;
            if (temporl == temporl2) {
                ActualizaStado = "2";
            } else {
                ActualizaStado = "1";
            }
            int NumeroCuentaPago=0;
            if (!tem.get(0).getCUENTA().isEmpty()) {
                NumeroCuentaPago=Integer.parseInt(tem.get(0).getCUENTA());
            }
            ActualizaCobroCaja(tem.get(0).getMETODO_PAGO(), NumeroCuentaPago, temporl, ActualizaStado, tem.get(0).getTIPO());
        }



    }
    
    
    private void ActualizaCobroCaja(String metodoPago, int NumeroCuenta, double pagocon, String ActualizaStado, String Credito_contado){
        
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        String sql ="INSERT INTO Ncc_Venta_Pagos("
                + "Pagos_Abono,"
                + "Pagos_IDQuienPago,"
                + "Pagos_IDMetodoPago,"
                + "Pagos_NumCuenta,"
                + "Pagos_ID_Venta) VALUES(?,?,?,?,?) ";
        
        String sql2="UPDATE Ncc_Venta SET Venta_contado_o_credito=?, Venta_Finiquitada=? WHERE Venta_ID=?";
        conexion x = new conexion();
        cnx = x.crearConexion(DATABASE_NAME);
        
        try {
            cnx.setAutoCommit(false);
            ps=cnx.prepareStatement(sql);
            ps2=cnx.prepareStatement(sql2);
            ps.setDouble(1, pagocon);
            ps.setString(2, DatosUsuarios.get(0).getID());
            ps.setString(3, metodoPago);
            ps.setInt(4, NumeroCuenta);
            ps.setString(5, Label_NoVenta.getText());
            ps.executeUpdate();
            
            ps2.setString(1, ActualizaStado);
            ps2.setString(2, Credito_contado);
            ps2.setString(3, Label_NoVenta.getText());
            ps2.executeUpdate();
            
            cnx.commit();
            double total = Double.parseDouble(LabelSumaTotal.getText());
            double resultado = total - pagocon;
            
            LabelSumaTotal.setText(resultado + "");
            
            BigDecimal Rtotal = new BigDecimal(resultado);
            Rtotal = Rtotal.setScale(2, RoundingMode.HALF_UP);
            LabelSumaTotal.setText(Rtotal.toString());
            
            
            
            
            
            double Verifica = Double.parseDouble(LabelSumaTotal.getText());
            if (Verifica <= 0.0) {
                HashMap<String, Object> ParametrosJasperReport = new HashMap<>();

                ParametrosJasperReport.put("ID_VENTA", Label_NoVenta.getText());
                ParametrosJasperReport.put("NOMBRE_EMPRESA", DatosUsuarios.get(0).getSUCURSAL_SELECCIONADA());
                ParametrosJasperReport.put("PAGO", pagocon);
                ParametrosJasperReport.put("NTOTAL", Double.parseDouble(LabelSumaTotal.getText()));

                InputStream fis2 = null;
                fis2 = this.getClass().getResourceAsStream("/Reportes/TicketVentaFinal.jasper");
                JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                JasperPrintManager.printReport(print, true);
                
                LimpiarTodo();
                alertas.NotificacionInformacion("Venta", "La venta ha sido liquidada");
            }
            
            
            
        } catch (SQLException ex) {
            try {
                if (cnx != null) {
                cnx.rollback();
            }
            } catch (SQLException e) {
            }
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if (cnx != null) {
                    cnx.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (SQLException e) {
            }
        }
        
       
        
    }
    
    
    private MetodosPago consultaMetodoPago(){
    
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_MetodosPago";
        conexion x = new conexion();
        cnx = x.crearConexion(DATABASE_NAME);

        List<MetodosPago> choices = new ArrayList<>();

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                choices.add(new MetodosPago(rs.getString("MPag_Codigo"), rs.getString("MPag_Descripcion")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        ChoiceDialog<MetodosPago> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Metodo Pagos");
        dialog.setHeaderText("Eleccion");
        dialog.setContentText("Seleccione un metodo de pago");

        // Traditional way to get the response value.
        Optional<MetodosPago> result = dialog.showAndWait();
         
           return result.get();

        
    }

    @FXML
    private void AccionBtn_Buscar(ActionEvent event) {
        IniciaBuscador();
    }

    private void IniciaBuscador() {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/ClasesObjetos/buscador/BProducto.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(BProductoController.class.getName()).log(Level.SEVERE, null, e);
        }
        BProductoController principal = Loader.getController();
        principal.setDatos(DATABASE_NAME);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("Busqueda");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        List<TablaBuscador> select = principal.getSeleccion();
        SumaEnTablaCompra(select);
        
    }

    @FXML
    private void AccionEditaPrecio(ActionEvent event) {
    }
    
    
    
    
    
    private void SumaEnTablaCompra(List<TablaBuscador> Lista) {
        OBserbaleTablaVenta = FXCollections.observableArrayList();

        List<TablaBuscador> select = Lista;

        if (TABLA_COMPRA.getItems().isEmpty()) {
            select.forEach((seleccionado) -> {

                double importe = seleccionado.getCatidad() * seleccionado.getPRECIO();

                double cien = 100;
                double IVA = (seleccionado.getIVA() / cien) * importe;
                double IEPS = (seleccionado.getIEPS() / cien) * importe;
                double ISR = (seleccionado.getISR() / cien) * importe;

                importe += IVA + IEPS + ISR;

                OBserbaleTablaVenta.add(new TABLA_COMPRA(
                        seleccionado.getID(),
                        seleccionado.getCatidad(),
                        seleccionado.getCOD_UNIDAD(),
                        seleccionado.getDESCRIPCION(),
                        seleccionado.getPRECIO(),
                        IVA,
                        IEPS,
                        ISR,
                        importe,
                        seleccionado.getIVA(),
                        seleccionado.getIEPS(),
                        seleccionado.getISR(),
                        seleccionado.getCOD_UNIDAD_CODIGO()
                ));

            });
        } else {

            select.forEach((seleccionado) -> {
                band = true;
                TABLA_COMPRA.getItems().forEach((T) -> {

                    if (T.getID() == seleccionado.getID()) {

                        T.setCANTIDAD(seleccionado.getCatidad() + T.getCANTIDAD());

                        double importe = T.getCANTIDAD() * T.getPRECIO();

                        double cien = 100;
                        double IVA = (seleccionado.getIVA() / cien) * importe;
                        double IEPS = (seleccionado.getIEPS() / cien) * importe;
                        double ISR = (seleccionado.getISR() / cien) * importe;

                        importe += IVA + IEPS + ISR;

                        T.setTOTAL(importe);
                        T.setIVA(IVA);
                        T.setIEPS(IEPS);
                        T.setISR(ISR);
                        band = false;
                    }

                });

                if (band) {
                    double importe = seleccionado.getCatidad() * seleccionado.getPRECIO();
                    double cien = 100;
                    double IVA = (seleccionado.getIVA() / cien) * importe;
                    double IEPS = (seleccionado.getIEPS() / cien) * importe;
                    double ISR = (seleccionado.getISR() / cien) * importe;

                    importe += IVA + IEPS + ISR;

                    OBserbaleTablaVenta.add(new TABLA_COMPRA(
                            seleccionado.getID(),
                            seleccionado.getCatidad(),
                            seleccionado.getCOD_UNIDAD(),
                            seleccionado.getDESCRIPCION(),
                            seleccionado.getPRECIO(),
                            IVA,
                            IEPS,
                            ISR,
                            importe,
                            seleccionado.getIVA(),
                            seleccionado.getIEPS(),
                            seleccionado.getISR(),
                            seleccionado.getCOD_UNIDAD_CODIGO()));
                }

            });

        }

        COL_CANTIDAD.setCellValueFactory(new PropertyValueFactory<>("CANTIDAD"));
        COL_UNIDAD.setCellValueFactory(new PropertyValueFactory<>("UNIDAD"));
        COL_DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("DESCRIPCION"));
        COL_PRECIO.setCellValueFactory(new PropertyValueFactory<>("PRECIO"));
        COL_IVA.setCellValueFactory(new PropertyValueFactory<>("IVA"));
        COL_IEPS.setCellValueFactory(new PropertyValueFactory<>("IEPS"));
        COL_ISR.setCellValueFactory(new PropertyValueFactory<>("ISR"));
        COL_TOTAL.setCellValueFactory(new PropertyValueFactory<>("TOTAL"));

        //TABLA_COMPRA.setItems(OBserbaleTablaVenta);
        TABLA_COMPRA.getItems().addAll(OBserbaleTablaVenta);
        sumaTotal();
    }

    @FXML
    private void AccionBuscarCliente(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (txt_rfc_cliente.getText().isEmpty()) {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("/ClasesObjetos/buscador/BClientes.fxml"));

                try {
                    Loader.load();
                } catch (IOException e) {
                    Logger.getLogger(BClientesController.class.getName()).log(Level.SEVERE, null, e);
                }
                BClientesController principal = Loader.getController();
                principal.setDatos(DATABASE_NAME);

                Parent p = Loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.setTitle("Busqueda");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                LimpiarDatosClientes();
                txt_rfc_cliente.setText(principal.getRFC());
                BuscarCliente();
                
            } else {
                txt_rfc_cliente.setEditable(false);
                BuscarCliente();
            }
        }
    }

    @FXML
    private void AccionDobleClicCliente(MouseEvent event) {
        if (event.getClickCount() == 2) {
            txt_rfc_cliente.setEditable(true);
        }
    }
    
    
    private void BuscarCliente(){
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Clientes WHERE Client_RFC=?";
        conexion x = new conexion();
        cnx = x.crearConexion(DATABASE_NAME);
        
        try {
            ps=cnx.prepareStatement(sql);
            ps.setString(1, txt_rfc_cliente.getText());
            
            rs=ps.executeQuery();
            
            
            if (rs.next()) {
                 txt_rfc_cliente.setText(rs.getString("Client_RFC"));
                 txt_cliente.setText(rs.getString("Client_Nombre"));
                 LabelClienteCorreo.setText(rs.getString("Client_email"));
                 txt_n_cliente.setText(rs.getString("Client_Id"));
            }else{
            alertas.NotificacionWarning("No encontrado", "El cliente no se encuentra");
            txt_rfc_cliente.setEditable(true);
            LimpiarDatosClientes();
            }
                           
               
      
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
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
                Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    private void LimpiarDatosClientes(){
  
        txt_rfc_cliente.clear();
        txt_cliente.clear();
        LabelClienteCorreo.setText("");
        txt_n_cliente.clear();
    }
    
    private void LimpiarTodo() {
        txt_rfc_cliente.clear();
        txt_cliente.clear();
        LabelClienteCorreo.setText("");
        txt_n_cliente.clear();
        
        TABLA_COMPRA.getItems().clear();
        Label_NoVenta.setText("");
        LabelSumaTotal.setText("");
        Boton_Cobrar.setDisable(true);
        Btn_Imprime_Venta.setDisable(false);
        btn_buscar_articulos.setDisable(false);
    }
    
    
      private void sumaTotal() {
        sumatotal = 0.0;
        TABLA_COMPRA.getItems().forEach((suma) -> {

            sumatotal += suma.getTOTAL();

            BigDecimal total = new BigDecimal(sumatotal);
            sumatotal = total.setScale(2, RoundingMode.HALF_UP).doubleValue();

        });

        LabelSumaTotal.setText(Double.toString(sumatotal));
    }

    @FXML
    private void AccionImprimeVenta(ActionEvent event) {

        if (txt_n_cliente.getText().isEmpty() || TABLA_COMPRA.getItems().isEmpty()) {
            alertas.NotificacionWarning("Sin cliente", "Debe asignar cliente a venta");
        } else {
            boolean respuesta = alertas.alertaConfirmacion("Impresion", "", "¿Desea crear venta?");
            if (respuesta) {
                
                 CrearImpresionVenta();
            }
        }
    }
    
    
    private void CrearImpresionVenta(){
    ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        String sql = "INSERT INTO Ncc_Venta("
                + "Venta_Cliente_ID,"
                + "Venta_usuario_creador,"
                + "Venta_monto_total,"
                + "Venta_contado_o_credito) VALUES(?,?,?,?)";
        
        String sql2 = "INSERT INTO Ncc_Venta_Detalle("
                + "DetalleVenta_Cantidad,"
                + "DetalleVenta_Descripcion,"
                + "DetalleVenta_Precio_unitario,"
                + "DetalleVenta_Iva,"
                + "DetalleVenta_Total,"
                + "DetalleVenta_Venta_ID,"
                + "DetalleVenta_Unidad,"
                + "DetalleVenta_IEPS,"
                + "DetalleVenta_ISR,"
                + "DetalleVenta_Iva_Real,"
                + "DetalleVenta_IEPS_Real,"
                + "DetalleVenta_ISR_Real,"
                + "DetalleVenta_Unidad_Real,"
                + "DetalleVenta_ID_Producto) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        conexion x = new conexion();
        cnx = x.crearConexion(DATABASE_NAME);
        
        try {
            cnx.setAutoCommit(false);
            ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps2 = cnx.prepareStatement(sql2);
            
            
            ps.setString(1, txt_n_cliente.getText());
            ps.setString(2, LabelUsuarioAtiende.getText());
            ps.setString(3, LabelSumaTotal.getText());
            ps.setInt(4, 0);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            
            
            
            for (ClasesObjetos.TABLA_COMPRA item : TABLA_COMPRA.getItems()) {
                ps2.setDouble(1, item.getCANTIDAD());
                ps2.setString(2, item.getDESCRIPCION());
                ps2.setDouble(3, item.getPRECIO());
                ps2.setDouble(4, item.getIVA());
                ps2.setDouble(5, item.getTOTAL());
                //Numero de venta
                ps2.setString(6, rs.getString(1));

                ps2.setString(7, item.getUNIDAD());
                ps2.setDouble(8, item.getIEPS());
                ps2.setDouble(9, item.getISR());

                ps2.setInt(10, item.getIVA_REAL());
                ps2.setInt(11, item.getIEPS_REAL());
                ps2.setInt(12, item.getISR_REAL());
                ps2.setString(13, item.getUNIDAD_REAL());
                ps2.setInt(14, item.getID());
                ps2.executeUpdate();
            }
            cnx.commit();
            
            
            
            HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
            ParametrosJasperReport.put("ID_VENTA", rs.getString(1));
            ParametrosJasperReport.put("NOMBRE_EMPRESA", DatosUsuarios.get(0).getSUCURSAL_SELECCIONADA());

            InputStream fis2 = null;
            fis2 = this.getClass().getResourceAsStream("/Reportes/TicketVenta.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
            JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
            JasperPrintManager.printReport(print, true);
            
            Label_NoVenta.setText(rs.getString(1));
            Boton_Cobrar.setDisable(false);
            Btn_Imprime_Venta.setDisable(true);
            btn_buscar_articulos.setDisable(true);
        
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (cnx != null) {
                    cnx.rollback();
                    alertas.NotificacionError("Error", "No se pudo crear venta intente de nuevo");
                }
            } catch (SQLException e) {
            }
        } catch (JRException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
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
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                alertas.alertaDeExcepcion("Error", "bd", "", e.getMessage());
            }
        }
    }

    @FXML
    private void AccionConsultaVenta(ActionEvent event) {
        ObservableList<TABLA_COMPRA> ObsTem=FXCollections.observableArrayList();
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Consulta Venta");
        dialog.setHeaderText("Escria el numero de venta");
        //dialog.setContentText("Please enter your name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ResultSet rs = null;
            PreparedStatement ps = null;
            Connection cnx = null;
            String sql = "SELECT * FROM Ncc_Venta "
                    + "INNER JOIN Ncc_Venta_Detalle ON Ncc_Venta_Detalle.DetalleVenta_Venta_ID = Ncc_Venta.Venta_ID\n"
                    + "INNER JOIN Ncc_Clientes ON Ncc_Clientes.Client_Id = Ncc_Venta.Venta_Cliente_ID "
                    + "WHERE Venta_ID=?";
            conexion x = new conexion();
            cnx = x.crearConexion(DATABASE_NAME);
            try {
                ps=cnx.prepareStatement(sql);
                ps.setString(1, result.get());
                
                rs=ps.executeQuery();
                
                if (rs.next()) {
                    
                    txt_rfc_cliente.setText(rs.getString("Client_RFC"));
                    txt_cliente.setText(rs.getString("Client_Nombre"));
                    txt_n_cliente.setText(rs.getString("Client_Id"));
                   LabelClienteCorreo.setText(rs.getString("Client_email"));
                   
                   Label_NoVenta.setText(rs.getString("Venta_ID"));
                   LabelUsuarioAtiende.setText(rs.getString("Venta_usuario_creador"));
                   LabelSumaTotal.setText(rs.getString("Venta_monto_total"));
                    rs.previous();
                    while (rs.next()) {                        
                        ObsTem.add(new TABLA_COMPRA(
                                    rs.getInt("DetalleVenta_ID_Producto"),
                                    rs.getDouble("DetalleVenta_Cantidad"),
                                    rs.getString("DetalleVenta_Unidad"),
                                    rs.getString("DetalleVenta_Descripcion"),
                                    rs.getDouble("DetalleVenta_Precio_unitario"),
                                    rs.getDouble("DetalleVenta_Iva"),
                                    rs.getDouble("DetalleVenta_IEPS"),
                                    rs.getDouble("DetalleVenta_ISR"),
                                    rs.getDouble("DetalleVenta_Total"),
                                    rs.getInt("DetalleVenta_Iva_Real"),
                                    rs.getInt("DetalleVenta_IEPS_Real"),
                                    rs.getInt("DetalleVenta_ISR_Real"),
                                    rs.getString("DetalleVenta_Unidad_Real")
                        ));
                    }
                    
                    COL_CANTIDAD.setCellValueFactory(new PropertyValueFactory<>("CANTIDAD"));
                    COL_UNIDAD.setCellValueFactory(new PropertyValueFactory<>("UNIDAD"));
                    COL_DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("DESCRIPCION"));
                    COL_PRECIO.setCellValueFactory(new PropertyValueFactory<>("PRECIO"));
                    COL_IVA.setCellValueFactory(new PropertyValueFactory<>("IVA"));
                    COL_IEPS.setCellValueFactory(new PropertyValueFactory<>("IEPS"));
                    COL_ISR.setCellValueFactory(new PropertyValueFactory<>("ISR"));
                    COL_TOTAL.setCellValueFactory(new PropertyValueFactory<>("TOTAL"));
                    
                    TABLA_COMPRA.setItems(ObsTem);
                    VerificaPagosVentas();
                }else{
                alertas.NotificacionWarning("Sin resultados", "La venta "+result.get()+" no existe");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
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
        }
    }
    
    
   private void VerificaPagosVentas(){
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT SUM(Pagos_Abono) FROM Ncc_Venta_Pagos WHERE Pagos_ID_Venta=?";
        conexion x = new conexion();
        cnx = x.crearConexion(DATABASE_NAME);

        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, Label_NoVenta.getText());
            rs = ps.executeQuery();

            rs.next();
            double pagos = rs.getDouble(1);
            double Total = Double.parseDouble(LabelSumaTotal.getText());
            double resultado = Total - pagos;
            
            BigDecimal ResultadoFinal=new BigDecimal(resultado);
            ResultadoFinal=ResultadoFinal.setScale(2, RoundingMode.HALF_UP);
            LabelSumaTotal.setText(ResultadoFinal + "");
            
            double Comparar = Double.parseDouble(LabelSumaTotal.getText());
            if (Comparar == 0.00) {
                Boton_Cobrar.setDisable(true);
                btn_buscar_articulos.setDisable(true);
            }else{
            Boton_Cobrar.setDisable(false);
            Btn_Imprime_Venta.setDisable(true);
            btn_buscar_articulos.setDisable(true);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
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
    }

}
