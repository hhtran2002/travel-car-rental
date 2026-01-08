import React from "react";

export default function TripHistory({ trips }) {
  // Lọc ra các chuyến đã hoàn thành, hủy hoặc đã nhận
  const history = trips.filter((t) => t.status !== "pending");

  return (
    <div className="space-y-4">
      <h2 className="text-xl font-bold text-gray-800">Lịch sử hoạt động</h2>
      {history.map((trip) => (
        <div
          key={trip.id}
          className="bg-white p-4 rounded-lg shadow-sm border flex justify-between items-center"
        >
          <div>
            <p className="font-semibold text-gray-800">{trip.date}</p>
            <p className="text-sm text-gray-600">{trip.location}</p>
          </div>
          <div className="text-right">
            <span
              className={`px-2 py-1 rounded text-xs font-bold 
              ${
                trip.status === "completed"
                  ? "bg-green-100 text-green-700"
                  : trip.status === "accepted"
                  ? "bg-blue-100 text-blue-700"
                  : "bg-red-100 text-red-700"
              }`}
            >
              {trip.status.toUpperCase()}
            </span>
            <p className="font-bold mt-1">{trip.price.toLocaleString()}đ</p>
          </div>
        </div>
      ))}
    </div>
  );
}
