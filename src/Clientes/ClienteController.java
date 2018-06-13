/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clientes;

import ClasesObjetos.Cliente;
import ClasesObjetos.MetodosPago;
import ClasesObjetos.Usuario;
import alertas.alertas;
import bd.conexion;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class ClienteController implements Initializable {

    @FXML
    private TextField txt_busqueda;
    @FXML
    private TableView<Cliente> Tabla_Clientes;
    private ObservableList<Cliente> ObslClientes;
    private ObservableList<MetodosPago> OBSerMetPagos;
    private ObservableList<Usuario> OBSvendedor;
    @FXML
    private TableColumn<?, ?> Col_ID;
    @FXML
    private TableColumn<?, ?> Col_Nombre;
    @FXML
    private TableColumn<?, ?> Col_Calle;
    @FXML
    private TableColumn<?, ?> Col_Ciudad;
    @FXML
    private TableColumn<?, ?> Col_Estado;
    @FXML
    private TableColumn<?, ?> Col_Telefono;
    @FXML
    private TableColumn<?, ?> Col_RFC;
    @FXML
    private TableColumn<?, ?> Col_Email;
    @FXML
    private TableColumn<?, ?> Col_cp;
    @FXML
    private TableColumn<?, ?> Col_Colonia;
    @FXML
    private JFXTextField txt_ciudad;
    @FXML
    private JFXTextField txt_calle;
    @FXML
    private JFXTextField txt_cp;
    @FXML
    private JFXTextField txt_telefono;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_rfc;
    @FXML
    private JFXTextField txt_colonia;
    @FXML
    private JFXTextField txt_nombre;
    @FXML
    private JFXComboBox<String> combobox_estado;

    private String IDUsuario = "0";
    private int index;
    @FXML
    private Button Btn_acciones;
    @FXML
    private Button btn_aplica_cambios;

    private String BDatos;
    @FXML
    private JFXComboBox<MetodosPago> ComboboxMetodoPago;
    @FXML
    private JFXComboBox<Usuario> ComboboxVendedor;
    @FXML
    private JFXTextField txt_limiteCredito;
    @FXML
    private JFXTextField txt_diasCredito;
    @FXML
    private TableColumn<?, ?> Col_LimiteCredito;
    @FXML
    private TableColumn<?, ?> Col_MetodoPago;
    @FXML
    private TableColumn<?, ?> Col_DiasCredito;
    @FXML
    private TableColumn<?, ?> Col_Vendedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        combobox_estado.getItems().add("Aguascalientes");
        combobox_estado.getItems().add("Baja California");
        combobox_estado.getItems().add("Baja California Sur");
        combobox_estado.getItems().add("Campeche");
        combobox_estado.getItems().add("Chiapas");
        combobox_estado.getItems().add("Chihuahua");
        combobox_estado.getItems().add("Ciudad de México");
        combobox_estado.getItems().add("Coahuila");
        combobox_estado.getItems().add("Colima");
        combobox_estado.getItems().add("Durango");
        combobox_estado.getItems().add("Guanajuato");
        combobox_estado.getItems().add("Guerrero");
        combobox_estado.getItems().add("Hidalgo");
        combobox_estado.getItems().add("Jalisco");
        combobox_estado.getItems().add("México");
        combobox_estado.getItems().add("Michoacán");
        combobox_estado.getItems().add("Morelos");
        combobox_estado.getItems().add("Nayarit");
        combobox_estado.getItems().add("Nuevo León");
        combobox_estado.getItems().add("Oaxaca");
        combobox_estado.getItems().add("Puebla");
        combobox_estado.getItems().add("Querétaro");
        combobox_estado.getItems().add("Quintana Roo");
        combobox_estado.getItems().add("San Luis Potosí");
        combobox_estado.getItems().add("Sinaloa");
        combobox_estado.getItems().add("Sonora");
        combobox_estado.getItems().add("Tabasco");
        combobox_estado.getItems().add("Tamaulipas");
        combobox_estado.getItems().add("Tlaxcala");
        combobox_estado.getItems().add("Veracruz");
        combobox_estado.getItems().add("Yucatán");
        combobox_estado.getItems().add("Zacatecas");

        combobox_estado.getSelectionModel().selectFirst();

    }

    public void ObtenerConexion(String BDatos) {

        this.BDatos = BDatos;

        TablaClientesCargar();
        CargaMetodosPagos();
        CargaVendedor();

    }

    private void TablaClientesCargar() {
        ObslClientes = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Clientes \n"
                + "INNER JOIN Ncc_User ON Ncc_Clientes.Client_ID_vendedor = Ncc_User.User_ID \n"
                + "INNER JOIN Ncc_MetodosPago ON Ncc_Clientes.Client_metodoPago = Ncc_MetodosPago.MPag_Codigo \n"
                + " WHERE Client_status = 1";
        conexion x = new conexion();
        cnx = x.crearConexion(BDatos);

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ObslClientes.add(new Cliente(rs.getString("Client_Id"), rs.getString("Client_Nombre"), rs.getString("Client_Calle"), rs.getString("Client_Colonia"), rs.getString("Client_Ciudad"), rs.getString("Client_Estado"), rs.getString("Client_Cp"), rs.getString("Client_Telefono1"), rs.getString("Client_RFC"), rs.getString("Client_email"), rs.getString("Client_LimiteCredito"), rs.getString("MPag_Descripcion"), rs.getString("Client_DiasCredito"), rs.getString("User_Usuario")));

            }
        } catch (SQLException e) {
            alertas.alertaDeExcepcion("Error", "bd", "", e.getMessage());
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

        Col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Col_Nombre.setCellValueFactory(new PropertyValueFactory<>("NOMBRE"));
        Col_Calle.setCellValueFactory(new PropertyValueFactory<>("CALLE"));
        Col_Ciudad.setCellValueFactory(new PropertyValueFactory<>("CIUDAD"));
        Col_Estado.setCellValueFactory(new PropertyValueFactory<>("ESTADO"));
        Col_Telefono.setCellValueFactory(new PropertyValueFactory<>("TELEFONO"));
        Col_RFC.setCellValueFactory(new PropertyValueFactory<>("RFC"));
        Col_Email.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        Col_cp.setCellValueFactory(new PropertyValueFactory<>("CP"));
        Col_Colonia.setCellValueFactory(new PropertyValueFactory<>("COLONIA"));

        Col_LimiteCredito.setCellValueFactory(new PropertyValueFactory<>("LIMITECREDITO"));
        Col_MetodoPago.setCellValueFactory(new PropertyValueFactory<>("METODOPAGO"));
        Col_DiasCredito.setCellValueFactory(new PropertyValueFactory<>("DIASCREDITO"));
        Col_Vendedor.setCellValueFactory(new PropertyValueFactory<>("VENDEDOR"));

        Tabla_Clientes.setItems(ObslClientes);

        FilteredList<Cliente> filteredData = new FilteredList<>(ObslClientes, p -> true);

        txt_busqueda.textProperty().addListener((observable, oldValue, newValue)
                -> {
            filteredData.setPredicate(Cliente
                    -> {
                // Si el texto del filtro está vacío, muestre todo el inventario.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Comparar el codigo y la descripcion de cada articulo con el texto del filtro
                String lowerCaseFilter = newValue.toLowerCase();
                if (Cliente.getNOMBRE().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con el primer codigo.
                } else if (Cliente.getRFC().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // El filtro coincide con la descripcion.
                }
                return false; // No existe ninguna coincidencia.
            });

        });

        // 3. Envolver la lista filtrada en una lista clasificada.
        SortedList<Cliente> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(Tabla_Clientes.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        Tabla_Clientes.setItems(sortedData);

    }

    @FXML
    private void AccionNuevoCliente(ActionEvent event) {

        if (Integer.parseInt(IDUsuario) > 0) {
            txt_nombre.setText("");
            txt_calle.setText("");
            txt_ciudad.setText("");
            combobox_estado.setValue("");
            txt_telefono.setText("");
            txt_rfc.setText("");
            txt_email.setText("");
            txt_cp.setText("");
            txt_colonia.setText("");
            IDUsuario = "0";
            Btn_acciones.setText("Guardar");
            btn_aplica_cambios.setDisable(true);

            txt_diasCredito.setText("");
            txt_limiteCredito.setText("");
            ComboboxMetodoPago.getSelectionModel().selectFirst();
            ComboboxVendedor.getSelectionModel().selectFirst();
        } else {

            String nombre = txt_nombre.getText();
            String calle = txt_calle.getText();
            String colonia = txt_colonia.getText();
            String cidad = txt_ciudad.getText();

            String cp = txt_cp.getText();
            String estado = combobox_estado.getValue();
            String telefono = txt_telefono.getText();
            String rfc = txt_rfc.getText();
            String email = txt_email.getText();

            String LimiteCredito = txt_limiteCredito.getText();
            String MetodoPago = ComboboxMetodoPago.getValue().getCODIGO();
            String DiasCredito = txt_diasCredito.getText();
            String VendedorID = ComboboxVendedor.getValue().getID();

            if (nombre.isEmpty() && calle.isEmpty() && cidad.isEmpty() && telefono.isEmpty() && rfc.isEmpty() && email.isEmpty() && cp.isEmpty() && colonia.isEmpty()) {

                alertas.arlertaInformacion("Nuevo Cliente", "Favor de completar los datos");
            } else {

                ResultSet rs = null;
                PreparedStatement ps = null;
                PreparedStatement ps2 = null;
                Connection cnx = null;

                String sql = "INSERT INTO Ncc_Clientes("
                        + "Client_Nombre,"
                        + "Client_Calle,"
                        + "Client_Colonia,"
                        + "Client_Ciudad,"
                        + "Client_Cp,"
                        + "Client_Estado,"
                        + "Client_Telefono1,"
                        + "Client_RFC,"
                        + "Client_email,"
                        + "Client_LimiteCredito,"
                        + "Client_metodoPago,"
                        + "Client_DiasCredito,"
                        + "Client_ID_vendedor) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                String SqlFind = "SELECT * FROM Ncc_Clientes WHERE Client_RFC=? LIMIT 1";

                conexion x = new conexion();
                cnx = x.crearConexion(BDatos);

                try {
                    ps = cnx.prepareStatement(SqlFind);
                    ps.setString(1, rfc);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        ps2 = cnx.prepareStatement(sql);
                        ps2.setString(1, nombre);
                        ps2.setString(2, calle);
                        ps2.setString(3, colonia);
                        ps2.setString(4, cidad);
                        ps2.setString(5, cp);
                        ps2.setString(6, estado);
                        ps2.setString(7, telefono);
                        ps2.setString(8, rfc);
                        ps2.setString(9, email);
                        ps2.setString(10, LimiteCredito);
                        ps2.setString(11, MetodoPago);
                        ps2.setString(12, DiasCredito);
                        ps2.setString(13, VendedorID);

                        if (ps2.executeUpdate() > 0) {
                            alertas.NotificacionInformacion("Cliente", "Cliente creado correctamente");
                            TablaClientesCargar();

                            txt_nombre.setText("");
                            txt_calle.setText("");
                            txt_colonia.setText("");
                            txt_ciudad.setText("");

                            txt_cp.setText("");
                            combobox_estado.getSelectionModel().selectFirst();
                            txt_telefono.setText("");
                            txt_rfc.setText("");
                            txt_email.setText("");

                            txt_limiteCredito.setText("");
                            ComboboxMetodoPago.getSelectionModel().selectFirst();
                            txt_diasCredito.setText("");
                            ComboboxVendedor.getSelectionModel().selectFirst();
                        }
                    } else {
                        alertas.NotificacionWarning("Sin exito", "El cliente con RFC " + rfc + " Existe");
                    }
                } catch (SQLException e) {
                    alertas.alertaDeExcepcion("Error conecion", "Upps", "servido", e.getMessage());
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
                    }
                }

            }

        }

    }

    @FXML
    private void AccionClicTablaCliente(MouseEvent event) {
        if (event.getClickCount() == 2) {
            index = Tabla_Clientes.getSelectionModel().getSelectedIndex();

            String Nombre = Tabla_Clientes.getItems().get(index).getNOMBRE();
            String Calle = Tabla_Clientes.getItems().get(index).getCALLE();
            String Ciudad = Tabla_Clientes.getItems().get(index).getCIUDAD();
            String Estado = Tabla_Clientes.getItems().get(index).getESTADO();
            String Telefono = Tabla_Clientes.getItems().get(index).getTELEFONO();
            String RFC = Tabla_Clientes.getItems().get(index).getRFC();
            String Email = Tabla_Clientes.getItems().get(index).getEMAIL();
            String cp = Tabla_Clientes.getItems().get(index).getCP();
            String Colonia = Tabla_Clientes.getItems().get(index).getCOLONIA();

            String LimiteCredito = Tabla_Clientes.getItems().get(index).getLIMITECREDITO();
            String MetodoPago = Tabla_Clientes.getItems().get(index).getMETODOPAGO();
            String DiasCredito = Tabla_Clientes.getItems().get(index).getDIASCREDITO();
            String VendedorID = Tabla_Clientes.getItems().get(index).getVENDEDOR();

            IDUsuario = Tabla_Clientes.getItems().get(index).getID();
            Btn_acciones.setText("Limpiar");

            txt_nombre.setText(Nombre);
            txt_calle.setText(Calle);
            txt_ciudad.setText(Ciudad);
            combobox_estado.setValue(Estado);
            txt_telefono.setText(Telefono);
            txt_rfc.setText(RFC);
            txt_email.setText(Email);
            txt_cp.setText(cp);
            txt_colonia.setText(Colonia);
            btn_aplica_cambios.setDisable(false);

            txt_limiteCredito.setText(LimiteCredito);

            ComboboxMetodoPago.getItems().forEach((codPag) -> {
                if (MetodoPago.equals(codPag.getDESCRIPCION())) {

                    ComboboxMetodoPago.getSelectionModel().select(new MetodosPago(codPag.getCODIGO(), codPag.getDESCRIPCION()));
                }
            });

            txt_diasCredito.setText(DiasCredito);

            ComboboxVendedor.getItems().forEach((vendedor) -> {
                if (VendedorID.equals(vendedor.getNOMBRE())) {
//                    ComboboxVendedor.getSelectionModel().select(new Usuario(vendedor.getID(), vendedor.getNOMBRE()));

                }
            });

            //System.out.println(ComboboxMetodoPago.getValue().getCODIGO());
        }
    }

    @FXML
    private void AccionAplicaCambios(ActionEvent event) {
        String NombreTem = Tabla_Clientes.getItems().get(index).getNOMBRE();

        boolean resp = alertas.alertaConfirmacion("Edicion de cliente", NombreTem, "¿Desea aplicar cambios?");
        if (resp && Integer.parseInt(IDUsuario) > 0) {
            ResultSet rs = null;
            PreparedStatement ps = null;
            Connection cnx = null;

            String sql = "UPDATE Ncc_Clientes SET "
                    + "Client_Nombre=?, "
                    + "Client_Calle=?, "
                    + "Client_Colonia=?,"
                    + "Client_Ciudad=?,"
                    + "Client_Cp=?,"
                    + "Client_Estado=?,"
                    + "Client_Telefono1=?,"
                    + "Client_RFC=?,"
                    + "Client_email=?,"
                    + "Client_LimiteCredito = ?,"
                    + "Client_metodoPago = ?,"
                    + "Client_DiasCredito = ?,"
                    + "Client_ID_vendedor = ?"
                    + "WHERE Client_Id = ?";
            String Nombre = txt_nombre.getText();
            String Calle = txt_calle.getText();
            String Colonia = txt_colonia.getText();
            String Ciudad = txt_ciudad.getText();
            String cp = txt_cp.getText();
            String Estado = combobox_estado.getValue();
            String Telefono = txt_telefono.getText();
            String RFC = txt_rfc.getText();
            String Email = txt_email.getText();

            String LimiteCredito = txt_limiteCredito.getText();
            String MetodoPago = ComboboxMetodoPago.getValue().getCODIGO();
            String DiasCredito = txt_diasCredito.getText();
            String VendedorID = ComboboxVendedor.getValue().getID();

            try {

                conexion x = new conexion();
                cnx = x.crearConexion(BDatos);

                ps = cnx.prepareStatement(sql);

                ps.setString(1, Nombre);
                ps.setString(2, Calle);
                ps.setString(3, Colonia);
                ps.setString(4, Ciudad);
                ps.setString(5, cp);
                ps.setString(6, Estado);
                ps.setString(7, Telefono);
                ps.setString(8, RFC);
                ps.setString(9, Email);
                ps.setString(10, LimiteCredito);
                ps.setString(11, MetodoPago);
                ps.setString(12, DiasCredito);
                ps.setString(13, VendedorID);
                ps.setString(14, IDUsuario);

                if (ps.executeUpdate() > 0) {
                    alertas.arlertaInformacion("Actualizacion", "Actualizacion correcta");

                    TablaClientesCargar();

                    txt_nombre.setText("");
                    txt_calle.setText("");
                    txt_colonia.setText("");
                    txt_ciudad.setText("");

                    txt_cp.setText("");
                    combobox_estado.getSelectionModel().selectFirst();
                    txt_telefono.setText("");
                    txt_rfc.setText("");
                    txt_email.setText("");

                    txt_limiteCredito.setText("");
                    ComboboxMetodoPago.getSelectionModel().selectFirst();
                    txt_diasCredito.setText("");
                    ComboboxVendedor.getSelectionModel().selectFirst();
                    
                    Btn_acciones.setText("Guardar");
                    IDUsuario = "0";

                }
            } catch (SQLException e) {
                alertas.alertaDeExcepcion("Error", "Ocurrio un error", e.getSQLState(), e.toString());
            }
        } else {
            alertas.arlertaInformacion("Actualizacion", "Debe seleccionar un usuario");
        }

    }

    private void CargaMetodosPagos() {
        OBSerMetPagos = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_MetodosPago";
        conexion x = new conexion();
        cnx = x.crearConexion(BDatos);

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                OBSerMetPagos.add(new MetodosPago(rs.getString("MPag_Codigo"), rs.getString("MPag_Descripcion")));

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

        ComboboxMetodoPago.getItems().addAll(OBSerMetPagos);
        ComboboxMetodoPago.getSelectionModel().selectFirst();

    }

    private void CargaVendedor() {
        OBSvendedor = FXCollections.observableArrayList();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_User";
        conexion x = new conexion();
        cnx = x.crearConexion(BDatos);

        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
//                OBSvendedor.add(new Usuario(rs.getString("User_ID"), rs.getString("User_Usuario")));

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

        ComboboxVendedor.getItems().addAll(OBSvendedor);

        ComboboxVendedor.getSelectionModel().selectFirst();

    }

}
