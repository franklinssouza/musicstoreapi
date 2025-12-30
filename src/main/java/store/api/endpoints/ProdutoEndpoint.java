package store.api.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.api.config.exceptions.StoreException;
import store.api.domain.ProdutoDto;
import store.api.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoEndpoint {

    private final ProdutoService service;

    public ProdutoEndpoint(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<ProdutoDto> buscarTodosSie() {
        return service.buscarTodosSite();
    }

    @GetMapping()
    public List<ProdutoDto> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/search")
    public List<ProdutoDto> pesquisar(@RequestParam(name="filtro", required = false) String filtro) {
        return service.pesquisar(filtro);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto criar(@RequestBody ProdutoDto body) throws StoreException {
        return service.create(body);
    }

    @GetMapping("/{id}")
    public ProdutoDto buscar(@PathVariable Integer id) { return service.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) throws StoreException {
        service.delete(id);
    }
}
