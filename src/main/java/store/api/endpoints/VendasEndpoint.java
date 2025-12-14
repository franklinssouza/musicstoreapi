package store.api.endpoints;

import org.springframework.web.bind.annotation.*;
import store.api.service.VendasService;

@RestController
@RequestMapping("/sales")
public class VendasEndpoint {

    private final VendasService service;

    public VendasEndpoint(VendasService service) {
        this.service = service;
    }


}
