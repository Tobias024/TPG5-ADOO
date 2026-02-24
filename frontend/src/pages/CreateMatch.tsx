import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/client';
import toast from 'react-hot-toast';

interface Deporte {
  id: number;
  nombre: string;
}

export default function CreateMatch() {
  const navigate = useNavigate();
  const [deportes, setDeportes] = useState<Deporte[]>([]);
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({
    deporteId: '',
    ubicacion: '',
    fechaHora: '',
    duracion: '2 min', // 2 min minimum for testing
    cantidadJugadores: 4,
    nivelMinimo: 'PRINCIPIANTE',
    nivelMaximo: 'AVANZADO',
    estrategiaEmparejamiento: 'LIBRE',
  });

  useEffect(() => {
    api.get('/deportes').then(res => setDeportes(res.data)).catch(() => {});
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const payload = { ...form, deporteId: Number(form.deporteId) };
      const res = await api.post('/partidos', payload);
      toast.success('Partido creado!');
      navigate(`/partidos/${res.data.id}`);
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'Error al crear el partido');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">Crear Partido</h1>
      <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 space-y-5">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Deporte</label>
          <select
            value={form.deporteId}
            onChange={e => setForm({ ...form, deporteId: e.target.value })}
            className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
            required
          >
            <option value="">Seleccionar deporte...</option>
            {deportes.map(d => (
              <option key={d.id} value={d.id}>{d.nombre}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Ubicación</label>
          <input
            type="text"
            value={form.ubicacion}
            onChange={e => setForm({ ...form, ubicacion: e.target.value })}
            className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
            placeholder="Ej: Parque Centenario"
            required
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Fecha y Hora</label>
            <input
              type="datetime-local"
              value={form.fechaHora}
              onChange={e => setForm({ ...form, fechaHora: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Duración</label>
            <select
              value={form.duracion}
              onChange={e => setForm({ ...form, duracion: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
            >
              <option value="2 min">2 minutos (pruebas)</option>
              <option value="30 min">30 minutos</option>
              <option value="60 min">60 minutos</option>
              <option value="90 min">90 minutos</option>
              <option value="120 min">120 minutos</option>
            </select>
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Cantidad de Jugadores</label>
          <input
            type="number"
            min={2}
            max={30}
            value={form.cantidadJugadores}
            onChange={e => setForm({ ...form, cantidadJugadores: Number(e.target.value) })}
            className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
            required
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Nivel Mínimo</label>
            <select
              value={form.nivelMinimo}
              onChange={e => setForm({ ...form, nivelMinimo: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
            >
              <option value="PRINCIPIANTE">Principiante</option>
              <option value="INTERMEDIO">Intermedio</option>
              <option value="AVANZADO">Avanzado</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Nivel Máximo</label>
            <select
              value={form.nivelMaximo}
              onChange={e => setForm({ ...form, nivelMaximo: e.target.value })}
              className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
            >
              <option value="PRINCIPIANTE">Principiante</option>
              <option value="INTERMEDIO">Intermedio</option>
              <option value="AVANZADO">Avanzado</option>
            </select>
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Estrategia de Emparejamiento</label>
          <select
            value={form.estrategiaEmparejamiento}
            onChange={e => setForm({ ...form, estrategiaEmparejamiento: e.target.value })}
            className="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none"
          >
            <option value="LIBRE">Libre</option>
            <option value="POR_NIVEL">Por Nivel</option>
            <option value="POR_CERCANIA">Por Cercanía</option>
            <option value="POR_HISTORIAL">Por Historial</option>
          </select>
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-primary-600 hover:bg-primary-700 text-white font-medium py-3 rounded-lg transition-colors disabled:opacity-50"
        >
          {loading ? 'Creando...' : 'Crear Partido'}
        </button>
      </form>
    </div>
  );
}
