package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.ListaCarrinhoDto;
import store.api.integracao.assas.AsaasPaymentLinkResponse;
import store.api.integracao.melhorenvio.ConsultaFreteRequest;
import store.api.integracao.melhorenvio.FreteEntregaDto;
import store.api.integracao.melhorenvio.MelhorEnvioApi;
import store.api.service.PagamentoService;

@RestController
@RequestMapping("/payment")
public class PagamentoEndpoint {

    private final PagamentoService service;
    private final MelhorEnvioApi melhorEnvioApi;

    public PagamentoEndpoint(PagamentoService service, MelhorEnvioApi melhorEnvioApi) {
        this.service = service;
        this.melhorEnvioApi = melhorEnvioApi;
    }

    @PostMapping("/pix")
    @ResponseStatus(HttpStatus.CREATED)
    public AsaasPaymentLinkResponse prepararPagamentoPix(@RequestBody ListaCarrinhoDto body) throws StoreException {
        return service.prepararPagamento(body);
    }

    @PostMapping("/credit")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean prepararPagamentoCartao(@RequestBody ListaCarrinhoDto body) throws StoreException {
        service.prepararPagamento(body);
        return true;
    }

    @PostMapping("/cep")
    @ResponseStatus(HttpStatus.CREATED)
    public FreteEntregaDto consultarCep(@RequestBody ConsultaFreteRequest body) throws StoreException {
        return this.melhorEnvioApi.calcularFrete(body);
    }
}