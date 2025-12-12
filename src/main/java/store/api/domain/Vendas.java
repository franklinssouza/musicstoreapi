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
    private Date data;
    private Double total;

    @Column(name = "TIPO_PAGAMENTO")
    private Integer tipoPagamento;

    @Column(name = "AVISO_ENVIADO")
    private Boolean avisoEnviado;

    private String hash;
    private String tamanho;

    public VendasDto toDto(){
        return VendasDto.builder()
                .id(this.id)
                .build();
    }
}
