//package com.rentalcar.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.rentalcar.dto.BookingRequest;
//import com.rentalcar.entity.Booking;
//import com.rentalcar.entity.Contract;
//import com.rentalcar.repository.BookingRepository;
//import com.rentalcar.repository.ContractRepository;
//
//@Service
//public class CustomerBookingService {
//
//    private final BookingRepository bookingRepository;
//    private final ContractRepository contractRepository;
//
//    public CustomerBookingService(BookingRepository bookingRepository,
//                                  ContractRepository contractRepository) {
//        this.bookingRepository = bookingRepository;
//        this.contractRepository = contractRepository;
//    }
//
//    // ===================== 1. ĐẶT XE =====================
//    // Tự lái (driverId = null) hoặc thuê tài xế (driverId != null)
//    @Transactional
//    public Booking createBooking(Long userId, BookingRequest request) {
//
//        Booking booking = new Booking();
//
//        // userId lấy từ JWT
//        booking.setUserId(userId);
//        booking.setCarId(request.getCarId());
//        booking.setDriverId(request.getDriverId());
//        booking.setDiscountId(request.getDiscountId());
//
//        booking.setStartDate(request.getStartDate());
//        booking.setEndDate(request.getEndDate());
//        booking.setPickupLocation(request.getPickupLocation());
//        booking.setDropoffLocation(request.getDropoffLocation());
//        booking.setNote(request.getNote());
//
//        booking.setStatus("pending");
//        booking.setTripStatus("assigned");
//        booking.setCreatedAt(LocalDateTime.now());
//
//        return bookingRepository.save(booking);
//    }
//
//    // ===================== 2. XEM DANH SÁCH ĐƠN THUÊ =====================
//    public List<Booking> getMyBookings(Long userId) {
//        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
//    }
//
//    // ===================== 3. XEM CHI TIẾT ĐƠN THUÊ =====================
//    public Booking getBookingDetail(Long bookingId, Long userId) {
//
//        Booking booking = bookingRepository
//                .findByBookingIdAndUserId(bookingId, userId);
//
//        if (booking == null) {
//            throw new RuntimeException("Booking not found or access denied");
//        }
//
//        return booking;
//    }
//
//    // ===================== 4. HỦY ĐƠN THUÊ =====================
//    @Transactional
//    public Booking cancelBooking(Long bookingId, Long userId) {
//
//        Booking booking = bookingRepository
//                .findByBookingIdAndUserId(bookingId, userId);
//
//        if (booking == null) {
//            throw new RuntimeException("Booking not found or access denied");
//        }
//
//        // chỉ cho huỷ khi chưa nhận xe
//        if (!"pending".equals(booking.getStatus())) {
//            throw new RuntimeException("Cannot cancel this booking");
//        }
//
//        booking.setStatus("cancelled");
//        booking.setTripStatus("cancelled");
//
//        return bookingRepository.save(booking);
//    }
//
//    // ===================== 5. NHẬN XE =====================
//    @Transactional
//    public Booking receiveCar(Long bookingId, Long userId) {
//
//        Booking booking = bookingRepository
//                .findByBookingIdAndUserId(bookingId, userId);
//
//        if (booking == null) {
//            throw new RuntimeException("Booking not found");
//        }
//
//        if (!"assigned".equals(booking.getTripStatus())) {
//            throw new RuntimeException("Cannot receive car");
//        }
//
//        booking.setTripStatus("in_progress");
//        bookingRepository.save(booking);
//
//        // ⭐ TẠO CONTRACT NẾU CHƯA CÓ
//        contractRepository.findByBookingId(bookingId)
//                .orElseGet(() -> {
//                    Contract contract = new Contract();
//                    contract.setBookingId(bookingId);
//                    contract.setCreatedDate(LocalDateTime.now());
//                    contract.setStatus("active");
//                    contract.setSignedBy("customer");
//                    return contractRepository.save(contract);
//                });
//
//        return booking;
//    }
//
//    // ===================== 6. TRẢ XE =====================
//    @Transactional
//    public Booking returnCar(Long bookingId, Long userId) {
//
//        Booking booking = bookingRepository
//                .findByBookingIdAndUserId(bookingId, userId);
//
//        if (booking == null) {
//            throw new RuntimeException("Booking not found");
//        }
//
//        if (!"in_progress".equals(booking.getTripStatus())) {
//            throw new RuntimeException("Cannot return car");
//        }
//
//        booking.setTripStatus("completed");
//        booking.setStatus("completed");
//        bookingRepository.save(booking);
//
//        Contract contract = contractRepository.findByBookingId(bookingId)
//                .orElseThrow(() -> new RuntimeException("Contract not found"));
//
//        contract.setReturnDate(LocalDateTime.now());
//
//        long days = java.time.temporal.ChronoUnit.DAYS.between(
//                booking.getStartDate(),
//                booking.getEndDate()
//        );
//        if (days <= 0) days = 1;
//
//        long pricePerDay = 1_000_000;
//        contract.setTotalPrice(days * pricePerDay);
//        contract.setStatus("completed");
//
//        contractRepository.save(contract);
//
//        return booking;
//    }
//
//}
