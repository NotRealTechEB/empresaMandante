package cl.dgac.empresaMandante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Dto(
@NotBlank(message = "la empresa debe tener un nombre")
@Size(min = 2, max = 60, message = "el nombre debe tener entre 3 a 60 caracteres")
String nombre,
@NotBlank(message ="la empresa debe tener rut")
@Size(max = 15, message= "como maximo el rut debe tener 15 caracteres")
String rut,
@NotBlank(message = "la empresa debe tener un nombre")
@Size(min = 10, max = 60, message = "el nombre debe tener entre 10 a 80 caracteres")
String email,
Long id
) {

}
