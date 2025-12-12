package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemCarrinhoRequestDto {

    private Integer id;
    private String nome;
    private String descricao;
    private String posicao;
    private Double valor;
    private String categoria;
    private String tamanho;
    private Integer quantidade;
    private Double precoTotal;
}
