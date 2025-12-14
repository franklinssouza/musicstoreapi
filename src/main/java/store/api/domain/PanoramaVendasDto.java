package store.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanoramaVendasDto {

    private long quantidadeVendas;
    private Double valorTotalVendas;
    private double totalVendasDiarias;
    private String produtoMaisVendido;
}
