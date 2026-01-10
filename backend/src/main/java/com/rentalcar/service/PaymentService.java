package com.rentalcar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentalcar.dto.PaymentRequest;
import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Contract;
import com.rentalcar.entity.Payment;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.repository.ContractRepository;
import com.rentalcar.repository.PaymentRepository;

@Service
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(BookingRepository bookingRepository,
                          ContractRepository contractRepository,
                          PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.contractRepository = contractRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment pay(Long userId, PaymentRequest request) {

        Booking booking = bookingRepository
                .findByBookingIdAndUserId(request.getBookingId(), userId);

        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        if (!"completed".equals(booking.getStatus())) {
            throw new RuntimeException("Booking chưa hoàn tất");
        }

        Contract contract = contractRepository
                .findByBookingId(booking.getBookingId())
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (!"completed".equals(contract.getStatus())) {
            throw new RuntimeException("Contract chưa hoàn tất");
        }

        Payment payment = new Payment();
        payment.setContractId(contract.getContractId());
        payment.setAmount(contract.getTotalPrice());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("completed");

        paymentRepository.save(payment);

        contract.setStatus("paid");
        contractRepository.save(contract);

        return payment;
    }

}
