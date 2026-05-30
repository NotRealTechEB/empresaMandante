package cl.dgac.empresaMandante.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.dgac.empresaMandante.dto.Dto;
import cl.dgac.empresaMandante.exepcion.ErrorEnRecursos;
import cl.dgac.empresaMandante.mapper.Mapper;
import cl.dgac.empresaMandante.model.Modelo;
import cl.dgac.empresaMandante.repository.Repositorio;

@Service
public class Servicio {
    @Autowired
    private Repositorio repo;

    public List<Dto> listarEmpresas(){
        List<Modelo> lista = repo.findAll();
        return Mapper.parseoListas(lista);
    }
    public List<Dto> filtarNombre(String name ){
        List<Dto> lista =Mapper.parseoListas(repo.findByNombre(name));
        if (lista.isEmpty()) {
        throw new ErrorEnRecursos("No existe empresa con el nombre " + name);
    }
        return lista; 
    }
    public Dto filtarut(String rut ){
        Modelo model = repo.findByRut(rut).orElseThrow(
            ()->new ErrorEnRecursos("Empresa con RUT " + rut + " no encontrada"));
        return Mapper.modelTodDto(model);
    } 
    
    public Dto add(Dto model){
        repo.save(Mapper.addModel(model));
        return (model);
    }
    public String delete (String rut){
        Dto dto = filtarut(rut);
        repo.deleteById(dto.id());
        return "la empresa " +dto.nombre()+" fue eliminada exitosamente";
    }
    public Modelo validarId(Long id){
        Modelo model =repo.findById(id).orElseThrow( 
            ()->new ErrorEnRecursos("Empresa con el id " + id + " no encontrada"));
        return model ;
    }
}
