import { useQuery } from '@tanstack/react-query';
import api from './client';
import { Catalogo } from '../types';

export const getCatalogo = (id: number) =>
  api.get<Catalogo>(`/catalogos/${id}`).then(r => r.data);

export const useCatalogo = (id: number | null) =>
  useQuery({ queryKey: ['catalogo', id], queryFn: () => getCatalogo(id!), enabled: id != null });
