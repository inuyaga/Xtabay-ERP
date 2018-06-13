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
public class TipoCompra {
    
    private final String ID;
    private final String TIPO;

    public String getID() {
        return ID;
    }

    public String getTIPO() {
        return TIPO;
    }
    
    @Override
    public String toString(){
    return this.TIPO;
    }
    
    public TipoCompra(String id, String tipo){
    this.ID=id;
    this.TIPO=tipo;
    }
}
