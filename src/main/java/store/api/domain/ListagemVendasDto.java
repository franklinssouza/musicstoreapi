package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListagemVendasDto {
    private Long idVenda;
    private EnderecoDto endereco;
    private List<ItemCarrinhoRequestDto> produtos;
    private UsuarioDto usuario;
    private String data;
    private Double valorTotal;
    private String tipoPagamento;
}
