import React, { createContext, useContext, useEffect, useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { login as loginApi, getPersona, getCliente } from '../api';
import { Persona, Cliente } from '../types';

interface AuthState {
  token: string | null;
  idPersona: number | null;
  idCliente: number | null;
  persona: Persona | null;
  cliente: Cliente | null;
  cargando: boolean;
}

interface AuthContextValue extends AuthState {
  login: (documento: string, contrasenia: string) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextValue | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [state, setState] = useState<AuthState>({
    token: null,
    idPersona: null,
    idCliente: null,
    persona: null,
    cliente: null,
    cargando: true,
  });

  useEffect(() => {
    AsyncStorage.multiGet(['token', 'idPersona', 'idCliente']).then(([[, token], [, idPersona], [, idCliente]]) => {
      setState(s => ({
        ...s,
        token,
        idPersona: idPersona ? Number(idPersona) : null,
        idCliente: idCliente ? Number(idCliente) : null,
        cargando: false,
      }));
    });
  }, []);

  const login = async (documento: string, contrasenia: string) => {
    const response = await loginApi({ documento, contrasenia });
    const { token, idPersona } = response;

    let idCliente: number | null = null;
    let persona: Persona | null = null;
    let cliente: Cliente | null = null;

    try {
      persona = await getPersona(idPersona);
      const clienteData = await getCliente(idPersona);
      cliente = clienteData;
      idCliente = clienteData.identificador;
    } catch (_) {}

    await AsyncStorage.multiSet([
      ['token', token],
      ['idPersona', String(idPersona)],
      ['idCliente', idCliente ? String(idCliente) : ''],
    ]);

    setState({ token, idPersona, idCliente, persona, cliente, cargando: false });
  };

  const logout = async () => {
    await AsyncStorage.multiRemove(['token', 'idPersona', 'idCliente']);
    setState({ token: null, idPersona: null, idCliente: null, persona: null, cliente: null, cargando: false });
  };

  return (
    <AuthContext.Provider value={{ ...state, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth debe usarse dentro de AuthProvider');
  return ctx;
}
