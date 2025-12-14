package store.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="dados_compra")
public class DadosCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pedido;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Double valorFrente;

    public DadosCompraDto toDto(){
        return DadosCompraDto.builder()
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
