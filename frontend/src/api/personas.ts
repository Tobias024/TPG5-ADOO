import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import api from './client';
import { Persona } from '../types';

export const createPersona = (data: Persona) =>
  api.post('/personas', data).then(r => r.data);

export const getPersona = (id: number) =>
  api.get<Persona>(`/personas/${id}`).then(r => r.data);

export const updatePersona = ({ id, data }: { id: number; data: Persona }) =>
  api.put<Persona>(`/personas/${id}`, data).then(r => r.data);

export const useCreatePersona = () => useMutation({ mutationFn: createPersona });

export const useGetPersona = (id: number | null) =>
  useQuery({ queryKey: ['persona', id], queryFn: () => getPersona(id!), enabled: id != null });

export const useUpdatePersona = () => {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: updatePersona,
    onSuccess: (_, { id }) => qc.invalidateQueries({ queryKey: ['persona', id] }),
  });
};
