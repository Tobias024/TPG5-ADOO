import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import api from './client';
import { Producto } from '../types';

export const crearProducto = (data: Producto) =>
  api.post('/productos', data).then(r => r.data);

export const getMisProductos = (idDuenio: number) =>
  api.get<Producto[]>(`/productos/duenio/${idDuenio}`).then(r => r.data);

export const confirmarProducto = ({ id, decision }: { id: number; decision: string }) =>
  api.post(`/productos/${id}/confirmacion`, { decision }).then(r => r.data);

export const useCrearProducto = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: crearProducto,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['mis-productos'] }),
  });
};

export const useMisProductos = (idDuenio: number | null) =>
  useQuery({
    queryKey: ['mis-productos', idDuenio],
    queryFn: () => getMisProductos(idDuenio!),
    enabled: idDuenio != null,
  });

export const useConfirmarProducto = () =>
  useMutation({ mutationFn: confirmarProducto });
