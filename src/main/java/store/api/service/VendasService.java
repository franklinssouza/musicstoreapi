package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import store.api.domain.Mercadoria;
import store.api.domain.Usuario;
import store.api.domain.Vendas;
import store.api.domain.VendasDto;
import store.api.repository.MercadoriaRepository;
import store.api.repository.UsuarioRepository;
import store.api.repository.VendasRepository;

import java.util.List;

@Service
public class VendasService {

    private final VendasRepository vendasRepository;
    private final MercadoriaRepository mercadoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public VendasService(VendasRepository vendasRepository, MercadoriaRepository mercadoriaRepository, UsuarioRepository usuarioRepository) {
        this.vendasRepository = vendasRepository;
        this.mercadoriaRepository = mercadoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<VendasDto> findAll() {
        return vendasRepository.buscarTodos().stream()
                .map(Vendas::toDto).toList();
    }

    @Transactional
    public void registrarVendaPorToken(String hashAssas, String token) {
        if(!token.contains("|") && !token.contains("@")) {
            return;
        }
        String[] dadosToken = token.split("|");
        String[] dadosUsuario = dadosToken[0].split("@");
        Long idUsuario = Long.parseLong(dadosUsuario[0]);

        Usuario usuario = this.usuarioRepository.findById(idUsuario).get();
        Vendas byHash = this.vendasRepository.findByHash(hashAssas);
        if(byHash == null) {
            String loja = dadosUsuario[1];

            String[] listaPedidos = dadosToken[1].split("-");
            for (String pedido : listaPedidos) {
                String[] dadosPedido = pedido.split(":");
                Long idProduto = Long.parseLong(dadosPedido[0]);
                Integer quantidade = Integer.parseInt(dadosPedido[1]);
                String tamanho = dadosPedido[2].toString();

                Mercadoria mercadoria = this.mercadoriaRepository.findById(idProduto).get();

                Vendas vendas = new Vendas();
                vendas.setHash(hashAssas);
                vendas.setQuantidade(quantidade);
                vendas.setAvisoEnviado(false);
                vendas.setMercadoria(mercadoria);
                vendas.setUsuario(usuario);
                vendas.setTipoPagamento(0);
                vendas.setTotal(mercadoria.getValor() * quantidade);
                vendas.setTamanho(tamanho);

                this.vendasRepository.save(vendas);
            }
        }
    }
}
