package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.ListaComprasDto;
import store.api.domain.ListaComprasRealizadasDto;
import store.api.domain.UsuarioDto;
import store.api.service.PagamentoService;
import store.api.service.UsuarioService;

@RestController
@RequestMapping("/payment")
public class PagamentoEndpoint {

    private final PagamentoService service;
    public PagamentoEndpoint(PagamentoService service) {
        this.service = service;
    }

    @PostMapping("/pix")
    @ResponseStatus(HttpStatus.CREATED)
    public ListaComprasRealizadasDto realizarPagamento(@RequestBody ListaComprasDto body) throws StoreException {
        return service.realizarPagamento(body);
    }
}