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

    @Query(value="SELECT m FROM Mercadoria m where m.ativo = true and ( " +
            "m.estoquep > 0 or " +
            "m.estoquem > 0 or " +
            "m.estoqueg > 0 or " +
            "m.estoquegg > 0 or " +
            "m.estoqueexg > 0 " +
            ") ")
    List<Mercadoria> buscarTodosSite();

    @Query(value="SELECT m FROM Mercadoria m order by m.nome")
    List<Mercadoria> buscarTodos();
}
