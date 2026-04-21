import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import api from './client';
import { MetodoPago } from '../types';

export const getMetodosPago = (idCliente: number) =>
  api.get<MetodoPago[]>(`/metodos-pago/cliente/${idCliente}`).then(r => r.data);

export const agregarMetodo = (data: MetodoPago) =>
  api.post('/metodos-pago', data).then(r => r.data);

export const eliminarMetodo = (id: number) =>
  api.delete(`/metodos-pago/${id}`).then(r => r.data);

export const useMetodosPago = (idCliente: number | null) =>
  useQuery({
    queryKey: ['metodos-pago', idCliente],
    queryFn: () => getMetodosPago(idCliente!),
    enabled: idCliente != null,
  });

export const useAgregarMetodo = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: agregarMetodo,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['metodos-pago'] }),
  });
};

export const useEliminarMetodo = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: eliminarMetodo,
    onSuccess: () => qc.invalidateQueries({ queryKey: ['metodos-pago'] }),
  });
};
