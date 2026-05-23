package cl.dgac.empresaMandante.exepcion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import cl.dgac.empresaMandante.dto.DtoError;
import jakarta.servlet.http.HttpServletRequest;

public class ExepcionesGlobales {
@ExceptionHandler(ErrorEnRecursos.class)
    public ResponseEntity<DtoError> ErrorEnRecursos(ErrorEnRecursos ex, HttpServletRequest request){
        DtoError error = new DtoError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "recurso no encontrado",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validacionesDto(MethodArgumentNotValidException ex){
        Map<String, String> errores =new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String campo =((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo,mensaje);
        });
        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);

    }
    public ResponseEntity<DtoError> internalError(Exception ex, HttpServletRequest request) {
        DtoError error = new DtoError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Error Interno del Servidor",
                "Ocurrió un error inesperado: " + ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);}
    @ExceptionHandler(DataIntegrityViolationException.class)
    
public ResponseEntity<DtoError> manejarDuplicados(DataIntegrityViolationException ex, HttpServletRequest request) {
    DtoError error = new DtoError(
        LocalDateTime.now(),
        HttpStatus.CONFLICT.value(), // Código 409 Conflict es el ideal aquí
        "Error de duplicidad",
        "Los datos ingresados ya se encuentran registrados (RUT o Nombre duplicado).",
        request.getRequestURI()
    );
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
}
}


