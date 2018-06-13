/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Salidas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author esteban
 */
public class PuntoVenta {

    private final DoubleProperty CANTIDAD;
     private final StringProperty UNIDAD;
    private final StringProperty ESCRIPCION;
    private final StringProperty CODIGO;
    private final DoubleProperty IMPORTE;
    private final IntegerProperty IMPUESTO;
    private final StringProperty SUMA_IMPUESTO;
    private final DoubleProperty DESCUENTO;
    private final DoubleProperty TOTAL;

    public PuntoVenta(double id, String unidad, String descripcion, String codigo, double importe, int impuesto, String suma_impuesto, double descuento, double total) {
        this.CANTIDAD = new SimpleDoubleProperty(id);
        this.UNIDAD = new SimpleStringProperty(unidad);
        this.ESCRIPCION = new SimpleStringProperty(descripcion);
        this.CODIGO = new SimpleStringProperty(codigo);
        this.IMPORTE = new SimpleDoubleProperty(importe);
        this.IMPUESTO = new SimpleIntegerProperty(impuesto);
        this.SUMA_IMPUESTO = new SimpleStringProperty(suma_impuesto);
        this.DESCUENTO = new SimpleDoubleProperty(descuento);
        this.TOTAL = new SimpleDoubleProperty(total);
    }

    public final void setCANTIDAD(Double value) {
        CANTIDAD.set(value);
    }

    public final Double getCANTIDAD() {   
        return CANTIDAD.get();
    }

    public final DoubleProperty CANTIDADProperty() {
        return CANTIDAD;
    }

    public final void setUNIDAD(String value) {
        UNIDAD.set(value);
    }

    public final String getUNIDAD() {
        return UNIDAD.get();
    }

    public final StringProperty UNIDADProperty() {
        return UNIDAD;
    }

    public final void setESCRIPCION(String value) {
        ESCRIPCION.set(value);
    }

    public final String getESCRIPCION() {
        return ESCRIPCION.get();
    }

    public final StringProperty ESCRIPCIONProperty() {
        return ESCRIPCION;
    }

    public final void setCODIGO(String value) {
        CODIGO.set(value);
    }

    public final String getCODIGO() {
        return CODIGO.get();
    }

    public final StringProperty CODIGOProperty() {
        return CODIGO;
    }

    public final void setIMPORTE(Double value) {
        IMPORTE.set(value);
    }

    public final Double getIMPORTE() {
        return IMPORTE.get();
    }

    public final DoubleProperty IMPORTEProperty() {
        return IMPORTE;
    }

    public final void setIMPUESTO(Integer value) {
        IMPUESTO.set(value);
    }

    public final Integer getIMPUESTO() {
        return IMPUESTO.get();
    }

    public final IntegerProperty IMPUESTOProperty() {
        return IMPUESTO;
    }

    public final void setSUMA_IMPUESTO(String value) {
        SUMA_IMPUESTO.set(value);
    }

    public final String getSUMA_IMPUESTO() {
        return SUMA_IMPUESTO.get();
    }

    public final StringProperty SUMA_IMPUESTOProperty() {
        return SUMA_IMPUESTO;
    }

    public final void setDESCUENTO(Double value) {
        DESCUENTO.set(value);
    }

    public final Double getDESCUENTO() {
        return DESCUENTO.get();
    }

    public final DoubleProperty DESCUENTOProperty() {
        return DESCUENTO;
    }

    public final void setTOTAL(Double value) {
        TOTAL.set(value);
    }

    public final Double getTOTAL() {
        return TOTAL.get();
    }

    public final DoubleProperty TOTALProperty() {
        return TOTAL;
    }
   

   


}
