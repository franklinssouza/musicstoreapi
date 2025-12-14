package store.api.endpoints;

import org.springframework.web.bind.annotation.*;
import store.api.domain.ListagemVendasDto;
import store.api.domain.PanoramaVendasDto;
import store.api.domain.PesquisaVendasDto;
import store.api.domain.UsuarioDto;
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



}
