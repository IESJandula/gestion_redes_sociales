import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // Dirección del backend
});

api.interceptors.request.use(async (config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
    console.log("📡 Enviando token al backend:", token);
  }
  return config;
});

export default api;