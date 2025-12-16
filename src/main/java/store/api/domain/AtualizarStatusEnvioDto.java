package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtualizarStatusEnvioDto {
    private Integer status;
    private Long idVenda;
    private String codigoRastreio;
}
