import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import api from './client';
import { Pujo } from '../types';

export const getPujos = () => api.get<Pujo[]>('/pujos/todos').then(r => r.data);
export const crearPujo = (data: Pujo) => api.post<Pujo>('/pujos', data).then(r => r.data);

export const usePujos = () =>
  useQuery({ queryKey: ['pujos'], queryFn: getPujos });

export const useCrearPujo = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: crearPujo,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['pujos'] }),
  });
};
