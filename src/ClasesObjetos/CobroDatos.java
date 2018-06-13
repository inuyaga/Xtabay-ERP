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
 * @author soporte
 */
public class CobroDatos {
    
    private final StringProperty TIPO;
    private final StringProperty METODO_PAGO;
    private final StringProperty PAGO_CLIENTE;
    private final StringProperty CUENTA;

    public final String getTIPO() {
        return TIPO.get();
    }

    public final void setTIPO(String value) {
        TIPO.set(value);
    }

    public StringProperty TIPOProperty() {
        return TIPO;
    }

    public final String getMETODO_PAGO() {
        return METODO_PAGO.get();
    }

    public final void setMETODO_PAGO(String value) {
        METODO_PAGO.set(value);
    }

    public StringProperty METODO_PAGOProperty() {
        return METODO_PAGO;
    }

    public final String getPAGO_CLIENTE() {
        return PAGO_CLIENTE.get();
    }

    public final void setPAGO_CLIENTE(String value) {
        PAGO_CLIENTE.set(value);
    }

    public StringProperty PAGO_CLIENTEProperty() {
        return PAGO_CLIENTE;
    }

    public final String getCUENTA() {
        return CUENTA.get();
    }

    public final void setCUENTA(String value) {
        CUENTA.set(value);
    }

    public StringProperty CUENTAProperty() {
        return CUENTA;
    }
    
    public CobroDatos(String tipo,String metodo_pago, String pago_cliente,String cuenta){
        
        this.TIPO=new SimpleStringProperty(tipo);
        this.METODO_PAGO=new SimpleStringProperty(metodo_pago);
        this.PAGO_CLIENTE=new SimpleStringProperty(pago_cliente);
        this.CUENTA=new SimpleStringProperty(cuenta);
    
    }
    
}
