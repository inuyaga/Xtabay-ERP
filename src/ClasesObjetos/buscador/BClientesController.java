/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos.buscador;

import bd.conexion;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author soporte
 */
public class BClientesController implements Initializable {

    private String BDatos;
    private String RFC_CLIENTE;
    @FXML
    private TableView<BClientesTabla> Tabla_Cliente;
    private ObservableList<BClientesTabla> OBSCliente;
    @FXML
    private TableColumn<?, ?> COL_RFC;
    @FXML
    private TableColumn<?, ?> COL_NOMBRE;
    @FXML
    private TextField txt_buscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDatos(String bd) {

        this.BDatos = bd;
        
        GeneraTablaCliente();
    }
    
    public String getRFC(){
    return RFC_CLIENTE;
    }
    
    private void GeneraTablaCliente(){
    
    ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql = "SELECT * FROM Ncc_Clientes";
        conexion x = new conexion();
        cnx = x.crearConexion(BDatos);
        
        OBSCliente=FXCollections.observableArrayList();
        
        try {
            ps=cnx.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
               OBSCliente.add(new BClientesTabla(rs.getString("Client_RFC"), rs.getString("Client_Nombre")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        COL_NOMBRE.setCellValueFactory(new PropertyValueFactory<>("NOMBRE"));
        COL_RFC.setCellValueFactory(new PropertyValueFactory<>("RFC"));
        
        Tabla_Cliente.setItems(OBSCliente);
        
         FilteredList<BClientesTabla> filteredData = new FilteredList<>(OBSCliente, p -> true);

        txt_buscar.textProperty().addListener((observable, oldValue, newValue)
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
        SortedList<BClientesTabla> sortedData = new SortedList<>(filteredData);
        // 4. Vincule el comparador SortedList al comparador TableView.
        sortedData.comparatorProperty().bind(Tabla_Cliente.comparatorProperty());
        // 5. Agregue los datos clasificados (y filtrados) a la tabla.
        Tabla_Cliente.setItems(sortedData);
        
    }

    @FXML
    private void AccionDobleClicTabla(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index=Tabla_Cliente.getSelectionModel().getSelectedIndex();
            RFC_CLIENTE=Tabla_Cliente.getItems().get(index).getRFC();
            
            final Node source = (Node) event.getSource();
            final Stage nodo = (Stage) source.getScene().getWindow();
            nodo.close();
        }
    }

}
