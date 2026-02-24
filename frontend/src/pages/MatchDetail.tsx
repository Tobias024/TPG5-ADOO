import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/client';
import { useAuth } from '../context/AuthContext';
import StateBadge from '../components/StateBadge';
import toast from 'react-hot-toast';

interface Jugador {
  id: number;
  nombre: string;
  nivel: string;
}

interface Partido {
  id: number;
  deporte: string;
  deporteId: number;
  organizador: string;
  organizadorId: number;
  ubicacion: string;
  fechaHora: string;
  duracion: string;
  cantidadJugadores: number;
  jugadoresActuales: number;
  estado: string;
  nivelMinimo: string;
  nivelMaximo: string;
  estrategiaEmparejamiento: string;
  jugadores: Jugador[];
  resultadoEquipo1?: number;
  resultadoEquipo2?: number;
  ganadorId?: number;
  ganadorNombre?: string;
}

interface Comentario {
  id: number;
  partidoId: number;
  usuarioId: number;
  usuarioNombre: string;
  texto: string;
  fechaCreacion: string;
}

export default function MatchDetail() {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [partido, setPartido] = useState<Partido | null>(null);
  const [loading, setLoading] = useState(true);
  const [acting, setActing] = useState(false);
  const [showFinalizeModal, setShowFinalizeModal] = useState(false);
  const [score1, setScore1] = useState(0);
  const [score2, setScore2] = useState(0);
  const [selectedWinner, setSelectedWinner] = useState<number | null>(null);
  const [comentarios, setComentarios] = useState<Comentario[]>([]);
  const [comentarioTexto, setComentarioTexto] = useState('');
  const [loadingComentarios, setLoadingComentarios] = useState(false);
  const [postingComentario, setPostingComentario] = useState(false);

  const fetchPartido = () => {
    api.get(`/partidos/${id}`).then(res => setPartido(res.data)).finally(() => setLoading(false));
  };

  const fetchComentarios = () => {
    if (!id) return;
    setLoadingComentarios(true);
    api.get(`/partidos/${id}/comentarios`)
      .then(res => setComentarios(res.data))
      .catch(() => setComentarios([]))
      .finally(() => setLoadingComentarios(false));
  };

  useEffect(() => { fetchPartido(); }, [id]);

  useEffect(() => {
    if (partido?.estado === 'FINALIZADO') fetchComentarios();
  }, [partido?.id, partido?.estado]);

  if (loading || !partido) {
    return (
      <div className="flex items-center justify-center h-96">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  const isOrganizer = user?.id === partido.organizadorId;
  const isPlayer = partido.jugadores.some(j => j.id === user?.id);
  const canJoin = partido.estado === 'FALTAN_JUGADORES' && !isPlayer;
  const canPlayNow = isOrganizer && partido.estado === 'CONFIRMADO'; // start immediately
  const canConfirmSchedule = isOrganizer && partido.estado === 'ARMADO'; // confirm and wait for scheduled time to auto-start
  const canFinalize = isOrganizer && partido.estado === 'EN_JUEGO';
  const canCancel = isOrganizer && !['FINALIZADO', 'CANCELADO', 'EN_JUEGO'].includes(partido.estado);

  const handleConfirmSchedule = async () => {
    setActing(true);
    try {
      const res = await api.post(`/partidos/${id}/avanzar`);
      setPartido(res.data);
      toast.success('Partido confirmado. Se iniciará automáticamente a la hora programada.');
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'Error al confirmar');
    } finally {
      setActing(false);
    }
  };

  const handleJoin = async () => {
    setActing(true);
    try {
      const res = await api.post(`/partidos/${id}/inscribir`);
      setPartido(res.data);
      toast.success('Te uniste al partido!');
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'No se pudo unir al partido');
    } finally {
      setActing(false);
    }
  };

  const handlePlayNow = async () => {
    setActing(true);
    try {
      const res = await api.post(`/partidos/${id}/avanzar`);
      setPartido(res.data);
      toast.success('¡El partido está en juego!');
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'Error al iniciar partido');
    } finally {
      setActing(false);
    }
  };

  const handleCancel = async () => {
    setActing(true);
    try {
      const res = await api.post(`/partidos/${id}/cancelar`);
      setPartido(res.data);
      toast.success('Partido cancelado');
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'Error al cancelar');
    } finally {
      setActing(false);
    }
  };

  const handleFinalize = async () => {
    setActing(true);
    try {
      const res = await api.post(`/partidos/${id}/finalizar`, {
        resultadoEquipo1: score1,
        resultadoEquipo2: score2,
        ganadorId: selectedWinner
      });
      setPartido(res.data);
      setShowFinalizeModal(false);
      toast.success('Partido finalizado!');
      fetchComentarios();
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'Error al finalizar');
    } finally {
      setActing(false);
    }
  };

  const handleEnviarComentario = async (e: React.FormEvent) => {
    e.preventDefault();
    const texto = comentarioTexto.trim();
    if (!texto || !user) return;
    setPostingComentario(true);
    try {
      const res = await api.post(`/partidos/${id}/comentarios`, { texto });
      setComentarios(prev => [...prev, res.data]);
      setComentarioTexto('');
      toast.success('Comentario publicado');
    } catch (err: any) {
      toast.error(err.response?.data?.error || 'Error al publicar comentario');
    } finally {
      setPostingComentario(false);
    }
  };

  const fecha = new Date(partido.fechaHora);

  return (
    <div className="max-w-3xl mx-auto px-4 py-8">
      <button onClick={() => navigate(-1)} className="text-primary-600 hover:text-primary-700 mb-4 text-sm flex items-center gap-1">
        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
        </svg>
        Volver
      </button>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
        <div className="bg-primary-700 px-6 py-5 text-white">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold">{partido.deporte}</h1>
              <p className="text-primary-200 mt-1">Organizado por {partido.organizador}</p>
            </div>
            <StateBadge estado={partido.estado} />
          </div>
        </div>

        <div className="p-6">
          <div className="grid grid-cols-2 gap-6 mb-6">
            <div>
              <p className="text-xs font-medium text-gray-400 uppercase">Ubicación</p>
              <p className="text-gray-900 font-medium mt-1">{partido.ubicacion}</p>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-400 uppercase">Fecha y Hora</p>
              <p className="text-gray-900 font-medium mt-1">
                {fecha.toLocaleDateString('es-AR')} - {fecha.toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' })}
              </p>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-400 uppercase">Duración</p>
              <p className="text-gray-900 font-medium mt-1">{partido.duracion}</p>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-400 uppercase">Jugadores</p>
              <p className="text-gray-900 font-medium mt-1">{partido.jugadoresActuales} / {partido.cantidadJugadores}</p>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-400 uppercase">Nivel Requerido</p>
              <p className="text-gray-900 font-medium mt-1">{partido.nivelMinimo} - {partido.nivelMaximo}</p>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-400 uppercase">Emparejamiento</p>
              <p className="text-gray-900 font-medium mt-1 capitalize">{partido.estrategiaEmparejamiento.replace('_', ' ').toLowerCase()}</p>
            </div>
          </div>

          <div className="border-t border-gray-100 pt-5 mb-6">
            <h3 className="text-sm font-semibold text-gray-900 mb-3">Jugadores Inscriptos</h3>
            {partido.jugadores.length === 0 ? (
              <p className="text-gray-500 text-sm">No hay jugadores inscriptos todavía.</p>
            ) : (
              <div className="space-y-2">
                {partido.jugadores.map(j => (
                  <div key={j.id} className="flex items-center justify-between bg-gray-50 rounded-lg px-4 py-2.5">
                    <span className="font-medium text-gray-900">{j.nombre}</span>
                    <span className="text-xs bg-primary-100 text-primary-700 px-2 py-0.5 rounded-full">{j.nivel}</span>
                  </div>
                ))}
              </div>
            )}
          </div>

          {partido.estado === 'FINALIZADO' && partido.resultadoEquipo1 !== null && (
            <div className="border-t border-gray-100 pt-5 mb-6">
              <h3 className="text-sm font-semibold text-gray-900 mb-3">Resultado Final</h3>
              <div className="bg-gradient-to-r from-primary-50 to-green-50 rounded-xl p-4">
                <div className="flex items-center justify-center gap-6">
                  <div className="text-center">
                    <p className="text-xs text-gray-500 mb-1">Equipo 1</p>
                    <p className="text-3xl font-bold text-primary-700">{partido.resultadoEquipo1}</p>
                  </div>
                  <span className="text-2xl text-gray-400">-</span>
                  <div className="text-center">
                    <p className="text-xs text-gray-500 mb-1">Equipo 2</p>
                    <p className="text-3xl font-bold text-primary-700">{partido.resultadoEquipo2}</p>
                  </div>
                </div>
                {partido.ganadorNombre && (
                  <div className="mt-3 text-center">
                    <span className="inline-flex items-center gap-1 bg-yellow-100 text-yellow-800 px-3 py-1 rounded-full text-sm font-medium">
                      <svg className="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                      </svg>
                      Ganador: {partido.ganadorNombre}
                    </span>
                  </div>
                )}
              </div>
            </div>
          )}

          {partido.estado === 'FINALIZADO' && (
            <div className="border-t border-gray-100 pt-5 mb-6">
              <h3 className="text-sm font-semibold text-gray-900 mb-3">Comentarios</h3>
              {user && (
                <form onSubmit={handleEnviarComentario} className="mb-4">
                  <div className="flex gap-2">
                    <input
                      type="text"
                      value={comentarioTexto}
                      onChange={(e) => setComentarioTexto(e.target.value)}
                      placeholder="Escribe un comentario..."
                      maxLength={1000}
                      className="flex-1 border border-gray-300 rounded-lg px-3 py-2 text-sm focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                    />
                    <button
                      type="submit"
                      disabled={postingComentario || !comentarioTexto.trim()}
                      className="bg-primary-600 hover:bg-primary-700 text-white font-medium px-4 py-2 rounded-lg text-sm transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      {postingComentario ? '...' : 'Enviar'}
                    </button>
                  </div>
                </form>
              )}
              {loadingComentarios ? (
                <div className="flex items-center justify-center py-6">
                  <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600"></div>
                </div>
              ) : comentarios.length === 0 ? (
                <p className="text-gray-500 text-sm">Aún no hay comentarios. ¡Sé el primero en comentar!</p>
              ) : (
                <div className="space-y-3">
                  {comentarios.map(c => (
                    <div key={c.id} className="bg-gray-50 rounded-lg px-4 py-3">
                      <div className="flex items-center justify-between gap-2 mb-1">
                        <span className="font-medium text-gray-900 text-sm">{c.usuarioNombre}</span>
                        <span className="text-xs text-gray-400">
                          {new Date(c.fechaCreacion).toLocaleDateString('es-AR')} {new Date(c.fechaCreacion).toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' })}
                        </span>
                      </div>
                      <p className="text-gray-700 text-sm whitespace-pre-wrap">{c.texto}</p>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          <div className="flex gap-3">
            {canJoin && (
              <button
                onClick={handleJoin}
                disabled={acting}
                className="flex-1 bg-green-600 hover:bg-green-700 text-white font-medium py-2.5 rounded-lg transition-colors disabled:opacity-50"
              >
                Unirme al Partido
              </button>
            )}
            {canConfirmSchedule && (
              <button
                onClick={handleConfirmSchedule}
                disabled={acting}
                className="flex-1 bg-green-600 hover:bg-green-700 text-white font-medium py-2.5 rounded-lg transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                Confirmar (iniciar a la hora programada)
              </button>
            )}
            {canPlayNow && (
              <button
                onClick={handlePlayNow}
                disabled={acting}
                className="flex-1 bg-orange-500 hover:bg-orange-600 text-white font-medium py-2.5 rounded-lg transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM9.555 7.168A1 1 0 008 8v4a1 1 0 001.555.832l3-2a1 1 0 000-1.664l-3-2z" clipRule="evenodd" />
                </svg>
                Jugar Ahora
              </button>
            )}
            {canFinalize && (
              <button
                onClick={() => setShowFinalizeModal(true)}
                disabled={acting}
                className="flex-1 bg-green-600 hover:bg-green-700 text-white font-medium py-2.5 rounded-lg transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                </svg>
                Finalizar Partido
              </button>
            )}
            {canCancel && (
              <button
                onClick={handleCancel}
                disabled={acting}
                className="flex-1 bg-red-600 hover:bg-red-700 text-white font-medium py-2.5 rounded-lg transition-colors disabled:opacity-50"
              >
                Cancelar Partido
              </button>
            )}
          </div>
        </div>
      </div>

      {showFinalizeModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl shadow-xl max-w-md w-full mx-4 overflow-hidden">
            <div className="bg-primary-700 px-6 py-4 text-white">
              <h2 className="text-lg font-semibold">Finalizar Partido</h2>
              <p className="text-primary-200 text-sm">Ingresa el resultado final</p>
            </div>
            <div className="p-6">
              <div className="flex items-center justify-center gap-4 mb-6">
                <div className="text-center">
                  <label className="block text-xs text-gray-500 mb-2">Equipo 1</label>
                  <input
                    type="number"
                    min="0"
                    value={score1}
                    onChange={(e) => setScore1(parseInt(e.target.value) || 0)}
                    className="w-20 text-center text-2xl font-bold border border-gray-300 rounded-lg py-2 focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                  />
                </div>
                <span className="text-2xl text-gray-400 mt-6">-</span>
                <div className="text-center">
                  <label className="block text-xs text-gray-500 mb-2">Equipo 2</label>
                  <input
                    type="number"
                    min="0"
                    value={score2}
                    onChange={(e) => setScore2(parseInt(e.target.value) || 0)}
                    className="w-20 text-center text-2xl font-bold border border-gray-300 rounded-lg py-2 focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                  />
                </div>
              </div>

              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">Ganador (opcional)</label>
                <select
                  value={selectedWinner || ''}
                  onChange={(e) => setSelectedWinner(e.target.value ? parseInt(e.target.value) : null)}
                  className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                >
                  <option value="">Sin ganador / Empate</option>
                  {partido.jugadores.map(j => (
                    <option key={j.id} value={j.id}>{j.nombre}</option>
                  ))}
                </select>
              </div>

              <div className="flex gap-3">
                <button
                  onClick={() => setShowFinalizeModal(false)}
                  className="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2.5 rounded-lg transition-colors"
                >
                  Cancelar
                </button>
                <button
                  onClick={handleFinalize}
                  disabled={acting}
                  className="flex-1 bg-green-600 hover:bg-green-700 text-white font-medium py-2.5 rounded-lg transition-colors disabled:opacity-50"
                >
                  Confirmar
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
