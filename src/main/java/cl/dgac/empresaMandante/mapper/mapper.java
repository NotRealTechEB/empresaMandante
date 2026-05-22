package cl.dgac.empresaMandante.mapper;

import java.util.ArrayList;
import java.util.List;

import cl.dgac.empresaMandante.dto.Dto;
import cl.dgac.empresaMandante.model.Modelo;

public class mapper {
        public static Modelo addModel(Dto entity){
        Modelo empresa= new Modelo();
        empresa.setEmail(entity.email());
        empresa.setNombre(entity.nombre());
        empresa.setRut(entity.rut());
        empresa.setId(null);
        return empresa;
    }
    public static Modelo updateModel(Long id ,Dto entity){
        Modelo empresa= new Modelo();
        empresa.setEmail(entity.email());
        empresa.setNombre(entity.nombre());
        empresa.setRut(entity.rut());
        empresa.setId(id);
        return empresa;
    }
    public static Dto modelTodDto(Modelo entiity){
        Dto dto = new Dto(entiity.getNombre(),
            entiity.getRut(), entiity.getEmail(), entiity.getId());
            return dto;
    }
    public static List<Dto> parseoListas(List<Modelo> lista ){
        List<Dto>dtos= new ArrayList<>();
        for (Modelo modelo : lista) {
            dtos.add(modelTodDto(modelo));
        }
        return dtos;
    }

}
