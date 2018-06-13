package Grupo;

import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class FXMLGrupoController implements Initializable {

    @FXML
    private JFXTextField CajaNuevoGrupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void GuardarGrupo(ActionEvent event)
    {
        ResultSet rs = null;
        PreparedStatement ps=null;
        Connection cnx = null;
        conexion conexbd=new conexion();
        conexbd.crearConexion();
        cnx=conexbd.getConexion();
        String sq3l = "INSERT INTO Ncc_Grupos(Group_Name)\n" +
                        "SELECT ?\n" +
                        "FROM dual\n" +
                        "WHERE NOT EXISTS (SELECT Group_Name FROM Ncc_Grupos WHERE Group_Name=? LIMIT 1)";
        String NombreGrupo = CajaNuevoGrupo.getText();
        
        if(!NombreGrupo.isEmpty())
        {
            try {
                ps = cnx.prepareStatement(sq3l);
                ps.setString(1, trim(NombreGrupo));
                ps.setString(2, trim(NombreGrupo));
                if(ps.executeUpdate() > 0 )
                {
                    alertas.arlertaInformacion("Información guardada", "El grupo fue creado correctamente");
                }else{
                    alertas.arlertaInformacion("Información repetida", "El grupo ya existe");
                }
            } catch (SQLException ex) {
                alertas.alertaError("Error","Error al intentar crear el grupo", ex.getMessage());
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
        }
    }
    
}