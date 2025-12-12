package store.api.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import store.api.EmailSender;
import store.api.domain.ListaComprasDto;
import store.api.domain.ListaComprasRealizadasDto;
import store.api.integracao.assas.AssasApi;
import store.api.integracao.zapi.ZapApi;
import store.api.repository.MercadoriaRepository;
import store.api.repository.UsuarioRepository;

import java.util.List;

@Service
public class PagamentoService {
    private final MercadoriaRepository mercadoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailSender emailSender;
    private final ZapApi zapUtil;
    private final AssasApi assasApi;
    public PagamentoService(MercadoriaRepository mercadoriaRepository, UsuarioRepository usuarioRepository, EmailSender emailSender, ZapApi zapUtil, AssasApi assasApi) {
        this.mercadoriaRepository = mercadoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailSender = emailSender;
        this.zapUtil = zapUtil;
        this.assasApi = assasApi;
    }
    @Transactional
    public ListaComprasRealizadasDto realizarPagamento(ListaComprasDto dadosCompra){
        return ListaComprasRealizadasDto.builder().build();
    }
}
