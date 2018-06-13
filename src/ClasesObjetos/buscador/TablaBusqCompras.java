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
public class TablaBusqCompras
{
    private final IntegerProperty IDCOMPRA;
    private final StringProperty PROVEEDOR;
    private final DoubleProperty MONTO;
    private final StringProperty FACTURA;
    private final IntegerProperty ID_PROVEEDOR;
    private StringProperty OBSERVACION;

    public final String getOBSERVACION() {
        return OBSERVACION.get();
    }

    public final void setOBSERVACION(String value) {
        OBSERVACION.set(value);
    }

    public StringProperty OBSERVACIONProperty() {
        return OBSERVACION;
    }
    public final int getIDCOMPRA() {
        return IDCOMPRA.get();
    }

    public final void setIDCOMPRA(int value) {
        IDCOMPRA.set(value);
    }

    public IntegerProperty IDCOMPRAProperty() {
        return IDCOMPRA;
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

    public final double getMONTO() {
        return MONTO.get();
    }

    public final void setMONTO(double value) {
        MONTO.set(value);
    }

    public DoubleProperty MONTOProperty() {
        return MONTO;
    }

    public final String getFACTURA() {
        return FACTURA.get();
    }

    public final void setFACTURA(String value) {
        FACTURA.set(value);
    }

    public StringProperty FACTURAProperty() {
        return FACTURA;
    }

    public final int getID_PROVEEDOR() {
        return ID_PROVEEDOR.get();
    }

    public final void setID_PROVEEDOR(int value) {
        ID_PROVEEDOR.set(value);
    }

    public IntegerProperty ID_PROVEEDORProperty() {
        return ID_PROVEEDOR;
    }

   
    
    public TablaBusqCompras(int compra,String proveedor,double monto, String fac, int ID_provedor)
    {
        this.IDCOMPRA=new SimpleIntegerProperty(compra);
        this.PROVEEDOR=new SimpleStringProperty(proveedor);
        this.MONTO= new SimpleDoubleProperty(monto);
        this.FACTURA= new SimpleStringProperty(fac);
        this.ID_PROVEEDOR=new SimpleIntegerProperty(ID_provedor);
    }
    
    public TablaBusqCompras(int compra,String proveedor,double monto, String fac, int ID_provedor, String observacion)
    {
        this.IDCOMPRA=new SimpleIntegerProperty(compra);
        this.PROVEEDOR=new SimpleStringProperty(proveedor);
        this.MONTO= new SimpleDoubleProperty(monto);
        this.FACTURA= new SimpleStringProperty(fac);
        this.ID_PROVEEDOR=new SimpleIntegerProperty(ID_provedor);
        this.OBSERVACION=new SimpleStringProperty(observacion);
    }
}
