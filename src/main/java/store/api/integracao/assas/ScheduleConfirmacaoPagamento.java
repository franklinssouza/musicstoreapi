package store.api.integracao.assas;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import store.api.service.VendasService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduleConfirmacaoPagamento {

    private final AssasApi assasApi;
    private final VendasService vendasService;

    public ScheduleConfirmacaoPagamento(AssasApi assasApi, VendasService vendasService) {
        this.assasApi = assasApi;
        this.vendasService = vendasService;
    }

    @Scheduled(cron = "0 */1 * * * *") // a cada 1 minuto
    public void executarTarefa() {

        int offset = 0;
        int limit  = 100;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (true) {

            ConsultaPixResponse consultaPixResponse = this.assasApi.processarPagamentoPix(offset, limit);

            if (consultaPixResponse == null || consultaPixResponse.getData() == null) {
                break;
            }

            for (PixData data : consultaPixResponse.getData()) {

                LocalDateTime dateCreated = LocalDateTime.parse(data.getEffectiveDate(), formatter);
                boolean isToday = dateCreated.toLocalDate().isEqual(LocalDate.now());

                if (isToday) {
                    this.vendasService.registrarVendaPorToken(data.getId(), data.getExternalReference());
                }
            }

            int totalCount = consultaPixResponse.getTotalCount();
            offset += limit;
            if (offset >= totalCount) {
                break;
            }
        }
    }
}
