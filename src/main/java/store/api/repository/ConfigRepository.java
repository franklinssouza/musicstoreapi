package store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.api.domain.Config;
import store.api.domain.Venda;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

}
