package store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.api.domain.Vendas;

import java.util.Date;
import java.util.List;

@Repository
public interface VendasRepository extends JpaRepository<Vendas, Long> {
    @Query(value="SELECT m FROM Vendas m ")
    List<Vendas> buscarTodos();

    Vendas findByHash(String hashAssas);

    @Query(value = "select count(o.id) from Vendas o where o.hash = :hashAssas")
    Long existePorHash(String hashAssas);

    @Query(value="select o from Vendas o where o.dataPagamento between :inicio and :fim")
    List<Vendas> pesquisarVendas(Date inicio, Date fim);
}
