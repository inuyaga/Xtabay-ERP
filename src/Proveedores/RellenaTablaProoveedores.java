package Proveedores;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class RellenaTablaProoveedores
{
    private final StringProperty IDProveedor;
    private final StringProperty RFC;
    private final StringProperty NOMBREPROVEEDOR;
    private final StringProperty DIASCREDITO;
    private final StringProperty METODOPAGO;
    private final StringProperty CORREO;
    private final StringProperty TELEFONO;
    private final StringProperty CALLE;
    private final StringProperty CP;
    private final StringProperty COLONIA;
    private final StringProperty CIUDAD;
    private final StringProperty ESTADO;
    private final StringProperty LIMITECREDITO;
    private final StringProperty TIEMPOENTREFA;

    public final String getLIMITECREDITO() {
        return LIMITECREDITO.get();
    }

    public final void setLIMITECREDITO(String value) {
        LIMITECREDITO.set(value);
    }

    public StringProperty LIMITECREDITOProperty() {
        return LIMITECREDITO;
    }

    public final String getTIEMPOENTREFA() {
        return TIEMPOENTREFA.get();
    }

    public final void setTIEMPOENTREFA(String value) {
        TIEMPOENTREFA.set(value);
    }

    public StringProperty TIEMPOENTREFAProperty() {
        return TIEMPOENTREFA;
    }

    public final String getIDProveedor() {
        return IDProveedor.get();
    }

    public final void setIDProveedor(String value) {
        IDProveedor.set(value);
    }

    public StringProperty IDProveedorProperty() {
        return IDProveedor;
    }

    public final String getRFC() {
        return RFC.get();
    }

    public final void setRFC(String value) {
        RFC.set(value);
    }

    public StringProperty RFCProperty() {
        return RFC;
    }

    public final String getNOMBREPROVEEDOR() {
        return NOMBREPROVEEDOR.get();
    }

    public final void setNOMBREPROVEEDOR(String value) {
        NOMBREPROVEEDOR.set(value);
    }

    public StringProperty NOMBREPROVEEDORProperty() {
        return NOMBREPROVEEDOR;
    }

    public final String getDIASCREDITO() {
        return DIASCREDITO.get();
    }

    public final void setDIASCREDITO(String value) {
        DIASCREDITO.set(value);
    }

    public StringProperty DIASCREDITOProperty() {
        return DIASCREDITO;
    }

    public final String getMETODOPAGO() {
        return METODOPAGO.get();
    }

    public final void setMETODOPAGO(String value) {
        METODOPAGO.set(value);
    }

    public StringProperty METODOPAGOProperty() {
        return METODOPAGO;
    }

    public final String getCORREO() {
        return CORREO.get();
    }

    public final void setCORREO(String value) {
        CORREO.set(value);
    }

    public StringProperty CORREOProperty() {
        return CORREO;
    }

    public final String getTELEFONO() {
        return TELEFONO.get();
    }

    public final void setTELEFONO(String value) {
        TELEFONO.set(value);
    }

    public StringProperty TELEFONOProperty() {
        return TELEFONO;
    }

    public final String getCALLE() {
        return CALLE.get();
    }

    public final void setCALLE(String value) {
        CALLE.set(value);
    }

    public StringProperty CALLEProperty() {
        return CALLE;
    }

    public final String getCP() {
        return CP.get();
    }

    public final void setCP(String value) {
        CP.set(value);
    }

    public StringProperty CPProperty() {
        return CP;
    }

    public final String getCOLONIA() {
        return COLONIA.get();
    }

    public final void setCOLONIA(String value) {
        COLONIA.set(value);
    }

    public StringProperty COLONIAProperty() {
        return COLONIA;
    }

    public final String getCIUDAD() {
        return CIUDAD.get();
    }

    public final void setCIUDAD(String value) {
        CIUDAD.set(value);
    }

    public StringProperty CIUDADProperty() {
        return CIUDAD;
    }

    public final String getESTADO() {
        return ESTADO.get();
    }

    public final void setESTADO(String value) {
        ESTADO.set(value);
    }

    public StringProperty ESTADOProperty() {
        return ESTADO;
    }
    
    public RellenaTablaProoveedores(String idpro, String rfc, String Nombre, String dias, String mpago, String correo, String telefono,String calle,String cp,String colonia,String ciudad, String estado, String T_Entrega,String LimiteCred)
    {
        this.IDProveedor=new SimpleStringProperty(idpro);
        this.RFC=new SimpleStringProperty(rfc);
        this.NOMBREPROVEEDOR=new SimpleStringProperty(Nombre);
        this.DIASCREDITO=new SimpleStringProperty(dias);
        this.METODOPAGO=new SimpleStringProperty(mpago);
        this.CORREO=new SimpleStringProperty(correo);
        this.TELEFONO=new SimpleStringProperty(telefono);
        this.CALLE=new SimpleStringProperty(calle);
        this.CP=new SimpleStringProperty(cp);
        this.COLONIA=new SimpleStringProperty(colonia);
        this.CIUDAD=new SimpleStringProperty(ciudad);
        this.ESTADO=new SimpleStringProperty(estado);
        this.LIMITECREDITO=new SimpleStringProperty(LimiteCred);
        this.TIEMPOENTREFA=new SimpleStringProperty(T_Entrega);
    }
}
