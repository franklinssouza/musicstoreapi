package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import store.api.config.exceptions.StoreException;
import store.api.domain.*;
import store.api.repository.ItemCarrinhoRepository;
import store.api.repository.MercadoriaRepository;
import store.api.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemCarrinhoService {

    private final MercadoriaRepository mercadoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;


    public ItemCarrinhoService(MercadoriaRepository mercadoriaRepository1, UsuarioRepository usuarioRepository, ItemCarrinhoRepository mercadoriaRepository) {
        this.mercadoriaRepository = mercadoriaRepository1;
        this.usuarioRepository = usuarioRepository;
        this.itemCarrinhoRepository = mercadoriaRepository;
    }

    @Transactional
    public List<ItemCarrinho> create(ListaCarrinhoDto dadosCompra) throws StoreException {
        List<ItemCarrinho> dados = null;
        try {
            dados = new ArrayList<>();
            this.prepararCadastro(dadosCompra);
            Usuario user = this.usuarioRepository.findById(dadosCompra.getIdUsuario()).get();
            for (ItemCarrinhoRequestDto compra : dadosCompra.getCompras()) {

                Mercadoria mercadoria = mercadoriaRepository.findById(compra.getId().longValue()).get();
                ItemCarrinho itemCarrinho = new ItemCarrinho();
                itemCarrinho.setData(new Date());
                itemCarrinho.setMercadoria(mercadoria);
                itemCarrinho.setUsuario(user);
                itemCarrinho.setTotal(compra.getPrecoTotal());
                itemCarrinho.setQuantidade(compra.getQuantidade());

                dados.add(itemCarrinhoRepository.save(itemCarrinho));
            }
        } catch (Exception e) {
            throw new StoreException("Não foi possível efetuar sua compra. Tente novamente mais tarde.", e);
        }
        return dados;
    }

    private void prepararCadastro(ListaCarrinhoDto dadosCompra) throws StoreException {
        itemCarrinhoRepository.deleteByUsuario(dadosCompra.getIdUsuario());
    }
}
