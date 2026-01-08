import React, { useState } from "react";
import { User, SteeringWheel, History } from "lucide-react";
import TripStatus from "./TripStatus";
import TripHistory from "./TripHistory";
import DriverProfile from "./DriverProfile";

// 1. Định nghĩa kiểu dữ liệu (Interface)
export interface Trip {
  id: string;
  customerName: string;
  location: string;
  date: string;
  price: number;
  status: "pending" | "accepted" | "rejected" | "completed" | "cancelled";
}

export interface DriverProfileType {
  name: string;
  phone: string;
  licenseNumber: string;
  vehicleModel: string;
}

export default function DriverDashboard() {
  const [activeTab, setActiveTab] = useState<"status" | "history" | "profile">(
    "status"
  );

  // 2. Sử dụng kiểu dữ liệu cho State
  const [profile, setProfile] = useState<DriverProfileType>({
    name: "Nguyễn Văn Tài",
    phone: "0909123456",
    licenseNumber: "B2-987654",
    vehicleModel: "VinFast VF8",
  });

  const [trips, setTrips] = useState<Trip[]>([
    {
      id: "T001",
      customerName: "Trần Thị B",
      location: "Sân bay TSN -> Quận 1",
      date: "20/01 14:00",
      price: 500000,
      status: "pending",
    },
    {
      id: "T002",
      customerName: "Lê Văn C",
      location: "Landmark 81 -> Vũng Tàu",
      date: "21/01 08:00",
      price: 1500000,
      status: "pending",
    },
    {
      id: "T003",
      customerName: "Phạm D",
      location: "Quận 7 -> Quận 2",
      date: "15/01 09:00",
      price: 300000,
      status: "completed",
    },
  ]);

  const handleTripAction = (id: string, action: "accepted" | "rejected") => {
    setTrips(trips.map((t) => (t.id === id ? { ...t, status: action } : t)));
  };

  const handleUpdateProfile = (newProfile: Partial<DriverProfileType>) => {
    setProfile({ ...profile, ...newProfile });
    alert("Đã cập nhật thông tin!");
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col pb-20">
      <header className="bg-blue-600 text-white p-4 shadow sticky top-0 z-10">
        <h1 className="text-lg font-bold">Tài xế: {profile.name}</h1>
      </header>

      <main className="flex-1 p-4">
        {activeTab === "status" && (
          // Lưu ý: File TripStatus cũng nên đổi thành .tsx để nhận props đúng kiểu
          <TripStatus trips={trips} onAction={handleTripAction} />
        )}
        {activeTab === "history" && <TripHistory trips={trips} />}
        {activeTab === "profile" && (
          <DriverProfile profile={profile} onUpdate={handleUpdateProfile} />
        )}
      </main>

      <nav className="fixed bottom-0 w-full bg-white border-t flex justify-around py-3 z-20">
        <button
          onClick={() => setActiveTab("status")}
          className={`flex flex-col items-center ${
            activeTab === "status" ? "text-blue-600" : "text-gray-400"
          }`}
        >
          <SteeringWheel size={24} />
          <span className="text-xs mt-1">Chuyến đi</span>
        </button>
        <button
          onClick={() => setActiveTab("history")}
          className={`flex flex-col items-center ${
            activeTab === "history" ? "text-blue-600" : "text-gray-400"
          }`}
        >
          <History size={24} />
          <span className="text-xs mt-1">Lịch sử</span>
        </button>
        <button
          onClick={() => setActiveTab("profile")}
          className={`flex flex-col items-center ${
            activeTab === "profile" ? "text-blue-600" : "text-gray-400"
          }`}
        >
          <User size={24} />
          <span className="text-xs mt-1">Cá nhân</span>
        </button>
      </nav>
    </div>
  );
}
