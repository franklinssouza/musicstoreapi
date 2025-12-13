package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.UsuarioDto;
import store.api.domain.VendasDto;
import store.api.service.UsuarioService;
import store.api.service.VendasService;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class VendasEndpoint {

    private final VendasService service;

    public VendasEndpoint(VendasService service) {
        this.service = service;
    }


}
