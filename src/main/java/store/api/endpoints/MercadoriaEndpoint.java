package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.MercadoriaDto;
import store.api.domain.MercadoriaDto;
import store.api.service.MercadoriaService;
import store.api.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaEndpoint {

    private final MercadoriaService service;

    public MercadoriaEndpoint(MercadoriaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<MercadoriaDto> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/search")
    public List<MercadoriaDto> pesquisar(@RequestParam(name="filtro", required = false) String filtro) {
        return service.pesquisar(filtro);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MercadoriaDto criar(@RequestBody MercadoriaDto body) throws StoreException {
        return service.create(body);
    }

    @GetMapping
    public List<MercadoriaDto> listar() { return service.findAll(); }

    @GetMapping("/{id}")
    public MercadoriaDto buscar(@PathVariable Integer id) { return service.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) { service.delete(id); }
}
