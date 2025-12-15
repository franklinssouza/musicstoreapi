package store.api.integracao.melhorenvio;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FreteEntregaDto {

    private String nome;
    private int dias;
    private String valor;
}

