import { useQuery } from '@tanstack/react-query';
import api from './client';
import { RegistroSubasta } from '../types';

export const getRegistros = () =>
  api.get<RegistroSubasta[]>('/registros-subasta/todos').then(r => r.data);

export const useRegistros = () =>
  useQuery({ queryKey: ['registros'], queryFn: getRegistros });
