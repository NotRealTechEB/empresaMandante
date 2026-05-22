package cl.dgac.empresaMandante.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.dgac.empresaMandante.exepcion.ErrorEnRecursos;
import cl.dgac.empresaMandante.model.Modelo;
import cl.dgac.empresaMandante.repository.Repositorio;

@Service
public class Servicio {
    @Autowired
    private Repositorio repo;

    public List<Modelo> listarEmpresas(){
        List<Modelo> lista = repo.findAll();
        return lista;
    }
    public List<Modelo> filtarNombre(String name ){
        List<Modelo> lista =repo.findByNombre(name);
        if (lista.isEmpty()) {
        throw new ErrorEnRecursos("No existe empresa con el nombre " + name);
    }
        return lista; 
    }
    public Modelo filtarut(String rut ){
        Modelo model = repo.findByRut(rut).orElseThrow(
            ()->new ErrorEnRecursos("Empresa con RUT " + rut + " no encontrada"));
        return model;
    } 
    
    public Modelo add(Modelo model){
        repo.save(model);
        return (model);
    }
    public void delete (Long id){
        repo.deleteById(id);
    }
    public Modelo validarId(Long id){
        Modelo model =repo.findById(id).orElseThrow( ()->new ErrorEnRecursos("Empresa con el id " + id + " no encontrada"));
        return model ;
    }
}
