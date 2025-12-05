package store.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.Mercadoria;

import java.util.List;

@Repository
public interface MercadoriaRepository extends CrudRepository<Mercadoria, Long> {
    Mercadoria findByNome(String email);

    @Query(value="SELECT m FROM Mercadoria m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(m.descricao) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Mercadoria> pesquisar(String filtro);
}
