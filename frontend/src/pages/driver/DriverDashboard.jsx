// frontend/src/pages/driver/DriverDashboard.jsx
import { useState, useEffect } from "react";
import { driverApi } from "../../api/driverApi";

const DriverDashboard = () => {
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(true);

  // Giả lập data để bạn test giao diện ngay (Sau này xóa đi dùng API thật)
  const mockTrips = [
    {
      id: 1,
      customerName: "Nguyễn Văn A",
      route: "HCM - Vũng Tàu",
      date: "2024-01-10",
      status: "ASSIGNED",
      price: "2.500.000",
    },
    {
      id: 2,
      customerName: "Trần Thị B",
      route: "Sân bay TSN - Quận 1",
      date: "2024-01-09",
      status: "IN_PROGRESS",
      price: "500.000",
    },
  ];

  useEffect(() => {
    // Khi có API thì bỏ comment dòng dưới
    // loadTrips();
    setTrips(mockTrips);
    setLoading(false);
  }, []);

  const loadTrips = async () => {
    try {
      const data = await driverApi.getMyTrips();
      setTrips(data);
    } catch (error) {
      console.error("Lỗi tải chuyến đi:", error);
    }
  };

  const handleStatusChange = async (id, newStatus) => {
    if (
      !window.confirm(`Bạn chắc chắn muốn đổi trạng thái thành ${newStatus}?`)
    )
      return;

    // Gọi API update (khi có backend)
    // await driverApi.updateTripStatus(id, newStatus);

    // Update State giả lập
    setTrips((prev) =>
      prev.map((t) => (t.id === id ? { ...t, status: newStatus } : t))
    );
  };

  if (loading)
    return (
      <div className="text-[#00FF00] text-center mt-10">
        Đang tải dữ liệu...
      </div>
    );

  const newRequests = trips.filter((t) => t.status === "ASSIGNED");
  const activeTrips = trips.filter(
    (t) => t.status === "IN_PROGRESS" || t.status === "CONFIRMED"
  );

  return (
    <div className="space-y-8">
      {/* Section 1: Chuyến đi hiện tại (Quan trọng nhất) */}
      <section>
        <h2 className="text-xl font-bold mb-4 flex items-center text-[#00FF00]">
          <span className="w-2 h-2 bg-[#00FF00] rounded-full mr-2 animate-pulse"></span>
          CHUYẾN ĐI HIỆN TẠI
        </h2>

        {activeTrips.length === 0 ? (
          <p className="text-gray-500 italic">
            Bạn đang rảnh rỗi, chưa có chuyến nào đang chạy.
          </p>
        ) : (
          <div className="grid gap-4">
            {activeTrips.map((trip) => (
              <div
                key={trip.id}
                className="bg-[#1e1e1e] p-5 rounded-xl border border-gray-700 shadow-lg relative overflow-hidden"
              >
                <div className="absolute top-0 left-0 w-1 h-full bg-[#00FF00]"></div>
                <div className="flex justify-between items-start mb-3">
                  <div>
                    <h3 className="text-lg font-bold text-white">
                      {trip.customerName}
                    </h3>
                    <p className="text-sm text-gray-400">{trip.route}</p>
                  </div>
                  <span className="bg-yellow-600/20 text-yellow-500 text-xs px-2 py-1 rounded border border-yellow-600">
                    {trip.status}
                  </span>
                </div>

                <div className="mt-4 flex gap-3">
                  <button
                    onClick={() => handleStatusChange(trip.id, "COMPLETED")}
                    className="flex-1 bg-[#00FF00] text-black font-bold py-3 rounded hover:opacity-90 transition"
                  >
                    HOÀN THÀNH CHUYẾN
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </section>

      <hr className="border-gray-800" />

      {/* Section 2: Yêu cầu mới */}
      <section>
        <h2 className="text-xl font-bold mb-4 text-white">
          YÊU CẦU MỚI ({newRequests.length})
        </h2>
        <div className="grid gap-4">
          {newRequests.map((trip) => (
            <div
              key={trip.id}
              className="bg-[#2a2a2a] p-5 rounded-xl border border-gray-700"
            >
              <div className="flex justify-between mb-2">
                <span className="text-sm text-[#00FF00]">{trip.date}</span>
                <span className="font-mono font-bold">{trip.price}</span>
              </div>
              <h3 className="text-lg font-bold text-white">{trip.route}</h3>
              <p className="text-gray-400 text-sm mb-4">
                Khách: {trip.customerName}
              </p>

              <div className="grid grid-cols-2 gap-3">
                <button
                  onClick={() => handleStatusChange(trip.id, "CONFIRMED")}
                  className="bg-transparent border border-[#00FF00] text-[#00FF00] py-2 rounded font-bold hover:bg-[#00FF00] hover:text-black transition"
                >
                  NHẬN
                </button>
                <button
                  onClick={() => handleStatusChange(trip.id, "REJECTED")}
                  className="bg-gray-700 text-gray-300 py-2 rounded hover:bg-red-900 hover:text-red-200 transition"
                >
                  TỪ CHỐI
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default DriverDashboard;
