package store.api.service;

import org.springframework.stereotype.Service;
import store.api.repository.DadosCompraRepository;

@Service
public class DadosCompraService {
    private final DadosCompraRepository dadosCompraRepository;

    public DadosCompraService(DadosCompraRepository dadosCompraRepository) {
        this.dadosCompraRepository = dadosCompraRepository;
    }
}