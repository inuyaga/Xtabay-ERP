package AltaProd;

import ClasesObjetos.Marcas;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Clientes.Departamento;
import bd.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static jdk.nashorn.internal.objects.NativeString.trim;
import alertas.alertas;
/**
 * FXML Controller class
 *
 * @author Sistemas2
 *
 */
public class AltaProdController implements Initializable {

    @FXML
    private JFXTextField TXTCODIGOINTERNO;
    @FXML
    private JFXTextField TEXTDESCR;
    @FXML
    private JFXTextField TXTSTOCK;
    @FXML
    private ComboBox<Marcas> COMBOMARCA;
    @FXML
    private JFXTextField TXTUNIDAD;
    @FXML
    private JFXTextField TXTCODBARRAS;
    @FXML
    private JFXTextField TXTPRECIOCOSTO;
    @FXML
    private ComboBox<Departamento> CMBDEPARTAMENTO;
    @FXML
    private JFXTextField TXTPRODSERV;
    @FXML
    private Label LblPorcentaje1;
    @FXML
    private JFXTextField TXTPORCENTAJE1;
    @FXML
    private Label LblPorcentaje2;
    @FXML
    private JFXTextField TXTPORCENTAJE2;
    @FXML
    private Label LblPorcentaje3;
    @FXML
    private JFXTextField TXTPORCENTAJE3;
    @FXML
    private Label LblPorcentaje4;
    @FXML
    private JFXTextField TXTPORCENTAJE4;
    @FXML
    private Label LblPorcentaje5;
    @FXML
    private JFXTextField TXTPORCENTAJE5;
    @FXML
    private Label LblRespuestaDeBusqueda;

    private String BaseDatos;
    private ObservableList<Departamento> OBSerMetPagos;
    private ObservableList<Marcas> OBSerMarcas;
    @FXML
    private JFXTextField TXTIVA;
    @FXML
    private JFXTextField TXTIEPS;
    @FXML
    private JFXTextField TXTISR;
    @FXML
    private JFXTextField txtPROVEEDOR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje1, TXTPORCENTAJE1);
        GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje2, TXTPORCENTAJE2);
        GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje3, TXTPORCENTAJE3);
        GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje4, TXTPORCENTAJE4);
        GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje5, TXTPORCENTAJE5);
        TXTPRECIOCOSTO.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje1, TXTPORCENTAJE1,newValue);
                GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje2, TXTPORCENTAJE2,newValue);
                GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje3, TXTPORCENTAJE3,newValue);
                GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje4, TXTPORCENTAJE4,newValue);
                GenerarValores(LblRespuestaDeBusqueda, LblPorcentaje5, TXTPORCENTAJE5,newValue);
            }
        });
    }
    
    public void GenerarValores(Label Respuesta,Label resultado, TextField CajaCalculo)
    {
        CajaCalculo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (TXTPRECIOCOSTO.getText().isEmpty()) {
                    Respuesta.setText("Introduzca precio costo");
                } else {
                    if (isDouble(CajaCalculo.getText()) && isDouble(TXTPRECIOCOSTO.getText())) {
                        double costo = Double.parseDouble(TXTPRECIOCOSTO.getText());
                        double porcentaje = (Double.parseDouble(newValue) / 100);
                        double total = (costo * porcentaje) + costo;
                        BigDecimal NuevoTotal=new BigDecimal(total);
                        NuevoTotal=NuevoTotal.setScale(2, RoundingMode.HALF_UP);
                        Respuesta.setText("");
                        resultado.setText("Precio : "+NuevoTotal);
                    } else if (newValue.isEmpty()) {
                        resultado.setText("");
                        Respuesta.setText("Solo se admiten números");
                    }
                }
            }
        });
    }
    
    public void GenerarValores(Label Respuesta,Label resultado, TextField CajaCalculo, String newValue)
    {
        if (newValue.isEmpty()){
            Respuesta.setText("Introduzca precio costo");
        } else {
            if (isDouble(CajaCalculo.getText()) && isDouble(newValue)) {
                double costo = Double.parseDouble(newValue);
                double porcentaje = (Double.parseDouble(CajaCalculo.getText()) / 100);
                double total = (costo * porcentaje) + costo;
                BigDecimal totalFinal= new BigDecimal(total);
                totalFinal=totalFinal.setScale(2, RoundingMode.HALF_UP);
                Respuesta.setText("");
                resultado.setText("Precio : "+totalFinal);
            } else if (CajaCalculo.getText().isEmpty()) {
                resultado.setText("");
                Respuesta.setText("Solo se admiten números");
            }
        }
    }

    public void rellenarDepto()
    {
        OBSerMarcas = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Marca";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                OBSerMarcas.add(new Marcas(rs.getString("Marca_ID"), rs.getString("Marca_Descripcion")));

            }
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

        COMBOMARCA.getItems().addAll(OBSerMarcas);
        COMBOMARCA.getSelectionModel().selectFirst();
    }
    
    public void rellenarMarcas()
    {
        OBSerMetPagos = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Departamentos";
        conexion x = new conexion();
        cnx = x.crearConexion(BaseDatos);

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                OBSerMetPagos.add(new Departamento(rs.getString("Depto_id"), rs.getString("Depto_descripcion")));

            }
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

        CMBDEPARTAMENTO.getItems().addAll(OBSerMetPagos);
        CMBDEPARTAMENTO.getSelectionModel().selectFirst();
    }
    
    public void setBasedeDatos(String bd)
    {
        this.BaseDatos = bd;
        rellenarDepto();
        rellenarMarcas();
    }

    @FXML
    private void GuardarProducto(ActionEvent event)
    {
        String codigo_interno=trim(TXTCODIGOINTERNO.getText());
        String codigo_barras=trim(TXTCODBARRAS.getText());
        String descripcion_prod=trim(TEXTDESCR.getText());
        String stockminimo=trim(TXTSTOCK.getText());
        String unidad=trim(TXTUNIDAD.getText());
        String marca=COMBOMARCA.getSelectionModel().getSelectedItem().getIDMARCA();
        String porcentajeIVA=trim(TXTIVA.getText());
        String porcentajeIEPS=trim(TXTIEPS.getText());
        String preciocosto=trim(TXTPRECIOCOSTO.getText());
        String proveedor=trim(txtPROVEEDOR.getText());
        String departamento=CMBDEPARTAMENTO.getSelectionModel().getSelectedItem().getID();
        String ClaveProdServ=trim(TXTPRODSERV.getText());
        String porcentajeISR=trim(TXTISR.getText());
        String porcentaje1=trim(TXTPORCENTAJE1.getText());
        String porcentaje2=trim(TXTPORCENTAJE2.getText());
        String porcentaje3=trim(TXTPORCENTAJE3.getText());
        String porcentaje4=trim(TXTPORCENTAJE4.getText());
        String porcentaje5=trim(TXTPORCENTAJE5.getText());
        
        if(!codigo_interno.isEmpty() && !codigo_barras.isEmpty() && !descripcion_prod.isEmpty() && !stockminimo.isEmpty() && !unidad.isEmpty() && !porcentajeIVA.isEmpty() && !porcentajeIEPS.isEmpty() && !preciocosto.isEmpty() && !proveedor.isEmpty() && !ClaveProdServ.isEmpty() && !porcentajeISR.isEmpty() && !porcentaje1.isEmpty() && !porcentaje2.isEmpty() && !porcentaje3.isEmpty() && !porcentaje4.isEmpty() && !porcentaje5.isEmpty())
        {
            ResultSet rs = null;
            PreparedStatement ps=null;
            Connection cnx = null;
            conexion conexbd=new conexion();
            conexbd.crearConexion();
            cnx=conexbd.getConexion();
            String sq3l = "INSERT INTO Ncc_Productos(Prod_CodInterno,Prod_CodBarras,Prod_Descripcion,Prod_StockMinimo,Prod_idUnidad,Prod_MarcaID,Prod_IVA,Prod_IEPS,Prod_Costo,Prod_ProvedorID,Prod_idDepto,Prod_ClaveProdServ,Prod_ISR,Prod_Precio1,Prod_Precio2,Prod_Precio3,Prod_Precio4,Prod_Precio5)\n" +
                            "SELECT ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?\n" +
                            "FROM dual\n" +
                            "WHERE NOT EXISTS (SELECT Prod_CodInterno,Prod_CodBarras FROM Ncc_Productos WHERE Prod_CodInterno=? OR Prod_CodBarras=? LIMIT 1)";
            try 
            {
                if(ValidarPieza() >= 1)
                {
                    if(ValidarProdServ() >= 1)
                    {
                        ps = cnx.prepareStatement(sq3l);
                        ps.setString(1, codigo_interno);
                        ps.setString(2, codigo_barras);
                        ps.setString(3, descripcion_prod);
                        ps.setString(4, stockminimo);
                        ps.setString(5, unidad);
                        ps.setString(6, marca);
                        ps.setString(7, porcentajeIVA);
                        ps.setString(8, porcentajeIEPS);
                        ps.setString(9, preciocosto);
                        ps.setString(10, proveedor);
                        ps.setString(11, departamento);
                        ps.setString(12, ClaveProdServ);
                        ps.setString(13, porcentajeISR);
                        ps.setString(14, porcentaje1);
                        ps.setString(15, porcentaje2);
                        ps.setString(16, porcentaje3);
                        ps.setString(17, porcentaje4);
                        ps.setString(18, porcentaje5);

                        ps.setString(19, trim(codigo_interno));//ultimo del where
                        ps.setString(20, trim(codigo_barras));//ultimo del where
                        if(ps.executeUpdate() > 0 )
                        {
                            alertas.arlertaInformacion("Información guardada", "El producto fue creado correctamente");
                            LimpiarFormulario();
                        }else{
                            alertas.arlertaInformacion("Información repetida", "El producto ya existe");
                        }
                    }else{
                        alertas.NotificacionWarning("No existe código de producto o serv", "No se guardó ningún cambio");
                    }
                }else{
                    alertas.NotificacionWarning("No existe código de pieza", "No se guardó ningún cambio");
                }
            } catch (SQLException ex) {
                alertas.alertaError("Error","Error al intentar crear el producto", ex.getMessage());
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
            alertas.NotificacionInformacion("Captura", "No deje campos vacíos");
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

    public boolean isInteger(String numer) {
        try {
            Integer.parseInt(numer);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void LimpiarFormulario()
    {
        TXTCODIGOINTERNO.setText("");
        TXTCODBARRAS.setText("");
        TEXTDESCR.setText("");
        TXTSTOCK.setText("");
        TXTUNIDAD.setText("");
        TXTIVA.setText("");
        TXTIEPS.setText("");
        TXTPRECIOCOSTO.setText("");
        txtPROVEEDOR.setText("");
        TXTPRODSERV.setText("");
        TXTISR.setText("");
        TXTPORCENTAJE1.setText("");
        TXTPORCENTAJE2.setText("");
        TXTPORCENTAJE3.setText("");
        TXTPORCENTAJE4.setText("");
        TXTPORCENTAJE5.setText("");
    }
    
    @FXML
    public int ValidarProdServ()
    {
        int bandera=0;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        ResultSet x = null;
        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        
        String sql2="SELECT * FROM Ncc_ClavesProdServ WHERE c_ClaveProdServ = ?";
        try
        {
            ps2=cnx.prepareStatement(sql2);
            ps2.setString(1, trim(TXTPRODSERV.getText()));
            x=ps2.executeQuery();
            if(x.next()){
                bandera++;
                alertas.arlertaInformacion("Encontrado "+x.getString("c_ClaveProdServ"), "Producto y/o servicio encontrado: "+x.getString("Descripción"));
            }else{
                alertas.NotificacionWarning("No existe", "No se encontró producto o servicio");
                bandera=0;
            }
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
            bandera=0;
        }
        return bandera;
    }
    
    @FXML
    public int ValidarPieza()
    {
        int bandera=0;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        ResultSet x = null;
        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        
        String sql2="SELECT * FROM Ncc_Unidades WHERE Unidad_ClaveUnidad = ?";
        try
        {
            ps2=cnx.prepareStatement(sql2);
            ps2.setString(1, trim(TXTUNIDAD.getText()));
            x=ps2.executeQuery();
            if(x.next()){
                bandera++;
                alertas.arlertaInformacion("Encontrado "+x.getString("Unidad_ClaveUnidad"), "Unidad capturada: "+x.getString("Unidad_Descripcion"));
            }else{
                alertas.NotificacionWarning("No existe", "No se encontró ninguna unidad con esa clave de unidad");
                bandera=0;
            }
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getLocalizedMessage(), e.getMessage());
            bandera=0;
        }
        return bandera;
    }
}