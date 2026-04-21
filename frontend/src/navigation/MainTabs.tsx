import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { View, Text, StyleSheet } from 'react-native';
import { colors, fonts, spacing } from '../theme';

import HomeStack from './HomeStack';
import DescubrirStack from './DescubrirStack';
import PerfilStack from './PerfilStack';

const Tab = createBottomTabNavigator();

function TabIcon({ label, focused }: { label: string; focused: boolean }) {
  return (
    <View style={styles.iconWrap}>
      <Text style={[styles.iconLabel, focused && styles.iconActive]}>{label}</Text>
      {focused && <View style={styles.dot} />}
    </View>
  );
}

export default function MainTabs() {
  return (
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
        tabBarStyle: styles.tabBar,
        tabBarShowLabel: false,
      }}
    >
      <Tab.Screen
        name="HomeTab"
        component={HomeStack}
        options={{ tabBarIcon: ({ focused }) => <TabIcon label="Inicio" focused={focused} /> }}
      />
      <Tab.Screen
        name="DescubrirTab"
        component={DescubrirStack}
        options={{ tabBarIcon: ({ focused }) => <TabIcon label="Descubrir" focused={focused} /> }}
      />
      <Tab.Screen
        name="PerfilTab"
        component={PerfilStack}
        options={{ tabBarIcon: ({ focused }) => <TabIcon label="Perfil" focused={focused} /> }}
      />
    </Tab.Navigator>
  );
}

const styles = StyleSheet.create({
  tabBar: {
    backgroundColor: colors.bordo,
    borderTopWidth: 0,
    height: 64,
    paddingBottom: spacing(3),
  },
  iconWrap: { alignItems: 'center', justifyContent: 'center', paddingTop: spacing(2) },
  iconLabel: {
    fontFamily: fonts.robotoMedium,
    fontSize: 12,
    color: colors.rosaClaro,
    opacity: 0.7,
  },
  iconActive: { opacity: 1, color: colors.blanco },
  dot: {
    width: 4,
    height: 4,
    borderRadius: 2,
    backgroundColor: colors.blanco,
    marginTop: 2,
  },
});
