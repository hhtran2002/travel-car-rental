// frontend/src/layouts/AdminLayout.jsx
import { Outlet, Link, useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { logout as doLogout } from "../auth";

const AdminLayout = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const [theme, setTheme] = useState(localStorage.getItem("theme") || "dark");

  useEffect(() => {
    const root = window.document.documentElement;
    if (theme === "dark") root.classList.add("dark");
    else root.classList.remove("dark");
    localStorage.setItem("theme", theme);
  }, [theme]);

  const toggleTheme = () =>
    setTheme((prev) => (prev === "dark" ? "light" : "dark"));

  const handleLogout = () => {
    if (!window.confirm("Bạn muốn đăng xuất khỏi trang Admin?")) return;
    doLogout();
    navigate("/", { replace: true });
  };

  const MenuItem = ({ to, label, icon }) => {
    const isActive =
      location.pathname === to || location.pathname.startsWith(to + "/");

    return (
      <Link
        to={to}
        className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-all duration-200 mb-1
        ${
          isActive
            ? "bg-[#00FF00] text-black font-bold shadow-lg shadow-green-500/20"
            : "text-gray-400 hover:bg-gray-800 hover:text-white"
        }`}
      >
        <span className="text-xl">{icon}</span>
        <span className="font-medium">{label}</span>
      </Link>
    );
  };

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-[#0f0f0f] flex transition-colors duration-300 font-sans">
      <aside className="w-64 bg-white dark:bg-[#121212] border-r border-gray-200 dark:border-gray-800 flex-shrink-0 fixed h-full z-20 hidden md:block transition-colors">
        <div className="h-16 flex items-center justify-center border-b border-gray-200 dark:border-gray-800">
          <h1 className="text-2xl font-black tracking-tighter text-gray-800 dark:text-white">
            RENTA<span className="text-[#00FF00]">CAR</span>
            <span className="text-xs ml-1 bg-gray-200 dark:bg-gray-700 px-1 rounded text-gray-500 dark:text-gray-300">
              ADMIN
            </span>
          </h1>
        </div>

        <nav className="p-4 mt-4">
          <div className="text-xs font-bold text-gray-400 uppercase mb-2 pl-2 tracking-wider">
            Tổng quan
          </div>
          <MenuItem to="/admin" label="Dashboard" icon="📊" />

          <div className="text-xs font-bold text-gray-400 uppercase mb-2 pl-2 tracking-wider mt-6">
            Quản lý
          </div>
          <MenuItem to="/admin/cars" label="Xe & Kho bãi" icon="🚗" />
          <MenuItem to="/admin/bookings" label="Đơn đặt xe" icon="📅" />
          <MenuItem to="/admin/customers" label="Khách hàng" icon="👥" />
          <MenuItem to="/admin/contracts" label="Hợp đồng" icon="📝" />
          <MenuItem to="/admin/kyc" label="Duyệt giấy tờ (KYC)" icon="🪪" />
          <MenuItem to="/admin/documents" label="Kiểm tra giấy tờ" icon="🪪" />
          <MenuItem to="/admin/esign" label="Ký số (eSign)" icon="✍️" />
          <MenuItem
            to="/admin/vehicle-registration"
            label="OCR Cavet xe"
            icon="🧾"
          />

          <div className="text-xs font-bold text-gray-400 uppercase mb-2 pl-2 tracking-wider mt-6">
            Hệ thống
          </div>
          <MenuItem to="/admin/settings" label="Cài đặt" icon="⚙️" />
        </nav>

        <div className="absolute bottom-0 w-full p-4 border-t border-gray-200 dark:border-gray-800 bg-gray-50 dark:bg-[#121212]">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-[#00FF00] to-blue-500 flex items-center justify-center text-black font-bold">
              AD
            </div>
            <div>
              <p className="text-sm font-bold text-gray-700 dark:text-white">
                Admin User
              </p>
              <p className="text-xs text-gray-500">Super Admin</p>
            </div>
          </div>
        </div>
      </aside>

      <div className="flex-1 md:ml-64 flex flex-col min-h-screen">
        <header className="h-16 bg-white/80 dark:bg-[#121212]/80 backdrop-blur-md sticky top-0 z-10 border-b border-gray-200 dark:border-gray-800 px-6 flex items-center justify-between">
          <h2 className="font-bold text-lg text-gray-700 dark:text-gray-200">
            Quản trị hệ thống
          </h2>

          <div className="flex items-center gap-4">
            <button
              onClick={toggleTheme}
              className="p-2 rounded-full bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-yellow-400 hover:scale-110 transition"
            >
              {theme === "dark" ? "☀️" : "🌙"}
            </button>

            <button
              onClick={handleLogout}
              className="px-4 py-2 text-sm font-bold text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition"
            >
              Đăng xuất
            </button>
          </div>
        </header>

        {/* ✅ thêm relative z-0 để tránh overlay lạ */}
        <main className="p-6 relative z-0">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default AdminLayout;
