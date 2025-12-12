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
    private Double valor;
    private String tamanho;
    private String nome;
    private Integer quantidade;
}
