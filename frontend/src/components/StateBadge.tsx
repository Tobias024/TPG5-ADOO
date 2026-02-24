const STATE_STYLES: Record<string, string> = {
  FALTAN_JUGADORES: 'bg-yellow-100 text-yellow-800 border-yellow-300',
  ARMADO: 'bg-blue-100 text-blue-800 border-blue-300',
  CONFIRMADO: 'bg-green-100 text-green-800 border-green-300',
  EN_JUEGO: 'bg-purple-100 text-purple-800 border-purple-300',
  FINALIZADO: 'bg-gray-100 text-gray-800 border-gray-300',
  CANCELADO: 'bg-red-100 text-red-800 border-red-300',
};

const STATE_LABELS: Record<string, string> = {
  FALTAN_JUGADORES: 'Faltan Jugadores',
  ARMADO: 'Partido Armado',
  CONFIRMADO: 'Confirmado',
  EN_JUEGO: 'En Juego',
  FINALIZADO: 'Finalizado',
  CANCELADO: 'Cancelado',
};

export default function StateBadge({ estado }: { estado: string }) {
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${STATE_STYLES[estado] || 'bg-gray-100 text-gray-800'}`}>
      {STATE_LABELS[estado] || estado}
    </span>
  );
}
