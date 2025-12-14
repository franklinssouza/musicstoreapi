package store.api.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.DadosCompra;
import store.api.domain.ItemCarrinhoRequestDto;
import store.api.domain.ListaCarrinhoDto;
import store.api.integracao.assas.*;
import store.api.integracao.zapi.ZapApi;
import store.api.repository.DadosCompraRepository;
import store.api.util.Validationtil;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Service
public class PagamentoService {

    private final EmailSender emailSender;
    private final ZapApi zapUtil;
    private final AssasApi assasApi;

    private final DadosCompraRepository dadosCompraRepository;

    public PagamentoService(EmailSender emailSender, ZapApi zapUtil, AssasApi assasApi, DadosCompraRepository dadosCompraRepository) {
        this.emailSender = emailSender;
        this.zapUtil = zapUtil;
        this.assasApi = assasApi;
        this.dadosCompraRepository = dadosCompraRepository;
    }

    @Transactional
    public QrCodePixResponse prepararPagamentoPix(ListaCarrinhoDto dadosPedido) throws StoreException {

        Validationtil.validarEndereco(dadosPedido.getEndereco());

        double valor = dadosPedido.getCompras().stream()
                .mapToDouble(ItemCarrinhoRequestDto::getValor)
                .sum();

        DadosCompra dadosCompra = DadosCompra.builder()
                .pedido(new ObjectMapper().writeValueAsString(dadosPedido))
                .endereco(dadosPedido.getEndereco().getEndereco())
                .numero(dadosPedido.getEndereco().getNumero())
                .bairro(dadosPedido.getEndereco().getBairro())
                .cidade(dadosPedido.getEndereco().getCidade())
                .estado(dadosPedido.getEndereco().getEstado())
                .cep(dadosPedido.getEndereco().getCep())
                .build();
        dadosCompra = this.dadosCompraRepository.save(dadosCompra);
        return assasApi.gerarQrCodePix(valor, dadosCompra.getId().toString());
    }

    @Transactional
    public QrCodePixResponse prepararPagamentoCartao(ListaCarrinhoDto dadosCompra) throws StoreException {


        PagamentoCartaoRequest pagamento = new PagamentoCartaoRequest();

        pagamento.setCustomer("cus_000152537198");
        pagamento.setBillingType("CREDIT_CARD");
        pagamento.setInstallmentCount(1);
        pagamento.setInstallmentValue(new BigDecimal("5.00"));
        pagamento.setDueDate("2026-05-10");
        pagamento.setDescription("Mentoria Execução Trabalhista");
        pagamento.setExternalReference("37@PMS|5:1:P");

        CreditCard creditCard = new CreditCard();
        creditCard.setHolderName("Miriam Parreiras de souz");
        creditCard.setNumber("5485140879060520");
        creditCard.setExpiryMonth("11");
        creditCard.setExpiryYear("33");
        creditCard.setCcv("963");

        pagamento.setCreditCard(creditCard);

        CreditCardHolderInfo holderInfo = new CreditCardHolderInfo();
        holderInfo.setName("Miriam Parreiras de souz");
        holderInfo.setEmail("joao@email.com");
        holderInfo.setCpfCnpj("05953667671");
        holderInfo.setPostalCode("32260100");
        holderInfo.setAddressNumber("100");
        holderInfo.setPhone("31991021028");

        pagamento.setCreditCardHolderInfo(holderInfo);

        StringBuilder buffer = new StringBuilder();

        for (ItemCarrinhoRequestDto compra : dadosCompra.getCompras()) {
            buffer.append(compra.getId()).append(":")
                    .append(compra.getQuantidade())
                    .append(compra.getTamanho()).append("-");
        }

        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey("louvorportaiseternos@gmail.com");
        pixRequest.setDescription("PMS");
        pixRequest.setValue(1.0);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2045-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(false);
        pixRequest.setExternalReference(new ObjectMapper().writeValueAsString(dadosCompra));

        return assasApi.gerarQrCodePix(pixRequest);
    }
}