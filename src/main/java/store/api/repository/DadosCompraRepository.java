package store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.DadosCompra;

@Repository
public interface DadosCompraRepository extends JpaRepository<DadosCompra, Long> {

}
