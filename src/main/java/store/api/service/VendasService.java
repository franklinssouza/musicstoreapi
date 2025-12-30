package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.*;
import store.api.integracao.zapi.ZapApi;
import store.api.integracao.zapi.ZapiMessageUtil;
import store.api.repository.DadosCompraRepository;
import store.api.repository.ProdutoRepository;
import store.api.repository.UsuarioRepository;
import store.api.repository.VendasRepository;
import store.api.util.DateUtil;
import store.api.util.FormatUtil;
import tools.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class VendasService {

    private final VendasRepository vendasRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ZapApi zapApi;
    private final EmailSender emailSender;
    private final DadosCompraRepository dadosCompraRepository;

    public VendasService(VendasRepository vendasRepository, ProdutoRepository mercadoriaRepository, UsuarioRepository usuarioRepository, ZapApi zapApi, EmailSender emailSender, DadosCompraRepository dadosCompraRepository) {
        this.vendasRepository = vendasRepository;
        this.produtoRepository = mercadoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.zapApi = zapApi;
        this.emailSender = emailSender;
        this.dadosCompraRepository = dadosCompraRepository;
    }

    @Transactional
    public void registrarVendaPorToken(String hashAssas, String token, String dataPagamento) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dataEfetivaPagamento = sdf.parse(dataPagamento);
            Long idDadosCompra = Long.parseLong(token);
            Optional<Venda> dadosCompra = this.dadosCompraRepository.findById(idDadosCompra);
            if (dadosCompra.isPresent()) {
                Venda venda = dadosCompra.get();

                if(venda.getPago()){
                   return;
                }
                venda.setPago(true);
                venda.setDataPagamento(dataEfetivaPagamento);
                venda.setHash(hashAssas);
                venda.setStatus(1);

                ListaCarrinhoDto dadosVenda = new ObjectMapper().readValue(venda.getPedido(), ListaCarrinhoDto.class);

                for (ItemCarrinhoRequestDto compra : dadosVenda.getCompras()) {
                    Integer quantidade = compra.getQuantidade();
                    Produto produto = produtoRepository.findById(compra.getId().longValue()).get();
                    if(compra.getTamanho().equalsIgnoreCase("p")) {
                        produto.setEstoquep(produto.getEstoquep() - quantidade);
                    }
                    if(compra.getTamanho().equalsIgnoreCase("m")) {
                        produto.setEstoquem(produto.getEstoquem() - quantidade);
                    }
                    if(compra.getTamanho().equalsIgnoreCase("g")) {
                        produto.setEstoqueg(produto.getEstoqueg() - quantidade);
                    }
                    if(compra.getTamanho().equalsIgnoreCase("gg")) {
                        produto.setEstoquegg(produto.getEstoquegg() - quantidade);
                    }
                    if(compra.getTamanho().equalsIgnoreCase("exg")) {
                        produto.setEstoqueexg(produto.getEstoqueexg() - quantidade);
                    }
                    produto.incrementarVenda(quantidade);
                    this.produtoRepository.save(produto);
                }

                this.vendasRepository.save(venda);

                ListaCarrinhoDto pedido = new ObjectMapper().readValue(venda.getPedido(), ListaCarrinhoDto.class);
                Optional<Usuario> optUser = this.usuarioRepository.findById(pedido.getIdUsuario());

                if(optUser.isPresent()) {
                    Usuario usuario = optUser.get();

                    StringBuilder buffer = new StringBuilder();
                    for (ItemCarrinhoRequestDto compra : pedido.getCompras()) {

                        buffer.append(" *").append(FormatUtil.formatarComZero(compra.getQuantidade())).append("* ")
                                .append(FormatUtil.capitalizar(compra.getNome())).append(" ");

                        if (!StringUtils.isEmpty(compra.getTamanho())) {
                            buffer.append("- Tam ");
                            buffer.append(compra.getTamanho()).append(" ");
                        }
                        buffer.append("- ").append("R$ ")
                                .append(FormatUtil.format(compra.getQuantidade() * compra.getValor()))
                                .append("\n");
                    }

                    buffer.append("\n").append(" O total da sua compra foi: ").append("R$ ")
                            .append(FormatUtil.format(venda.getValorTotal()))
                            .append("\n");

                    String compraRealizada = ZapiMessageUtil.compraRealizadaLoja;
                    compraRealizada = compraRealizada.replace("XXX", usuario.getNomeSimples()).replace("YYY", buffer.toString());
                    this.zapApi.enviarTexto(compraRealizada, usuario.getTelefone());

                    this.emailSender.enviarEmailCompra(usuario.getEmail(),
                                                       usuario.getNomeSimples(),
                                                       buffer.toString());
                }
            }
        } catch (NumberFormatException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

//            if(token != null && token.contains("|") && token.contains("@")) {
//
//                String[] dadosToken = token.split("\\|");
//                String[] dadosUsuario = dadosToken[0].split("@");
//                Long idUsuario = Long.parseLong(dadosUsuario[0]);
//
//                Optional<Usuario> byId = this.usuarioRepository.findById(idUsuario);
//
//                if(byId.isPresent()) {
//                    Usuario usuario = byId.get();
//                    boolean byHash = this.vendasRepository.existePorHash(hashAssas)>0?true:false;
//                    List<Vendas> listaVendas = new ArrayList<>();
//
//                    if (!byHash) {
//                        String loja = dadosUsuario[1];
//
//                        String[] listaPedidos = dadosToken[1].split("-");
//
//                        StringBuffer buffer = new StringBuffer();
//
//                        for (String pedido : listaPedidos) {
//                            String[] dadosPedido = pedido.split(":");
//                            Long idProduto = Long.parseLong(dadosPedido[0]);
//                            Integer quantidade = Integer.parseInt(dadosPedido[1]);
//                            String tamanho = dadosPedido[2].toString();
//
//                            Mercadoria mercadoria = this.mercadoriaRepository.findById(idProduto).get();
//
//                            Vendas vendas = new Vendas();
//                            vendas.setHash(hashAssas);
//                            vendas.setQuantidade(quantidade);
//                            vendas.setAvisoEnviado(false);
//                            vendas.setMercadoria(mercadoria);
//                            vendas.setUsuario(usuario);
//                            vendas.setTipoPagamento(0);
//                            vendas.setTotal(mercadoria.getValor() * quantidade);
//                            vendas.setTamanho(tamanho);
//                            vendas.setDataCadastro(new Date());
//
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date data = sdf.parse(dataPagamento);
//                            vendas.setDataPagamento(data);
//
//                            buffer.append(" *").append(TextoUtil.formatarComZero(quantidade)).append("* ")
//                                    .append(TextoUtil.capitalizar(mercadoria.getNome())).append(" ");
//
//                            if (!StringUtils.isEmpty(tamanho)) {
//                                buffer.append("- Tam ");
//                                buffer.append(tamanho).append(" ");;
//                            }
//                            buffer.append("- ").append("R$")
//                                    .append(quantidade * mercadoria.getValor())
//                                    .append("\n");
//
//                            listaVendas.add(vendas);
//                        }
//
//                        if(!listaVendas.isEmpty()) {
//                            this.vendasRepository.saveAllAndFlush(listaVendas);
//                            String compraRealizada = ZapiMessageUtil.compraRealizadaLoja;
//                            compraRealizada = compraRealizada.replace("XXX",usuario.getNomeSimples())
//                                    .replace("YYY",buffer.toString());
//                            this.zapApi.enviarTexto(compraRealizada, usuario.getTelefone());
//                        }
//                    }
//                }
//            }
    }

    public PanoramaVendasDto buscarPanorama() {
        List<Venda> vendas = this.vendasRepository.findAll();

        double totalValorVendas = vendas.stream()
                .filter(Venda::getPago)
                .mapToDouble(Venda::getValorTotal)
                .sum();
        long quantidadeVendas = vendas.stream().filter(Venda::getPago).count();
        double totalVendasHoje = vendas.stream()
                .filter(Venda::getPago)
                .filter(v -> v.getDataPagamento()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .isEqual(LocalDate.now()))
                .mapToDouble(Venda::getValorTotal)
                .sum();

        String produtoMaisComprado = "";
        Map<String, Integer> quantidadePorProduto = new HashMap<>();

        for (Venda venda : vendas) {
            ListaCarrinhoDto pedido = new ObjectMapper().readValue(venda.getPedido(), ListaCarrinhoDto.class);

            for (ItemCarrinhoRequestDto compra : pedido.getCompras()) {
                String nomeProduto = compra.getNome();
                Integer quantidade = compra.getQuantidade();
                quantidadePorProduto.merge(nomeProduto, quantidade, Integer::sum);
            }
        }

        int maiorQuantidade = 0;
        for (Map.Entry<String, Integer> entry : quantidadePorProduto.entrySet()) {
            if (entry.getValue() > maiorQuantidade) {
                maiorQuantidade = entry.getValue();
                produtoMaisComprado = entry.getKey();
            }
        }

        return PanoramaVendasDto.builder()
                .quantidadeVendas(quantidadeVendas)
                .produtoMaisVendido(produtoMaisComprado)
                .totalVendasDiarias(totalVendasHoje)
                .valorTotalVendas(totalValorVendas)
                .build();
    }

    public List<ListagemVendasDto> buscarVendasUsuario(Long idUsuario) {
        return this.vendasRepository.buscarVendasUsuario(idUsuario)
                .stream()
                .map(Venda::toListaVendaDto).toList();
    }

    public List<ListagemVendasDto> pesquisarVendas(PesquisaVendasDto dto) {
        if (dto.getInicio() == null) {
            dto.setInicio(DateUtil.dateToString(DateUtil.primeiroDiaDoMes(), "yyyy-MM-dd"));
        }
        if (dto.getTermino() == null) {
            dto.setTermino(DateUtil.dateToString(DateUtil.ultimoDiaDoMes(), "yyyy-MM-dd"));
        }
        return this.vendasRepository.pesquisarVendas(DateUtil.getDataZerada(DateUtil.stringToDate(dto.getInicio(), "yyyy-MM-dd")).getTime(),
                        DateUtil.colocarEmMeiaNoite(DateUtil.stringToDate(dto.getTermino(), "yyyy-MM-dd")))
                .stream()
                .map(Venda::toListaVendaDto).toList();
    }

    @Transactional
    public void atualizarStatusVenda(AtualizarStatusEnvioDto body) throws StoreException {
        Optional<Venda> byId = this.vendasRepository.findById(body.getIdVenda());
        if(byId.isPresent()) {
            Venda venda = byId.get();
            venda.setStatus(body.getStatus());
            venda.setCodigoRastreio(body.getCodigoRastreio());
            this.vendasRepository.save(venda);
        }else{
            throw new StoreException("Não foi possível atualizar o status da compra. Tente novamente mais tarde.");
        }
    }
}
