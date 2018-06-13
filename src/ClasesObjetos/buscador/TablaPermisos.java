package ClasesObjetos.buscador;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class TablaPermisos
{
    private final IntegerProperty ID;
    private final StringProperty SERIE;
    private final StringProperty PERMISO;

    public final int getID() {
        return ID.get();
    }

    public final void setID(int value) {
        ID.set(value);
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public final String getSERIE() {
        return SERIE.get();
    }

    public final void setSERIE(String value) {
        SERIE.set(value);
    }

    public StringProperty SERIEProperty() {
        return SERIE;
    }

    public final String getPERMISO() {
        return PERMISO.get();
    }

    public final void setPERMISO(String value) {
        PERMISO.set(value);
    }

    public StringProperty PERMISOProperty() {
        return PERMISO;
    }
    
    public TablaPermisos(int Id,String Serie, String Permiso)
    {
        this.ID=new SimpleIntegerProperty(Id);
        this.SERIE=new SimpleStringProperty(Serie);
        this.PERMISO=new SimpleStringProperty(Permiso);
    }
}
