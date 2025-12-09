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
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String email;
    private String cep;
    private String senha;
    private String telefone;
    private String estado;
    private String pais;

    public String getNomeSimples(){
        return this.nome.split(" ")[0];
    }
    public UsuarioDto toDto(){
        return UsuarioDto.builder()
                .id(this.id)
                .cep(this.cep)
                .nome(this.nome)
                .endereco(this.endereco)
                .telefone(this.telefone)
                .email(this.email)
                .senha(this.senha)
                .estado(this.estado)
                .pais(this.pais)
                .build();
    }

}
