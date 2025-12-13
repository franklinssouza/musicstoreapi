package store.api.integracao.assas;

import lombok.Data;

@Data
public class CreditCard {
    private String holderName;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String ccv;
}
