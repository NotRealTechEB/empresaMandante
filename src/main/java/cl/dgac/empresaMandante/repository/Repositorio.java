package cl.dgac.empresaMandante.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import cl.dgac.empresaMandante.model.Modelo;

@Repository
public interface Repositorio extends JpaRepository<Modelo,Long> {
Optional<Modelo> findByRut(String rut);
@Query("SELECT i FROM Modelo i WHERE i.nombre ILIKE %:nombre%")
List<Modelo> findByNombre(@RequestParam("nombre") String nombre); 
}
