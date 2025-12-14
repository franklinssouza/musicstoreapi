package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendasDto1 {
    private Long id;
    private MercadoriaDto mercadoria;
    private Integer quantidade;
    private Date data;
    private Double total;
    private Integer tipoPagamento;
}
