import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { HomeStackParamList } from './types';

import HomeScreen from '../screens/home/Home';
import SubastasActivasScreen from '../screens/home/SubastasActivas';
import DetalleSubastaScreen from '../screens/home/DetalleSubasta';
import ParticiparSubastaScreen from '../screens/home/ParticiparSubasta';
import DetalleItemVendidoScreen from '../screens/home/DetalleItemVendido';
import CasoAdquisicionScreen from '../screens/home/CasoAdquisicion';

const Stack = createNativeStackNavigator<HomeStackParamList>();

export default function HomeStack() {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Home" component={HomeScreen} />
      <Stack.Screen name="SubastasActivas" component={SubastasActivasScreen} />
      <Stack.Screen name="DetalleSubasta" component={DetalleSubastaScreen} />
      <Stack.Screen name="ParticiparSubasta" component={ParticiparSubastaScreen} />
      <Stack.Screen name="DetalleItemVendido" component={DetalleItemVendidoScreen} />
      <Stack.Screen name="CasoAdquisicion" component={CasoAdquisicionScreen} />
    </Stack.Navigator>
  );
}
