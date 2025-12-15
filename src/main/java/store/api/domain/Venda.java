package store.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.api.util.DateUtil;
import tools.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="vendas")
public class Venda {
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
    private String observacao;
    private Integer status; // 0 - ag pagamento 1 - pago 2 - enviado
    private Boolean pago;
    @Column(name = "CODIGO_RASTREIO")
    private String codigoRastreio;

    @Column(name="VALOR_FRETE")
    private Double valorFrente;

    @Column(name="VALOR_TOTAL")
    private Double valorTotal;

    @Column(name="TOTAL_ITENS")
    private Integer totalItens;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;

    @Column(name = "RETIRADA_LOCAL")
    private Boolean retiradaLocal;

    private String hash;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuiario;

    public ListagemVendasDto toListaVendaDto(){

        String data = DateUtil.dateToString(this.dataPagamento, "dd/MM/YYYY HH:mm:ss");

        EnderecoDto endereco = EnderecoDto.builder()
                .endereco(this.endereco)
                .numero(this.numero)
                .cep(this.cep)
                .bairro(this.bairro)
                .cidade(this.cidade)
                .estado(this.estado)
                .pais("Brasil")
                .build();

        List<ItemCarrinhoRequestDto> compras = new ObjectMapper().readValue(this.pedido, ListaCarrinhoDto.class).getCompras();

        for (ItemCarrinhoRequestDto compra : compras) {
            compra.setImagem("https://portaismusic.com.br/img/produtos/"+ compra.getId() +"/imagem1.jpg");
        }

        return ListagemVendasDto.builder()
                .idVenda(this.id)
                .endereco(endereco)
                .produtos(compras)
                .usuario(this.usuiario.toDto())
                .valorTotal(this.valorTotal)
                .retiradaLocal(this.retiradaLocal != null && this.retiradaLocal)
                .data(data)
                .build();
    }
}
