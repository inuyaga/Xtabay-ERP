package Proveedores;

import ClasesObjetos.MetodosPago;
import ClasesObjetos.Usuario;
import ClasesObjetos.buscador.BComprasConsultaController;
import ClasesObjetos.buscador.BComprasPorPagar_PagosController;
import Entradas.ComprasController;
import alertas.alertas;
import bd.conexion;
import cma.carmelo.jasperviewerfx.JasperFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeString.trim;
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
public class PagoAproveedoresController implements Initializable {

    private String BaseDatos;
    private ObservableList<Usuario> USUARIO;
    private double SaldoPendienteGlobal;
    @FXML
    private JFXTextField TXTNUMERO;
    @FXML
    private Label LBLSALDOCOMPRA;
    @FXML
    private JFXTextField TXTCOMPRA;
    @FXML
    private Label LBLMONTOCOMPRA;
    @FXML
    private JFXTextField TXTPROVEEDOR;
    @FXML
    private Label LBLSALDOPROVEEDOR;
    @FXML
    private TextArea TXADETALLEPROVEEDOR;
    @FXML
    private JFXTextField TXTPAGO;
    @FXML
    private ComboBox<MetodosPago> CMBPAGO;
    @FXML
    private JFXTextArea TXANOTA;
    @FXML
    private JFXTextArea TXAREFERENCIA;
    @FXML
    private JFXButton BTNGUARDAR;
    @FXML
    private Button BTNNUEVO;
    @FXML
    private Button BTNCONSULTAR;
    @FXML
    private Button BTNELIMINAR;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        TXTNUMERO.setDisable(true);
//        LBLSALDOCOMPRA.setText("Saldo:");
        TXTCOMPRA.setDisable(true);
//        LBLMONTOCOMPRA.setText("Monto:");
        TXTPROVEEDOR.setDisable(true);
//        LBLSALDOPROVEEDOR.setText("Saldo:");
        TXADETALLEPROVEEDOR.setDisable(true);
        TXTPAGO.setDisable(true);
        TXANOTA.setDisable(true);
        TXAREFERENCIA.setDisable(true);
        BTNGUARDAR.setVisible(false);
    }    
    
    public void setBasedeDatos(String bd, ObservableList<Usuario> DatosUsuarios)
    {
        this.BaseDatos=bd;
        this.USUARIO=DatosUsuarios;
        LlenaMPago();
    }
    
    public void resetForm()    
    {
        TXTNUMERO.setText("");
        LBLSALDOCOMPRA.setText("Saldo pendiente:");
        TXTCOMPRA.setText("");
        LBLMONTOCOMPRA.setText("Monto total:");
        TXTPROVEEDOR.setText("");
        LBLSALDOPROVEEDOR.setText("Saldo a proveedor:");
        TXADETALLEPROVEEDOR.setText("");
        TXTPAGO.setText("");
        TXANOTA.setText("");
        TXAREFERENCIA.setText("");
        BTNGUARDAR.setVisible(false);
        SaldoPendienteGlobal=0;
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
                CMBPAGO.getItems().add(new MetodosPago(rs.getString("MPag_Codigo"), rs.getString("MPag_Descripcion")));
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
        CMBPAGO.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void NUEVONUMERO()
    {
        resetForm();
        TXTNUMERO.setDisable(true);
        LBLSALDOCOMPRA.setText("Saldo pendiente:");
        TXTCOMPRA.setDisable(false);
        LBLMONTOCOMPRA.setText("Monto total:");
        TXTPROVEEDOR.setDisable(true);
        LBLSALDOPROVEEDOR.setText("Saldo a proveedor:");
        TXADETALLEPROVEEDOR.setDisable(true);
        TXTPAGO.setDisable(false);
        TXANOTA.setDisable(false);
        TXAREFERENCIA.setDisable(false);
        
        ResultSet rs = null;
        PreparedStatement ps=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sq3l = "INSERT INTO Ncc_Pagos(Pagos_IDCompra,Pagos_Abono,Pagos_IDQuienPago,Pagos_IDMetodoPago)"
                + "VALUES(?,?,?,?)";
        try {
            ps = cnx.prepareStatement(sq3l, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, 0);
            ps.setDouble(2, 0);
            ps.setDouble(3, 0);
            ps.setDouble(4, 0);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            TXTNUMERO.setText(rs.getString(1));
            BTNGUARDAR.setVisible(true);
            TXTCOMPRA.requestFocus();
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "bd", "", e.getMessage());
        }
        
    }
    
    @FXML
    public void GuardarPago()
    {
        if(!trim(TXTCOMPRA.getText()).isEmpty() && !trim(TXTPAGO.getText()).isEmpty())
        {
            try {
                ResultSet x=ValidarNumeroCompra(trim(TXTCOMPRA.getText()));
                if(x.next()){
                    if(SaldoPendienteGlobal <= 0)
                    {
                        alertas.NotificacionInformacion("No hay saldo pendiente", "No existe saldo pendiente para esta compra, intente con otra");
                    }else{
                        if(Double.parseDouble(TXTPAGO.getText()) > SaldoPendienteGlobal)
                        {
                            alertas.NotificacionInformacion("Pago no válido", "El pago es mayor que el saldo pendiente de la compra");
                        }else{
                            LBLMONTOCOMPRA.setText(x.getString("Compra_monto_total"));
                            ResultSet rs = null;
                            PreparedStatement ps=null;
                            PreparedStatement ps2=null;
                            Connection cnx = null;
                            conexion conexbd=new conexion();
                            conexbd.crearConexion();
                            cnx=conexbd.getConexion();
                            String sq3l = "UPDATE Ncc_Pagos SET Pagos_IDCompra=?,Pagos_Abono=?,Pagos_IDQuienPago=?,Pagos_IDMetodoPago=?, Pagos_Referencia=?, Pagos_Observacion=?,Pagos_IDProveedor=? WHERE "
                                    + "Pagos_ID=?";
                            String sql="UPDATE Ncc_Compras SET Compra_Finiquitada=? WHERE Compra_ID=?";
                            try {
                                cnx.setAutoCommit(false);
                                ps = cnx.prepareStatement(sq3l, Statement.RETURN_GENERATED_KEYS);
                                ps.setString(1, trim(TXTCOMPRA.getText()));
                                ps.setString(2, trim(TXTPAGO.getText()));
                                ps.setString(3, USUARIO.get(0).getID());
                                ps.setString(4, CMBPAGO.getValue().getCODIGO());
                                ps.setString(5, trim(TXAREFERENCIA.getText()));
                                ps.setString(6, trim(TXANOTA.getText()));
                                ps.setString(7, trim(TXTPROVEEDOR.getText()));
                                ps.setString(8, trim(TXTNUMERO.getText()));
                                ps.executeUpdate();
                                
                                if(SaldoPendienteGlobal == Double.parseDouble(TXTPAGO.getText()))
                                {
                                    ps2=cnx.prepareStatement(sql);
                                    ps2.setInt(1, 2);
                                    ps2.setString(2, trim(TXTCOMPRA.getText()));
                                    ps2.executeUpdate();
                                }
                                
                                BTNGUARDAR.setVisible(false);
                                SaldoPendienteGlobal=0;
                                cnx.commit();
                                alertas.arlertaInformacion("Pago con éxito", "El cobro fue generado con éxito");
                                boolean respuesta = alertas.alertaConfirmacion("Impresion", "", "¿Desea imprimir?");
                                if (respuesta)
                                {
                                    try
                                    {
                                        HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                                        ParametrosJasperReport.put("NumeroDeRecibo", Integer.parseInt(TXTNUMERO.getText()));
                                        InputStream fis2 = null;
                                        fis2 = this.getClass().getResourceAsStream("/Reportes/ReciboDePagoProveedores.jasper");
                                        JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                                        JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                                        JasperFX jpFX = new JasperFX(print);
                                        jpFX.show();
                                        resetForm();
                                    } catch (NumberFormatException | JRException e)
                                    {
                                        alertas.alertaDeExcepcion("Error al generar el ticket", "ticket", "Jasper", e.getMessage());
                                    }
                                }else
                                {
                                    resetForm();
                                }
                            }catch (SQLException e)
                            {
                                cnx.rollback();
                                alertas.alertaDeExcepcion("Error", "bd", "", e.getMessage());
                            }
                        }
                    }
                }else{
                    alertas.NotificacionInformacion("La compra no existe o no esta en condiciones de pago", "La compra no existe, no tiene factura enlazada o ya fue finiquitada");
                }
            } catch (SQLException e) {
                alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
            }
        }else{
            alertas.NotificacionInformacion("Captura la información solicitada", "No deje campos vacíos");
        }
    }
    // regresa informaciòn de la compra como es el salfo de la compra 
    public ResultSet ValidarNumeroCompra(String compra)
    {
        PreparedStatement ps2 = null;
        Connection cnx = null;
        ResultSet x = null;
        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        
        String sql2="SELECT * FROM Ncc_Compras LEFT JOIN Ncc_Proveedores ON Ncc_Proveedores.Pro_ID=Ncc_Compras.Compra_Proveedor_ID WHERE Ncc_Compras.Compra_ID = ? and Ncc_Compras.Compra_Entrega=1 and (Ncc_Compras.Compra_Finiquitada=1 OR Ncc_Compras.Compra_Finiquitada=0)";
        try
        {
            ps2=cnx.prepareStatement(sql2);
            ps2.setString(1, trim(compra));
            x=ps2.executeQuery();
        } catch (SQLException e) {
            x=null;
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
        }
        return x;
    }
    
    public ResultSet ValidarsumasaldoproveedorCompra(String proveedor)
    {
        PreparedStatement ps2 = null;
        Connection cnx = null;
        ResultSet x = null;
        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        
        String sql2="SELECT SUM(Compra_monto_total) as saldo FROM Ncc_Compras WHERE Compra_Proveedor_ID = ? ";
        try
        {
            ps2=cnx.prepareStatement(sql2);
            ps2.setString(1, trim(proveedor));
            x=ps2.executeQuery();
        } catch (SQLException e) {
            x=null;
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
        }
        return x;
    }
    
    public ResultSet ValidarPagosAproveedor(String compra)
    {
        PreparedStatement ps2 = null;
        Connection cnx = null;
        ResultSet x = null;
        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        
        String sql2="SELECT SUM(Pagos_Abono) as Pagado FROM Ncc_Pagos WHERE Pagos_IDCompra = ? ";
        try
        {
            ps2=cnx.prepareStatement(sql2);
            ps2.setString(1, trim(compra));
            x=ps2.executeQuery();
        } catch (SQLException e) {
            x=null;
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
        }
        return x;
    }
    
    public ResultSet ValidarPAgosAproveedor(String proveedor)
    {
        PreparedStatement ps2 = null;
        Connection cnx = null;
        ResultSet x = null;
        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        
        String sql2="SELECT SUM(Pagos_Abono) as deuda FROM Ncc_Pagos WHERE Pagos_IDProveedor = ? ";
        try
        {
            ps2=cnx.prepareStatement(sql2);
            ps2.setString(1, trim(proveedor));
            x=ps2.executeQuery();
        } catch (SQLException e) {
            x=null;
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
        }
        return x;
    }
    
    @FXML
    public void CONSULTARNUMERO()
    {
        resetForm();
        TXTNUMERO.setDisable(false);
        LBLSALDOCOMPRA.setText("Saldo pendiente:");
        TXTCOMPRA.setDisable(true);
        LBLMONTOCOMPRA.setText("Monto total:");
        TXTPROVEEDOR.setDisable(true);
        LBLSALDOPROVEEDOR.setText("Saldo a proveedor:");
        TXADETALLEPROVEEDOR.setDisable(true);
        TXTPAGO.setDisable(true);
        TXANOTA.setDisable(true);
        TXAREFERENCIA.setDisable(true);
        BTNGUARDAR.setVisible(false);
        TXTNUMERO.requestFocus();
    }
    
    @FXML
    public void ELIMINARNUMERO()
    {   
        if(TXTNUMERO.isDisable())
        {
            TXTNUMERO.setDisable(false);
            TXTNUMERO.requestFocus();
        }else{
            boolean respuesta = alertas.alertaConfirmacion("Eliminaciòn", "", "¿Desea eliminar este pago?");
            if (respuesta)
            {
                PreparedStatement ps = null;
                int rs = 0;
                String sql = "DELETE FROM Ncc_Pagos WHERE Pagos_ID=?";
                Connection cnx = null;
                conexion x = new conexion();
                cnx = x.crearConexion(BaseDatos);
                TXTNUMERO.setDisable(false);
                LBLSALDOCOMPRA.setText("Saldo pendiente:");
                TXTCOMPRA.setDisable(true);
                LBLMONTOCOMPRA.setText("Monto total:");
                TXTPROVEEDOR.setDisable(true);
                LBLSALDOPROVEEDOR.setText("Saldo a proveedor:");
                TXADETALLEPROVEEDOR.setDisable(true);
                TXTPAGO.setDisable(true);
                TXANOTA.setDisable(true);
                TXAREFERENCIA.setDisable(true);
                BTNGUARDAR.setVisible(false);
                try
                {
                    cnx.setAutoCommit(false);
                    ps = cnx.prepareStatement(sql);
                    ps.setString(1, trim(TXTNUMERO.getText()));
                    ps.executeUpdate();
                    rs = ps.getUpdateCount();
                    cnx.commit();

                    if(rs > 0)
                    {
                        alertas.NotificacionInformacion("Eliminación", "El pago N°:" + TXTNUMERO.getText() + " ha sido eliminado");
                        resetForm();
                    }else{
                        alertas.NotificacionInformacion("Sin cambios", "El pago N°:" + TXTNUMERO.getText() + " no se encontró, no se aplicó ningún cambio");
                    }
                } catch (SQLException e){
                    if (cnx != null) {
                        try {
                            cnx.rollback();
                            alertas.NotificacionError("Error", "No se realizaron los cambios");
                        } catch (SQLException ex1) {
                            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, e);
                }finally {
                    try {
                        if (cnx != null) {
                            cnx.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException ex) {
                         alertas.alertaDeExcepcion("Error", "Ocurrio un error", ex.getLocalizedMessage(), ex.getMessage());
                    }
                }
            }else
            {
                resetForm();
            }
               
        }
    }

    private void principal()
    {
        ResultSet x=ValidarNumeroCompra(trim(TXTCOMPRA.getText()));
            ResultSet z=ValidarPagosAproveedor(trim(TXTCOMPRA.getText()));
            
            try {
                if(x.next())
                {
                    double pa=0;
                    if(z.next()){
                        if(z.getString("Pagado")!= null){
                            pa=Double.parseDouble(z.getString("Pagado"));
                        }
                    }
                    double monto,resul;
                    SaldoPendienteGlobal=Double.parseDouble(x.getString("Compra_monto_total"))-pa;
                    BigDecimal total = new BigDecimal(SaldoPendienteGlobal);
                    SaldoPendienteGlobal = total.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    BigDecimal montoo = new BigDecimal(x.getString("Compra_monto_total"));
                    monto = montoo.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    double saldo=0;
                    double pagos=0;
                    double resultado;
                    LBLSALDOCOMPRA.setText("Saldo pendiente: "+SaldoPendienteGlobal);
                    LBLMONTOCOMPRA.setText("Monto total:"+monto);
                    TXADETALLEPROVEEDOR.setText(x.getString("Pro_RFC")+":\n "+x.getString("Pro_Proveedor"));
                    TXTPROVEEDOR.setText(x.getString("Compra_Proveedor_ID"));
                    TXTPAGO.setText(""+SaldoPendienteGlobal);
                    ResultSet y=ValidarPAgosAproveedor(trim(TXTPROVEEDOR.getText()));
                    ResultSet w=ValidarsumasaldoproveedorCompra(trim(TXTPROVEEDOR.getText()));
                    if(y.next())
                    {
                        if(y.getString("deuda") != null){
                            pagos=Double.parseDouble(y.getString("deuda"));
                        }else{
                            pagos=0;
                        }
                    }else{
                        pagos=0;
                    }
                    if(w.next())
                    {
                        saldo=Double.parseDouble(w.getString("saldo"));
                    }else{
                        saldo=0;
                    }
                    resultado=saldo-pagos;
                    BigDecimal res = new BigDecimal(resultado);
                    resul = res.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    LBLSALDOPROVEEDOR.setText("Saldo: "+resul);
                }else{
                    LBLMONTOCOMPRA.setText("Monto total:");
                    TXADETALLEPROVEEDOR.setText("");
                    TXTPROVEEDOR.setText("");
                    TXTPAGO.setText("");
                    LBLSALDOPROVEEDOR.setText("Saldo:");
                    alertas.NotificacionInformacion("La compra no existe o no esta en condiciones de pago", "La compra no existe, no tiene factura enlazada o ya fue finiquitada");
                    SaldoPendienteGlobal=0;
                }
            } catch (SQLException e) {
                alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
            }
    }
    
    private void principalDos()
    {
        ResultSet x=ValidarNumeroCompra(trim(TXTCOMPRA.getText()));
        ResultSet z=ValidarPagosAproveedor(trim(TXTCOMPRA.getText()));

        try {
            if(x.next())
            {
                double pa=0;
                if(z.next()){
                    if(z.getString("Pagado")!= null){
                        pa=Double.parseDouble(z.getString("Pagado"));
                    }
                }
                double monto,resul;
                SaldoPendienteGlobal=Double.parseDouble(x.getString("Compra_monto_total"))-pa;
                BigDecimal total = new BigDecimal(SaldoPendienteGlobal);
                SaldoPendienteGlobal = total.setScale(2, RoundingMode.HALF_UP).doubleValue();
                BigDecimal montoo = new BigDecimal(x.getString("Compra_monto_total"));
                monto = montoo.setScale(2, RoundingMode.HALF_UP).doubleValue();
                double saldo=0;
                double pagos=0;
                double resultado;
                LBLSALDOCOMPRA.setText("Saldo pendiente: "+SaldoPendienteGlobal);
                LBLMONTOCOMPRA.setText("Monto total:"+monto);
                TXADETALLEPROVEEDOR.setText(x.getString("Pro_RFC")+":\n "+x.getString("Pro_Proveedor"));
                TXTPROVEEDOR.setText(x.getString("Compra_Proveedor_ID"));
//                TXTPAGO.setText(""+SaldoPendienteGlobal);
                ResultSet y=ValidarPAgosAproveedor(trim(TXTPROVEEDOR.getText()));
                ResultSet w=ValidarsumasaldoproveedorCompra(trim(TXTPROVEEDOR.getText()));
                if(y.next())
                {
                    if(y.getString("deuda") != null){
                        pagos=Double.parseDouble(y.getString("deuda"));
                    }else{
                        pagos=0;
                    }
                }else{
                    pagos=0;
                }
                if(w.next())
                {
                    saldo=Double.parseDouble(w.getString("saldo"));
                }else{
                    saldo=0;
                }
                resultado=saldo-pagos;
                BigDecimal res = new BigDecimal(resultado);
                resul = res.setScale(2, RoundingMode.HALF_UP).doubleValue();
                LBLSALDOPROVEEDOR.setText("Saldo: "+resul);
            }else{
                LBLMONTOCOMPRA.setText("Monto total:");
                TXADETALLEPROVEEDOR.setText("");
                TXTPROVEEDOR.setText("");
                TXTPAGO.setText("");
                LBLSALDOPROVEEDOR.setText("Saldo a proveedor:");
                alertas.NotificacionInformacion("La compra no existe o no esta en condiciones de pago", "La compra no existe, no tiene factura enlazada o ya fue finiquitada");
                SaldoPendienteGlobal=0;
            }
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
        }
    }
    
    private void ConsultaReciboPago()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT Pagos_IDCompra,Pagos_Abono,Pagos_Referencia,Pagos_Observacion,Pagos_IDMetodoPago,MPag_Descripcion FROM Ncc_Pagos INNER JOIN Ncc_MetodosPago ON Pagos_IDMetodoPago=MPag_Codigo WHERE Pagos_ID = ? LIMIT 1";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);
        
        try 
        {
            ps=cnx.prepareStatement(sql);
            ps.setString(1, trim(TXTNUMERO.getText()));
            rs=ps.executeQuery();
            if(rs.next())
            {
                TXTCOMPRA.setText(rs.getString("Pagos_IDCompra"));
                TXANOTA.setText(rs.getString("Pagos_Observacion"));
                TXAREFERENCIA.setText(rs.getString("Pagos_Referencia"));
                CMBPAGO.setValue(new MetodosPago(rs.getString("Pagos_IDMetodoPago"),rs.getString("MPag_Descripcion")));
                TXTPAGO.setText(rs.getString("Pagos_Abono"));
            }else{
                TXTCOMPRA.setText("");
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
    }
    @FXML
    private void AccionValidarCompra(KeyEvent event)
    {
        if(event.getCode()==KeyCode.ENTER)
        {
            if(trim(TXTCOMPRA.getText()).isEmpty())
            {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("/ClasesObjetos/buscador/BComprasPorPagar_Pagos.fxml"));

                try {
                    Loader.load();
                } catch (IOException e) {
                    Logger.getLogger(BComprasPorPagar_PagosController.class.getName()).log(Level.SEVERE, null, e);
                }
                BComprasPorPagar_PagosController principal = Loader.getController();
                principal.setBasedeDatos(BaseDatos);

                Parent p = Loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.setTitle("Busqueda");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                TXTCOMPRA.setText(principal.getIDCOMPRA()+"");
            }else{
                principal();
            }
        }
    }

    @FXML
    private void accionbusquedaconsultar(KeyEvent event) 
    {
        if(event.getCode()==KeyCode.ENTER)
        {
            if(trim(TXTNUMERO.getText()).isEmpty())
            {
                resetForm();
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("/ClasesObjetos/buscador/BComprasConsulta.fxml"));

                try {
                    Loader.load();
                } catch (IOException e) {
                    Logger.getLogger(BComprasConsultaController.class.getName()).log(Level.SEVERE, null, e);
                }
                BComprasConsultaController principal = Loader.getController();
                principal.setBasedeDatos(BaseDatos);

                Parent p = Loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.setTitle("Busqueda");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                TXTNUMERO.setText(principal.getIDCOMPRA()+"");
                if(!trim(TXTNUMERO.getText()).isEmpty())
                {
                    BTNGUARDAR.setVisible(false);
                    ConsultaReciboPago();
                    principalDos();
                }
            }else{
                BTNGUARDAR.setVisible(false);
                ConsultaReciboPago();
                principalDos();
            }
        }
    }
}