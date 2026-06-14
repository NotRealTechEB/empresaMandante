package cl.dgac.empresaMandante.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import cl.dgac.empresaMandante.model.EmpresaMandanteModelo;

public interface EmpresaMandanteRepositorio extends JpaRepository<EmpresaMandanteModelo,Long> {
Optional<EmpresaMandanteModelo> findByRut(String rut);
@Query("SELECT i FROM EmpresaMandanteModelo i WHERE i.nombre ILIKE %:nombre%")
List<EmpresaMandanteModelo> findByNombre(@RequestParam("nombre") String nombre); 
}
