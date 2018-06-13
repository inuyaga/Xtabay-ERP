package ncc;

import alertas.alertas;
import bd.conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import principal.PrincipalController;
import ClasesObjetos.Sucursal;
import ClasesObjetos.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author esteban
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private TextField txt_usr;
    @FXML
    private TextField txt_pass;

    private int ValidaVistaSuc = 0;
    //private HashMap<String, Object> DatosUsuarios;
    private ObservableList<Usuario> DatosUsuarios;
    private List<Integer> PRIVIGELIOS;
    private Date fechaVigencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void accion_boton_entrar(KeyEvent event) {

        Timestamp fechacompara = null;
        List<Sucursal> choices = new ArrayList<>();
        if (event.getCode() == KeyCode.ENTER) {
            String usuario = txt_usr.getText();
            String contraseña = txt_pass.getText();

            boolean resp;
            resp = login(usuario, contraseña);

            if (resp) {
                fechacompara = new Timestamp(System.currentTimeMillis());
                ResultSet rs = null;
                PreparedStatement ps = null;
                Connection cnx = null;
                String sql = "SELECT * FROM Ncc_Sucursales";

                if (ValidaVistaSuc == 1 && fechaVigencia.after(fechacompara)) {

                    conexion bdatos = new conexion();
                    bdatos.crearConexion();
                    cnx = bdatos.getConexion();

                    try {
                        ps = cnx.prepareStatement(sql);

                        rs = ps.executeQuery();

                        while (rs.next()) {

                            choices.add(new Sucursal(rs.getString("Suc_Id"), rs.getString("Suc_Nombre")));

                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ChoiceDialog<Sucursal> dialog = new ChoiceDialog<>(choices.get(0), choices);
                    dialog.setTitle("Sucursal");
                    dialog.setHeaderText("Eleccion");
                    dialog.setContentText("Seleccione una sucursal");

                    // Traditional way to get the response value.
                    Optional<Sucursal> result = dialog.showAndWait();
                    if (result.isPresent()) {

                        if (choices.contains(result.get())) {
                            int IDSuc = Integer.parseInt(result.get().getId());
                            FXMLLoader Loader = new FXMLLoader();
                            Loader.setLocation(getClass().getResource("/principal/principal.fxml"));

                            try {
                                Loader.load();
                            } catch (IOException e) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, e);
                            }
                            PrincipalController principal = Loader.getController();
                            DatosUsuarios.get(0).setSUCURSAL_SELECCIONADA(result.get().getNombre());
                            principal.datos_usuarios(PRIVIGELIOS, DatosUsuarios, IDSuc);

                            Parent p = Loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(p));
                            stage.setMaximized(true);
                            stage.setTitle(result.get().getNombre());

                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent e) {

                                    boolean resp = alertas.alertaConfirmacion("Salir", "Desea salir del sistema", "¿Esta seguro?");
                                    if (resp) {
                                        System.exit(0);
                                    } else {
                                        e.consume();
                                    }
                                }
                            });

                            stage.show();

                            final Node source = (Node) event.getSource();
                            final Stage nodo = (Stage) source.getScene().getWindow();
                            nodo.close();
                        } else {

                        }

                    }

                } else if (ValidaVistaSuc == 0 && fechaVigencia.after(fechacompara)) {
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("/principal/principal.fxml"));

                    try {
                        Loader.load();
                    } catch (IOException e) {
                        Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, e);
                    }
                    PrincipalController nccPrincipal = Loader.getController();
                    DatosUsuarios.get(0).setSUCURSAL_SELECCIONADA(DatosUsuarios.get(0).getNOMBRESUC());
                    nccPrincipal.datos_usuarios(PRIVIGELIOS, DatosUsuarios, Integer.parseInt(DatosUsuarios.get(0).getSUCURSALID()));

                    Parent p = Loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.setMaximized(true);

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent e) {

                            boolean resp = alertas.alertaConfirmacion("Salir", "Desea salir del sistema", "¿Esta seguro?");
                            if (resp) {
                                System.exit(0);
                            } else {
                                e.consume();
                            }
                        }
                    });

                    stage.setTitle(DatosUsuarios.get(0).getNOMBRESUC());

                    stage.show();

                    final Node source = (Node) event.getSource();
                    final Stage nodo = (Stage) source.getScene().getWindow();
                    nodo.close();
                } else {
                    alertas.arlertaInformacion("Inicio de sesion", "Tu vigencia a caducado");
                    System.out.println(fechaVigencia);
                    System.out.println(fechacompara);
                }

            }

        }
    }

    private boolean seleccion() {

        return true;
    }

    public void busquedaPermisoUsuario(int Id) {

        PRIVIGELIOS = new ArrayList<>();
        ResultSet rs = null;
        int i = 0;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT\n"
                + "Ncc_User.User_ID,\n"
                + "Ncc_User.User_Nombre,\n"
                + "Ncc_PermisoUsuario.UP_Id,\n"
                + "Ncc_Permisos.Per_ID,\n"
                + "Ncc_Permisos.Per_Serie,\n"
                + "Ncc_Permisos.Per_Descripcion\n"
                + "FROM\n"
                + "Ncc_PermisoUsuario\n"
                + "INNER JOIN Ncc_Permisos ON Ncc_PermisoUsuario.UP_Id_Permiso = Ncc_Permisos.Per_ID\n"
                + "INNER JOIN Ncc_User ON Ncc_User.User_ID = Ncc_PermisoUsuario.UP_IdUser\n"
                + "WHERE\n"
                + "Ncc_User.User_ID = ?";

        conexion conexDB = new conexion();
        conexDB.crearConexion();
        cnx = conexDB.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, Id);
            rs = ps.executeQuery();
            while (rs.next()) {
                PRIVIGELIOS.add(rs.getInt("Per_ID"));
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
            } catch (SQLException e) {
            }
        }
    }
    
    
    public void permisosGrupos(int ID){
    ResultSet rs = null;
        int i = 0;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_GroupPermisos WHERE GP_IdGroup = ?";

        conexion conexDB = new conexion();
        conexDB.crearConexion();
        cnx = conexDB.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                PRIVIGELIOS.add(rs.getInt("GP_IdPermiso"));
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
            } catch (SQLException e) {
            }
        }
    }

    private boolean login(String usr, String pass) {
        boolean retorno = false;

        ResultSet resp = null;
        PreparedStatement prepare = null;
        Connection conex = null;
        try {
            conexion consulta = new conexion();
            consulta.crearConexion();

            conex = consulta.getConexion();
            String sql = "SELECT * FROM Ncc_User \n"
                    + "INNER JOIN Ncc_Sucursales ON Ncc_User.User_IdSuc = Ncc_Sucursales.Suc_Id \n"
                    + " WHERE User_Usuario = ? AND User_Password = ? LIMIT 1";

            prepare = conex.prepareStatement(sql);

            prepare.setString(1, usr);
            prepare.setString(2, pass);

            resp = prepare.executeQuery();
            if (resp.next()) {

                DatosUsuarios = FXCollections.observableArrayList();
                resp.previous();

                while (resp.next()) {
                    ValidaVistaSuc = resp.getInt("User_VerlListaSuc");
                    fechaVigencia = resp.getTimestamp("User_Vigencia");
                    
                    DatosUsuarios.add(new Usuario(resp.getString("User_ID"), resp.getString("User_Nombre"), resp.getString("User_Vigencia"), resp.getString("User_IdSuc"), resp.getString("User_Usuario"), resp.getString("Suc_Nombre"),""));

                }

                busquedaPermisoUsuario(Integer.parseInt(DatosUsuarios.get(0).getID()));
                permisosGrupos(Integer.parseInt(DatosUsuarios.get(0).getID()));
                retorno = true;
            } else {
                alertas.arlertaInformacion("Error autenticacion", "Usuario o contraseña incorrecta");
                retorno = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resp != null) {
                    resp.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (conex != null) {
                    conex.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        return retorno;

    }

    @FXML
    private void AccionCerrarVentana(MouseEvent event) {
        System.exit(0);
    }

}
