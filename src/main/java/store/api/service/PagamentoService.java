package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.ItemCarrinhoRequestDto;
import store.api.domain.ListaCarrinhoDto;
import store.api.integracao.assas.*;
import store.api.integracao.zapi.ZapApi;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Service
public class PagamentoService {


    private final EmailSender emailSender;
    private final ZapApi zapUtil;
    private final AssasApi assasApi;

    @Value("${assas.chavepix}")
    private String chavePixAssas;

    public PagamentoService(EmailSender emailSender, ZapApi zapUtil, AssasApi assasApi ) {
        this.emailSender = emailSender;
        this.zapUtil = zapUtil;
        this.assasApi = assasApi;
    }

    @Transactional
    public QrCodePixResponse prepararPagamentoPix(ListaCarrinhoDto dadosCompra) throws StoreException {
        StringBuilder buffer = new StringBuilder();
        double valor = 0;
        for (ItemCarrinhoRequestDto compra : dadosCompra.getCompras()) {
            buffer.append(compra.getId()).append(":")
                    .append(compra.getQuantidade()).append(":")
                    .append(compra.getTamanho()).append("-");
            valor+=compra.getValor();
        }

        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey(chavePixAssas);
        pixRequest.setDescription("PMS");
        pixRequest.setValue(valor);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2045-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(false);
        pixRequest.setExternalReference(dadosCompra.getIdUsuario() + "@PMS|"+buffer.toString().substring(0, buffer.length()-1));

        return assasApi.gerarQrCodePix(pixRequest);
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
        pixRequest.setAddressKey(chavePixAssas);
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