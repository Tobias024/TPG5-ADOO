import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import AuctionCard from '../../components/AuctionCard';
import { colors, fonts, spacing } from '../../theme';
import { useSubastas } from '../../api';
import { useAuth } from '../../context/AuthContext';

export default function HomeScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { persona } = useAuth();
  const { data: subastas, isLoading } = useSubastas();

  const abiertas = subastas?.filter(s => s.estado === 'abierta') ?? [];
  const proximamente = subastas?.filter(s => s.estado !== 'abierta') ?? [];

  return (
    <Screen>
      <Text style={styles.pageTitle}>HOME</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={styles.scroll}>
        <Text style={styles.welcome}>¡Hola{persona?.nombre ? `, ${persona.nombre.split(' ')[0]}` : ''}!</Text>
        <Text style={styles.subtitle}>Subastas disponibles</Text>

        {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

        {abiertas.length > 0 && (
          <>
            <View style={styles.sectionHeader}>
              <Text style={styles.sectionTitle}>Activas ahora</Text>
              <TouchableOpacity onPress={() => navigation.navigate('SubastasActivas')}>
                <Text style={styles.verTodas}>Ver todas</Text>
              </TouchableOpacity>
            </View>
            {abiertas.slice(0, 3).map(s => (
              <AuctionCard
                key={s.identificador}
                subasta={s}
                onPress={() => navigation.navigate('DetalleSubasta', { idSubasta: s.identificador })}
              />
            ))}
          </>
        )}

        {proximamente.length > 0 && (
          <>
            <Text style={[styles.sectionTitle, { marginTop: spacing(4) }]}>Próximamente</Text>
            {proximamente.slice(0, 2).map(s => (
              <AuctionCard
                key={s.identificador}
                subasta={s}
                compact
                onPress={() => navigation.navigate('DetalleSubasta', { idSubasta: s.identificador })}
              />
            ))}
          </>
        )}

        {!isLoading && abiertas.length === 0 && proximamente.length === 0 && (
          <Text style={styles.empty}>No hay subastas disponibles.</Text>
        )}

        <View style={{ height: spacing(8) }} />
      </ScrollView>
    </Screen>
  );
}

const styles = StyleSheet.create({
  pageTitle: {
    fontFamily: fonts.interBold,
    fontSize: 48,
    color: colors.blanco,
    textAlign: 'center',
    letterSpacing: -0.96,
    position: 'absolute',
    top: -36,
    alignSelf: 'center',
  },
  scroll: { flex: 1, marginTop: spacing(4) },
  welcome: {
    fontFamily: fonts.robotoMedium,
    fontSize: 28,
    color: colors.bordo,
    marginBottom: spacing(1),
  },
  subtitle: {
    fontFamily: fonts.robotoRegular,
    fontSize: 16,
    color: colors.grisTexto,
    marginBottom: spacing(5),
  },
  sectionHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: spacing(3),
  },
  sectionTitle: {
    fontFamily: fonts.robotoMedium,
    fontSize: 18,
    color: colors.negro,
    marginBottom: spacing(3),
  },
  verTodas: {
    fontFamily: fonts.robotoMedium,
    fontSize: 14,
    color: colors.bordo,
  },
  empty: {
    fontFamily: fonts.robotoRegular,
    fontSize: 15,
    color: colors.grisTexto,
    textAlign: 'center',
    marginTop: spacing(10),
  },
});
