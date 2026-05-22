package cl.dgac.empresaMandante.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.dgac.empresaMandante.model.Modelo;

@Repository
public interface Repositorio extends JpaRepository<Modelo,Long> {
List<Modelo> findByNombre (String nombre);
Optional<Modelo> findByRut(String rut);
}
