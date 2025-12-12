package store.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.Mercadoria;
import store.api.domain.Vendas;

import java.util.List;

@Repository
public interface VendasRepository extends CrudRepository<Vendas, Long> {
    @Query(value="SELECT m FROM Vendas m ")
    List<Vendas> buscarTodos();

}
