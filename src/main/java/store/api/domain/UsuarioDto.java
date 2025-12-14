package store.api.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.api.integracao.assas.RegistroClienteAssasRequest;
import store.api.util.TelefoneUtil;
import store.api.util.FormatUtil;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String nome;
    private EnderecoDto endereco;
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private String confirmacaoEmail;
    private String telefone;
    private String idUserAssas;
    private String cpf;

    public String getNomeSimples(){
        return this.nome.split(" ")[0];
    }

    public RegistroClienteAssasRequest toUserAssas() {
        return RegistroClienteAssasRequest.builder()
                .name(FormatUtil.capitalizar(this.nome).trim())
                .address(FormatUtil.capitalizar(this.endereco.getEndereco()).trim())
                .phone(TelefoneUtil.toNumber(this.telefone))
                .stateInscription(this.endereco.getEstado())
                .postalCode(this.endereco.getCep())
                .cpfCnpj(this.cpf)
                .email(this.email).build();
    }

    public Usuario toEntity() {
        return Usuario.builder()
                .id(this.id)
                .idUserAssas(this.idUserAssas)
                .nome(FormatUtil.capitalizar(this.nome).trim())
                .endereco(FormatUtil.capitalizar(this.endereco.getEndereco()).trim())
                .numero(this.endereco.getNumero())
                .cep(this.endereco.getCep())
                .bairro(this.endereco.getBairro())
                .cidade(this.endereco.getCidade())
                .estado(this.endereco.getEstado())
                .pais("Brasil")
                .telefone(TelefoneUtil.toNumber(telefone))
                .senha(this.senha)
                .cpf(this.cpf)
                .email(this.email).build();
    }
}
