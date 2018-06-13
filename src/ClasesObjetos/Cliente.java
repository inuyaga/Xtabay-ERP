/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author esteban
 */
public class Cliente {
    
    private final StringProperty ID;
    private final StringProperty NOMBRE;
    private final StringProperty CALLE;
    private final StringProperty COLONIA;
    private final StringProperty CIUDAD;
    private final StringProperty ESTADO;
    private final StringProperty CP;
    private final StringProperty TELEFONO;
    private final StringProperty RFC;
    private final StringProperty EMAIL;
    
    private final StringProperty LIMITECREDITO;
    private final StringProperty METODOPAGO;
    private final StringProperty DIASCREDITO;
    private final StringProperty VENDEDOR;
    
    
     public Cliente(String id, String nombre, String calle, String colonia, 
             String ciudad, String estado, String cp, String teleono, 
             String rfc, String email,String limiteCredit,String metodoPago,String DiasCredit,String vendedor){
        this.ID=new SimpleStringProperty(id);
        this.NOMBRE=new SimpleStringProperty(nombre);
        this.CALLE=new SimpleStringProperty(calle);
        this.COLONIA=new SimpleStringProperty(colonia);
        this.CIUDAD=new SimpleStringProperty(ciudad);
        this.ESTADO=new SimpleStringProperty(estado);
        this.CP=new SimpleStringProperty(cp);
        this.TELEFONO=new SimpleStringProperty(teleono);
        this.RFC=new SimpleStringProperty(rfc);
        this.EMAIL=new SimpleStringProperty(email);
        
        this.LIMITECREDITO=new SimpleStringProperty(limiteCredit);
        this.METODOPAGO=new SimpleStringProperty(metodoPago);
        this.DIASCREDITO=new SimpleStringProperty(DiasCredit);
        this.VENDEDOR=new SimpleStringProperty(vendedor);
    }
    
    public final void setID(String value){
        ID.set(value);
    }
    
    
     public final String getID() {
        return ID.get();
    }

    public final StringProperty IDProperty() {
        return ID;
    }

    public final void setNOMBRE(String value) {
        NOMBRE.set(value);
    }

    public final String getNOMBRE() {
        return NOMBRE.get();
    }

    public final StringProperty NOMBREProperty() {
        return NOMBRE;
    }

    public final void setCALLE(String value) {
        CALLE.set(value);
    }

    public final String getCALLE() {
        return CALLE.get();
    }

    public final StringProperty CALLEProperty() {
        return CALLE;
    }

    public final void setCOLONIA(String value) {
        COLONIA.set(value);
    }

    public final String getCOLONIA() {
        return COLONIA.get();
    }

    public final StringProperty COLONIAProperty() {
        return COLONIA;
    }

    public final void setCIUDAD(String value) {
        CIUDAD.set(value);
    }

    public final String getCIUDAD() {
        return CIUDAD.get();
    }

    public final StringProperty CIUDADProperty() {
        return CIUDAD;
    }

    public final void setESTADO(String value) {
        ESTADO.set(value);
    }

    public final String getESTADO() {
        return ESTADO.get();
    }

    public final StringProperty ESTADOProperty() {
        return ESTADO;
    }

    public final void setCP(String value) {
        CP.set(value);
    }

    public final String getCP() {
        return CP.get();
    }

    public final StringProperty CPProperty() {
        return CP;
    }

    public final void setTELEFONO(String value) {
        TELEFONO.set(value);
    }

    public final String getTELEFONO() {
        return TELEFONO.get();
    }

    public final StringProperty TELEFONOProperty() {
        return TELEFONO;
    }

    public final void setRFC(String value) {
        RFC.set(value);
    }

    public final String getRFC() {
        return RFC.get();
    }

    public final StringProperty RFCProperty() {
        return RFC;
    }

    public final void setEMAIL(String value) {
        EMAIL.set(value);
    }

    public final String getEMAIL() {
        return EMAIL.get();
    }

    public final StringProperty EMAILProperty() {
        return EMAIL;
    }

    public final void setLIMITECREDITO(String value) {
        LIMITECREDITO.set(value);
    }

    public final String getLIMITECREDITO() {
        return LIMITECREDITO.get();
    }

    public final StringProperty LIMITECREDITOProperty() {
        return LIMITECREDITO;
    }

    public final void setMETODOPAGO(String value) {
        METODOPAGO.set(value);
    }

    public final String getMETODOPAGO() {
        return METODOPAGO.get();
    }

    public final StringProperty METODOPAGOProperty() {
        return METODOPAGO;
    }

    public final void setDIASCREDITO(String value) {
        DIASCREDITO.set(value);
    }

    public final String getDIASCREDITO() {
        return DIASCREDITO.get();
    }

    public final StringProperty DIASCREDITOProperty() {
        return DIASCREDITO;
    }

    public final void setVENDEDOR(String value) {
        VENDEDOR.set(value);
    }

    public final String getVENDEDOR() {
        return VENDEDOR.get();
    }

    public final StringProperty VENDEDORProperty() {
        return VENDEDOR;
    }
   
    
    
}
