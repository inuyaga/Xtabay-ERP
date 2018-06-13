package ClasesObjetos.buscador;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class TablaGrupos
{
    private final IntegerProperty ID;
    private final StringProperty NAME;
    private StringProperty FECHA_ADICION;

    public final int getID() {
        return ID.get();
    }

    public final void setID(int value) {
        ID.set(value);
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public final String getNAME() {
        return NAME.get();
    }

    public final void setNAME(String value) {
        NAME.set(value);
    }

    public StringProperty NAMEProperty() {
        return NAME;
    }
    
    public TablaGrupos(int id,String Descripcion)
    {
        this.ID=new SimpleIntegerProperty(id);
        this.NAME=new SimpleStringProperty(Descripcion);
    }
    
    public TablaGrupos(int id,String Descripcion, String fecha_adicion)
    {
        this.ID=new SimpleIntegerProperty(id);
        this.NAME=new SimpleStringProperty(Descripcion);
        this.FECHA_ADICION=new SimpleStringProperty(fecha_adicion);
    }
}
