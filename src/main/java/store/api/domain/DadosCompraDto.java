package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DadosCompraDto {
    private Long id;
    private String pedido;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Double valorFrente;

    public DadosCompra toEntity(){
        return DadosCompra.builder()
                .pedido(this.pedido)
                .id(this.id)
                .endereco(this.endereco)
                .numero(this.numero)
                .bairro(this.bairro)
                .cidade(this.cidade)
                .estado(this.estado)
                .cep(this.cep)
                .build();
    }

}
