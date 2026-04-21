import { useMutation, useQuery } from '@tanstack/react-query';
import api from './client';
import { Foto } from '../types';

export const getFotosProducto = (idProducto: number) =>
  api.get<Foto[]>(`/productos/${idProducto}/fotos`).then(r => r.data);

export const subirFoto = (data: Foto) =>
  api.post('/fotos', data).then(r => r.data);

export const useFotosProducto = (idProducto: number | null) =>
  useQuery({
    queryKey: ['fotos', idProducto],
    queryFn: () => getFotosProducto(idProducto!),
    enabled: idProducto != null,
  });

export const useSubirFoto = () => useMutation({ mutationFn: subirFoto });
