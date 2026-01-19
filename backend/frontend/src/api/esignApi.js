import axiosClient from "./axiosClient";

export const esignApi = {
  sign: (file) => {
    const fd = new FormData();
    fd.append("file", file);

    return axiosClient.post("/esign/sign", fd, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
};
