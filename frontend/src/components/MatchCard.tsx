import { Link } from 'react-router-dom';
import StateBadge from './StateBadge';

interface MatchCardProps {
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

export default function MatchCard(props: MatchCardProps) {
  const fecha = new Date(props.fechaHora);

  return (
    <Link
      to={`/partidos/${props.id}`}
      className="block bg-white rounded-xl shadow-sm border border-gray-200 hover:shadow-md hover:border-primary-300 transition-all p-5"
    >
      <div className="flex items-start justify-between mb-3">
        <div>
          <h3 className="text-lg font-semibold text-gray-900">{props.deporte}</h3>
          <p className="text-sm text-gray-500">por {props.organizador}</p>
        </div>
        <StateBadge estado={props.estado} />
      </div>
      <div className="space-y-2 text-sm text-gray-600">
        <div className="flex items-center gap-2">
          <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          {props.ubicacion}
        </div>
        <div className="flex items-center gap-2">
          <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
          </svg>
          {fecha.toLocaleDateString('es-AR')} - {fecha.toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' })}
        </div>
        <div className="flex items-center gap-2">
          <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          {props.duracion}
        </div>
        {props.estado === 'FINALIZADO' && props.resultadoEquipo1 !== undefined && props.resultadoEquipo1 !== null ? (
          <div className="mt-3 pt-3 border-t border-gray-100">
            <div className="flex items-center justify-center gap-3 bg-gradient-to-r from-primary-50 to-green-50 rounded-lg py-2">
              <span className="text-xl font-bold text-primary-700">{props.resultadoEquipo1}</span>
              <span className="text-gray-400">-</span>
              <span className="text-xl font-bold text-primary-700">{props.resultadoEquipo2}</span>
            </div>
            {props.ganadorNombre && (
              <p className="text-xs text-center text-yellow-700 mt-1">
                Ganador: {props.ganadorNombre}
              </p>
            )}
          </div>
        ) : (
          <div className="flex items-center justify-between mt-3 pt-3 border-t border-gray-100">
            <div className="flex items-center gap-1">
              <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
              <span className="font-medium">{props.jugadoresActuales}/{props.cantidadJugadores}</span>
            </div>
            <span className="text-xs text-gray-400 capitalize">
              {props.estrategiaEmparejamiento.replace('_', ' ').toLowerCase()}
            </span>
          </div>
        )}
      </div>
    </Link>
  );
}
