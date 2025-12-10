package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.MercadoriaDto;
import store.api.domain.UsuarioDto;
import store.api.service.MercadoriaService;
import store.api.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsuarioEndpoint {

    private final UsuarioService service;

    public UsuarioEndpoint(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto login(@RequestBody UsuarioDto body) throws StoreException {
        return service.login(body);
    }
}