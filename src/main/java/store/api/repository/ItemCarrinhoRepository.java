package store.api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.ItemCarrinho;

import java.util.List;

@Repository
public interface ItemCarrinhoRepository extends CrudRepository<ItemCarrinho, Long> {

    @Query(value="SELECT m FROM ItemCarrinho m order by m.data desc")
    List<ItemCarrinho> buscarTodos();

    @Query(value="delete from ItemCarrinho o where o.usuario.id = :idUsuario")
    @Modifying
    void deleteByUsuario(Long idUsuario);
}
