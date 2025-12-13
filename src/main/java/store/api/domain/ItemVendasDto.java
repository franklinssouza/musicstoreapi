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
public class ItemVendasDto {
    private Long id;
    private String imagem;
    private String tamanho;
    private MercadoriaDto mercadoria;
    private UsuarioDto usuario;
    private Integer quantidade;
    private String data;
    private Double total;
    private String tipoPagamento;
}
