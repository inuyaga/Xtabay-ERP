package AperturaSucursal;

import ClasesObjetos.MetodosPago;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import alertas.alertas;
import bd.conexion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeString.trim;
/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class AperturaSucController implements Initializable {

    @FXML
    private JFXTextField TxtNombreSucursal;
    @FXML
    private JFXTextField TxtDireccionSuc;
    @FXML
    private JFXTextField TxtEsloganSuc;
    @FXML
    private JFXTextField TxtTelefonoSuc;
    @FXML
    private JFXTextField TxtRfcSuc;
    @FXML
    private JFXComboBox<MetodosPago> CmbMetodoPagoSuc;
    @FXML
    private JFXButton BtnGuardarSuc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LlenarCombo();
    }    

    private void LlenarCombo()
    {
        List<MetodosPago> lista = new ArrayList<>();
        
        ResultSet rs = null;
        PreparedStatement ps=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sql="SELECT * FROM Ncc_MetodosPago";
        
        try {
            ps=cnx.prepareStatement(sql);
            rs=ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new MetodosPago(rs.getString("MPag_Codigo"), rs.getString("MPag_Descripcion")));
                
            }
            
            CmbMetodoPagoSuc.getItems().addAll(lista);
            CmbMetodoPagoSuc.getSelectionModel().selectFirst();
            
        } catch (SQLException ex) {
            Logger.getLogger(AperturaSucController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
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
    }
    @FXML
    private void AperturaSucursal(ActionEvent event)
    {
        ResultSet rs = null;
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        PreparedStatement ps3=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sq3l = "INSERT INTO Ncc_Sucursales(Suc_Nombre,Suc_Direccion,Suc_Eslogan,Suc_telefono,Suc_RFC,Suc_Id_MetPago,Suc_Db)\n" +
                        "SELECT ?,?,?,?,?,?,?\n" +
                        "FROM dual\n" +
                        "WHERE NOT EXISTS (SELECT Suc_Nombre FROM Ncc_Sucursales WHERE Suc_Nombre = ? LIMIT 1)";
        
        String Sucursal = TxtNombreSucursal.getText();
        String Direccion = TxtDireccionSuc.getText();
        String Eslogan = TxtEsloganSuc.getText();
        String Telefono = TxtTelefonoSuc.getText();
        String RFC = TxtRfcSuc.getText();
        String MetPAgo = CmbMetodoPagoSuc.getValue().getCODIGO();
        String datab=Sucursal.replace(" ","");
        StringBuffer  SQLstructrura=new StringBuffer();
        if(!Sucursal.isEmpty() && !Direccion.isEmpty() && !Eslogan.isEmpty() && !Telefono.isEmpty() && !RFC.isEmpty() && !MetPAgo.isEmpty())
        {
            try {
                ps = cnx.prepareStatement(sq3l);
                ps.setString(1, trim(Sucursal));
                ps.setString(2, trim(Direccion));
                ps.setString(3, trim(Eslogan));
                ps.setString(4, trim(Telefono));
                ps.setString(5, trim(RFC));
                ps.setString(6, trim(MetPAgo));
                ps.setString(7, trim(datab));
                ps.setString(8, trim(Sucursal));
                if(ps.executeUpdate() > 0 )
                {
                    String sql = "CREATE DATABASE "+datab;
                    ps2=cnx.prepareStatement(sql);
                    ps2.executeUpdate();
                    
                    File archivo = null;
                    FileReader fr = null;
                    BufferedReader br = null;
                    try {
                        archivo = new File ("NCC.sql");
                        fr = new FileReader (archivo);
                        br = new BufferedReader(fr);

                        // Lectura del fichero
                        String linea;
                        while((linea=br.readLine())!=null)
                            SQLstructrura.append(linea + "\n ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally{
                        try{                    
                           if( null != fr ){   
                              fr.close();     
                           }                  
                        }catch (Exception e2){ 
                           e2.printStackTrace();
                        }
                     }
                    
                    Connection cx=conexbd.crearConexion(datab);
                    Statement ps4=null;
                    ps4=cx.createStatement();
                    String[] splitSql=SQLstructrura.toString().split(";");
                    for (int i = 0; i < splitSql.length-1; i++) {
                        ps4.addBatch(splitSql[i]);
                        System.out.println(splitSql[i]);
                    }
                    ps4.executeBatch();
                    alertas.arlertaInformacion("Información guardada", "La sucursal fue aperturada correctamente");
                }else{
                    alertas.arlertaInformacion("Información repetida", "La sucursal ya existe");
                }
            } catch (SQLException ex) {
                alertas.alertaError("Error","Error al intentar aperturar nueva sucursal", ex.getMessage());
            }
            finally
            {
                TxtNombreSucursal.setText("");
                TxtDireccionSuc.setText("");
                TxtEsloganSuc.setText("");
                TxtTelefonoSuc.setText("");
                TxtRfcSuc.setText("");
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
    
}
