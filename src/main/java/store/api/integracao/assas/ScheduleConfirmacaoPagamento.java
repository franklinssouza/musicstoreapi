package store.api.integracao.assas;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import store.api.service.VendasService;

@Component
public class ScheduleConfirmacaoPagamento {

    private final AssasApi assasApi;
    private final VendasService vendasService;


    public ScheduleConfirmacaoPagamento(AssasApi assasApi, VendasService vendasService) {
        this.assasApi = assasApi;
        this.vendasService = vendasService;
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void executarTarefa() {
        System.out.println("Tarefa executada a cada 5 minutos!");
    }
}
