package store.api.endpoints;

import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.*;
import store.api.repository.MercadoriaRepository;
import store.api.service.MercadoriaService;
import store.api.service.UsuarioService;
import store.api.service.VendasService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminEndpoint {

    private final VendasService vendasService;
    private final UsuarioService usuarioService;
    public AdminEndpoint(VendasService service, UsuarioService usuarioService) {
        this.vendasService = service;
        this.usuarioService = usuarioService;
    }

    @GetMapping("panorama")
    public PanoramaVendasDto buscarPanorama() {
        return vendasService.buscarPanorama();
    }

    @PostMapping("sales/search")
    public List<ListagemVendasDto> pesquisarVendas(@RequestBody PesquisaVendasDto body) {
        return vendasService.pesquisarVendas(body);
    }

    @GetMapping("users")
    public List<UsuarioDto> buscarUsuarios() {
        return usuarioService.buscarUsuarios();
    }


    @PostMapping("status-venda")
    public boolean atualizarStatusVenda(@RequestBody AtualizarStatusEnvioDto body) throws StoreException {
        vendasService.atualizarStatusVenda(body);
        return true;
    }
}
