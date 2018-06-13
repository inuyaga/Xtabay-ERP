/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author esteban
 */
public class ConteoProductoSeleccion {
    
    private final StringProperty ID;
    private final IntegerProperty cantidad;

    public final void setID(String value) {
        ID.set(value);
    }

    public final String getID() {
        return ID.get();
    }

    public final StringProperty IDProperty() {
        return ID;
    }

    public final void setCantidad(Integer value) {
        cantidad.set(value);
    }

    public final Integer getCantidad() {
        return cantidad.get();
    }

    public final IntegerProperty cantidadProperty() {
        return cantidad;
    }

   
  
    public ConteoProductoSeleccion(String id, int cantidad){
    this.ID=new SimpleStringProperty(id);
    this.cantidad= new SimpleIntegerProperty(cantidad);
    }
    
}
