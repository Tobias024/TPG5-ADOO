import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useEffect, useState } from 'react';
import api from '../api/client';

export default function Navbar() {
  const { user, logout } = useAuth();
  const location = useLocation();
  const [unread, setUnread] = useState(0);

  useEffect(() => {
    api.get('/notificaciones/no-leidas').then(res => setUnread(res.data.count)).catch(() => {});
    const interval = setInterval(() => {
      api.get('/notificaciones/no-leidas').then(res => setUnread(res.data.count)).catch(() => {});
    }, 15000);
    return () => clearInterval(interval);
  }, []);

  const navLink = (to: string, label: string) => (
    <Link
      to={to}
      className={`px-3 py-2 rounded-md text-sm font-medium transition-colors ${
        location.pathname === to
          ? 'bg-primary-700 text-white'
          : 'text-primary-100 hover:bg-primary-600 hover:text-white'
      }`}
    >
      {label}
    </Link>
  );

  return (
    <nav className="bg-primary-800 fixed top-0 left-0 right-0 z-50 shadow-lg">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center space-x-4">
            <Link to="/" className="text-white font-bold text-xl tracking-tight">
              Uno Mas
            </Link>
            {navLink('/', 'Dashboard')}
            {navLink('/partidos', 'Partidos')}
            {navLink('/partidos/nuevo', 'Crear Partido')}
          </div>
          <div className="flex items-center space-x-4">
            <Link
              to="/notificaciones"
              className="relative text-primary-100 hover:text-white transition-colors"
            >
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
              </svg>
              {unread > 0 && (
                <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
                  {unread > 9 ? '9+' : unread}
                </span>
              )}
            </Link>
            <span className="text-primary-200 text-sm">{user?.nombre}</span>
            <button
              onClick={logout}
              className="bg-primary-600 hover:bg-primary-500 text-white px-3 py-1.5 rounded text-sm transition-colors"
            >
              Salir
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
