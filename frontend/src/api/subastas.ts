import { useQuery } from '@tanstack/react-query';
import api from './client';
import { Subasta } from '../types';

export const getSubastas = () => api.get<Subasta[]>('/subastas/todos').then(r => r.data);
export const getSubasta = (id: number) => api.get<Subasta>(`/subastas/${id}`).then(r => r.data);

export const useSubastas = () =>
  useQuery({ queryKey: ['subastas'], queryFn: getSubastas });

export const useSubasta = (id: number | null) =>
  useQuery({ queryKey: ['subasta', id], queryFn: () => getSubasta(id!), enabled: id != null });
