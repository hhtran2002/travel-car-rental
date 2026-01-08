import React from "react";
import { MapPin, Calendar, CheckCircle, XCircle } from "lucide-react";

export default function TripStatus({ trips, onAction }) {
  // Chỉ lấy các đơn đang chờ (pending)
  const pendingTrips = trips.filter((t) => t.status === "pending");

  return (
    <div className="space-y-4">
      <h2 className="text-xl font-bold text-gray-800">Yêu cầu mới</h2>
      {pendingTrips.length === 0 ? (
        <p className="text-gray-500 text-center mt-10">Hiện chưa có đơn nào.</p>
      ) : (
        pendingTrips.map((trip) => (
          <div
            key={trip.id}
            className="bg-white p-4 rounded-lg shadow border border-gray-100"
          >
            <div className="flex justify-between mb-2">
              <h3 className="font-bold">{trip.customerName}</h3>
              <span className="text-green-600 font-bold">
                {trip.price.toLocaleString()}đ
              </span>
            </div>
            <div className="text-sm text-gray-600 space-y-1 mb-4">
              <div className="flex items-center gap-2">
                <MapPin size={16} /> {trip.location}
              </div>
              <div className="flex items-center gap-2">
                <Calendar size={16} /> {trip.date}
              </div>
            </div>
            <div className="flex gap-3">
              <button
                onClick={() => onAction(trip.id, "accepted")}
                className="flex-1 bg-blue-600 text-white py-2 rounded flex justify-center items-center gap-2 hover:bg-blue-700"
              >
                <CheckCircle size={18} /> Nhận đơn
              </button>
              <button
                onClick={() => onAction(trip.id, "rejected")}
                className="flex-1 bg-red-100 text-red-600 py-2 rounded flex justify-center items-center gap-2 hover:bg-red-200"
              >
                <XCircle size={18} /> Từ chối
              </button>
            </div>
          </div>
        ))
      )}
    </div>
  );
}
