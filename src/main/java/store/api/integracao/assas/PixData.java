package store.api.integracao.assas;

import lombok.Data;

@Data
public class PixData {

    private String id;
    private String transferId;
    private String endToEndIdentifier;
    private String finality;
    private Double value;
    private Double changeValue;
    private Double refundedValue;

    private String dateCreated;
    private String effectiveDate;
    private String scheduledDate;

    private String status;
    private String type;
    private String originType;
    private String conciliationIdentifier;
    private String description;
    private String transactionReceiptUrl;

    private Double chargedFeeValue;

    private boolean canBeRefunded;
    private String refundDisabledReason;
    private String refusalReason;
    private boolean canBeCanceled;

    private String originalTransaction;

    private ExternalAccount externalAccount;

    private String qrCode;

    private String payment;

    private String addressKey;
    private String addressKeyType;

    private String externalReference;
}
