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
public class MercadoriaDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String posicao;
    private Double valor;
    private Boolean ativo;
    private String categoria;
    private String imagem1;
    private String imagem2;
    private String imagem3;
    private String imagem4;
    private String imagem5;

    public Mercadoria toEntity(){
        return Mercadoria.builder()
                .id(this.id)
                .nome(this.nome)
                .descricao(this.descricao)
                .posicao(this.posicao)
                .valor(this.valor)
                .ativo(this.ativo)
                .categoria(this.categoria)
                .imagem1(this.imagem1)
                .imagem2(this.imagem2)
                .imagem3(this.imagem3)
                .imagem4(this.imagem4)
                .imagem5(this.imagem5)
                .build();
    }

}
