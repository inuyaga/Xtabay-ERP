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
public class Sucursal {
    
    private final String nombre;
    private final String id;
    
    public Sucursal(String id, String nom){
    this.id=id;
    this.nombre=nom;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
   
    
}
