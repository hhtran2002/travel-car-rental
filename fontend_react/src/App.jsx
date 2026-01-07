// src/App.jsx
import { Routes, Route, useLocation } from "react-router-dom";
import Navbar from "./component/Navbar";
import ProtectedRoute from "./component/ProtectedRoute";

import Home from "./page/Home";
import OwnerRegisterGuide from "./page/OwnerRegisterGuide";
import Login from "./page/Login";
import Register from "./page/Register";
import ForgotPassword from "./page/ForgotPassword";
import ResetPassword from "./page/ResetPassword";
import AdminCustomers from "./page/AdminCustomers";
import NotFound from "./page/NotFound";
import Footer from "./component/Footer";

export default function App() {
  const location = useLocation();
  const backgroundLocation = location.state?.backgroundLocation;

  return (
    <div className="appShell">
      <Navbar />

      <main className="appMain">
        <div className="container">
          {/* ✅ nếu có backgroundLocation -> Routes sẽ render trang nền (Home) */}
          <Routes location={backgroundLocation || location}>
            <Route path="/" element={<Home />} />
            <Route path="/owner/register-guide" element={<OwnerRegisterGuide />} />

            {/* ✅ vẫn giữ /login để khi gõ trực tiếp URL thì nó là trang login bình thường */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route path="/reset-password" element={<ResetPassword />} />

            <Route
              path="/admin/customers"
              element={
                <ProtectedRoute allowRoles={["admin"]}>
                  <AdminCustomers />
                </ProtectedRoute>
              }
            />

            <Route path="*" element={<NotFound />} />
          </Routes>
        </div>
      </main>

      {backgroundLocation && (
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/reset-password" element={<ResetPassword />} />
        </Routes>
      )}



      <Footer />
    </div>
  );
}
