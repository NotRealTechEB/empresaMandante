package cl.dgac.empresaMandante.exepcion;

public class ErrorEnRecursos extends RuntimeException {
    public  ErrorEnRecursos  (String mensaje){
        super(mensaje);
    }

}
