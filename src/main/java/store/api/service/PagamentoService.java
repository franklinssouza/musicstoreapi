package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.ItemCarrinhoRequestDto;
import store.api.domain.ListaCarrinhoDto;
import store.api.integracao.QrCodePixRequest;
import store.api.integracao.assas.AssasApi;
import store.api.integracao.assas.QrCodePixResponse;
import store.api.integracao.zapi.ZapApi;
import tools.jackson.databind.ObjectMapper;

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

        for (ItemCarrinhoRequestDto compra : dadosCompra.getCompras()) {
            buffer.append(compra.getId()).append(":")
                    .append(compra.getQuantidade()).append(":")
                    .append(compra.getTamanho()).append("-");
        }

        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey(chavePixAssas);
        pixRequest.setDescription(buffer.toString());
        pixRequest.setValue(1.0);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2045-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(false);
        pixRequest.setExternalReference(buffer.toString());

        return assasApi.gerarQrCodePix(pixRequest);
    }

    @Transactional
    public QrCodePixResponse prepararPagamentoCartao(ListaCarrinhoDto dadosCompra) throws StoreException {
        StringBuilder buffer = new StringBuilder();

        for (ItemCarrinhoRequestDto compra : dadosCompra.getCompras()) {
            buffer.append(compra.getId()).append(":")
                    .append(compra.getQuantidade())
                    .append(compra.getTamanho()).append("-");
        }

        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey(chavePixAssas);
        pixRequest.setDescription(buffer.toString());
        pixRequest.setValue(1.0);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2045-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(false);
        pixRequest.setExternalReference(new ObjectMapper().writeValueAsString(dadosCompra));

        return assasApi.gerarQrCodePix(pixRequest);
    }

}
