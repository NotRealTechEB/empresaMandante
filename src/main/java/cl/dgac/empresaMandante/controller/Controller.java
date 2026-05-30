package cl.dgac.empresaMandante.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cl.dgac.empresaMandante.dto.Dto;
import cl.dgac.empresaMandante.mapper.Mapper;
import cl.dgac.empresaMandante.service.Servicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.5/Emandante")
public class Controller {
    private final Servicio servicio ;
    public Controller(Servicio servicio) {
        this.servicio = servicio;
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Dto>>  getMethodName() {
        List<Dto> lista =servicio.listarEmpresas();
        return  new ResponseEntity<List<Dto>>(lista, HttpStatus.OK);
    }
    @GetMapping("/buscaRut")
    public ResponseEntity<Dto> buscarRut(@RequestParam (name="rut") String rut) {
        Dto modelo = servicio.filtarut(rut); 
        return new ResponseEntity<Dto>(modelo, HttpStatus.OK);
    }
    @GetMapping("/buscaNombre")
    public ResponseEntity<List<Dto>> buscarNombre(@RequestParam(name="nombre") String nombre) {
        List<Dto> lista = servicio.filtarNombre(nombre); 
        return new ResponseEntity<List<Dto>>(lista, HttpStatus.OK);
    }
    @PostMapping("/agreagrEmpresas")
    public ResponseEntity<Dto> agragarEmpresa(@Valid@RequestBody Dto entity) {
        servicio.add(entity);
        Dto modelo = servicio.filtarut(entity.rut());
        return new ResponseEntity<Dto>(modelo,HttpStatus.OK);
    }
    @PutMapping("/editar")
    public ResponseEntity<Dto> editarEmpresa(@RequestParam(name="rut") String rut, @Valid @RequestBody Dto entity) {
        Dto modelo =servicio.filtarut(rut);
        return new ResponseEntity<Dto>(servicio.add(Mapper.modelTodDto(Mapper.updateModel(modelo.id(), entity))), HttpStatus.OK);
    }
    @DeleteMapping("borrarEmprea")
    public ResponseEntity<String> eliminarEmpresa(@RequestParam(name= "rut") String rut){
        return new ResponseEntity<String> (servicio.delete(rut),HttpStatus.OK);


    }
}
