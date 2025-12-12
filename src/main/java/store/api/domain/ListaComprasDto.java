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
public class ListaComprasDto {

    private Long idCLiente;
    private List<ComprasRequestDto> compras = new ArrayList<>();
    private Boolean cartaoCredito=true;
}
