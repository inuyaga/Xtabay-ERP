/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventario;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author esteban
 */
public class TablaProductos {
    
    private final StringProperty ID;
    private final StringProperty INTERNO;
    private final StringProperty BARRAS;
    private final StringProperty DESCRIPCION;
    private final StringProperty DEPARTAMENTO;
    
    public TablaProductos(String id,String interno, String barras, String descripcion, String departamento){
        
        this.ID=new SimpleStringProperty(id);
        this.INTERNO=new SimpleStringProperty(interno);
        this.BARRAS=new SimpleStringProperty(barras);
        this.DESCRIPCION=new SimpleStringProperty(descripcion);
        this.DEPARTAMENTO=new SimpleStringProperty(departamento);
    
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

    public final String getINTERNO() {
        return INTERNO.get();
    }

    public final void setINTERNO(String value) {
        INTERNO.set(value);
    }

    public StringProperty INTERNOProperty() {
        return INTERNO;
    }

    public final String getBARRAS() {
        return BARRAS.get();
    }

    public final void setBARRAS(String value) {
        BARRAS.set(value);
    }

    public StringProperty BARRASProperty() {
        return BARRAS;
    }

    public final String getDESCRIPCION() {
        return DESCRIPCION.get();
    }

    public final void setDESCRIPCION(String value) {
        DESCRIPCION.set(value);
    }

    public StringProperty DESCRIPCIONProperty() {
        return DESCRIPCION;
    }

    public final String getDEPARTAMENTO() {
        return DEPARTAMENTO.get();
    }

    public final void setDEPARTAMENTO(String value) {
        DEPARTAMENTO.set(value);
    }

    public StringProperty DEPARTAMENTOProperty() {
        return DEPARTAMENTO;
    }
    
    
    
}
