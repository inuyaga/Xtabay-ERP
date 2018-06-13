/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import ClasesObjetos.Grupos;
import ClasesObjetos.Sucursal;
import alertas.alertas;
import bd.conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class Agrega_usuarioController implements Initializable {

    @FXML
    private TextField txt_nombre;
    @FXML
    private PasswordField txt_password;
    @FXML
    private DatePicker txt_fecha;
    @FXML
    private CheckBox chexbox_visualiza;
    @FXML
    private ChoiceBox<Sucursal> choicebox_sucursales;
    @FXML
    private TextField txt_usuario;
    @FXML
    private Button btn_guardar;
    @FXML
    private Text mensaje;
    @FXML
    private ChoiceBox<Grupos> choicebox_grupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Sucursal> sucursales = FXCollections.observableArrayList();
        
       ObservableList<Grupos> grupos = FXCollections.observableArrayList();

        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Sucursales";
        String sql2 = "SELECT * FROM Ncc_Grupos";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();

        try {
            ps = cnx.prepareCall(sql);
            rs = ps.executeQuery();
            sucursales.add(new Sucursal("", "Seleccione una sucursal"));
            grupos.add(new Grupos("Seleccione un grupo", ""));
            while (rs.next()) {
                sucursales.add(new Sucursal(rs.getString("Suc_Id"), rs.getString("Suc_Nombre")));
            }
            
            ps2=cnx.prepareStatement(sql2);
            rs2=ps2.executeQuery();
            while (rs2.next()) {
                grupos.add(new Grupos(rs2.getString("Group_Name"), rs2.getString("Group_ID")));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Agrega_usuarioController.class.getName()).log(Level.SEVERE, null, ex);
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
                
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                
            } catch (SQLException e) {
            }

        }

        choicebox_sucursales.getItems().addAll(sucursales);
        choicebox_sucursales.getSelectionModel().selectFirst();
        
        choicebox_grupo.getItems().addAll(grupos);
        choicebox_grupo.getSelectionModel().selectFirst();

    }

    @FXML
    private void Accion_Guarda(ActionEvent event) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        String sql = "INSERT INTO Ncc_User(User_Nombre, User_Password, User_Vigencia, User_VerlListaSuc, User_IdSuc, User_Usuario, User_IDgrupo) VALUES(?,?,?,?,?,?,?)";
        String sqlFound = "SELECT * FROM Ncc_User WHERE User_Usuario = ?";
        int visualiza = 0;
        String nombre = txt_nombre.getText();
        String usuario = txt_usuario.getText();
        String contraseña = txt_password.getText();
        LocalDate vigencia = txt_fecha.getValue();
        String IDSucursal = choicebox_sucursales.getValue().getId();
        String IDGrupo = choicebox_grupo.getValue().getID();
        if (chexbox_visualiza.isSelected()) {
            visualiza = 1;
        }

        LocalDate actual = LocalDate.now();

        if (!nombre.isEmpty() && !usuario.isEmpty() && !contraseña.isEmpty() && !IDSucursal.isEmpty()) {
            if (vigencia.isAfter(actual)) {

                try {
                    conexion cnexBD = new conexion();
                    cnexBD.crearConexion();
                    cnx = cnexBD.getConexion();

                    ps2 = cnx.prepareStatement(sqlFound);
                    ps2.setString(1, usuario);
                    rs = ps2.executeQuery();

                    if (!rs.next()) {
                        ps = cnx.prepareStatement(sql);
                        ps.setString(1, nombre);
                        ps.setString(2, contraseña);
                        ps.setDate(3, Date.valueOf(vigencia));
                        ps.setInt(4, visualiza);
                        ps.setString(5, IDSucursal);
                        ps.setString(6, usuario);
                        ps.setString(7, IDGrupo);
                        ps.execute();

                        txt_nombre.setText("");
                        txt_usuario.setText("");
                        txt_password.setText("");
                        txt_fecha.setValue(actual);
                        choicebox_sucursales.getSelectionModel().clearSelection();

                        if (chexbox_visualiza.isSelected()) {
                            chexbox_visualiza.setSelected(false);
                        }
                        alertas.arlertaInformacion("Creacion de Usuario", "Usuario creado correctamente");
                    } else {
                        alertas.arlertaInformacion("Creacion de Usuario", "El usuario " + usuario + " ya existe");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Agrega_usuarioController.class.getName()).log(Level.SEVERE, null, ex);
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

            } else {
                alertas.arlertaInformacion("Fecha", "la fecha debe ser mayor a la actual");
            }
        } else {
            alertas.arlertaInformacion("Campos vacioos", "Favor de completar los campos");
        }

    }

}
