package ClasesObjetos.buscador;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class TablaPagosConsulta {
    private final IntegerProperty PAGO;
    private final IntegerProperty COMPRA;
    private final StringProperty PROVEEDOR;
    private final DoubleProperty ABONO;
    private final StringProperty METODOPAGO;
    private final StringProperty REFERENCIA;
    private final StringProperty OBSERVACION;

    public final int getPAGO() {
        return PAGO.get();
    }

    public final void setPAGO(int value) {
        PAGO.set(value);
    }

    public IntegerProperty PAGOProperty() {
        return PAGO;
    }

    public final int getCOMPRA() {
        return COMPRA.get();
    }

    public final void setCOMPRA(int value) {
        COMPRA.set(value);
    }

    public IntegerProperty COMPRAProperty() {
        return COMPRA;
    }

    public final String getPROVEEDOR() {
        return PROVEEDOR.get();
    }

    public final void setPROVEEDOR(String value) {
        PROVEEDOR.set(value);
    }

    public StringProperty PROVEEDORProperty() {
        return PROVEEDOR;
    }

    public final double getABONO() {
        return ABONO.get();
    }

    public final void setABONO(double value) {
        ABONO.set(value);
    }

    public DoubleProperty ABONOProperty() {
        return ABONO;
    }

    public final String getMETODOPAGO() {
        return METODOPAGO.get();
    }

    public final void setMETODOPAGO(String value) {
        METODOPAGO.set(value);
    }

    public StringProperty METODOPAGOProperty() {
        return METODOPAGO;
    }

    public final String getREFERENCIA() {
        return REFERENCIA.get();
    }

    public final void setREFERENCIA(String value) {
        REFERENCIA.set(value);
    }

    public StringProperty REFERENCIAProperty() {
        return REFERENCIA;
    }

    public final String getOBSERVACION() {
        return OBSERVACION.get();
    }

    public final void setOBSERVACION(String value) {
        OBSERVACION.set(value);
    }

    public StringProperty OBSERVACIONProperty() {
        return OBSERVACION;
    }
    
    public TablaPagosConsulta(int pago,int compra,String proveedor, double abono,String Mpago, String Referencia,String observacion)
    {
        this.PAGO=new SimpleIntegerProperty(pago);
        this.COMPRA=new SimpleIntegerProperty(compra);
        this.PROVEEDOR=new SimpleStringProperty(proveedor);
        this.ABONO=new SimpleDoubleProperty(abono);
        this.METODOPAGO=new SimpleStringProperty(Mpago);
        this.REFERENCIA=new SimpleStringProperty(Referencia);
        this.OBSERVACION=new SimpleStringProperty(observacion);
    }
    
}
