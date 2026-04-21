import { useQuery } from '@tanstack/react-query';
import api from './client';
import { Notificacion } from '../types';

export const getNotificaciones = (idCliente: number) =>
  api.get<Notificacion[]>(`/notificaciones/cliente/${idCliente}`).then(r => r.data);

export const useNotificaciones = (idCliente: number | null) =>
  useQuery({
    queryKey: ['notificaciones', idCliente],
    queryFn: () => getNotificaciones(idCliente!),
    enabled: idCliente != null,
  });
