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
public class Usuario {

    private final StringProperty ID;
    private final StringProperty NOMBRE;
    private final StringProperty VIGENCIA;
    private final StringProperty SUCURSALID;
    private final StringProperty USUARIO;
    private final StringProperty NOMBRESUC;
    private final StringProperty SUCURSAL_SELECCIONADA;

    public Usuario(String id, String nombre, String vigencia, String SucursalID, String Usuario, String NombreSUC, String suc_select) {
        this.ID = new SimpleStringProperty(id);
        this.NOMBRE = new SimpleStringProperty(nombre);
        this.VIGENCIA = new SimpleStringProperty(vigencia);
        this.SUCURSALID = new SimpleStringProperty(SucursalID);
        this.USUARIO = new SimpleStringProperty(Usuario);
        this.NOMBRESUC = new SimpleStringProperty(NombreSUC);
        this.SUCURSAL_SELECCIONADA = new SimpleStringProperty(suc_select);
    }

    public final String getID() {
        return ID.get();
    }

    public final void setID(String value) {
        ID.set(value);
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public final String getNOMBRE() {
        return NOMBRE.get();
    }

    public final void setNOMBRE(String value) {
        NOMBRE.set(value);
    }

    public StringProperty NOMBREProperty() {
        return NOMBRE;
    }

    public final String getVIGENCIA() {
        return VIGENCIA.get();
    }

    public final void setVIGENCIA(String value) {
        VIGENCIA.set(value);
    }

    public StringProperty VIGENCIAProperty() {
        return VIGENCIA;
    }

    public final String getSUCURSALID() {
        return SUCURSALID.get();
    }

    public final void setSUCURSALID(String value) {
        SUCURSALID.set(value);
    }

    public StringProperty SUCURSALIDProperty() {
        return SUCURSALID;
    }

    public final String getUSUARIO() {
        return USUARIO.get();
    }

    public final void setUSUARIO(String value) {
        USUARIO.set(value);
    }

    public StringProperty USUARIOProperty() {
        return USUARIO;
    }

    public final String getNOMBRESUC() {
        return NOMBRESUC.get();
    }

    public final void setNOMBRESUC(String value) {
        NOMBRESUC.set(value);
    }

    public StringProperty NOMBRESUCProperty() {
        return NOMBRESUC;
    }

    public final String getSUCURSAL_SELECCIONADA() {
        return SUCURSAL_SELECCIONADA.get();
    }

    public final void setSUCURSAL_SELECCIONADA(String value) {
        SUCURSAL_SELECCIONADA.set(value);
    }

    public StringProperty SUCURSAL_SELECCIONADAProperty() {
        return SUCURSAL_SELECCIONADA;
    }

    @Override
    public String toString() {
        return NOMBRE.toString();
    }

}
