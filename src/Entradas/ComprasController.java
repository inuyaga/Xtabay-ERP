/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entradas;

import ClasesObjetos.TABLA_COMPRA;
import ClasesObjetos.TipoCompra;
import ClasesObjetos.Usuario;
import ClasesObjetos.buscador.BProductoController;
import ClasesObjetos.buscador.TablaBuscador;
import alertas.alertas;
import bd.conexion;
import cma.carmelo.jasperviewerfx.JasperFX;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeString.trim;
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
public class ComprasController implements Initializable {

    private String BD;
    @FXML
    private TextField txt_factura;
    @FXML
    private DatePicker DateEntrega;
    @FXML
    private TextField txt_No_proveedor;
    
    @FXML
    private Text Label_fecha;
    @FXML
    private Text Label_No_compra;
    @FXML
    private Label Lable_Nombre_Prov;
    @FXML
    private Label Label_rfc_prov;
    @FXML
    private Label Label_correo_prov;

    private ObservableList<TABLA_COMPRA> OBserbaleTablaVentaTemp;
    private ObservableList<TABLA_COMPRA> OBserbaleTablaVenta;

   
    @FXML
    private TableColumn<?, ?> COL_CANTIDAD;
    @FXML
    private TableColumn<?, ?> COL_DESCRIPCION;
    @FXML
    private TableColumn<?, ?> COL_PRECIO;
    @FXML
    private TableColumn<?, ?> COL_IVA;
    @FXML
    private TableColumn<?, ?> COL_TOTAL;
    @FXML
    private Label LabelSumaTotal;

    private ObservableList<Usuario> USUARIO;
    @FXML
    private TableColumn<?, ?> COL_IEPS;
    @FXML
    private TableColumn<?, ?> COL_ISR;
    @FXML
    private ComboBox<TipoCompra> Chexbox_tipo_compra;
    @FXML
    private Label Label_dias_credito;
    @FXML
    private Label Lable_N_provedor;
    @FXML
    private TableColumn<?, ?> COL_UNIDAD;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Button btn_genera_compra;
    @FXML
    private Button btn_eliminar_compra;
    @FXML
    private Button btn_crear_folio_compra;
    @FXML
    private Button btn_imprime_compra;
    @FXML
    private Button btn_recibir;

    private String status_compra;
    
    @FXML
    private TableView<TABLA_COMPRA> TABLA_COMPRA;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        OBserbaleTablaVenta = FXCollections.observableArrayList();
        Date fecha = new Date();

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        Label_fecha.setText(formateador.format(fecha));
        Chexbox_tipo_compra.getItems().add(new TipoCompra("1", "Contado"));
        Chexbox_tipo_compra.getItems().add(new TipoCompra("2", "Credito"));
        Chexbox_tipo_compra.getSelectionModel().selectFirst();

    }

    public void setBd(String bd, ObservableList<Usuario> DatosUsuarios) {
        this.BD = bd;
        this.USUARIO = DatosUsuarios;
    }

    @FXML
    private void AccionAgregaProducto(ActionEvent event) {
        MuestraBusqueda();
    }

    private boolean band;

    private void MuestraBusqueda() {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/ClasesObjetos/buscador/BProducto.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(BProductoController.class.getName()).log(Level.SEVERE, null, e);
        }
        BProductoController principal = Loader.getController();
        principal.setDatos(BD);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("Busqueda");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        SumaEnTablaCompra(principal.getSeleccion());
        sumaTotal();

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
    }
    double sumatotal;

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
    private void AccionGeneraCompra(ActionEvent event) {

        if (Label_No_compra.getText().isEmpty()) {
            alertas.NotificacionWarning("Sin folio de compra", "Debe generar un folio de compra primero");
        } else if (Lable_N_provedor.getText().isEmpty()) {
            alertas.NotificacionWarning("Sin Numero de proveedor", "Debe ingresar un numero de proveedor");
        } else {
            boolean resp = alertas.alertaConfirmacion("Nueva Compra", "", "¿Generar compra?");
            if (resp) {
                CreacionDeCompra();
            }
        }

    }

    @FXML
    private void AccionEnterTxtProveedor(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (trim(txt_No_proveedor.getText()).isEmpty()) {       
            }else{
            BusquedaProveedor();
            }
            
        }
    }

    private void BusquedaProveedor() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Proveedores WHERE Pro_ID=?";
        conexion x = new conexion();
        cnx = x.crearConexion(BD);
        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, txt_No_proveedor.getText());
            rs = ps.executeQuery();

            if (rs.next()) {
                Lable_Nombre_Prov.setText(rs.getString("Pro_Proveedor"));
                Label_rfc_prov.setText(rs.getString("Pro_RFC"));
                Label_correo_prov.setText(rs.getString("Pro_Correo"));
                Label_dias_credito.setText(rs.getString("Pro_DiasCredito"));
                DateEntrega.setValue(LocalDate.now().plusDays(rs.getInt("Pro_TiempoEntrega")));
                Lable_N_provedor.setText(rs.getString("Pro_ID"));

            } else {
                Lable_Nombre_Prov.setText("");
                Label_rfc_prov.setText("");
                Label_correo_prov.setText("");
                Lable_N_provedor.setText("");
                alertas.NotificacionWarning("Codigo", "Clave de proveedor no existe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void AccionKeysPane(KeyEvent event) {
        final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        if (keyComb1.match(event)) {
            boolean respuesta = alertas.alertaConfirmacion("Nueva compra", "", "¿Desea generar una compra?");
            if (respuesta) {
                CreaFolioCompra();
            }
        }
    }

    @FXML
    private void AccionBotonConsulta(ActionEvent event) {
        String numero = alertas.alertaTextInput("Consulta de compra", "", "Numero de compra");
        if (isInteger(numero)) {

            ResultSet rs = null;
            ResultSet rs2 = null;
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            Connection cnx = null;
            String sql = "SELECT * FROM Ncc_Compras WHERE Compra_ID=?";

            String sql2 = "SELECT * FROM Ncc_Compra_Detalle WHERE Detalle_Compra_ID=?";
            conexion x = new conexion();
            cnx = x.crearConexion(BD);

            try {
                ps = cnx.prepareStatement(sql);
                ps2 = cnx.prepareStatement(sql2);
                ps.setString(1, numero);
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getString("Compra_fecha_entrega") == null && rs.getString("Compra_Factura") == null && rs.getString("Compra_Entrega") == null) {
                        alertas.NotificacionWarning("Compra indefinida", "Esta compra no ha sido terminada");
                        Label_No_compra.setText(rs.getString("Compra_ID"));
                        Label_fecha.setText(rs.getTimestamp("Compra_fecha_creacion").toLocalDateTime().toLocalDate().toString());
                        LimpiarCampos();
                    } else {
                        Chexbox_tipo_compra.getSelectionModel().selectFirst();
                        DateEntrega.setValue(rs.getTimestamp("Compra_fecha_entrega").toLocalDateTime().toLocalDate());
                        Label_fecha.setText(rs.getTimestamp("Compra_fecha_creacion").toLocalDateTime().toLocalDate().toString());
                        LabelSumaTotal.setText(rs.getString("Compra_monto_total"));
                        Label_No_compra.setText(rs.getString("Compra_ID"));
                        status_compra = rs.getString("Compra_Finiquitada");
                        txt_No_proveedor.setText(rs.getString("Compra_Proveedor_ID"));
                        BusquedaProveedor();
                        txt_factura.setText(rs.getString("Compra_Factura"));

                        if (rs.getInt("Compra_Entrega") == 0) {
                            btn_recibir.setDisable(false);
                        } else if (rs.getInt("Compra_Entrega") == 1) {
                            btn_recibir.setDisable(true);
                        }

                        txt_observaciones.setText(rs.getString("Compra_observacion"));

                        ps2.setInt(1, rs.getInt("Compra_ID"));
                        rs2 = ps2.executeQuery();

                        OBserbaleTablaVentaTemp = FXCollections.observableArrayList();
                        while (rs2.next()) {
                            OBserbaleTablaVentaTemp.add(new TABLA_COMPRA(
                                    rs2.getInt("Detalle_ID_Producto"),
                                    rs2.getDouble("Detalle_Cantidad"),
                                    rs2.getString("Detalle_Unidad"),
                                    rs2.getString("Detalle_Descripcion"),
                                    rs2.getDouble("Detalle_Precio_unitario"),
                                    rs2.getDouble("Detalle_Iva"),
                                    rs2.getDouble("Detalle_IEPS"),
                                    rs2.getDouble("Detalle_ISR"),
                                    rs2.getDouble("Detalle_Total"),
                                    rs2.getInt("Detalle_Iva_Real"),
                                    rs2.getInt("Detalle_IEPS_Real"),
                                    rs2.getInt("Detalle_ISR_Real"),
                                    rs2.getString("Detalle_Unidad_Real"))
                            );

                        }

                        COL_CANTIDAD.setCellValueFactory(new PropertyValueFactory<>("CANTIDAD"));
                        COL_UNIDAD.setCellValueFactory(new PropertyValueFactory<>("UNIDAD"));
                        COL_DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("DESCRIPCION"));
                        COL_PRECIO.setCellValueFactory(new PropertyValueFactory<>("PRECIO"));
                        COL_IVA.setCellValueFactory(new PropertyValueFactory<>("IVA"));
                        COL_IEPS.setCellValueFactory(new PropertyValueFactory<>("IEPS"));
                        COL_ISR.setCellValueFactory(new PropertyValueFactory<>("ISR"));
                        COL_TOTAL.setCellValueFactory(new PropertyValueFactory<>("TOTAL"));

                        TABLA_COMPRA.setItems(OBserbaleTablaVentaTemp);
                        btn_limpiar.setDisable(false);
                        btn_genera_compra.setDisable(true);
                        btn_eliminar_compra.setDisable(false);
                        btn_crear_folio_compra.setDisable(true);
                        btn_imprime_compra.setDisable(false);
                        txt_factura.setDisable(true);
                        btn_crear_folio_compra.setDisable(true);

                    }
                } else {
                    LimpiarCampos();
                    btn_limpiar.setDisable(true);
                    btn_genera_compra.setDisable(false);
                    btn_eliminar_compra.setDisable(true);
                    btn_crear_folio_compra.setDisable(false);
                    btn_imprime_compra.setDisable(true);
                    btn_crear_folio_compra.setDisable(false);
                    btn_recibir.setDisable(true);
                    alertas.NotificacionInformacion("Error numero compra", "¡El Folio:" + numero + " no existe! ingrese dato correcto");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            alertas.NotificacionError("Error", "Favor de introducir un numero");
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

    @FXML
    private void AccionCreaFolioCompra(ActionEvent event) {
        boolean respuesta = alertas.alertaConfirmacion("Creacion de folio compra", "", "¿Desea crear nuevo folio?");

        if (respuesta) {
            CreaFolioCompra();
        }

    }

    public void CreaFolioCompra() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "INSERT INTO Ncc_Compras(Compra_monto_total) VALUES(?)";
        conexion x = new conexion();
        cnx = x.crearConexion(BD);

        try {
            ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, 0.00);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            Label_No_compra.setText(rs.getString(1));

        } catch (SQLException ex) {
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void CreacionDeCompra() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        Connection cnx = null;
        String sql = "INSERT INTO Ncc_Compra_Detalle("
                + "Detalle_Cantidad,"
                + "Detalle_Descripcion,"
                + "Detalle_Precio_unitario,"
                + "Detalle_Iva,"
                + "Detalle_Total,"
                + "Detalle_Compra_ID,"
                + "Detalle_Unidad,"
                + "Detalle_IEPS,"
                + "Detalle_ISR,"
                + "Detalle_Iva_Real,"
                + "Detalle_IEPS_Real,"
                + "Detalle_ISR_Real,"
                + "Detalle_Unidad_Real,"
                + "Detalle_ID_Producto) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String sql2 = "UPDATE Ncc_Compras SET \n"
                + "Compra_fecha_entrega=?,\n"
                + "Compra_Factura=?,\n"
                + "Compra_Proveedor_ID=?,\n"
                + "Compra_usuario_creador=?,\n"
                + "Compra_monto_total=?\n,  "
                + "Compra_Entrega=?\n,  "
                + "Compra_contado_o_credito=?, \n"
                + "Compra_observacion=? \n"
                + "WHERE Compra_ID=?";

        String slq_actualiza = "UPDATE Ncc_Productos SET "
                + "Prod_Costo=?,"
                + "Prod_Existencia=Prod_Existencia+? "
                + "WHERE Prod_Id=?";
        conexion x = new conexion();
        cnx = x.crearConexion(BD);

        boolean entregado = false;

        try {
            cnx.setAutoCommit(false);
            ps = cnx.prepareStatement(sql);
            ps2 = cnx.prepareStatement(sql2);

            //compara si la compra lleva factura para saber si se actualiza existencia si o no
            if (!txt_factura.getText().isEmpty()) {
                ps3 = cnx.prepareStatement(slq_actualiza);
                for (ClasesObjetos.TABLA_COMPRA item : TABLA_COMPRA.getItems()) {
                    ps3.setDouble(1, item.getPRECIO());
                    ps3.setDouble(2, item.getCANTIDAD());
                    ps3.setInt(3, item.getID());
                    ps3.executeUpdate();
                }
                entregado = true;
            }

            ps2.setString(1, DateEntrega.getValue().toString());
            ps2.setString(2, txt_factura.getText());
            ps2.setString(3, txt_No_proveedor.getText());
            ps2.setString(4, USUARIO.get(0).getUSUARIO());
            ps2.setString(5, LabelSumaTotal.getText());
            if (entregado) {
                //La compra fue entregada
                ps2.setInt(6, 1);
            } else {
                //La compra no fue entregada estara esperando entrega
                ps2.setInt(6, 0);
            }
            ps2.setString(7, Chexbox_tipo_compra.getValue().getID());
            ps2.setString(8, txt_observaciones.getText());
            ps2.setString(9, Label_No_compra.getText());
            ps2.executeUpdate();

            for (ClasesObjetos.TABLA_COMPRA item : TABLA_COMPRA.getItems()) {
                ps.setDouble(1, item.getCANTIDAD());
                ps.setString(2, item.getDESCRIPCION());
                ps.setDouble(3, item.getPRECIO());
                ps.setDouble(4, item.getIVA());
                ps.setDouble(5, item.getTOTAL());
                ps.setString(6, Label_No_compra.getText());

                ps.setString(7, item.getUNIDAD());
                ps.setDouble(8, item.getIEPS());
                ps.setDouble(9, item.getISR());

                ps.setInt(10, item.getIVA_REAL());
                ps.setInt(11, item.getIEPS_REAL());
                ps.setInt(12, item.getISR_REAL());
                ps.setString(13, item.getUNIDAD_REAL());
                ps.setInt(14, item.getID());
                ps.executeUpdate();
            }

            cnx.commit();

            boolean respuesta = alertas.alertaConfirmacion("Impresion", "", "¿Desea imprimir?");
            if (respuesta) {
                HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                ParametrosJasperReport.put("ID_COMPRA", Integer.parseInt(Label_No_compra.getText()));
                ParametrosJasperReport.put("USER_IMPRIME", USUARIO.get(0).getUSUARIO());
                ParametrosJasperReport.put("EMPRESA_SELECCIONADA", USUARIO.get(0).getSUCURSAL_SELECCIONADA());
                InputStream fis2 = null;
                fis2 = this.getClass().getResourceAsStream("/Reportes/Orden_de_Compra.jasper");
                JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                //JasperPrintManager.printReport(print, true);
                JasperFX jpFX = new JasperFX(print);
                jpFX.show();
            }
            LimpiarCampos();
            alertas.NotificacionInformacion("Compra", "La compra se genero con exito");

        } catch (SQLException ex) {
            alertas.alertaDeExcepcion("Error", "", "El sigiente", ex.getMessage());
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
            if (cnx != null) {
                try {
                    cnx.rollback();
                    alertas.NotificacionError("Compra incumplida", "La compra no pudo completarse " + ex.getMessage());
                } catch (SQLException ex1) {
                    alertas.NotificacionError("Compra incumplida", "No se puedo deshacer cambios " + ex1.getMessage());
                }
            }
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps3 != null) {
                    ps3.close();
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

    private void LimpiarCampos() {
        Chexbox_tipo_compra.getSelectionModel().selectFirst();
        DateEntrega.setValue(LocalDate.now());
        LabelSumaTotal.setText("");
        Label_No_compra.setText("");
        Label_dias_credito.setText("");

        Lable_Nombre_Prov.setText("");
        Label_rfc_prov.setText("");
        Label_correo_prov.setText("");
        Lable_N_provedor.setText("");
        txt_No_proveedor.setText("");
        txt_factura.setText("");
        txt_factura.setDisable(false);
        txt_observaciones.setText("");
        TABLA_COMPRA.getItems().clear();

    }

    @FXML
    private void AccionEditaPrecio(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambio de precio");
        dialog.setHeaderText("Escriba nuevo precio");
        dialog.setContentText("Aqui:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(NumeroEscrito -> {

            int index = TABLA_COMPRA.getSelectionModel().getSelectedIndex();

            TABLA_COMPRA compra = TABLA_COMPRA.getItems().get(index);
            try {

                double recibido = Double.parseDouble(NumeroEscrito);

                compra.setPRECIO(recibido);
                double total = compra.getCANTIDAD() * compra.getPRECIO();

                double cien = 100;

                double IVA = (compra.getIVA_REAL() / cien) * total;
                double IEPS = (compra.getIEPS_REAL() / cien) * total;
                double ISR = (compra.getISR_REAL() / cien) * total;

                total += IVA + IEPS + ISR;

                BigDecimal redondeo = new BigDecimal(total);
                redondeo = redondeo.setScale(6, RoundingMode.HALF_UP);

                BigDecimal IVA_R = new BigDecimal(IVA);
                IVA_R = IVA_R.setScale(6, RoundingMode.HALF_UP);

                BigDecimal IEPS_R = new BigDecimal(IEPS);
                IEPS_R = IEPS_R.setScale(6, RoundingMode.HALF_UP);

                BigDecimal ISR_R = new BigDecimal(ISR);
                ISR_R = ISR_R.setScale(6, RoundingMode.HALF_UP);

                compra.setTOTAL(redondeo.doubleValue());

                compra.setIVA(IVA_R.doubleValue());
                compra.setIEPS(IEPS_R.doubleValue());
                compra.setISR(ISR_R.doubleValue());

                sumaTotal();

            } catch (NumberFormatException e) {
                alertas.NotificacionError("Dato Incorecto", "No es un numero; " + e.getMessage());
            }

        });

    }

    @FXML
    private void AccionBotonBuscarProveedor(ActionEvent event) {
        BusquedaProveedor();
    }

    @FXML
    private void AccionLimpiaCampos(ActionEvent event) {
        LimpiarCampos();
        btn_limpiar.setDisable(true);
        btn_genera_compra.setDisable(false);
        btn_eliminar_compra.setDisable(true);
        btn_crear_folio_compra.setDisable(false);
        btn_imprime_compra.setDisable(true);
        btn_crear_folio_compra.setDisable(false);
        btn_recibir.setDisable(true);

    }

    @FXML
    private void AccionEliminaCompra(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Eliminacion");
        alert.setHeaderText("Esta apunto de eliminar la compra N°:" + Label_No_compra.getText());
        alert.setContentText("¿Esta seguro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && status_compra.equals("0")) {

            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            Connection cnx = null;
            String sql = "DELETE FROM Ncc_Compra_Detalle WHERE Detalle_Compra_ID=?";

            String sql2 = "DELETE FROM Ncc_Compras WHERE Compra_ID=?";

            String slq_actualiza = "UPDATE Ncc_Productos SET "
                    + "Prod_Existencia=Prod_Existencia-? "
                    + "WHERE Prod_Id=?";
            conexion x = new conexion();
            cnx = x.crearConexion(BD);
            try {
                cnx.setAutoCommit(false);
                ps = cnx.prepareStatement(sql);
                ps2 = cnx.prepareStatement(sql2);

                ps.setString(1, Label_No_compra.getText());
                ps.executeUpdate();

                ps2.setString(1, Label_No_compra.getText());
                ps2.executeUpdate();

                if (!txt_factura.getText().isEmpty()) {
                    ps3 = cnx.prepareStatement(slq_actualiza);
                    for (ClasesObjetos.TABLA_COMPRA item : TABLA_COMPRA.getItems()) {
                        ps3 = cnx.prepareStatement(slq_actualiza);
                        ps3.setDouble(1, item.getCANTIDAD());
                        ps3.setInt(2, item.getID());
                        ps3.executeUpdate();
                    }
                }

                cnx.commit();
                alertas.NotificacionInformacion("Eliminacion", "La compra N°:" + Label_No_compra.getText() + " ha sido eliminado");

            } catch (SQLException ex) {
                if (cnx != null) {
                    try {
                        cnx.rollback();
                        alertas.NotificacionError("Error", "No se realizaron los cambios");
                    } catch (SQLException ex1) {
                        Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

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
                    if (ps3 != null) {
                        ps3.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            if (status_compra.equals("1")) {
                alertas.NotificacionError("Compra terminada", "No puede esliminar una compra que ya esta pagada");
            }
        }
    }

    @FXML
    private void AccionImprime(ActionEvent event) throws JRException {

        conexion x = new conexion();
        Connection cnx = x.crearConexion(BD);

        HashMap<String, Object> ParametrosJasperReport = new HashMap<>();

        ParametrosJasperReport.put("ID_COMPRA", Integer.parseInt(Label_No_compra.getText()));
        ParametrosJasperReport.put("USER_IMPRIME", USUARIO.get(0).getUSUARIO());
        ParametrosJasperReport.put("EMPRESA_SELECCIONADA", USUARIO.get(0).getSUCURSAL_SELECCIONADA());

        InputStream fis2 = null;
        fis2 = this.getClass().getResourceAsStream("/Reportes/Orden_de_Compra.jasper");
        JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
        JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
        JasperPrintManager.printReport(print, true);
//        JasperFX jpFX = new JasperFX(print);
//        jpFX.show();

    }

    @FXML
    private void AccionRebibeMercancia(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Captura de Factura");
        dialog.setHeaderText("Factura para la compra N°:" + Label_No_compra.getText());
        dialog.setContentText("Ingrese la factura:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            boolean res = alertas.alertaConfirmacion("Factura", "", "¿Es correcto esta captura? " + result.get());
            if (res) {

                ResultSet rs = null;
                PreparedStatement ps = null;
                PreparedStatement ps3 = null;
                Connection cnx = null;
                String sql = "UPDATE Ncc_Compras SET Compra_Factura=?, Compra_Entrega=? WHERE Compra_ID=?";
                String slq_actualiza = "UPDATE Ncc_Productos SET "
                        + "Prod_Costo=?,"
                        + "Prod_Existencia=Prod_Existencia+? "
                        + "WHERE Prod_Id=?";

                conexion x = new conexion();
                cnx = x.crearConexion(BD);

                try {
                    cnx.setAutoCommit(false);
                    ps = cnx.prepareStatement(sql);
                    ps3 = cnx.prepareStatement(slq_actualiza);

                    ps.setString(1, result.get());
                    ps.setInt(2, 1);
                    ps.setString(3, Label_No_compra.getText());
                    ps.executeUpdate();

                    for (ClasesObjetos.TABLA_COMPRA item : TABLA_COMPRA.getItems()) {
                        ps3.setDouble(1, item.getPRECIO());
                        ps3.setDouble(2, item.getCANTIDAD());
                        ps3.setInt(3, item.getID());
                        ps3.executeUpdate();
                    }
                    cnx.commit();
                    btn_recibir.setDisable(true);
                    txt_factura.setText(result.get());
                    alertas.NotificacionInformacion("Registros actualizados", "En hora buena");
                } catch (SQLException ex) {

                    try {
                        if (cnx != null) {
                            cnx.rollback();
                        }
                    } catch (SQLException e) {
                        Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, e);
                    }
                    Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {

                    try {
                        if (cnx != null) {
                            cnx.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                        if (ps3 != null) {
                            ps3.close();
                        }
                    } catch (SQLException e) {
                    }

                }

            }
        }

    }

}
