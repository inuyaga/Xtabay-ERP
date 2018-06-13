/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesObjetos.buscador;

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
public class TablaBuscador {

    private final IntegerProperty ID;
    private final StringProperty DESCRIPCION;
    private final StringProperty COD_UNIDAD;
    private final StringProperty COD_UNIDAD_CODIGO;
    private final StringProperty COD_INTERNO;
    private final IntegerProperty IVA;
    private final IntegerProperty IEPS;
    private final IntegerProperty ISR;
    private final StringProperty COD_BARRAS;
    private final DoubleProperty PRECIO;
    private DoubleProperty catidad;

    public final int getID() {
        return ID.get();
    }

    public final void setID(int value) {
        ID.set(value);
    }

    public IntegerProperty IDProperty() {
        return ID;
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

    public final String getCOD_UNIDAD() {
        return COD_UNIDAD.get();
    }

    public final void setCOD_UNIDAD(String value) {
        COD_UNIDAD.set(value);
    }

    public StringProperty COD_UNIDADProperty() {
        return COD_UNIDAD;
    }

    public final String getCOD_UNIDAD_CODIGO() {
        return COD_UNIDAD_CODIGO.get();
    }

    public final void setCOD_UNIDAD_CODIGO(String value) {
        COD_UNIDAD_CODIGO.set(value);
    }

    public StringProperty COD_UNIDAD_CODIGOProperty() {
        return COD_UNIDAD_CODIGO;
    }

    public final String getCOD_INTERNO() {
        return COD_INTERNO.get();
    }

    public final void setCOD_INTERNO(String value) {
        COD_INTERNO.set(value);
    }

    public StringProperty COD_INTERNOProperty() {
        return COD_INTERNO;
    }

    public final int getIVA() {
        return IVA.get();
    }

    public final void setIVA(int value) {
        IVA.set(value);
    }

    public IntegerProperty IVAProperty() {
        return IVA;
    }

    public final int getIEPS() {
        return IEPS.get();
    }

    public final void setIEPS(int value) {
        IEPS.set(value);
    }

    public IntegerProperty IEPSProperty() {
        return IEPS;
    }

    public final int getISR() {
        return ISR.get();
    }

    public final void setISR(int value) {
        ISR.set(value);
    }

    public IntegerProperty ISRProperty() {
        return ISR;
    }

    public final String getCOD_BARRAS() {
        return COD_BARRAS.get();
    }

    public final void setCOD_BARRAS(String value) {
        COD_BARRAS.set(value);
    }

    public StringProperty COD_BARRASProperty() {
        return COD_BARRAS;
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

    public final double getCatidad() {
        return catidad.get();
    }

    public final void setCatidad(double value) {
        catidad.set(value);
    }

    public DoubleProperty catidadProperty() {
        return catidad;
    }

    public TablaBuscador(int id, String descripcion, String unidad, String unidad_COD, String interno, int iva, int ieps, int isr, String barras, double precio) {
        this.ID = new SimpleIntegerProperty(id);
        this.DESCRIPCION = new SimpleStringProperty(descripcion);
        this.COD_UNIDAD = new SimpleStringProperty(unidad);
        this.COD_UNIDAD_CODIGO = new SimpleStringProperty(unidad_COD);
        this.COD_INTERNO = new SimpleStringProperty(interno);
        this.IVA = new SimpleIntegerProperty(iva);
        this.IEPS = new SimpleIntegerProperty(ieps);
        this.ISR = new SimpleIntegerProperty(isr);
        this.COD_BARRAS = new SimpleStringProperty(barras);
        this.PRECIO = new SimpleDoubleProperty(precio);
    }

    public TablaBuscador(int id, String descripcion, String unidad, String unidad_COD, String interno, int iva, int ieps, int isr, String barras, double precio, double cantidad) {
        this.ID = new SimpleIntegerProperty(id);
        this.DESCRIPCION = new SimpleStringProperty(descripcion);
        this.COD_UNIDAD = new SimpleStringProperty(unidad);
        this.COD_UNIDAD_CODIGO = new SimpleStringProperty(unidad_COD);
        this.COD_INTERNO = new SimpleStringProperty(interno);
        this.IVA = new SimpleIntegerProperty(iva);
        this.IEPS = new SimpleIntegerProperty(ieps);
        this.ISR = new SimpleIntegerProperty(isr);
        this.COD_BARRAS = new SimpleStringProperty(barras);
        this.PRECIO = new SimpleDoubleProperty(precio);
        this.catidad = new SimpleDoubleProperty(cantidad);
    }

}
