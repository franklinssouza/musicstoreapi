package store.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.Usuario;

@Repository
public interface UsuarioRepository  extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
