package com.rentalcar.dto;

public class PaymentRequest {

    private Long bookingId;
    private String paymentMethod; // cash | online

    public PaymentRequest() {}

    public Long getBookingId() {
        return bookingId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
