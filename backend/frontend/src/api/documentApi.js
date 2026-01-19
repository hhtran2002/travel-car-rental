// frontend/src/api/documentApi.js
import axiosClient from "./axiosClient";

export const documentApi = {
  scanVehicleRegistration: (file) => {
    const fd = new FormData();
    fd.append("image", file);

    return axiosClient.post("/ocr/vehicle-registration", fd, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  scanDriverLicense: (file) => {
    const fd = new FormData();
    fd.append("image", file);

    return axiosClient.post("/ocr/driver-license", fd, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  // ✅ Lưu thông tin cavet vào DB
  saveVehicleRegistration: (payload) => {
    return axiosClient.post("/docs/vehicle-registration", payload);
  },
};
