package store.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.Usuario;

import java.util.List;

@Repository
public interface UsuarioRepository  extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Usuario findByEmailAndSenha(String email, String senha);

    @Query(value = "select o from Usuario o order by o.nome")
    List<Usuario> buscarTodos();
}
