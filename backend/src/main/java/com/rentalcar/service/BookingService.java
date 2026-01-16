package com.rentalcar.service;

import com.rentalcar.dto.CreateBookingRequest;
import com.rentalcar.entity.Booking;
import com.rentalcar.entity.Driver;
import com.rentalcar.repository.BookingRepository;
import com.rentalcar.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final DriverRepository driverRepo;
    private final ContractService contractService;

    public BookingService(
            BookingRepository bookingRepo,
            DriverRepository driverRepo,
            ContractService contractService
    ) {
        this.bookingRepo = bookingRepo;
        this.driverRepo = driverRepo;
        this.contractService = contractService;
    }

    // ================= CUSTOMER TẠO BOOKING =================
    public Booking createBookingFromRequest(CreateBookingRequest req) {

        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setCarId(req.getCarId());
        booking.setDiscountId(req.getDiscountId());
        booking.setStartDate(req.getStartDate());
        booking.setEndDate(req.getEndDate());
        booking.setPickupLocation(req.getPickupLocation());
        booking.setDropoffLocation(req.getDropoffLocation());
        booking.setNote(req.getNote());

        booking.setStatus("pending");
        booking.setTripStatus("assigned");
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepo.save(booking);
    }

    // ================= CUSTOMER XEM DANH SÁCH ĐƠN =================
    public List<Booking> getCustomerBookings(Long userId) {
        return bookingRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // ================= CUSTOMER XEM CHI TIẾT ĐƠN =================
    public Booking getCustomerBookingDetail(Long bookingId, Long userId) {

        Booking booking = bookingRepo.findByBookingIdAndUserId(bookingId, userId);

        if (booking == null) {
            throw new RuntimeException("Không tìm thấy booking của user");
        }

        return booking;
    }
    

<<<<<<< HEAD
    // ADMIN XEM TẤT CẢ BOOKING
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // ================= ADMIN CONFIRM BOOKING =================
    public Booking confirmBooking(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (!"pending".equals(booking.getStatus())) {
            throw new RuntimeException("Booking không ở trạng thái pending");
        }

        booking.setStatus("confirmed");
        bookingRepo.save(booking);

        // Tạo hợp đồng
        contractService.createContractForBooking(booking);

        return booking;
    }

    // ================= ADMIN PHÂN TÀI XẾ =================
    public Booking assignDriver(Long bookingId, Long driverId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        // Validate driver tồn tại
        driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

        booking.setDriverId(driverId);
        booking.setTripStatus("assigned");

        return bookingRepo.save(booking);
    }

    // ================= DRIVER XEM DANH SÁCH CHUYẾN =================
    public List<Booking> getDriverBookings(Long driverId) {
        return bookingRepo.findByDriverId(driverId);
    }

    // ================= DRIVER NHẬN CHUYẾN =================
    public Booking driverAcceptBooking(Long bookingId, Long driverId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getDriverId() != null) {
            throw new RuntimeException("Chuyến đi đã có tài xế");
        }

        booking.setDriverId(driverId);
        booking.setTripStatus("assigned");

        return bookingRepo.save(booking);
    }

    // ================= DRIVER NHẬN XE =================
    public Booking pickupCar(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (!"confirmed".equals(booking.getStatus())) {
            throw new RuntimeException("Booking chưa được xác nhận");
        }

        if (!"assigned".equals(booking.getTripStatus())) {
            throw new RuntimeException("Không thể nhận xe");
        }

        booking.setTripStatus("in_progress");
        return bookingRepo.save(booking);
    }

    // ================= DRIVER HOÀN THÀNH CHUYẾN =================
    public Booking completeTrip(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (!"in_progress".equals(booking.getTripStatus())) {
            throw new RuntimeException("Chuyến đi chưa bắt đầu");
        }

        booking.setTripStatus("completed");
        booking.setStatus("completed");

        return bookingRepo.save(booking);
    }

    // ================= ADMIN / CUSTOMER HỦY BOOKING =================
    public Booking cancelBooking(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        booking.setStatus("cancelled");
        booking.setTripStatus("cancelled");

        return bookingRepo.save(booking);
    }
    // ================= ADMIN TRẢ XE =================
    public Booking returnCar(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (!"in_progress".equals(booking.getTripStatus())) {
            throw new RuntimeException("Chưa nhận xe thì không thể trả");
        }

        booking.setTripStatus("completed");
        booking.setStatus("completed");

        return bookingRepo.save(booking);
    }

=======
    
    private void updateDriverStatus(Long driverId, String status) {
        if (driverId != null) {
            Driver driver = driverRepository.findById(driverId).orElse(null);
            if (driver != null) {
                driver.setStatus(status);
                driverRepository.save(driver);
            }
        }
    }
    
    public List<Booking> getAllBookings() {
    
        return bookingRepository.findAll(org.springframework.data.domain.Sort.by("createdAt").descending());
    }


    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("confirmed");
        return bookingRepository.save(booking);
    }
    
>>>>>>> origin/minh
}
