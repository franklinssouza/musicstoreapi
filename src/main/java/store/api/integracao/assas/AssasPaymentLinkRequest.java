package store.api.integracao.assas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssasPaymentLinkRequest {

    @Builder.Default
    private String billingType = "UNDEFINED";

    @Builder.Default
    private String chargeType = "DETACHED";

    @Builder.Default
    private String name = "Venda de livros";

    @Builder.Default
    private String description = "Qualquer livro por apenas R$: 50,00";

    @Builder.Default
    private LocalDate endDate = LocalDate.now().plusMonths(1);

    @Builder.Default
    private Double value = 0.0;

    @Builder.Default
    private Integer dueDateLimitDays = 10;

    @Builder.Default
    private Integer maxInstallmentCount = 1;

    @Builder.Default
    private String externalReference = "";

    @Builder.Default
    private Boolean notificationEnabled = false;

    @Builder.Default
    private Boolean isAddressRequired = false;

    @Builder.Default
    private Callback callback = Callback.builder()
            .successUrl("https://portaismusic.com.br/loja/#/home?v=sell")
            .autoRedirect(true)
            .build();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Callback {

        @Builder.Default
        private String successUrl = "https://portaismusic.com.br/loja/#/home?v=sell";

        @Builder.Default
        private Boolean autoRedirect = true;
    }
}

