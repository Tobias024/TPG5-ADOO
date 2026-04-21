import { useQuery } from '@tanstack/react-query';
import api from './client';
import { Pais } from '../types';

export const getPaises = () => api.get<Pais[]>('/paises/todos').then(r => r.data);
export const usePaises = () => useQuery({ queryKey: ['paises'], queryFn: getPaises });
