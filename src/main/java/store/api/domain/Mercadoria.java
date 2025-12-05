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
@Table(name="mercadoria")
public class Mercadoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String posicao;
    private Double valor;
    private Boolean ativo;
    private String categoria;
    private String imagem;

    public MercadoriaDto toDto(){
        return MercadoriaDto.builder()
                .id(this.id)
                .nome(this.nome)
                .descricao(this.descricao)
                .posicao(this.posicao)
                .valor(this.valor)
                .ativo(this.ativo)
                .categoria(this.categoria)
                .imagem(this.imagem)
                .build();
    }

}
