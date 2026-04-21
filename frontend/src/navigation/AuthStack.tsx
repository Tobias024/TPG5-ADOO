import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AuthStackParamList } from './types';

import LoginScreen from '../screens/auth/Login';
import RegistroScreen from '../screens/auth/Registro';
import ConfirmacionRegistro1Screen from '../screens/auth/ConfirmacionRegistro1';
import ConfirmacionRegistro2Screen from '../screens/auth/ConfirmacionRegistro2';

const Stack = createNativeStackNavigator<AuthStackParamList>();

export default function AuthStack() {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Registro" component={RegistroScreen} />
      <Stack.Screen name="ConfirmacionRegistro1" component={ConfirmacionRegistro1Screen} />
      <Stack.Screen name="ConfirmacionRegistro2" component={ConfirmacionRegistro2Screen} />
    </Stack.Navigator>
  );
}
