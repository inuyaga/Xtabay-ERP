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


public class Grupos {
    private final String NOMBRE;
    private final String ID;
    
    public Grupos(String nombre, String id){
    this.ID=id;
    this.NOMBRE=nombre;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public String getID() {
        return ID;
    }
    @Override
    public String toString() {
        return this.NOMBRE;
    }
    
}
