import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Constants from 'expo-constants';

const baseURL = (Constants.expoConfig?.extra?.apiUrl as string) ?? 'http://localhost:8080';

const api = axios.create({ baseURL, timeout: 15000 });

api.interceptors.request.use(async (config) => {
  const token = await AsyncStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message = error.response?.data?.error ?? error.message ?? 'Error desconocido';
    return Promise.reject(new Error(message));
  }
);

export default api;
