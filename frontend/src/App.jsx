import { Routes, Route } from "react-router-dom";
import CarList from "./pages/CarList";
import CarDetail from "./pages/CarDetail";

// Driver Imports
import DriverLayout from "./layouts/DriverLayout";
import DriverDashboard from "./pages/driver/DriverDashboard";
import TripHistory from "./pages/driver/TripHistory";

//admin Imports
import AdminLayout from "./layouts/AdminLayout";
import AdminDashboard from "./pages/admin/AdminDashboard";

function App() {
  return (
    <Routes>
      <Route path="/" element={<CarList />} />
      <Route path="/cars/:id" element={<CarDetail />} />

      {/* Driver Routes */}
      <Route path="/driver" element={<DriverLayout />}>
        <Route index element={<DriverDashboard />} />{" "}
        {/* Mặc định vào dashboard */}
        <Route path="history" element={<TripHistory />} />
        {/* <Route path="profile" element={<DriverProfile />} /> */}
      </Route>

      {/* Admin Routes */}
      <Route path="/admin" element={<AdminLayout />}>
        <Route index element={<AdminDashboard />} />

        {/* Các route con chờ phát triển tiếp */}
        <Route
          path="cars"
          element={
            <div className="text-gray-500 dark:text-white">
              Quản lý Xe (Coming Soon)
            </div>
          }
        />
        <Route
          path="bookings"
          element={
            <div className="text-gray-500 dark:text-white">
              Quản lý Đơn hàng (Coming Soon)
            </div>
          }
        />
        <Route
          path="customers"
          element={
            <div className="text-gray-500 dark:text-white">
              Quản lý Khách hàng (Coming Soon)
            </div>
          }
        />
      </Route>
    </Routes>
  );
}

export default App;
