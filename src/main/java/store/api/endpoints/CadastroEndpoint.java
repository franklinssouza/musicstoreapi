package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.Usuario;
import store.api.domain.UsuarioDto;
import store.api.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/cadastro")
public class CadastroEndpoint {

    private final UsuarioService service;

    public CadastroEndpoint(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("reset-password")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean reenviarSenha(@RequestBody UsuarioDto body) throws StoreException {
        return this.service.reenviarSenha(body);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto criar(@RequestBody UsuarioDto body) throws StoreException {
        return service.create(body);
    }

    @GetMapping("/{id}")
    public UsuarioDto buscar(@PathVariable Integer id) { return service.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) { service.delete(id); }
}
