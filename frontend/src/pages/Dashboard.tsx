import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/client';
import { useAuth } from '../context/AuthContext';
import MatchCard from '../components/MatchCard';

interface Partido {
  id: number;
  deporte: string;
  organizador: string;
  ubicacion: string;
  fechaHora: string;
  duracion: string;
  cantidadJugadores: number;
  jugadoresActuales: number;
  estado: string;
  estrategiaEmparejamiento: string;
  resultadoEquipo1?: number;
  resultadoEquipo2?: number;
  ganadorNombre?: string;
}

interface UserProfile {
  id: number;
  nombre: string;
  mail: string;
  nivel: string;
  deporteFavorito: string | null;
  victorias: number;
}

export default function Dashboard() {
  const { user } = useAuth();
  const [misPartidos, setMisPartidos] = useState<Partido[]>([]);
  const [disponibles, setDisponibles] = useState<Partido[]>([]);
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      api.get('/partidos/mis-partidos'),
      api.get('/partidos?estado=FALTAN_JUGADORES'),
      api.get('/usuarios/me'),
    ]).then(([mis, disp, prof]) => {
      setMisPartidos(mis.data);
      setDisponibles(disp.data);
      setProfile(prof.data);
    }).finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-96">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Hola, {user?.nombre}!</h1>
        <p className="text-gray-500 mt-1">Bienvenido a Uno Mas</p>
      </div>

      {profile && (
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 mb-8">
          <div className="flex items-center justify-between">
            <div>
              <h2 className="text-lg font-semibold text-gray-900">Tu Perfil</h2>
              <div className="mt-2 flex gap-4 text-sm text-gray-600">
                <span>Nivel: <strong className="text-primary-700">{profile.nivel}</strong></span>
                {profile.deporteFavorito && (
                  <span>Deporte favorito: <strong className="text-primary-700">{profile.deporteFavorito}</strong></span>
                )}
                <span>Victorias: <strong className="text-primary-700">{profile.victorias}</strong></span>
              </div>
            </div>
            <Link
              to="/partidos/nuevo"
              className="bg-primary-600 hover:bg-primary-700 text-white px-5 py-2.5 rounded-lg font-medium transition-colors"
            >
              + Crear Partido
            </Link>
          </div>
        </div>
      )}

      <section className="mb-10">
        <h2 className="text-xl font-bold text-gray-900 mb-4">Mis Partidos</h2>
        {misPartidos.length === 0 ? (
          <div className="bg-white rounded-xl border border-gray-200 p-8 text-center text-gray-500">
            No estas inscripto en ningún partido todavía.
            <Link to="/partidos" className="text-primary-600 hover:underline ml-1">Buscar partidos</Link>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {misPartidos.map(p => <MatchCard key={p.id} {...p} />)}
          </div>
        )}
      </section>

      <section>
        <h2 className="text-xl font-bold text-gray-900 mb-4">Partidos Disponibles</h2>
        {disponibles.length === 0 ? (
          <div className="bg-white rounded-xl border border-gray-200 p-8 text-center text-gray-500">
            No hay partidos buscando jugadores en este momento.
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {disponibles.slice(0, 6).map(p => <MatchCard key={p.id} {...p} />)}
          </div>
        )}
      </section>
    </div>
  );
}
