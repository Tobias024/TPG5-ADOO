import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { DescubrirStackParamList } from './types';

import DescubrirScreen from '../screens/descubrir/Descubrir';
import DetalleSubastaScreen from '../screens/home/DetalleSubasta';
import ParticiparSubastaScreen from '../screens/home/ParticiparSubasta';

const Stack = createNativeStackNavigator<DescubrirStackParamList>();

export default function DescubrirStack() {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Descubrir" component={DescubrirScreen} />
      <Stack.Screen name="DetalleSubasta" component={DetalleSubastaScreen} />
      <Stack.Screen name="ParticiparSubasta" component={ParticiparSubastaScreen} />
    </Stack.Navigator>
  );
}
