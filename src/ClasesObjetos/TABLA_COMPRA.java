/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos;

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
public class TABLA_COMPRA {

    private final IntegerProperty ID;
    private final DoubleProperty CANTIDAD;
    private final StringProperty UNIDAD;
    private final StringProperty DESCRIPCION;
    private final DoubleProperty PRECIO;
    private final DoubleProperty IVA;
    private final DoubleProperty IEPS;
    private final DoubleProperty ISR;
    private final DoubleProperty TOTAL;
    private final IntegerProperty IVA_REAL;
    private final IntegerProperty IEPS_REAL;
    private final IntegerProperty ISR_REAL;
    private final StringProperty UNIDAD_REAL;
    
      public TABLA_COMPRA(int id, double cantidad, String unidad, String descrip, double precio, double iva, double ieps, double isr, double total, int iva_real, int ieps_real, int isr_real, String unidad_real) {
        this.ID = new SimpleIntegerProperty(id);
        this.CANTIDAD = new SimpleDoubleProperty(cantidad);
        this.UNIDAD = new SimpleStringProperty(unidad);
        this.DESCRIPCION = new SimpleStringProperty(descrip);
        this.PRECIO = new SimpleDoubleProperty(precio);
        this.IVA = new SimpleDoubleProperty(iva);
        this.IEPS = new SimpleDoubleProperty(ieps);
        this.ISR = new SimpleDoubleProperty(isr);
        this.TOTAL = new SimpleDoubleProperty(total);
        this.IVA_REAL = new SimpleIntegerProperty(iva_real);
        this.IEPS_REAL = new SimpleIntegerProperty(ieps_real);
        this.ISR_REAL = new SimpleIntegerProperty(isr_real);
        this.UNIDAD_REAL = new SimpleStringProperty(unidad_real);

    }

    public final int getID() {
        return ID.get();
    }

    public final void setID(int value) {
        ID.set(value);
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public final double getCANTIDAD() {
        return CANTIDAD.get();
    }

    public final void setCANTIDAD(double value) {
        CANTIDAD.set(value);
    }

    public DoubleProperty CANTIDADProperty() {
        return CANTIDAD;
    }

    public final String getUNIDAD() {
        return UNIDAD.get();
    }

    public final void setUNIDAD(String value) {
        UNIDAD.set(value);
    }

    public StringProperty UNIDADProperty() {
        return UNIDAD;
    }

    public final String getDESCRIPCION() {
        return DESCRIPCION.get();
    }

    public final void setDESCRIPCION(String value) {
        DESCRIPCION.set(value);
    }

    public StringProperty DESCRIPCIONProperty() {
        return DESCRIPCION;
    }

    public final double getPRECIO() {
        return PRECIO.get();
    }

    public final void setPRECIO(double value) {
        PRECIO.set(value);
    }

    public DoubleProperty PRECIOProperty() {
        return PRECIO;
    }

    public final double getIVA() {
        return IVA.get();
    }

    public final void setIVA(double value) {
        IVA.set(value);
    }

    public DoubleProperty IVAProperty() {
        return IVA;
    }

    public final double getIEPS() {
        return IEPS.get();
    }

    public final void setIEPS(double value) {
        IEPS.set(value);
    }

    public DoubleProperty IEPSProperty() {
        return IEPS;
    }

    public final double getISR() {
        return ISR.get();
    }

    public final void setISR(double value) {
        ISR.set(value);
    }

    public DoubleProperty ISRProperty() {
        return ISR;
    }

    public final double getTOTAL() {
        return TOTAL.get();
    }

    public final void setTOTAL(double value) {
        TOTAL.set(value);
    }

    public DoubleProperty TOTALProperty() {
        return TOTAL;
    }

    public final int getIVA_REAL() {
        return IVA_REAL.get();
    }

    public final void setIVA_REAL(int value) {
        IVA_REAL.set(value);
    }

    public IntegerProperty IVA_REALProperty() {
        return IVA_REAL;
    }

    public final int getIEPS_REAL() {
        return IEPS_REAL.get();
    }

    public final void setIEPS_REAL(int value) {
        IEPS_REAL.set(value);
    }

    public IntegerProperty IEPS_REALProperty() {
        return IEPS_REAL;
    }

    public final int getISR_REAL() {
        return ISR_REAL.get();
    }

    public final void setISR_REAL(int value) {
        ISR_REAL.set(value);
    }

    public IntegerProperty ISR_REALProperty() {
        return ISR_REAL;
    }

    public final String getUNIDAD_REAL() {
        return UNIDAD_REAL.get();
    }

    public final void setUNIDAD_REAL(String value) {
        UNIDAD_REAL.set(value);
    }

    public StringProperty UNIDAD_REALProperty() {
        return UNIDAD_REAL;
    }

  

}
