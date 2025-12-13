package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import store.api.domain.Mercadoria;
import store.api.domain.Usuario;
import store.api.domain.Vendas;
import store.api.domain.VendasDto;
import store.api.integracao.zapi.ZapApi;
import store.api.integracao.zapi.ZapiMessageUtil;
import store.api.repository.MercadoriaRepository;
import store.api.repository.UsuarioRepository;
import store.api.repository.VendasRepository;
import store.api.util.TextoUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VendasService {

    private final VendasRepository vendasRepository;
    private final MercadoriaRepository mercadoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ZapApi zapApi;;

    public VendasService(VendasRepository vendasRepository, MercadoriaRepository mercadoriaRepository, UsuarioRepository usuarioRepository, ZapApi zapApi) {
        this.vendasRepository = vendasRepository;
        this.mercadoriaRepository = mercadoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.zapApi = zapApi;
    }

    public List<VendasDto> findAll() {
        return vendasRepository.buscarTodos().stream()
                .map(Vendas::toDto).toList();
    }

    @Transactional
    public void registrarVendaPorToken(String hashAssas, String token, String dataPagamento) {
        try {
            if(token != null && token.contains("|") && token.contains("@")) {

                String[] dadosToken = token.split("\\|");
                String[] dadosUsuario = dadosToken[0].split("@");
                Long idUsuario = Long.parseLong(dadosUsuario[0]);

                Optional<Usuario> byId = this.usuarioRepository.findById(idUsuario);

                if(byId.isPresent()) {
                    Usuario usuario = byId.get();
                    boolean byHash = this.vendasRepository.existePorHash(hashAssas)>0?true:false;
                    List<Vendas> listaVendas = new ArrayList<>();

                    if (!byHash) {
                        String loja = dadosUsuario[1];

                        String[] listaPedidos = dadosToken[1].split("-");

                        StringBuffer buffer = new StringBuffer();

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
                            vendas.setDataCadastro(new Date());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date data = sdf.parse(dataPagamento);
                            vendas.setDataPagamento(data);

                            buffer.append(" *").append(TextoUtil.formatarComZero(quantidade)).append("* ")
                                    .append(TextoUtil.capitalizar(mercadoria.getNome())).append(" ");

                            if (!StringUtils.isEmpty(tamanho)) {
                                buffer.append("- Tam ");
                                buffer.append(tamanho).append(" ");;
                            }
                            buffer.append("- ").append("R$")
                                  .append(quantidade * mercadoria.getValor())
                                  .append("\n");

                            listaVendas.add(vendas);
                        }

                        if(!listaVendas.isEmpty()) {
                            this.vendasRepository.saveAllAndFlush(listaVendas);
                            String compraRealizada = ZapiMessageUtil.compraRealizadaLoja;
                            compraRealizada = compraRealizada.replace("XXX",usuario.getNomeSimples())
                                                             .replace("YYY",buffer.toString());
                            this.zapApi.enviarTexto(compraRealizada, usuario.getTelefone());
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
