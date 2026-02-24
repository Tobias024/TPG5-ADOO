import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import api from '../api/client';

interface User {
  id: number;
  nombre: string;
  mail: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (mail: string, password: string) => Promise<void>;
  register: (data: RegisterData) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

interface RegisterData {
  nombre: string;
  mail: string;
  password: string;
  nivel?: string;
  deporteFavoritoId?: number;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(() => {
    const stored = localStorage.getItem('user');
    return stored ? JSON.parse(stored) : null;
  });
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('token'));

  useEffect(() => {
    if (token) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem('token');
    }
  }, [token]);

  useEffect(() => {
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    } else {
      localStorage.removeItem('user');
    }
  }, [user]);

  const login = async (mail: string, password: string) => {
    const res = await api.post('/auth/login', { mail, password });
    setToken(res.data.token);
    setUser({ id: res.data.id, nombre: res.data.nombre, mail: res.data.mail });
  };

  const register = async (data: RegisterData) => {
    const res = await api.post('/auth/register', data);
    setToken(res.data.token);
    setUser({ id: res.data.id, nombre: res.data.nombre, mail: res.data.mail });
  };

  const logout = () => {
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout, isAuthenticated: !!token }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within AuthProvider');
  return context;
}
