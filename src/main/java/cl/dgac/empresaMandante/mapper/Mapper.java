package cl.dgac.empresaMandante.mapper;

import java.util.ArrayList;
import java.util.List;

import cl.dgac.empresaMandante.dto.Dto;
import cl.dgac.empresaMandante.model.EmpresaMandanteModelo;

public class Mapper {
        public static EmpresaMandanteModelo addModel(Dto entity){
        EmpresaMandanteModelo empresa= new EmpresaMandanteModelo();
        empresa.setEmail(entity.email());
        empresa.setNombre(entity.nombre());
        empresa.setRut(entity.rut());
        empresa.setId(null);
        return empresa;
    }
    public static EmpresaMandanteModelo updateModel(Long id ,Dto entity){
        EmpresaMandanteModelo empresa= new EmpresaMandanteModelo();
        empresa.setEmail(entity.email());
        empresa.setNombre(entity.nombre());
        empresa.setRut(entity.rut());
        empresa.setId(id);
        return empresa;
    }
    public static Dto modelTodDto(EmpresaMandanteModelo entiity){
        Dto dto = new Dto(entiity.getNombre(),
            entiity.getRut(), entiity.getEmail(), entiity.getId());
            return dto;
    }
    public static List<Dto> parseoListas(List<EmpresaMandanteModelo> lista ){
        List<Dto>dtos= new ArrayList<>();
        for (EmpresaMandanteModelo modelo : lista) {
            dtos.add(modelTodDto(modelo));
        }
        return dtos;
    }

}
