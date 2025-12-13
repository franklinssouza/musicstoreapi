package store.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.api.util.DateUtil;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="vendas")
public class Vendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mercadoria", nullable = false)
    private Mercadoria mercadoria;

    private Integer quantidade;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;

    private Double total;

    @Column(name = "TIPO_PAGAMENTO")
    private Integer tipoPagamento;

    @Column(name = "AVISO_ENVIADO")
    private Boolean avisoEnviado;

    private String hash;
    private String tamanho;

    public ItemVendasDto toDto(){
        return ItemVendasDto.builder()
                .id(this.id)
                .data(DateUtil.dateTimeToString(this.dataPagamento))
                .mercadoria(this.mercadoria.toDto())
                .total(this.total)
                .quantidade(this.quantidade)
                .usuario(this.usuario.toDto())
                .imagem(this.mercadoria.getImagem1())
                .tamanho(this.tamanho)
                .tipoPagamento(this.tipoPagamento.equals(0) ? "PIX": "CARTÃO DE C´R")
                .build();
    }
}
