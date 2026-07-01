package cl.dgac.empresaMandante.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import cl.dgac.empresaMandante.dto.Dto;
import cl.dgac.empresaMandante.mapper.Mapper;
import cl.dgac.empresaMandante.service.EmpresaMandanteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.5/Emandante")
@Tag(name = "Empresa Mandante",
    description = "esta api se encarga de comunicarse con la BD para hacer el crud basico y flitros de busqueda"
)

public class EmpresaMandanteController {
    private final EmpresaMandanteServicio servicio ;
    public EmpresaMandanteController(EmpresaMandanteServicio servicio) {
        this.servicio = servicio;
    }

    
    @GetMapping("/listar")
    @Operation(
        summary = "obtener todas las empresas Mandantes",
        description = "lista todas las empresas de la BD"
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "404", description = "lista vacia es decir ninguna solicitd existente")})

    public ResponseEntity<List<Dto>>  getMethodName() {
        List<Dto> lista =servicio.listarEmpresas();
        return  new ResponseEntity<List<Dto>>(lista, HttpStatus.OK);
    }
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "404", description = "el rut proporciononado no se encuenta ")})
    @GetMapping("/buscaRut")
    @Operation(
        summary = "empresa por rut",
        description = "trae la empresa con el rut de la empresa"
    )
    public ResponseEntity<Dto> buscarRut(@RequestParam (name="rut") String rut) {
        Dto modelo = servicio.filtarut(rut); 
        return new ResponseEntity<Dto>(modelo, HttpStatus.OK);
    }


    @GetMapping("/buscaNombre")
    @Operation(
        summary = "busca empresas con nombre similar",
        description = "Lista empresa con nombre similar"
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "404", description = "el rut proporciononado no se encuenta ")})
    public ResponseEntity<List<Dto>> buscarNombre(@RequestParam(name="nombre") String nombre) {
        List<Dto> lista = servicio.filtarNombre(nombre); 
        return new ResponseEntity<List<Dto>>(lista, HttpStatus.OK);
    }

    
    @PostMapping("/agreagrEmpresas")
    @Operation(
        summary = "agregar empresa",
        description = "agrega una empresa con el json adecuado"
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "400", description = "error en uno o mas puntos del json ")
    })
    public ResponseEntity<Dto> agragarEmpresa(@Valid @RequestBody(
        description = "Datos de la Empresa a Agregar",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Dto.class),
            examples = @ExampleObject(
    name = "Ejemplo de Empresa",
    summary = "Datos básicos de una empresa registrada",
    value = """
    {
        "id": 1,
        "nombre": "TechSolutions Chile",
        "rut": "76.123.456-K",
        "email": "contacto@techsolutions.cl"
    }
    """
)
        )) @org.springframework.web.bind.annotation.RequestBody  Dto entity) {
        servicio.add(entity);
        Dto modelo = servicio.filtarut(entity.rut());
        return new ResponseEntity<Dto>(modelo,HttpStatus.OK);
    }


    @PutMapping("/editar")
    @Operation(
        summary = "editar empresas",
        description = "edita una empresa que tenga el rut igual"
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "400", description = "error en uno o mas puntos del json ")
    })
    public ResponseEntity<Dto> editarEmpresa(@RequestParam(name="rut") String rut, @Valid 
    @RequestBody(
        description = "Datos de la Empresa a Agregar",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Dto.class),
            examples = @ExampleObject(
    name = "Ejemplo de Empresa",
    summary = "Datos básicos de una empresa registrada",
    value = """
    {
        "id": 1,
        "nombre": "TechSolutions Chile",
        "rut": "76.123.456-K",
        "email": "contacto@techsolutions.cl"
    }
    """
)
        )) @org.springframework.web.bind.annotation.RequestBody 
    Dto entity) {
        Dto modelo =servicio.filtarut(rut);
        return new ResponseEntity<Dto>(servicio.add(Mapper.modelTodDto(Mapper.updateModel(modelo.id(), entity))), HttpStatus.OK);
    }

    @DeleteMapping("/borrarEmprea")
    @Operation(
        summary = "borra empresa",
        description = "borra una empresa que tenga el rut igual"
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Operación exitosa"),
    @ApiResponse(responseCode = "404", description = "id inexistente en la bd.")
    })
    public ResponseEntity<String> eliminarEmpresa(@RequestParam(name= "rut") String rut){
        return new ResponseEntity<String> (servicio.delete(rut),HttpStatus.OK);


    }
}
