/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Salidas;

import ClasesObjetos.CobroDatos;
import ClasesObjetos.MetodosPago;
import ClasesObjetos.TipoCompra;
import bd.conexion;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author soporte
 */
public class CobroController implements Initializable {

    @FXML
    private ChoiceBox<TipoCompra> ChoiceBoxTipo;
    @FXML
    private ChoiceBox<MetodosPago> ChoiceBox_Metodo_Pago;
    @FXML
    private TextField txt_pagocliente;
    @FXML
    private Label Label_total;
    @FXML
    private Label Label_Cambio;
    
    private String BasesDatos;
    @FXML
    private VBox VBOX_Cuenta;
    @FXML
    private TextField txt_No_Cuenta;
    @FXML
    private Label Resta;
    
    private ObservableList<CobroDatos> DATOS_PAGOS;
    private String ActualizaStado;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DATOS_PAGOS=FXCollections.observableArrayList();
        ChoiceBoxTipo.getItems().add(new TipoCompra("1", "Contado"));
        ChoiceBoxTipo.getItems().add(new TipoCompra("2", "Credito"));
        ChoiceBoxTipo.getSelectionModel().selectFirst();
    }

    public void setDatos(String bd, String Total) {
        Label_total.setText(Total);
        this.BasesDatos = bd;
        
        consultaMetodoPago();
        CambioEnMetodoPago();
        CambioTextFielEfectivoCliente();
    }
    
    public ObservableList<CobroDatos> getDatos_Cobro() {
        return DATOS_PAGOS;
    }
    public String getStadoVenta() {
        return ActualizaStado;
    }
    
    private void consultaMetodoPago(){
    
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_MetodosPago";
        conexion x = new conexion();
        cnx = x.crearConexion(BasesDatos);
        
        try {
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChoiceBox_Metodo_Pago.getItems().add(new MetodosPago(rs.getString("MPag_Codigo"), rs.getString("MPag_Descripcion")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, e);
            }
            
            ChoiceBoxTipo.getSelectionModel().selectFirst();
        }

        

        
    }
    
    
    private void CambioEnMetodoPago() {
        ChoiceBox_Metodo_Pago.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getCODIGO().equals("03") || newValue.getCODIGO().equals("28") || newValue.getCODIGO().equals("04") || newValue.getCODIGO().equals("02")) {
                VBOX_Cuenta.setVisible(true);
                txt_pagocliente.setText(Label_total.getText());
                txt_pagocliente.setDisable(true);
            } else {
                VBOX_Cuenta.setVisible(false);
                txt_pagocliente.setDisable(false);
            }
        });
    }
    
   
    private void CambioTextFielEfectivoCliente() {
        txt_pagocliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                double total = Double.parseDouble(Label_total.getText());
                double nuevo = Double.parseDouble(newValue);
                double TOTAL = total - nuevo;
                
                if (TOTAL <= 0.0) {
                    TOTAL = TOTAL * -1;
                    BigDecimal totalFinal=new BigDecimal(TOTAL);
                    totalFinal=totalFinal.setScale(2, RoundingMode.HALF_UP);
                    Label_Cambio.setText(totalFinal + "");
                    Resta.setText("");
                } else if (TOTAL > 0.0) {
                    BigDecimal totalFinal=new BigDecimal(TOTAL);
                    totalFinal=totalFinal.setScale(2, RoundingMode.HALF_UP);
                    Resta.setText(totalFinal + "");
                    Label_Cambio.setText("");
                }
            } else {
                Resta.setText("");
                Label_Cambio.setText("");
            }
            
        });
    }

    @FXML
    private void AccionCancelar(ActionEvent event) {
        DATOS_PAGOS.clear();
        final Node source = (Node) event.getSource();
        final Stage nodo = (Stage) source.getScene().getWindow();
        nodo.close();
    }

    @FXML
    private void AccionAceptar(ActionEvent event) {
        DATOS_PAGOS.add(new CobroDatos(ChoiceBoxTipo.getValue().getID(), ChoiceBox_Metodo_Pago.getValue().getCODIGO(), txt_pagocliente.getText(), txt_No_Cuenta.getText()));
        final Node source = (Node) event.getSource();
        final Stage nodo = (Stage) source.getScene().getWindow();
        nodo.close();
    }

    
}
