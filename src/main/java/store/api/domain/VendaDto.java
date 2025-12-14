package store.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendaDto {
    private Long id;
    private String pedido;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Double valorFrente;
    private String observacao;
    private UsuarioDto usuiario;

    public Venda toEntity(){
        return Venda.builder()
                .pedido(this.pedido)
                .id(this.id)
                .endereco(this.endereco)
                .usuiario(this.usuiario.toEntity())
                .numero(this.numero)
                .bairro(this.bairro)
                .cidade(this.cidade)
                .estado(this.estado)
                .observacao(this.observacao)
                .cep(this.cep)
                .build();
    }

}
