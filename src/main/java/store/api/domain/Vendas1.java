package store.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.api.util.DateUtil;
import tools.jackson.databind.ObjectMapper;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="vendas")
public class Vendas1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mercadoria", nullable = false)
    private Mercadoria mercadoria;

    @ManyToOne
    @JoinColumn(name = "ID_DADOS_COMPRA", nullable = false)
    private Venda dadosCompra;

    private Integer quantidade;


    private Double total;

    @Column(name = "TIPO_PAGAMENTO")
    private Integer tipoPagamento;

    @Column(name = "AVISO_ENVIADO")
    private Boolean avisoEnviado;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;

    private String hash;

    private String tamanho;

    public ItemVendasDto toDto(){
        ListaCarrinhoDto readValue = new ListaCarrinhoDto();

        if(dadosCompra != null) {
            readValue = new ObjectMapper().readValue(this.dadosCompra.getPedido(), ListaCarrinhoDto.class);
        }

        return ItemVendasDto.builder()
                .id(this.id)
                .data(DateUtil.dateTimeToString(this.dataPagamento))
                .mercadoria(this.mercadoria.toDto())
                .dadosCompra(readValue.getCompras())
                .total(this.total)
                .quantidade(this.quantidade)
                .usuario(this.usuario.toDto())
                .imagem(this.mercadoria.getImagem1())
                .tamanho(this.tamanho)
                .tipoPagamento(this.tipoPagamento.equals(0) ? "PIX": "CARTÃO DE C´R")
                .build();
    }
}
