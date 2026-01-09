import axiosClient from "./axiosClient";

export const adminApi = {
  // 1. Lấy thống kê/danh sách đơn hàng
  getAllBookings: () => {
    return axiosClient.get("/admin/bookings");
  },

  // 2. Lấy danh sách khách hàng (Có phân trang)
  getCustomers: (page = 0, size = 100) => {
    // Backend trả về Page<CustomerResponse>
    return axiosClient.get(`/admin/customers?page=${page}&size=${size}`);
  },

  // 3. Lấy danh sách xe (Dùng public API vì AdminCarController thiếu hàm get list)
  getAllCars: () => {
    return axiosClient.get("/cars"); // Giả định bên CarController có mapping này
  },

  // 4. Duyệt đơn hàng
  confirmBooking: (id) => {
    return axiosClient.patch(`/admin/bookings/${id}/confirm`);
  },
};
