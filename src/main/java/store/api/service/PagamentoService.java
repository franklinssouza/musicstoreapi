package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.Usuario;
import store.api.domain.Venda;
import store.api.domain.ListaCarrinhoDto;
import store.api.integracao.assas.*;
import store.api.integracao.zapi.ZapApi;
import store.api.repository.DadosCompraRepository;
import store.api.repository.UsuarioRepository;
import store.api.util.FormatUtil;
import store.api.util.Validationtil;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class PagamentoService {

    private final EmailSender emailSender;
    private final ZapApi zapUtil;
    private final AssasApi assasApi;
    private final DadosCompraRepository dadosCompraRepository;

    private final UsuarioRepository usuarioRepository;
    public PagamentoService(EmailSender emailSender, ZapApi zapUtil, AssasApi assasApi, DadosCompraRepository dadosCompraRepository, UsuarioRepository usuarioRepository) {
        this.emailSender = emailSender;
        this.zapUtil = zapUtil;
        this.assasApi = assasApi;
        this.dadosCompraRepository = dadosCompraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AsaasPaymentLinkResponse prepararPagamento(ListaCarrinhoDto dadosPedido) throws StoreException {

        Validationtil.validarPagamentoPix(dadosPedido);

        Optional<Usuario> usuario = this.usuarioRepository.findById(dadosPedido.getIdUsuario());
        if(usuario.isPresent()){
            double valorTotalCompra = dadosPedido.getCompras().stream()
                    .mapToDouble(item -> item.getValor() * item.getQuantidade())
                    .sum();

            double valorFrente = dadosPedido.getValorFrete() == null ? 0.0 : FormatUtil.converttoDouble(dadosPedido.getValorFrete());
            Venda dadosCompra = Venda.builder()
                    .pedido(new ObjectMapper().writeValueAsString(dadosPedido))
                    .endereco(dadosPedido.getEndereco().getEndereco())
                    .numero(dadosPedido.getEndereco().getNumero())
                    .bairro(dadosPedido.getEndereco().getBairro())
                    .cidade(dadosPedido.getEndereco().getCidade())
                    .estado(dadosPedido.getEndereco().getEstado())
                    .cep(dadosPedido.getEndereco().getCep())
                    .observacao(dadosPedido.getObservacao())
                    .dataCadastro(new Date())
                    .usuario(usuario.get())
                    .valorFrente(valorFrente)
                    .retiradaLocal(dadosPedido.getRetiradaLocal())
                    .pago(false)
                    .status(0)
                    .valorTotal(valorTotalCompra + valorFrente)
                    .totalItens(dadosPedido.getCompras().size())
                    .build();

            dadosCompra = this.dadosCompraRepository.save(dadosCompra);

            AssasPaymentLinkRequest request = AssasPaymentLinkRequest.builder()
                    .billingType("UNDEFINED")
                    .chargeType("DETACHED")
                    .name("Produtos Portais Music")
                    .description("Compra de produtos na loja portais music")
                    .endDate(LocalDate.now().plusMonths(1))
                    .value(valorTotalCompra)
                    .dueDateLimitDays(10)
                    .maxInstallmentCount(1)
                    .externalReference(dadosCompra.getId().toString())
                    .notificationEnabled(false)
                    .isAddressRequired(false)
                    .callback(
                            AssasPaymentLinkRequest.Callback.builder()
                                    .successUrl("https://portaismusic.com.br/loja")
                                    .autoRedirect(true)
                                    .build()
                    )
                    .build();

            return assasApi.gerarLinkPagamento(request);
        }else{
            throw new StoreException("Seu usuário está inválido. Logue novamente na loja e tente rente realizar a sua compra novamente.");
        }

    }


}