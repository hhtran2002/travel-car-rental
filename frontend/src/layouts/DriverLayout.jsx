// frontend/src/layouts/DriverLayout.jsx
import { Outlet, Link, useLocation } from "react-router-dom";

const DriverLayout = () => {
  const location = useLocation();

  // Hàm check active link để tô màu xanh Neon
  const isActive = (path) =>
    location.pathname === path
      ? "text-[#00FF00] border-b-2 border-[#00FF00]"
      : "text-gray-400 hover:text-white";

  return (
    <div className="min-h-screen bg-[#121212] text-white font-sans">
      {/* Header riêng cho Driver */}
      <header className="fixed top-0 w-full z-50 bg-[#1e1e1e] border-b border-gray-800 shadow-lg">
        <div className="max-w-4xl mx-auto px-4 h-16 flex items-center justify-between">
          <div className="font-bold text-xl tracking-wider">
            DRIVER <span className="text-[#00FF00]">APP</span>
          </div>

          <nav className="flex gap-6 font-medium">
            <Link
              to="/driver"
              className={`py-4 transition-colors ${isActive("/driver")}`}
            >
              Công việc
            </Link>
            <Link
              to="/driver/history"
              className={`py-4 transition-colors ${isActive(
                "/driver/history"
              )}`}
            >
              Lịch sử
            </Link>
            <Link
              to="/driver/profile"
              className={`py-4 transition-colors ${isActive(
                "/driver/profile"
              )}`}
            >
              Tài khoản
            </Link>
          </nav>
        </div>
      </header>

      {/* Nội dung chính */}
      <main className="pt-20 pb-10 px-4 max-w-4xl mx-auto">
        <Outlet />
      </main>
    </div>
  );
};

export default DriverLayout;
