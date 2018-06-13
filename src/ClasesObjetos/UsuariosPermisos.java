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
public class UsuariosPermisos {

    private final StringProperty ID;
    private final StringProperty USUARIO;
    private final StringProperty NOMBRE;

    public UsuariosPermisos(String id, String user, String nom) {
        this.ID = new SimpleStringProperty(id);
        this.USUARIO = new SimpleStringProperty(user);
        this.NOMBRE = new SimpleStringProperty(nom);
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

    public final String getUSUARIO() {
        return USUARIO.get();
    }

    public final void setUSUARIO(String value) {
        USUARIO.set(value);
    }

    public StringProperty USUARIOProperty() {
        return USUARIO;
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

}
