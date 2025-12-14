package store.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    private String hash;

    public ItemVendasDto toDto(){
        return ItemVendasDto.builder()
                .build();
    }

}
