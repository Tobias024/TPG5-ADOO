import { useQuery } from '@tanstack/react-query';
import api from './client';
import { ItemCatalogo } from '../types';

export const getItemsCatalogo = () =>
  api.get<ItemCatalogo[]>('/items-catalogo/todos').then(r => r.data);

export const getItemCatalogo = (id: number) =>
  api.get<ItemCatalogo>(`/items-catalogo/${id}`).then(r => r.data);

export const useItemsCatalogo = () =>
  useQuery({ queryKey: ['items-catalogo'], queryFn: getItemsCatalogo });

export const useItemCatalogo = (id: number | null) =>
  useQuery({ queryKey: ['item-catalogo', id], queryFn: () => getItemCatalogo(id!), enabled: id != null });
