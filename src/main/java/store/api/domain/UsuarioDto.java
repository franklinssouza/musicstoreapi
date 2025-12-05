package store.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.api.util.TelefoneUtil;
import store.api.util.TextoUtil;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String nome;
    private String endereco;
    private String email;
    private String senha;
    private String confSenha;
    private String cep;
    private String telefone;

    public Usuario toEntity() {
        return Usuario.builder()
                .id(this.id)
                .nome(TextoUtil.capitalizar(this.nome).trim())
                .cep(this.cep)
                .endereco(TextoUtil.capitalizar(this.endereco).trim())
                .telefone(TelefoneUtil.toNumber(this.telefone))
                .senha(this.senha)
                .email(this.email).build();
    }
}
