package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.Usuario;
import store.api.domain.UsuarioDto;
import store.api.repository.UsuarioRepository;
import store.api.util.TelefoneUtil;
import store.api.util.Validationtil;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailSender emailSender;

    public UsuarioService(UsuarioRepository usuarioRepository, EmailSender emailSender) {
        this.usuarioRepository = usuarioRepository;
        this.emailSender = emailSender;
    }

    public void reenviarSenha(UsuarioDto dto) throws StoreException {
        Usuario user = usuarioRepository.findByEmail(dto.getEmail());
        if(user== null){
            throw new StoreException("Não foi encontrado nenhum usuário com esse email.");
        }else{
            emailSender.reenviarSenha(user.getNomeSimples(), dto.getEmail(), user.getSenha());
        }
    }

    @Transactional
    public UsuarioDto create(UsuarioDto dto) throws StoreException {
        boolean isNovo = dto.getId() == null;
        this.validarCadastro(dto);
        dto = usuarioRepository.save(dto.toEntity()).toDto();
        if(isNovo) {
            emailSender.enviarEmailBemVindo(dto.getEmail(), dto.getNome().split(" ") [0]);
        }
        return dto;
    }

    private void validarCadastro(UsuarioDto usuario) throws StoreException {
        if(StringUtils.isEmpty(usuario.getNome()) ||
                usuario.getNome().split(" ").length < 2){
            throw new StoreException("Informe o seu nome completo.");
        }

        if(StringUtils.isEmpty(usuario.getEmail()) || !Validationtil.validarEmail(usuario.getEmail())){
            throw new StoreException("Informe um email válido.");
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

        if(StringUtils.isEmpty(usuario.getSenha())){
            throw new StoreException("Informe uma senha válida.");
        }

        if(StringUtils.isEmpty(usuario.getConfSenha())){
            throw new StoreException("Informe a confirmação de senha.");
        }
        if(!usuario.getSenha().equalsIgnoreCase(usuario.getConfSenha())  ){
            throw new StoreException("A confirmação de senha não confere com a senha informada.");
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
}
