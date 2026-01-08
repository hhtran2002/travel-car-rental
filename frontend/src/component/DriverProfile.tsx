import React from "react";

export default function DriverProfile({ profile, onUpdate }) {
  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    onUpdate({
      name: formData.get("name"),
      phone: formData.get("phone"),
      licenseNumber: formData.get("license"),
    });
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-bold mb-4">Thông tin tài xế</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Họ tên
          </label>
          <input
            name="name"
            defaultValue={profile.name}
            className="mt-1 w-full p-2 border rounded focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Số điện thoại
          </label>
          <input
            name="phone"
            defaultValue={profile.phone}
            className="mt-1 w-full p-2 border rounded focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Biển số xe / Model
          </label>
          <input
            disabled
            value={profile.vehicleModel}
            className="mt-1 w-full p-2 bg-gray-100 border rounded text-gray-500"
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 font-medium"
        >
          Lưu thay đổi
        </button>
      </form>
    </div>
  );
}
