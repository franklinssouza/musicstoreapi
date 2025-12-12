package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.config.exceptions.StoreException;
import store.api.domain.ItemCarrinho;
import store.api.domain.ItemCarrinhoRequestDto;
import store.api.domain.ListaCarrinhoDto;
import store.api.domain.ListaComprasRealizadasDto;
import store.api.integracao.QrCodePixRequest;
import store.api.integracao.assas.AssasApi;
import store.api.integracao.assas.QrCodePixResponse;
import store.api.integracao.zapi.ZapApi;
import store.api.repository.MercadoriaRepository;

import java.util.List;

@Service
public class PagamentoService {


    private final EmailSender emailSender;
    private final ZapApi zapUtil;
    private final AssasApi assasApi;
    private final ItemCarrinhoService carrinhoService;


    @Value("${assas.chavepix}")
    private String chavePixAssas;

    public PagamentoService(EmailSender emailSender, ZapApi zapUtil, AssasApi assasApi, ItemCarrinhoService carrinhoService) {
        this.emailSender = emailSender;
        this.zapUtil = zapUtil;
        this.assasApi = assasApi;
        this.carrinhoService = carrinhoService;
    }

    @Transactional
    public QrCodePixResponse prepararPagamento(ListaCarrinhoDto dadosCompra) throws StoreException {
        List<ItemCarrinho> itemCarrinhos = this.carrinhoService.create(dadosCompra);
        StringBuffer buffer = new StringBuffer();
        StringBuffer bufferIds = new StringBuffer();

        for (ItemCarrinhoRequestDto compra : dadosCompra.getCompras()) {
            buffer.append(compra.getDescricao()).append(" ");
            bufferIds.append(compra.getId()).append(":");
        }
        QrCodePixRequest pixRequest = new QrCodePixRequest();
        pixRequest.setAddressKey(chavePixAssas);
        pixRequest.setDescription(buffer.toString());
        pixRequest.setValue(1.0);
        pixRequest.setFormat("ALL");
        pixRequest.setExpirationDate("2045-05-05 14:20:50");
        pixRequest.setExpirationSeconds(null);
        pixRequest.setAllowsMultiplePayments(true);
        pixRequest.setExternalReference(bufferIds.toString());

        return assasApi.gerarQrCodePix(pixRequest);
    }
}
