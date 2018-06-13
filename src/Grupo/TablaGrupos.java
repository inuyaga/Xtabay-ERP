package Grupo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sistemas2
 */
public class TablaGrupos {
    private final StringProperty IDG;
    private final StringProperty NOMBREGRUPO;
    private final StringProperty FECHA;
    private final StringProperty LOCREO;

    public final String getIDG() {
        return IDG.get();
    }

    public final void setIDG(String value) {
        IDG.set(value);
    }

    public StringProperty IDGProperty() {
        return IDG;
    }
    
    public final String getNOMBREGRUPO() {
        return NOMBREGRUPO.get();
    }

    public final void setNOMBREGRUPO(String value) {
        NOMBREGRUPO.set(value);
    }

    public StringProperty NOMBREGRUPOProperty() {
        return NOMBREGRUPO;
    }

    public final String getFECHA() {
        return FECHA.get();
    }

    public final void setFECHA(String value) {
        FECHA.set(value);
    }

    public StringProperty FECHAProperty() {
        return FECHA;
    }

    public final String getLOCREO() {
        return LOCREO.get();
    }

    public final void setLOCREO(String value) {
        LOCREO.set(value);
    }

    public StringProperty LOCREOProperty() {
        return LOCREO;
    }
    
    public TablaGrupos(String IDg,String NombreGrupo, String Fecha, String Locreo)
    {
        this.IDG=new SimpleStringProperty(IDg);
        this.NOMBREGRUPO=new SimpleStringProperty(NombreGrupo);
        this.FECHA=new SimpleStringProperty(Fecha);
        this.LOCREO=new SimpleStringProperty(Locreo);
    }
}
