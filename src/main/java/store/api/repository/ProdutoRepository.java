package store.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    Produto findByNome(String email);

    @Query(value="SELECT m FROM Produto m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(m.descricao) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Produto> pesquisar(String filtro);

    @Query(value="SELECT m FROM Produto m where m.ativo = true and ( " +
            "m.estoquep > 0 or " +
            "m.estoquem > 0 or " +
            "m.estoqueg > 0 or " +
            "m.estoquegg > 0 or " +
            "m.estoqueexg > 0 " +
            ") ")
    List<Produto> buscarTodosSite();

    @Query(value="SELECT m FROM Produto m order by m.nome")
    List<Produto> buscarTodos();
}
