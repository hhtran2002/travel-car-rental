import { Routes, Route } from "react-router-dom";
import CarList from "./pages/CarList";
import CarDetail from "./pages/CarDetail";

// Driver Imports
import DriverLayout from "./layouts/DriverLayout";
import DriverDashboard from "./pages/driver/DriverDashboard";
import TripHistory from "./pages/driver/TripHistory";
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
    </Routes>
  );
}

export default App;
