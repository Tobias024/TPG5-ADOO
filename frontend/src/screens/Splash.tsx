import React, { useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { colors, fonts } from '../theme';

export default function SplashScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();

  useEffect(() => {
    const timer = setTimeout(() => navigation.replace('Auth'), 2000);
    return () => clearTimeout(timer);
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.card}>
        <Text style={styles.title}>SubastAR</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.bordo,
    alignItems: 'center',
    justifyContent: 'center',
  },
  card: {
    width: '90%',
    aspectRatio: 0.9,
    backgroundColor: colors.bordo,
    borderRadius: 64,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 2,
    borderColor: colors.rosaClaro + '44',
  },
  title: {
    fontFamily: fonts.interBold,
    fontSize: 48,
    color: colors.blanco,
    letterSpacing: -0.96,
  },
});
