import { useMutation } from '@tanstack/react-query';
import api from './client';
import { LoginRequest, LoginResponse } from '../types';

export const login = (data: LoginRequest) =>
  api.post<LoginResponse>('/auth/login', data).then(r => r.data);

export const useLogin = () =>
  useMutation({ mutationFn: login });
