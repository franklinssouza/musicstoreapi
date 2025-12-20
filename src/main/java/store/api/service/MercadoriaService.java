package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import store.api.config.exceptions.StoreException;
import store.api.domain.Mercadoria;
import store.api.domain.MercadoriaDto;
import store.api.repository.MercadoriaRepository;

import java.util.List;

@Service
public class MercadoriaService {

    private final MercadoriaRepository mercadoriaRepository;

    public MercadoriaService(MercadoriaRepository mercadoriaRepository) {
        this.mercadoriaRepository = mercadoriaRepository;
    }

    public List<MercadoriaDto> buscarTodosSite() {
        return mercadoriaRepository.buscarTodosSite().stream()
                .map(Mercadoria::toDto).toList();
    }

    public List<MercadoriaDto> buscarTodos() {
        return mercadoriaRepository.buscarTodos().stream()
                .map(Mercadoria::toDto).toList();
    }

    public List<MercadoriaDto> pesquisar(String filtro) {
        return mercadoriaRepository.pesquisar(filtro).stream()
                .map(Mercadoria::toDto).toList();
    }

    @Transactional
    public MercadoriaDto create(MercadoriaDto dto) throws StoreException {
        this.validarCadastro(dto);
        return mercadoriaRepository.save(dto.toEntity()).toDto();
    }

    private void validarCadastro(MercadoriaDto dto) throws StoreException {
        if(StringUtils.isEmpty(dto.getNome())){
            throw new StoreException("Informe o seu nome para o produto.");
        }

        if(dto.getValor() == null || dto.getValor() <= 0){
            throw new StoreException("Informe um valor para o produto.");
        }

        if(StringUtils.isEmpty(dto.getDescricao()) || dto.getDescricao().length() < 5){
            throw new StoreException("Informe a descrição completa do produto.");
        }

        if(StringUtils.isEmpty(dto.getPosicao())){
            throw new StoreException("Informe a posição que esse produto irá ficar.");
        }

        if(StringUtils.isEmpty(dto.getCategoria())){
            throw new StoreException("Informe a categoria desse produto.");
        }

        if(StringUtils.isEmpty(dto.getImagem1())){
            throw new StoreException("Informe uma imagem para esse produto.");
        }

        if(dto.getId() == null) {
            Mercadoria procurado = this.mercadoriaRepository.findByNome(dto.getNome());
            if (procurado != null) {
                throw new StoreException("Já existe um produto com esse nome registrado.");
            }
        }else{
            Mercadoria procurado = this.mercadoriaRepository.findByNome(dto.getNome());
            if(procurado != null && procurado.getNome().equalsIgnoreCase(dto.getNome()) &&
               !procurado.getId().equals(dto.getId())){
                throw new StoreException("Já existe um usuário com esse email registrado.");
            }
        }
    }

    public List<MercadoriaDto> findAll() {
        return null;
    }

    public MercadoriaDto findById(long id) {
        return mercadoriaRepository.findById(id)
                .map(Mercadoria::toDto)
                .orElse(null);
    }

    @Transactional
    public void delete(long id) {
        this.mercadoriaRepository.deleteById(id);
    }


}
