package store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.api.domain.Venda;

import java.util.Date;
import java.util.List;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Long> {
    @Query(value="SELECT m FROM Venda m where m.pago = true")
    List<Venda> buscarTodos();

    Venda findByHash(String hashAssas);

    @Query(value = "select count(o.id) from Venda o where o.hash = :hashAssas")
    Long existePorHash(String hashAssas);

    @Query(value="select o from Venda o where o.dataPagamento between :inicio and :fim and o.pago = true")
    List<Venda> pesquisarVendas(Date inicio, Date fim);

    @Query(value="select o from Venda o where o.usuario.id = :idUsuario and o.pago = true")
    List<Venda> buscarVendasUsuario(Long idUsuario);
}
