package store.api.integracao.melhorenvio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaFreteRequest {

    private String id;
    private Integer width;
    private Integer height;
    private Integer length;
    private Integer weight;
    private String cep;
    private Integer quantity;
}

