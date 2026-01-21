package store.api.integracao.assas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String object;
    private String id;
    private LocalDate dateCreated;
    private String customer;
    private String checkoutSession;
    private String paymentLink;

    private BigDecimal value;
    private BigDecimal netValue;
    private BigDecimal originalValue;
    private BigDecimal interestValue;

    private String description;
    private String billingType;
    private LocalDate confirmedDate;

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
    private String escrow;
    private Object refunds;

    // =======================
    // Nested DTOs
    // =======================

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

