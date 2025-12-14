package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListaCarrinhoDto {

    private Long idUsuario;
    private List<ItemCarrinhoRequestDto> compras = new ArrayList<>();
    private EnderecoDto endereco;
    private String observacao;
}
