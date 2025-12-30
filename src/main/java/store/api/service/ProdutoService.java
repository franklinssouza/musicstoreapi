package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import store.api.config.exceptions.StoreException;
import store.api.domain.Produto;
import store.api.domain.ProdutoDto;
import store.api.repository.ProdutoRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Value("${img.path.produto}")
    private String pathProduto;

    @Value("${img.path.vitrine}")
    private String pathVitrine;

    @Value("${img.url.produto}")
    private String urlProduto;

    @Value("${img.url.vitrine}")
    private String urlVitrine;

    private final ProdutoRepository produtoRepository;

    private final VendasService vendasService;

    public ProdutoService(ProdutoRepository produtoRepository, VendasService vendasService) {
        this.produtoRepository = produtoRepository;
        this.vendasService = vendasService;
    }

    @Transactional(rollbackOn = StoreException.class)
    public ProdutoDto create(ProdutoDto dto) throws StoreException {
        this.validarCadastro(dto);
        ProdutoDto aux = new ProdutoDto();
        aux.setImagem1(dto.getImagem1());
        aux.setImagem2(dto.getImagem2());
        aux.setImagem3(dto.getImagem3());
        aux.setImagem4(dto.getImagem4());
        aux.setImagem5(dto.getImagem5());

        dto.setImagem1("");
        dto.setImagem2("");
        dto.setImagem3("");
        dto.setImagem4("");
        dto.setImagem5("");

        ProdutoDto retorno = produtoRepository.save(dto.toEntity()).toDto();

        retorno.setImagem1(aux.getImagem1());
        retorno.setImagem2(aux.getImagem2());
        retorno.setImagem3(aux.getImagem3());
        retorno.setImagem4(aux.getImagem4());
        retorno.setImagem5(aux.getImagem5());

        this.salvarImagens(retorno);
        return retorno;
    }

    private void salvarImagens(ProdutoDto dto) throws StoreException {
        try {
            Produto produto = produtoRepository.findById(dto.getId()).get();
            if(dto.getPosicao().equalsIgnoreCase("1")){ // 1 - Vitrine 2 - Colecao
                if(!StringUtils.isEmpty(dto.getImagem1())){

                    if(!dto.getImagem1().contains("portaismusic.com.br")) {
                        String replace = this.pathVitrine.replace("?", dto.getId().toString()) + "imagem1.png";
                        this.salvarImagem(dto.getImagem1(), replace);

                        produto.setImagem1(this.urlVitrine + "imagem1.png");
                        this.produtoRepository.save(produto);
                    }else{
                        produto.setImagem1(dto.getImagem1());
                        this.produtoRepository.save(produto);
                    }
                }
            }else{

                if(!StringUtils.isEmpty(dto.getImagem1())){

                    if(!dto.getImagem1().contains("portaismusic.com.br")) {
                        String idImg = UUID.randomUUID() + ".png";
                        String path = this.pathProduto.replace("?", dto.getId().toString()) + idImg;
                        this.salvarImagem(dto.getImagem1(), path);

                        String urlPath = this.urlProduto.replace("?", dto.getId().toString()) + idImg;
                        produto.setImagem1(urlPath);
                        this.produtoRepository.save(produto);
                    }else{
                        produto.setImagem1(dto.getImagem1());
                        this.produtoRepository.save(produto);
                    }
                }

                if(!StringUtils.isEmpty(dto.getImagem2())){

                    if(!dto.getImagem2().contains("portaismusic.com.br")) {

                        String idImg = UUID.randomUUID() + ".png";
                        String path = this.pathProduto.replace("?", dto.getId().toString()) + idImg;
                        this.salvarImagem(dto.getImagem2(), path);

                        String urlPath = this.urlProduto.replace("?", dto.getId().toString()) + idImg;
                        produto.setImagem2(urlPath);
                        this.produtoRepository.save(produto);
                    }else{
                        produto.setImagem2(dto.getImagem2());
                        this.produtoRepository.save(produto);
                    }
                }
                if(!StringUtils.isEmpty(dto.getImagem3())){

                    if(!dto.getImagem3().contains("portaismusic.com.br")) {

                        String idImg = UUID.randomUUID() + ".png";
                        String path = this.pathProduto.replace("?", dto.getId().toString()) + idImg;
                        this.salvarImagem(dto.getImagem3(), path);

                        String urlPath = this.urlProduto.replace("?", dto.getId().toString()) + idImg;
                        produto.setImagem3(urlPath);
                        this.produtoRepository.save(produto);
                    }else{
                        produto.setImagem3(dto.getImagem3());
                        this.produtoRepository.save(produto);
                    }
                }
                if(!StringUtils.isEmpty(dto.getImagem4())){
                    if(!dto.getImagem4().contains("portaismusic.com.br")) {
                        String idImg = UUID.randomUUID() + ".png";
                        String path = this.pathProduto.replace("?", dto.getId().toString()) + idImg;
                        this.salvarImagem(dto.getImagem4(), path);

                        String urlPath = this.urlProduto.replace("?", dto.getId().toString()) + idImg;
                        produto.setImagem4(urlPath);
                        this.produtoRepository.save(produto);
                    }else{
                        produto.setImagem4(dto.getImagem4());
                        this.produtoRepository.save(produto);
                    }
                }
                if(!StringUtils.isEmpty(dto.getImagem5())){
                    if(!dto.getImagem5().contains("portaismusic.com.br")) {
                        String idImg = UUID.randomUUID() + ".png";
                        String path = this.pathProduto.replace("?", dto.getId().toString()) + idImg;
                        this.salvarImagem(dto.getImagem5(), path);

                        String urlPath = this.urlProduto.replace("?", dto.getId().toString()) + idImg;
                        produto.setImagem5(urlPath);
                        this.produtoRepository.save(produto);
                    }else{
                        produto.setImagem5(dto.getImagem5());
                        this.produtoRepository.save(produto);
                    }
                }
            }
        } catch (Exception e) {
            throw new StoreException("Não foi possível salvar a imagem. Tente novamente mais tarde.", e);
        }
    }

    public void salvarImagem(String base64, String caminhoArquivo) throws IOException {
        byte[] imagemBytes = Base64.getDecoder().decode(base64);
        File arquivo = new File(caminhoArquivo);
        File diretorio = arquivo.getParentFile();
        System.out.println("diretorio " + diretorio);

        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            fos.write(imagemBytes);
        }
    }

    public List<ProdutoDto> buscarTodosSite() {
        return produtoRepository.buscarTodosSite().stream()
                .map(Produto::toDto).toList();
    }

    public List<ProdutoDto> buscarTodos() {
        return produtoRepository.buscarTodos().stream()
                .map(Produto::toDto).toList();
    }

    public List<ProdutoDto> pesquisar(String filtro) {
        return produtoRepository.pesquisar(filtro).stream()
                .map(Produto::toDto).toList();
    }


    private void validarCadastro(ProdutoDto dto) throws StoreException {

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

        if(dto.getEstoquep() <= 0 &&
                dto.getEstoquem() <= 0 &&
                dto.getEstoqueg() <= 0 &&
                dto.getEstoquegg() <= 0 &&
                dto.getEstoqueexg() <= 0 ){
            throw new StoreException("Informe o estoque para o produto.");
        }

        if(dto.getId() == null) {
            Produto procurado = this.produtoRepository.findByNome(dto.getNome());
            if (procurado != null) {
                throw new StoreException("Já existe um produto com esse nome registrado.");
            }
        }else{
            Produto procurado = this.produtoRepository.findByNome(dto.getNome());
            if(procurado != null && procurado.getNome().equalsIgnoreCase(dto.getNome()) &&
               !procurado.getId().equals(dto.getId())){
                throw new StoreException("Já existe um usuário com esse email registrado.");
            }
        }
    }

    public List<ProdutoDto> findAll() {
        return null;
    }

    public ProdutoDto findById(long id) {
        return produtoRepository.findById(id)
                .map(Produto::toDto)
                .orElse(null);
    }

    @Transactional(rollbackOn = StoreException.class)
    public void delete(Long idProduto) throws StoreException {

        try {
            Produto produto = this.produtoRepository.findById(idProduto).get();
            if (produto.getVendas() != null && produto.getVendas() > 0) {
                throw new StoreException("Esse produto não pode ser excluído pois existem vendas atreladas a ele.");
            }
            this.produtoRepository.deleteById(idProduto);

            String pathVitrine = this.pathVitrine.replace("?", idProduto.toString());
            String pathProduto = this.pathProduto.replace("?", idProduto.toString());

            System.out.println("DELETANDO VITRINE " + pathVitrine);
            System.out.println("DELETANDO PRODUTO " + pathProduto);

            FileUtils.deleteDirectory(new File(pathVitrine));
            FileUtils.deleteDirectory(new File(pathProduto));

        }catch (StoreException e){
            throw e;
        }catch (Exception e) {
            throw new StoreException("Não foi possível remover o produto. Tente novamente mais tarde.", e);
        }
    }
}