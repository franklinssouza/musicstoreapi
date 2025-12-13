package store.api.integracao.assas;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoCartaoRequest {

    private String customer;
    private String billingType;
    private Integer installmentCount;
    private BigDecimal installmentValue;
    private String dueDate;
    private String description;
    private String externalReference;
    private CreditCard creditCard;
    private CreditCardHolderInfo creditCardHolderInfo;
}
