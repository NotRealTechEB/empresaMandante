package cl.dgac.empresaMandante.dto;

import java.time.LocalDateTime;

public record DtoError(
    LocalDateTime fecha,
    int codigoHttp,
    String error,
    String mensaje,
    String ruta
) {

}
