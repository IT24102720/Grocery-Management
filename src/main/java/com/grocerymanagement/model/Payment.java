package com.grocerymanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment implements Serializable {
    private String paymentId;
    private Order order;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private LocalDateTime paymentDate;

    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        NET_BANKING,
        DIGITAL_WALLET
    }

    public enum PaymentStatus {
        PENDING,
        SUCCESSFUL,
        FAILED,
        REFUNDED
    }

}
