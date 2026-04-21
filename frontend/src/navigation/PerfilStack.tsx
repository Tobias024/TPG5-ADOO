import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { PerfilStackParamList } from './types';

import PerfilScreen from '../screens/perfil/Perfil';
import EditarPerfilScreen from '../screens/perfil/EditarPerfil';
import AgregarMetodoPagoScreen from '../screens/perfil/AgregarMetodoPago';
import MetricasScreen from '../screens/perfil/Metricas';
import MisSubastasScreen from '../screens/perfil/MisSubastas';
import SubastarAlgoPropioScreen from '../screens/perfil/SubastarAlgoPropio';
import SolicitudEnviadaScreen from '../screens/perfil/SolicitudEnviada';
import SolicitudAceptadaScreen from '../screens/perfil/SolicitudAceptada';
import SolicitudRechazadaScreen from '../screens/perfil/SolicitudRechazada';
import NotificacionesScreen from '../screens/notif/Notificaciones';
import AlertaNuevaSubastaScreen from '../screens/notif/AlertaNuevaSubasta';

const Stack = createNativeStackNavigator<PerfilStackParamList>();

export default function PerfilStack() {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Perfil" component={PerfilScreen} />
      <Stack.Screen name="EditarPerfil" component={EditarPerfilScreen} />
      <Stack.Screen name="AgregarMetodoPago" component={AgregarMetodoPagoScreen} />
      <Stack.Screen name="Metricas" component={MetricasScreen} />
      <Stack.Screen name="MisSubastas" component={MisSubastasScreen} />
      <Stack.Screen name="SubastarAlgoPropio" component={SubastarAlgoPropioScreen} />
      <Stack.Screen name="SolicitudEnviada" component={SolicitudEnviadaScreen} />
      <Stack.Screen name="SolicitudAceptada" component={SolicitudAceptadaScreen} />
      <Stack.Screen name="SolicitudRechazada" component={SolicitudRechazadaScreen} />
      <Stack.Screen name="Notificaciones" component={NotificacionesScreen} />
      <Stack.Screen name="AlertaNuevaSubasta" component={AlertaNuevaSubastaScreen} />
    </Stack.Navigator>
  );
}
