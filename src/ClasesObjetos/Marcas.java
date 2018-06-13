package ClasesObjetos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class Marcas {
    private final StringProperty IDMARCA;
    private final StringProperty DESCRIPCIONMARCA;

    public final String getIDMARCA() {
        return IDMARCA.get();
    }

    public final void setIDMARCA(String value) {
        IDMARCA.set(value);
    }

    public StringProperty IDMARCAProperty() {
        return IDMARCA;
    }

    public final String getDESCRIPCIONMARCA() {
        return DESCRIPCIONMARCA.get();
    }

    public final void setDESCRIPCIONMARCA(String value) {
        DESCRIPCIONMARCA.set(value);
    }

    public StringProperty DESCRIPCIONMARCAProperty() {
        return DESCRIPCIONMARCA;
    }
    
    public Marcas(String Marca, String Descripcion)
    {
        this.IDMARCA=new SimpleStringProperty(Marca);
        this.DESCRIPCIONMARCA=new SimpleStringProperty (Descripcion);
    }
    
    @Override
    public String toString() {
        return DESCRIPCIONMARCA.get();
    }
}
