/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author esteban
 */
public class TablaUsuarios {
    
    private final StringProperty ID;
    private final StringProperty USUARIO;
    private final StringProperty NOMBRE;
    private final StringProperty VIGENCIA;
    private final StringProperty SUCURSAL;
    private final StringProperty LISTASUC;
    private final StringProperty GRUPO;

    
    
    
   public TablaUsuarios(String id,String user,String nombre,String vigencia,String sucursal, String Lista, String grupo){
       this.ID=new SimpleStringProperty(id);
       this.NOMBRE=new SimpleStringProperty(nombre);
       this.SUCURSAL=new SimpleStringProperty(sucursal);
       this.USUARIO=new SimpleStringProperty(user);
       this.VIGENCIA=new SimpleStringProperty(vigencia);
       if (Lista.equals("1")) {
       Lista="Si";    
       }else{
       Lista="No";
       }
       this.LISTASUC=new SimpleStringProperty(Lista);
       this.GRUPO=new SimpleStringProperty(grupo);
   }
   
   public final String getGRUPO() {
        return GRUPO.get();
    }

    public final void setGRUPO(String value) {
        GRUPO.set(value);
    }

    public StringProperty GRUPOProperty() {
        return GRUPO;
    }

    
   
   
   public final String getLISTASUC() {
        return LISTASUC.get();
    }

    public final void setLISTASUC(String value) {
        LISTASUC.set(value);
    }

    public StringProperty LISTASUCProperty() {
        return LISTASUC;
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

    public final String getVIGENCIA() {
        return VIGENCIA.get();
    }

    public final void setVIGENCIA(String value) {
        VIGENCIA.set(value);
    }

    public StringProperty VIGENCIAProperty() {
        return VIGENCIA;
    }

    public final String getSUCURSAL() {
        return SUCURSAL.get();
    }

    public final void setSUCURSAL(String value) {
        SUCURSAL.set(value);
    }

    public StringProperty SUCURSALProperty() {
        return SUCURSAL;
    }
    
}
