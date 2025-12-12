package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.ListaCarrinhoDto;
import store.api.integracao.assas.QrCodePixResponse;
import store.api.service.PagamentoService;

@RestController
@RequestMapping("/payment")
public class PagamentoEndpoint {

    private final PagamentoService service;
    public PagamentoEndpoint(PagamentoService service) {
        this.service = service;
    }

    @PostMapping("/pix")
    @ResponseStatus(HttpStatus.CREATED)
    public QrCodePixResponse prepararPagamentoPix(@RequestBody ListaCarrinhoDto body) throws StoreException {
        return service.prepararPagamentoPix(body);
    }

    @PostMapping("/credit")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean prepararPagamentoCartao(@RequestBody ListaCarrinhoDto body) throws StoreException {
        service.prepararPagamentoCartao(body);
        return true;
    }
}