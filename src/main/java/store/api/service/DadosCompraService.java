package store.api.service;

import org.springframework.stereotype.Service;
import store.api.repository.DadosCompraRepository;
import store.api.repository.MercadoriaRepository;

@Service
public class DadosCompraService {
    private final DadosCompraRepository dadosCompraRepository;

    public DadosCompraService(DadosCompraRepository dadosCompraRepository) {
        this.dadosCompraRepository = dadosCompraRepository;
    }
}