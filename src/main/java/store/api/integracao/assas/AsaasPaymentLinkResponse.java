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
public class AsaasPaymentLinkResponse {

    private String id;
    private String name;
    private Integer value;
    private Boolean active;
    private String chargeType;
    private String url;
    private String billingType;
    private String subscriptionCycle;
    private String description;
    private LocalDate endDate;
    private Boolean deleted;
    private Integer viewCount;
    private Integer maxInstallmentCount;
    private Integer dueDateLimitDays;
    private Boolean notificationEnabled;
    private Boolean isAddressRequired;
    private String externalReference;
}

