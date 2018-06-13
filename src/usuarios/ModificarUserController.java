/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import ClasesObjetos.Sucursal;
import alertas.alertas;
import bd.conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class ModificarUserController implements Initializable {

    @FXML
    private TextField txt_nombre;
    @FXML
    private DatePicker txt_fecha;
    @FXML
    private CheckBox chexbox_visualiza;

    @FXML
    private TextField txt_usuario;
    @FXML
    private TableView<TablaUsuarios> TablaUsuarios;
    private ObservableList<TablaUsuarios> ObsList_Usuarios;
    @FXML
    private TableColumn<?, ?> Col_ID;
    @FXML
    private TableColumn<?, ?> Col_usuario;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_vigencia;
    @FXML
    private TableColumn<?, ?> col_sucursal;
    @FXML
    private TableColumn<?, ?> ColListaSuc;

    private String ID_Usuario_Cambio;
    @FXML
    private TextField txt_Busqueda;
    @FXML
    private Button btn_guardar;
    @FXML
    private TableColumn<?, ?> COL_GRUPO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TalblaUsuarioClase();

        
    }

    public void TalblaUsuarioClase() {
        ObsList_Usuarios = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_User "
                + "INNER JOIN Ncc_Sucursales ON Ncc_User.User_IdSuc = Ncc_Sucursales.Suc_Id "
                + "INNER JOIN Ncc_Grupos ON Ncc_User.User_IDgrupo = Ncc_Grupos.Group_ID";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                ObsList_Usuarios.add(new TablaUsuarios(rs.getString("User_ID"), rs.getString("User_Usuario"), rs.getString("User_Nombre"), rs.getDate("User_Vigencia").toString(), rs.getString("Suc_Nombre"), rs.getString("User_VerlListaSuc"),rs.getString("Group_Name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModificarUserController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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

        Col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Col_usuario.setCellValueFactory(new PropertyValueFactory<>("USUARIO"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("NOMBRE"));
        col_vigencia.setCellValueFactory(new PropertyValueFactory<>("VIGENCIA"));
        col_sucursal.setCellValueFactory(new PropertyValueFactory<>("SUCURSAL"));
        ColListaSuc.setCellValueFactory(new PropertyValueFactory<>("LISTASUC"));
        COL_GRUPO.setCellValueFactory(new PropertyValueFactory<>("GRUPO"));

        TablaUsuarios.setItems(ObsList_Usuarios);
        
        FilteredList<TablaUsuarios> filteredData = new FilteredList<>(ObsList_Usuarios, p -> true);

        txt_Busqueda.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(TablaUsuarios
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (TablaUsuarios.getUSUARIO().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                } else if (TablaUsuarios.getNOMBRE().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<TablaUsuarios> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(TablaUsuarios.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        TablaUsuarios.setItems(sortedData);
    }

    @FXML
    private void AccionClicTabla(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int idexTabla = TablaUsuarios.getSelectionModel().getSelectedIndex();

            String nombre = TablaUsuarios.getItems().get(idexTabla).getNOMBRE();
            String vigencia = TablaUsuarios.getItems().get(idexTabla).getVIGENCIA();
            String user = TablaUsuarios.getItems().get(idexTabla).getUSUARIO();
            String VerLista = TablaUsuarios.getItems().get(idexTabla).getLISTASUC();
            String sucursal = TablaUsuarios.getItems().get(idexTabla).getSUCURSAL();
            ID_Usuario_Cambio = TablaUsuarios.getItems().get(idexTabla).getID();
            btn_guardar.setDisable(false);

            if (VerLista.equals("Si")) {
                chexbox_visualiza.setSelected(true);
            } else {
                chexbox_visualiza.setSelected(false);
            }

            txt_nombre.setText(nombre);
            txt_fecha.setValue(LocalDate.parse(vigencia));
            txt_usuario.setText(user);

        }
    }

    @FXML
    private void AccionGurdaCambios(ActionEvent event) {
        PreparedStatement ps = null;
        Connection cnx = null;
        int visualiza = 0;
        String sql = "UPDATE Ncc_User SET User_Nombre=?, User_Vigencia=?, User_VerlListaSuc=? WHERE User_ID=? ";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();
        if (chexbox_visualiza.isSelected()) {
            visualiza = 1;
        }

        try {
            ps = cnx.prepareStatement(sql);

            ps.setString(1, txt_nombre.getText());
            ps.setString(2, txt_fecha.getValue().toString());
            ps.setInt(3, visualiza);
            ps.setString(4, ID_Usuario_Cambio);
            int bandera = ps.executeUpdate();
            if (bandera > 0) {
                alertas.arlertaInformacion("Actualizacion de Usuario", "El usuario" + txt_usuario.getText() + "\n fue actualizado");
                TalblaUsuarioClase();
            } else {
                alertas.alertaError("Error", "Actualizacion de Usuario", "Error al actualizar datos \nintente de nuevo por favor");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModificarUserController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void AccionChangePass(ActionEvent event) {
        
         int idexTabla = TablaUsuarios.getSelectionModel().getSelectedIndex();

        String nombre = TablaUsuarios.getItems().get(idexTabla).getNOMBRE();
        String IDUsuario = TablaUsuarios.getItems().get(idexTabla).getID();

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cambio de contraseña a");
        dialog.setHeaderText(nombre);


// Set the button types.
        ButtonType loginButtonType = new ButtonType("Cambiar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField username = new PasswordField();
        username.setPromptText("Contraseña");
        PasswordField password = new PasswordField();
        password.setPromptText("Reescriba");

        grid.add(new Label("Contraseña:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Reescriba:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            
            if (usernamePassword.getKey().equals(usernamePassword.getValue())) {
                cambioPass( IDUsuario,  usernamePassword.getValue(),  nombre);
                System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            }else{
                alertas.arlertaInformacion("Error", "Las contraseñas no coinciden");
            }
            
        });

    }
    
    public void cambioPass(String IDUser, String contraseña, String nombre){
        
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql2 = "UPDATE Ncc_User SET User_Password=? WHERE User_ID=?";
        
        conexion conexionBD=new conexion();
        conexionBD.crearConexion();
        cnx=conexionBD.getConexion();
        
        
        try {
            ps=cnx.prepareStatement(sql2);
            ps.setString(1, contraseña);
            ps.setString(2, IDUser);
            
            if (ps.executeUpdate() > 0) {
              alertas.arlertaInformacion("Cambio exitoso", nombre+" cambio de contraseña");  
            }
        } catch (SQLException e) {
        }finally {
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

    @FXML
    private void AccionChangeSucursal(ActionEvent event) {

        int idexTabla = TablaUsuarios.getSelectionModel().getSelectedIndex();

        String nombre = TablaUsuarios.getItems().get(idexTabla).getNOMBRE();
        String IDUsuario = TablaUsuarios.getItems().get(idexTabla).getID();

        List<Sucursal> choices = new ArrayList<>();

        ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Sucursales";
        String sql2 = "UPDATE Ncc_User SET User_IdSuc=? WHERE User_ID=?";

        conexion conexBD = new conexion();
        conexBD.crearConexion();
        cnx = conexBD.getConexion();

        try {
            ps = cnx.prepareCall(sql);
            rs = ps.executeQuery();
            choices.add(new Sucursal("", "Seleccione una sucursal"));

            while (rs.next()) {
                choices.add(new Sucursal(rs.getString("Suc_Id"), rs.getString("Suc_Nombre")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Agrega_usuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ChoiceDialog<Sucursal> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Cambio de sucursal");
        dialog.setHeaderText(nombre);
        dialog.setContentText("Elija una opcion: ");

// Traditional way to get the response value.
        Optional<Sucursal> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                ps2 = cnx.prepareStatement(sql2);
                ps2.setString(1, result.get().getId());
                ps2.setString(2, IDUsuario);

                int count = ps2.executeUpdate();
                if (count > 0) {
                    alertas.arlertaInformacion("Cambio de sucursal", "Nuevo cambio: " + result.get().getNombre());
                    TalblaUsuarioClase();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModificarUserController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                    if (cnx != null) {
                        cnx.close();
                    }

                } catch (SQLException e) {
                }

            }

        }
    }

}
