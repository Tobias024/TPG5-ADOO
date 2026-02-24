import { useState, useEffect } from 'react';
import api from '../api/client';

interface Notificacion {
  id: number;
  mensaje: string;
  tipo: string;
  fechaEnvio: string;
  leida: boolean;
}

export default function Notifications() {
  const [notificaciones, setNotificaciones] = useState<Notificacion[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get('/notificaciones').then(res => setNotificaciones(res.data)).finally(() => setLoading(false));
  }, []);

  const marcarLeida = async (id: number) => {
    await api.post(`/notificaciones/${id}/leer`);
    setNotificaciones(prev => prev.map(n => n.id === id ? { ...n, leida: true } : n));
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-96">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">Notificaciones</h1>

      {notificaciones.length === 0 ? (
        <div className="bg-white rounded-xl border border-gray-200 p-12 text-center text-gray-500">
          No tenes notificaciones.
        </div>
      ) : (
        <div className="space-y-2">
          {notificaciones.map(n => (
            <div
              key={n.id}
              className={`bg-white rounded-lg border p-4 flex items-start justify-between transition-colors ${
                n.leida ? 'border-gray-200' : 'border-primary-300 bg-primary-50'
              }`}
            >
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-1">
                  <span className={`inline-flex items-center px-2 py-0.5 rounded text-xs font-medium ${
                    n.tipo === 'EMAIL' ? 'bg-blue-100 text-blue-700' : 'bg-purple-100 text-purple-700'
                  }`}>
                    {n.tipo === 'EMAIL' ? 'Email' : 'Push'}
                  </span>
                  <span className="text-xs text-gray-400">
                    {new Date(n.fechaEnvio).toLocaleString('es-AR')}
                  </span>
                </div>
                <p className="text-sm text-gray-900">{n.mensaje}</p>
              </div>
              {!n.leida && (
                <button
                  onClick={() => marcarLeida(n.id)}
                  className="ml-3 text-xs text-primary-600 hover:text-primary-700 whitespace-nowrap"
                >
                  Marcar leída
                </button>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
