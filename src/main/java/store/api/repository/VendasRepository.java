package store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.api.domain.Venda;
import store.api.domain.Vendas1;

import java.util.Date;
import java.util.List;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Long> {
    @Query(value="SELECT m FROM Venda m ")
    List<Vendas1> buscarTodos();

    Vendas1 findByHash(String hashAssas);

    @Query(value = "select count(o.id) from Venda o where o.hash = :hashAssas")
    Long existePorHash(String hashAssas);

    @Query(value="select o from Venda o where o.dataPagamento between :inicio and :fim")
    List<Vendas1> pesquisarVendas(Date inicio, Date fim);
}
