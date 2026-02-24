import { useState, useEffect } from 'react';
import api from '../api/client';
import MatchCard from '../components/MatchCard';

interface Deporte {
  id: number;
  nombre: string;
}

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

export default function MatchList() {
  const [partidos, setPartidos] = useState<Partido[]>([]);
  const [deportes, setDeportes] = useState<Deporte[]>([]);
  const [filtroDeporte, setFiltroDeporte] = useState('');
  const [filtroEstado, setFiltroEstado] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get('/deportes').then(res => setDeportes(res.data)).catch(() => {});
  }, []);

  useEffect(() => {
    setLoading(true);
    let url = '/partidos';
    const params = new URLSearchParams();
    if (filtroDeporte) params.set('deporteId', filtroDeporte);
    if (filtroEstado) params.set('estado', filtroEstado);
    if (params.toString()) url += '?' + params.toString();

    api.get(url).then(res => setPartidos(res.data)).finally(() => setLoading(false));
  }, [filtroDeporte, filtroEstado]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">Partidos</h1>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-4 mb-6 flex flex-wrap gap-4">
        <div>
          <label className="block text-xs font-medium text-gray-500 mb-1">Deporte</label>
          <select
            value={filtroDeporte}
            onChange={e => setFiltroDeporte(e.target.value)}
            className="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
          >
            <option value="">Todos</option>
            {deportes.map(d => (
              <option key={d.id} value={d.id}>{d.nombre}</option>
            ))}
          </select>
        </div>
        <div>
          <label className="block text-xs font-medium text-gray-500 mb-1">Estado</label>
          <select
            value={filtroEstado}
            onChange={e => setFiltroEstado(e.target.value)}
            className="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
          >
            <option value="">Todos</option>
            <option value="FALTAN_JUGADORES">Faltan Jugadores</option>
            <option value="ARMADO">Armado</option>
            <option value="CONFIRMADO">Confirmado</option>
            <option value="EN_JUEGO">En Juego</option>
            <option value="FINALIZADO">Finalizado</option>
            <option value="CANCELADO">Cancelado</option>
          </select>
        </div>
      </div>

      {loading ? (
        <div className="flex items-center justify-center h-48">
          <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-primary-600"></div>
        </div>
      ) : partidos.length === 0 ? (
        <div className="bg-white rounded-xl border border-gray-200 p-12 text-center text-gray-500">
          No se encontraron partidos con los filtros seleccionados.
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {partidos.map(p => <MatchCard key={p.id} {...p} />)}
        </div>
      )}
    </div>
  );
}
