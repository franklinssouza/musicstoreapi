package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.Usuario;
import store.api.domain.UsuarioDto;
import store.api.integracao.assas.AssasApi;
import store.api.integracao.assas.RegistroClienteAssasResponse;
import store.api.integracao.zapi.ZapiMessageUtil;
import store.api.integracao.zapi.ZapApi;
import store.api.repository.UsuarioRepository;
import store.api.util.TelefoneUtil;
import store.api.util.Validationtil;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailSender emailSender;
    private final ZapApi zapUtil;
    private final AssasApi assasApi;

    public UsuarioService(UsuarioRepository usuarioRepository, EmailSender emailSender, ZapApi zapUtil, AssasApi assasApi) {
        this.usuarioRepository = usuarioRepository;
        this.emailSender = emailSender;
        this.zapUtil = zapUtil;
        this.assasApi = assasApi;
    }

    public boolean reenviarSenha(UsuarioDto dto) throws StoreException {
        Usuario user = usuarioRepository.findByEmail(dto.getEmail());
        if(user== null){
            throw new StoreException("Não foi encontrado nenhum usuário com esse email.");
        }else{
            zapUtil.enviarTexto(ZapiMessageUtil.reenvioSenha.replace("XXX",user.getNomeSimples())
                                                           .replace("YYY",user.getSenha()),
                                                            user.getTelefone());
            emailSender.reenviarSenha(user.getNomeSimples(), dto.getEmail(), user.getSenha());
        }
        return true;
    }

    @Transactional
    public UsuarioDto create(UsuarioDto dto) throws StoreException {
        boolean isNovo = dto.getId() == null;
        this.validarCadastro(dto);

        if(dto.getId() == null) {
            RegistroClienteAssasResponse response = assasApi.criarCliente(dto.toUserAssas());
            dto.setIdUserAssas(response.getId());
        }

        dto = usuarioRepository.save(dto.toEntity()).toDto();
        if(isNovo) {
            zapUtil.enviarTexto(ZapiMessageUtil.bemvindo.replace("XXX",dto.getNomeSimples()), dto.getTelefone());
            emailSender.enviarEmailBemVindo(dto.getEmail(), dto.getNome().split(" ") [0]);
        }
        return dto;
    }

    private void validarCadastro(UsuarioDto usuario) throws StoreException {
        if(StringUtils.isEmpty(usuario.getNome()) ||
                usuario.getNome().split(" ").length < 2){
            throw new StoreException("Informe o seu nome completo.");
        }
        if(StringUtils.isEmpty(usuario.getCpf()) || !Validationtil.isValidCPF(usuario.getCpf())){
            throw new StoreException("Informe cpf válido.");
        }

        if(StringUtils.isEmpty(usuario.getEmail()) || !Validationtil.validarEmail(usuario.getEmail())){
            throw new StoreException("Informe um email válido.");
        }

        if(StringUtils.isEmpty(usuario.getConfirmacaoEmail())){
            throw new StoreException("Informe a confirmação do e-mail.");
        }

        if(!usuario.getEmail().equalsIgnoreCase(usuario.getConfirmacaoEmail())  ){
            throw new StoreException("A confirmação de email não confere com o email informado.");
        }

        if(StringUtils.isEmpty(usuario.getEndereco()) || usuario.getEndereco().length() < 5){
            throw new StoreException("Informe o seu endereço completo.");
        }

        if(StringUtils.isEmpty(usuario.getCep()) || !Validationtil.isCepValido(usuario.getCep())){
            throw new StoreException("Informe o seu CEP completo.");
        }

        if(StringUtils.isEmpty(usuario.getEstado()) ){
            throw new StoreException("Informe o seu estado.");
        }

        if(StringUtils.isEmpty(usuario.getTelefone()) || !TelefoneUtil.isTelefoneCelularValido(usuario.getTelefone())){
            throw new StoreException("Informe um telefone válido.");
        }

        if(usuario.getId() == null) {
            if (StringUtils.isEmpty(usuario.getSenha())) {
                throw new StoreException("Informe uma senha válida.");
            }

            if (StringUtils.isEmpty(usuario.getConfirmacaoSenha())) {
                throw new StoreException("Informe a confirmação de senha.");
            }
            if (!usuario.getSenha().equalsIgnoreCase(usuario.getConfirmacaoSenha())) {
                throw new StoreException("A confirmação de senha não confere com a senha informada.");
            }
        }else{
            Optional<Usuario> procurado = this.usuarioRepository.findById(usuario.getId());
            if(!StringUtils.isEmpty(usuario.getSenha())){
                if (StringUtils.isEmpty(usuario.getConfirmacaoSenha())) {
                    throw new StoreException("Informe a confirmação de senha.");
                }
                if (!usuario.getSenha().equalsIgnoreCase(usuario.getConfirmacaoSenha())) {
                    throw new StoreException("A confirmação de senha não confere com a senha informada.");
                }
            }else{
                usuario.setSenha(procurado.get().getSenha());
            }
        }

        if(usuario.getId() == null) {
            Usuario procurado = this.usuarioRepository.findByEmail(usuario.getEmail());
            if (procurado != null) {
                throw new StoreException("Já existe um usuário com esse email registrado.");
            }
        }else{
            Usuario procurado = this.usuarioRepository.findByEmail(usuario.getEmail());
            if(procurado != null && procurado.getEmail().equalsIgnoreCase(usuario.getEmail()) &&
               !procurado.getId().equals(usuario.getId())){
                throw new StoreException("Já existe um usuário com esse email registrado.");
            }
        }
    }

    public List<UsuarioDto> findAll() {
        return null;
    }

    public UsuarioDto findById(long id) {
        return usuarioRepository.findById(id)
                .map(Usuario::toDto)
                .orElse(null);
    }

    @Transactional
    public void delete(long id) {
        this.usuarioRepository.deleteById(id);
    }

    public UsuarioDto login(UsuarioDto body) throws StoreException {
        Usuario byEmailAndSenha = usuarioRepository.findByEmailAndSenha(body.getEmail(), body.getSenha());
        if(byEmailAndSenha == null){
            throw new StoreException("Usuário inválido.");
        }
        return byEmailAndSenha.toDto();
    }
}
