package store.api.integracao.assas;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConfirmacaoPagamento {
    @Scheduled(cron = "0 */2 * * * *")
    public void executarTarefa() {
        System.out.println("Tarefa executada a cada 5 minutos!");
    }
}
