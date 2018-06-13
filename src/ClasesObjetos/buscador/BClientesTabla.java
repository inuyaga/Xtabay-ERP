/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos.buscador;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class BClientesTabla {
    
    private final StringProperty RFC;
    private final StringProperty NOMBRE;

    public final String getRFC() {
        return RFC.get();
    }

    public final void setRFC(String value) {
        RFC.set(value);
    }

    public StringProperty RFCProperty() {
        return RFC;
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
    
    public BClientesTabla(String rfc, String nombre){
    this.RFC=new SimpleStringProperty(rfc);
    this.NOMBRE=new SimpleStringProperty(nombre);
    }
    
}
