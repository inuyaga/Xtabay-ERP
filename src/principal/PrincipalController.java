package principal;

import AltaProd.AltaProdController;
import Clientes.ClienteController;
import Grupo.EditarGruposController;
import Grupo.PermisosGruposController;
import alertas.alertas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import usuarios.ModificarUserController;
import usuarios.Permisos_adicionalesController;
import AperturaSucursal.AperturaSucController;
import ClasesObjetos.Usuario;
import Entradas.ComprasController;
import Inventario.DepartamentosController;

import bd.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.MenuItem;
import Proveedores.NuevoProveedorController;
import javafx.collections.ObservableList;
import Marcas.MarcasController;
import Proveedores.PagoAproveedoresController;
import Reportes.VisualizarPermisosController;
import Salidas.FXMLPuntoVentaController;
/**
 * FXML Controller class
 *
 * @author esteban
 */
public class PrincipalController implements Initializable {

    @FXML
    private BorderPane PaneRemplazo;

    private String basedatos = "";
    private List<Integer> PERMISOS;
    private ObservableList<Usuario> DatosUsuarios;

    @FXML
    private MenuItem ItemNuevaSucursal;
    @FXML
    private MenuItem ItemEditaGrupo;
    @FXML
    private MenuItem ItemCreaGrupos;
    @FXML
    private MenuItem ItemPermisosAdicionalGrupos;
    @FXML
    private MenuItem ItemUsrModificar;
    @FXML
    private MenuItem ItemUsrAgrega;
    @FXML
    private MenuItem ItemUsrPermisosAdicionales;
    @FXML
    private MenuItem ItemClientes;
    @FXML
    private MenuItem ItemProvedores;
    @FXML
    private MenuItem ItemInventarioDepo;
    @FXML
    private MenuItem ItemInventarioProductos;
    @FXML
    private MenuItem ItemReportePermisos;
    @FXML
    private MenuItem ItemReporteUsuarios;
    @FXML
    private MenuItem ItemReporteGrupos;
    @FXML
    private MenuItem ItemSistemasMateniiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void datos_usuarios(List<Integer> permisos, ObservableList<Usuario> DatosUsuarios, int IDSucursal) {
        this.PERMISOS = permisos;
        this.DatosUsuarios = DatosUsuarios;
        //Consulta la base de datos de la sucursal que se ha elegido o la que entra por default
        consultaBD(IDSucursal);
        //Metodoque valida los permisos del usuario logueado
       validaPermisos();
    }

    private void consultaBD(int IDSuc) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection bdase = null;
        String sql = "SELECT * FROM Ncc_Sucursales WHERE Suc_Id=?  LIMIT 1";
        conexion x = new conexion();
        x.crearConexion();

        bdase = x.getConexion();

        try {
            ps = bdase.prepareStatement(sql);
            ps.setInt(1, IDSuc);
            rs = ps.executeQuery();
            while (rs.next()) {
                basedatos = rs.getString("Suc_Db");
            }
            System.out.println(basedatos);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (bdase != null) {
                    bdase.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    @FXML
    private void AccionAcercaDe(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/acerca_de/acerca.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void accionAgregaUsuario(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/usuarios/agrega_usuario.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);

    }

    @FXML
    private void AccionCrearGrupo(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Grupo/FXMLGrupo.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionUsrModificar(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/usuarios/ModificarUser.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(ModificarUserController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionCerrarSesion(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ncc/FXMLDocument.fxml"));
            Scene ecena = new Scene(root);
            Stage ecenario = new Stage();
            ecenario.setScene(ecena);
            ecenario.setTitle("Login");

//            ecenario.initModality(Modality.WINDOW_MODAL);
//            ecenario.initStyle(StageStyle.UNDECORATED);
            ecenario.show();

            Stage stage = (Stage) PaneRemplazo.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AccionPermisosAdicionales(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/usuarios/permisos_adicionales.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(Permisos_adicionalesController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionBotonSalir(ActionEvent event) {
        boolean resp = alertas.alertaConfirmacion("Salir", "Salir del sistema", "¿Desea salir realmente?");
        if (resp) {
            System.exit(0);
        }
    }

    @FXML
    private void AccionEdicionGrupos(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Grupo/EditarGrupos.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(EditarGruposController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionClientes(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Clientes/Cliente.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, e);
        }
        ClienteController Cleinte = Loader.getController();
        Cleinte.ObtenerConexion(basedatos);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionPermisosGrupos(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Grupo/PermisosGrupos.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(PermisosGruposController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionAperturaSucursal(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/AperturaSucursal/AperturaSuc.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(AperturaSucController.class.getName()).log(Level.SEVERE, null, e);
        }
        // PrincipalController nccPrincipal = Loader.getController();
        //xtabay.setDatosUsuario(DatosUsuario, PermisosUsuario);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionDepartamentos(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Inventario/departamentos.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(DepartamentosController.class.getName()).log(Level.SEVERE, null, e);
        }
        DepartamentosController nccPrincipal = Loader.getController();
        nccPrincipal.setBd(basedatos);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    private void validaPermisos() {

        PERMISOS.forEach((PERMISOS1) -> {
            

            switch (PERMISOS1) {
                case 1:
                    //PUEDE CREAR UN NUEVO USUARIO
                    ItemUsrAgrega.setVisible(true);
                    break;
                case 2:
                    //PUEDE CREAR UN NUEVO GRUPO
                    ItemCreaGrupos.setVisible(true);
                    break;
                case 3:
                    //PUEDE CREAR UNA NUEVA SUCURSAL
                    ItemNuevaSucursal.setVisible(true);
                    break;
                case 4:
                    //MODIFICA EL NOMBRE DE UN GRUPO EXISTENTE
                    ItemEditaGrupo.setVisible(true);
                    break;
                case 9:
                    //REPORTE DE USUARIOS
                    ItemReporteUsuarios.setVisible(true);
                    break;
                case 10:
                  //REPORTE DE SUCURSALES
                    break;
                case 11:
                    //REPORTE DE PERMISOS
                    ItemReportePermisos.setVisible(true);
                    break;
                case 12:
                    //PUEDE MODIFICAR UN USUARIO
                    ItemUsrModificar.setVisible(true);
                    break;
                case 13:
                    //PUEDE AGREGAR PERMISOS ADICIONALES A USUARIOS
                    ItemUsrPermisosAdicionales.setVisible(true);
                    break;
                case 15:
                    //PUEDE OTORGAR PERMISOS A GRUPOS
                    ItemPermisosAdicionalGrupos.setVisible(true);
                    break;
                case 16:
                    //ALTA A CLIENTES
                    ItemClientes.setVisible(true);
                    break;
                case 17:
                    //CREA DEPARTAMENTOS
                    ItemInventarioDepo.setVisible(true);
                    break;
                case 18:
                    //CREA PRODUCTOS
                    ItemInventarioProductos.setVisible(true);
                    break;
                case 19:
                    //REPORTE DE GRUPOS
                    ItemReporteGrupos.setVisible(true);
                    break;
                case 20:
                    //VER PROVEDORES
                    ItemProvedores.setVisible(true);
                    break;
                case 21:
                    //MATENIMIENTO DE SISTEMA
                    ItemSistemasMateniiento.setVisible(true);
                    break;
            }
        });

    }

    @FXML
    private void AccionEntradasCompras(ActionEvent event)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Entradas/Compras.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, e);
        }
        ComprasController nccPrincipal = Loader.getController();
        nccPrincipal.setBd(basedatos,DatosUsuarios);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionItemPuntoVenta(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Salidas/FXMLPuntoVenta.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(FXMLPuntoVentaController.class.getName()).log(Level.SEVERE, null, e);
        }
        FXMLPuntoVentaController nccPrincipal = Loader.getController();
        nccPrincipal.setBd(basedatos, DatosUsuarios);
        

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionProveedores(ActionEvent event)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Proveedores/NuevoProveedor.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(NuevoProveedorController.class.getName()).log(Level.SEVERE, null, e);
        }
        NuevoProveedorController nccPrincipal = Loader.getController();
        nccPrincipal.setBasedeDatos(basedatos);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }
    
    @FXML
    private void AccionAltaProducto(ActionEvent event)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/AltaProd/AltaProd.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            Logger.getLogger(AltaProdController.class.getName()).log(Level.SEVERE, null, e);
        }
        AltaProdController nccPrincipal = Loader.getController();
        nccPrincipal.setBasedeDatos(basedatos);

        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionAltaMarcas(ActionEvent event)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Marcas/Marcas.fxml"));
        try
        {
            Loader.load();
        } catch (IOException e)
        {
            Logger.getLogger(MarcasController.class.getName()).log(Level.SEVERE, null, e);
        }
        MarcasController nccPrincipal = Loader.getController();
        nccPrincipal.setBasedeDatos(basedatos);
        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }
    
    @FXML
    private void AccionPagoProveedores(ActionEvent event)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Proveedores/PagoAproveedores.fxml"));
        try
        {
            Loader.load();
        } catch (IOException e)
        {
            Logger.getLogger(PagoAproveedoresController.class.getName()).log(Level.SEVERE, null, e);
        }
        PagoAproveedoresController nccPrincipal = Loader.getController();
        nccPrincipal.setBasedeDatos(basedatos,DatosUsuarios);
        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }

    @FXML
    private void AccionEntregaMercancia(ActionEvent event) {
    }

    @FXML
    private void AccionVisualizarPermisos(ActionEvent event)
    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Reportes/VisualizarPermisos.fxml"));
        try
        {
            Loader.load();
        } catch (IOException e)
        {
            Logger.getLogger(VisualizarPermisosController.class.getName()).log(Level.SEVERE, null, e);
        }
        VisualizarPermisosController nccPrincipal = Loader.getController();
        nccPrincipal.setDatos(basedatos);
        Parent p = Loader.getRoot();
        PaneRemplazo.setCenter(p);
    }
}
