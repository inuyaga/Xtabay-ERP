package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class conexion  {
    Connection conexion = null;
    private String Servidor = "192.168.1.228";
    private String puerto = "3306";
    private String baseDatos = "NCC";
    private String usuario = "externo";
    private String contraseña = "0102261218";
  
   
    
   private List<Object> Respueta;
   private String errorDeException;

    public void setRespueta(List<Object> Respueta) {
        this.Respueta = Respueta;
    }

    

    public List<Object> getRespueta() {
        return Respueta;
    }

    public String getErrorDeException() {
        return errorDeException;
    }

  
  /*  
    public conexion(){
        Properties p = new Properties();
        try {
            p.load(new FileReader("config.properties"));
            this.Servidor = p.getProperty("SERVIDOR_DB");
            this.puerto = p.getProperty("PUERTO_BD");
            this.baseDatos = p.getProperty("NOMBRE_DB");
            this.usuario = p.getProperty("USUARIO_BD");
            this.contraseña = p.getProperty("PASSWORD_BD");
            p.clear();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
  */  
    public Connection getConexion()
{
        
   return conexion;
}
 
/**
* Método utilizado para establecer la conexión con la base de datos
* @return estado regresa el estado de la conexión, true si se estableció la conexión,
* falso en caso contrario
*/
    
public boolean crearConexion()
{
    
    try {
    Class.forName("com.mysql.jdbc.Driver");
    conexion = DriverManager.getConnection("jdbc:mysql://"+Servidor+":"+puerto+"/"+baseDatos,usuario,contraseña);
   } catch (SQLException ex) {
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("No existe conexión hacia el servidor!"+ex);
        alert.showAndWait();
      return false;
   } catch (ClassNotFoundException ex) {
      
      
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("Error en clases!"+ex);
        alert.showAndWait();
      return false;
   }
    
    
   
  return true;
  
  
}
public Connection crearConexion(String basededatos)
{
    Connection conexion=null;
    try {
    Class.forName("com.mysql.jdbc.Driver");
    conexion = DriverManager.getConnection("jdbc:mysql://"+Servidor+":"+puerto+"/"+basededatos,usuario,contraseña);
   } catch (SQLException ex) {
      alertas.alertas.alertaDeExcepcion("Error", "Conexion", "upps", ex.getMessage());
      return null;
   } catch (ClassNotFoundException ex) {
    
        alertas.alertas.alertaDeExcepcion("Error", "Conexion", "upps", ex.getMessage());
      return null;
   }
    
    
   
  return conexion;
  
  
}

int verdad;
int falso;
public void PruebaConexion(String se,String pt,String BD,String us,String pas){
    Respueta=new ArrayList<>();
   Thread Tarea=new Thread(){
    @Override
    public void run(){
    try {
    Class.forName("com.mysql.jdbc.Driver");
    DriverManager.getConnection("jdbc:mysql://"+se+":"+pt+"/"+BD,us,pas);
    
    
      Respueta.add(true);
   
    } catch (SQLException | ClassNotFoundException ex) {
      
       
        Respueta.add(false);

      // ex.printStackTrace();
       errorDeException=ex.getMessage();
       //alertas.alertaDeExcepcion("Error sql", "es", "Es causado por", ex.getMessage());
    
   }

        System.out.println("estoy vivo soy admin admin un hilo");
    }
    };
   
   Tarea.start();
   
   
    
 


}
// conxion por internet
public boolean crearConexionRemota()
{
    boolean estado=false;
    
   try {
      Class.forName("com.mysql.jdbc.Driver");
      conexion = DriverManager.getConnection("jdbc:mysql://"+Servidor+":"+puerto+"/"+baseDatos,usuario,contraseña);
      //conexion = DriverManager.getConnection("jdbc:mysql://187.157.119.164:3306/XTABAI","externo","0102261218");
      return true;
   } catch (SQLException ex) {
      
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("No existe conexión a la base de datos remota!"+ex);
        alert.showAndWait();
   } catch (ClassNotFoundException ex) {
      
      
      Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error!!");
        alert.setContentText("Error en clases!"+ex);
        alert.showAndWait();
   }
   return estado;
}



}
