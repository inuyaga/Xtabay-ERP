/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clientes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author esteban
 */
public class Departamento {

    private final StringProperty ID;
    private final StringProperty NOMBRE;

    public Departamento(String id, String nombre) {
        this.ID = new SimpleStringProperty(id);
        this.NOMBRE = new SimpleStringProperty(nombre);

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

    @Override
    public String toString() {
        return NOMBRE.get();
    }

}
