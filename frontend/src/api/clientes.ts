import { useMutation, useQuery } from '@tanstack/react-query';
import api from './client';
import { Cliente, ClienteRegistro, MetricasCliente } from '../types';

export const createCliente = (data: ClienteRegistro) =>
  api.post('/clientes', data).then(r => r.data);

export const getCliente = (id: number) =>
  api.get<Cliente>(`/clientes/${id}`).then(r => r.data);

export const getMetricas = (id: number) =>
  api.get<MetricasCliente>(`/clientes/${id}/metricas`).then(r => r.data);

export const useCreateCliente = () => useMutation({ mutationFn: createCliente });

export const useGetCliente = (id: number | null) =>
  useQuery({ queryKey: ['cliente', id], queryFn: () => getCliente(id!), enabled: id != null });

export const useMetricas = (id: number | null) =>
  useQuery({ queryKey: ['metricas', id], queryFn: () => getMetricas(id!), enabled: id != null });
