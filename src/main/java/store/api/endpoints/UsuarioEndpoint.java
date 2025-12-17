package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.ListagemVendasDto;
import store.api.domain.MercadoriaDto;
import store.api.domain.PesquisaVendasDto;
import store.api.domain.UsuarioDto;
import store.api.service.MercadoriaService;
import store.api.service.UsuarioService;
import store.api.service.VendasService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsuarioEndpoint {

    private final UsuarioService service;
    private final VendasService vendasService;


    public UsuarioEndpoint(UsuarioService service, VendasService vendasService) {
        this.service = service;
        this.vendasService = vendasService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto login(@RequestBody UsuarioDto body) throws StoreException {
        return service.login(body);
    }

    @GetMapping("sales/{idUsuario}")
    public List<ListagemVendasDto> pesquisarVendas(@PathVariable Long idUsuario) {
        return vendasService.buscarVendasUsuario(idUsuario);
    }
}