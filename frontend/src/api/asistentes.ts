import { useMutation, useQueryClient } from '@tanstack/react-query';
import api from './client';
import { Asistente } from '../types';

export const inscribirse = (data: Asistente) =>
  api.post<Asistente>('/asistentes', data).then(r => r.data);

export const useInscribirse = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: inscribirse,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['subastas'] }),
  });
};
