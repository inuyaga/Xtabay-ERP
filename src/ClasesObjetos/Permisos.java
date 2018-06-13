/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos;

/**
 *
 * @author esteban
 */
public class Permisos {
   final private String ID;
   final private String SERIE;
   final private String DESCRIPCION;
   
   public Permisos(String id, String serie, String descripcion){
   this.ID=id;
   this.SERIE=serie;
   this.DESCRIPCION=descripcion;
   }

    public String getID() {
        return ID;
    }

    public String getSERIE() {
        return SERIE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }
    
    @Override
    public String toString(){
    return this.DESCRIPCION;
    }
   
   
}
