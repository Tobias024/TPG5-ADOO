import React from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import Screen from '../../components/Screen';
import { colors, fonts, spacing } from '../../theme';
import { useRegistros } from '../../api';
import { useAuth } from '../../context/AuthContext';
import { formatMoney, formatDate } from '../../utils/format';

export default function CasoAdquisicionScreen() {
  const { idCliente } = useAuth();
  const { data: registros, isLoading } = useRegistros();

  const misRegistros = (registros ?? []).filter(r => r.cliente === idCliente);

  return (
    <Screen>
      <Text style={styles.pageTitle}>ADQUISICIONES</Text>
      <Text style={styles.heading}>Mis adquisiciones</Text>
      <Text style={styles.sub}>Ítems que ganaste en subasta</Text>

      {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

      <FlatList
        data={misRegistros}
        keyExtractor={r => String(r.identificador)}
        renderItem={({ item: r }) => (
          <View style={styles.card}>
            <View style={styles.row}>
              <Text style={styles.label}>Subasta</Text>
              <Text style={styles.value}>#{r.subasta}</Text>
            </View>
            <View style={styles.row}>
              <Text style={styles.label}>Producto</Text>
              <Text style={styles.value}>#{r.producto}</Text>
            </View>
            <View style={styles.row}>
              <Text style={styles.label}>Importe</Text>
              <Text style={[styles.value, styles.importe]}>{formatMoney(r.importe)}</Text>
            </View>
            {r.comision != null && (
              <View style={styles.row}>
                <Text style={styles.label}>Comisión</Text>
                <Text style={styles.value}>{formatMoney(r.comision)}</Text>
              </View>
            )}
          </View>
        )}
        ListEmptyComponent={
          !isLoading ? (
            <Text style={styles.empty}>No tenés adquisiciones registradas.</Text>
          ) : null
        }
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingBottom: spacing(8) }}
      />
    </Screen>
  );
}

const styles = StyleSheet.create({
  pageTitle: {
    fontFamily: fonts.interBold,
    fontSize: 32,
    color: colors.blanco,
    textAlign: 'center',
    letterSpacing: -0.96,
    position: 'absolute',
    top: -36,
    alignSelf: 'center',
    width: '100%',
  },
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(1), marginTop: spacing(4) },
  sub: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto, marginBottom: spacing(5) },
  card: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(3),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  row: { flexDirection: 'row', justifyContent: 'space-between', paddingVertical: spacing(1) },
  label: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto },
  value: { fontFamily: fonts.robotoMedium, fontSize: 14, color: colors.negro },
  importe: { color: colors.bordo },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(10) },
});
