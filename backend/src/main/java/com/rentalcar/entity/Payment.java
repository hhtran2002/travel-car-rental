package com.rentalcar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    private Long amount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // cash | online

    @Column(nullable = false)
    private String status; // pending | completed | failed | refunded

    public Payment() {}

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getContractId() {
        return contractId;
    }

    public Long getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
