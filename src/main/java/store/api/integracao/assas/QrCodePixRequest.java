package store.api.integracao;

import lombok.Data;

@Data
public class QrCodePixRequest {

    private String addressKey;
    private String description;
    private Double value;
    private String format;
    private String expirationDate;
    private Integer expirationSeconds; // null → Integer
    private Boolean allowsMultiplePayments;
    private String externalReference; // null → String

}
