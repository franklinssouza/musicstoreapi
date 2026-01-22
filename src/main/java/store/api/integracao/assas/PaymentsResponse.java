package store.api.integracao.assas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsResponse {

    private String object;
    private Boolean hasMore;
    private Integer totalCount;
    private Integer limit;
    private Integer offset;
    private List<Payment> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {

        private String object;
        private String id;
        private LocalDate dateCreated;
        private String customer;
        private Object checkoutSession;
        private String paymentLink;

        private BigDecimal value;
        private BigDecimal netValue;
        private BigDecimal originalValue;
        private BigDecimal interestValue;

        private String description;
        private String billingType;
        private String confirmedDate;

        private CreditCard creditCard;
        private String pixTransaction;
        private String pixQrCodeId;

        private String status;
        private LocalDate dueDate;
        private LocalDate originalDueDate;
        private LocalDate paymentDate;
        private LocalDate clientPaymentDate;

        private Integer installmentNumber;

        private String invoiceUrl;
        private String invoiceNumber;
        private String externalReference;

        private Boolean deleted;
        private Boolean anticipated;
        private Boolean anticipable;

        private LocalDate creditDate;
        private LocalDate estimatedCreditDate;

        private String transactionReceiptUrl;
        private String nossoNumero;
        private String bankSlipUrl;

        private LocalDate lastInvoiceViewedDate;
        private LocalDate lastBankSlipViewedDate;

        private Discount discount;
        private Fine fine;
        private Interest interest;

        private Boolean postalService;
        private Object escrow;
        private Object refunds;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditCard {
        private String creditCardNumber;
        private String creditCardBrand;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {
        private BigDecimal value;
        private LocalDate limitDate;
        private Integer dueDateLimitDays;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fine {
        private BigDecimal value;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Interest {
        private BigDecimal value;
        private String type;
    }
}

