package store.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.api.integracao.assas.RegistroClienteAssasRequest;
import store.api.integracao.assas.RegistroClienteAssasResponse;
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
    private String confirmacaoSenha;
    private String confirmacaoEmail;
    private String cep;
    private String telefone;
    private String estado;
    private String pais;
    private String idUserAssas;
    private String cidade;
    private String cpf;

    public String getNomeSimples(){
        return this.nome.split(" ")[0];
    }

    public RegistroClienteAssasRequest toUserAssas() {
        return RegistroClienteAssasRequest.builder()
                .name(TextoUtil.capitalizar(this.nome).trim())
                .address(TextoUtil.capitalizar(this.endereco).trim())
                .phone(TelefoneUtil.toNumber(this.telefone))
                .stateInscription(this.estado)
                .postalCode(this.cep)
                .cpfCnpj(this.cpf)
                .email(this.email).build();
    }

    public Usuario toEntity() {
        return Usuario.builder()
                .id(this.id)
                .idUserAssas(this.idUserAssas)
                .nome(TextoUtil.capitalizar(this.nome).trim())
                .cep(this.cep)
                .endereco(TextoUtil.capitalizar(this.endereco).trim())
                .telefone(TelefoneUtil.toNumber(this.telefone))
                .senha(this.senha)
                .estado(this.estado)
                .cidade(this.cidade)
                .cpf(this.cpf)
                .pais("Brasil")
                .email(this.email).build();
    }
}
