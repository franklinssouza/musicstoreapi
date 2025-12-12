package store.api.service;

import org.springframework.stereotype.Service;
import store.api.domain.Mercadoria;
import store.api.domain.Vendas;
import store.api.domain.VendasDto;
import store.api.repository.VendasRepository;

import java.util.List;

@Service
public class VendasService {

    private final VendasRepository vendasRepository;

    public VendasService(VendasRepository vendasRepository) {
        this.vendasRepository = vendasRepository;
    }

    public List<VendasDto> findAll() {
        return vendasRepository.buscarTodos().stream()
                .map(Vendas::toDto).toList();
    }
}
