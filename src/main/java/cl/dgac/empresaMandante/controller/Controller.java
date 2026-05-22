package cl.dgac.empresaMandante.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cl.dgac.empresaMandante.dto.Dto;
import cl.dgac.empresaMandante.mapper.mapper;
import cl.dgac.empresaMandante.model.Modelo;
import cl.dgac.empresaMandante.service.Servicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/Emandante")
public class Controller {
    private final Servicio servicio ;
    public Controller(Servicio servicio) {
        this.servicio = servicio;
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Dto>>  getMethodName() {
        List<Modelo> lista =servicio.listarEmpresas();
        return  new ResponseEntity<List<Dto>>(mapper.parseoListas(lista), HttpStatus.OK);
    }
    @GetMapping("/buscaRut")
    public ResponseEntity<Dto> buscarRut(@RequestParam (name="rut") String rut) {
        Modelo modelo = servicio.filtarut(rut); 
        return new ResponseEntity<Dto>(mapper.modelTodDto(modelo), HttpStatus.OK);
    }
    @GetMapping("/buscaNombre")
    public ResponseEntity<List<Dto>> buscarNombre(@RequestParam(name="nombre") String nombre) {
        List<Modelo> lista = servicio.filtarNombre(nombre); 
        return new ResponseEntity<List<Dto>>(mapper.parseoListas(lista), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Dto> agragarEmpresa(@Valid@RequestBody Dto entity) {
        servicio.add(mapper.addModel(entity));
        Modelo modelo = servicio.filtarut(entity.rut());
        return new ResponseEntity<Dto>(mapper.modelTodDto(modelo),HttpStatus.OK);
    }
    @PutMapping("/editar")
    public ResponseEntity<Dto> putMethodName(@RequestParam(name="rut") String rut, @Valid @RequestBody Dto entity) {
        Modelo modelo =servicio.filtarut(rut);
        return new ResponseEntity<Dto>(mapper.modelTodDto(servicio.add(mapper.updateModel(modelo.getId(), entity))), HttpStatus.OK
    );}
}
