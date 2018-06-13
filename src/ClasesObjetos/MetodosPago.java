package ClasesObjetos;

public class MetodosPago {
    private final String CODIGO;
    private final String DESCRIPCION;
    
    public MetodosPago(String Co,String Des)
    {
        this.CODIGO=Co;
        this.DESCRIPCION=Des;
    }
            
    public String getCODIGO() {
        return CODIGO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }
    
    @Override
    public String toString()
    {
        return this.DESCRIPCION;
    }
 
}
