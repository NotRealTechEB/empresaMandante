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
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.dgac.empresaMandante.dto.DtoExeption;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExepcionesGlobales {
    private ResponseEntity<DtoExeption> buildResponse(
        HttpStatus status,
        String mnesaje,
        String ruta,
        Map<String, String> detalles){
        DtoExeption dto = new DtoExeption(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            mnesaje,
            detalles,
            ruta
        );
        return  ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(ErrorEnRecursos.class)
    public ResponseEntity<DtoExeption> ErrorEnRecursos(ErrorEnRecursos ex, HttpServletRequest request){
        return buildResponse(HttpStatus.NOT_FOUND, "recurso no encontrado", request.getRequestURI(),
        null);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DtoExeption> validacionesDto(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> errores =new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String campo =((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo,mensaje);
        });
        return buildResponse(HttpStatus.BAD_REQUEST,
            "Errores en el Json",request.getRequestURI(),
            errores);}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DtoExeption> internalError(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error Interno del Servidor", request.getRequestURI(), null);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DtoExeption> manejarDuplicados(DataIntegrityViolationException ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.CONFLICT, "correo o rut estan ya en la BaseDatos",
    request.getRequestURI(), null);
    }
}


