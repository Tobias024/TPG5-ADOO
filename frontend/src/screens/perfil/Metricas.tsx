import React from 'react';
import { View, Text, StyleSheet, ScrollView, ActivityIndicator } from 'react-native';
import Screen from '../../components/Screen';
import { colors, fonts, spacing } from '../../theme';
import { useMetricas } from '../../api';
import { useAuth } from '../../context/AuthContext';
import { formatMoney } from '../../utils/format';

export default function MetricasScreen() {
  const { idCliente } = useAuth();
  const { data: metricas, isLoading } = useMetricas(idCliente);
  return (
    <Screen>
      <Text style={styles.pageTitle}>MÉTRICAS</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <Text style={styles.heading}>Mis métricas</Text>

        {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

        {metricas && (
          <>
            <View style={styles.statRow}>
              <StatCard label="Subastas\nparticipadas" value={String(metricas.subastasParticipadas ?? 0)} />
              <StatCard label="Subastas\nganadas" value={String(metricas.subastasGanadas ?? 0)} />
            </View>
            <View style={styles.statRow}>
              <StatCard label="Total\ncomprado" value={formatMoney(metricas.totalComprado ?? 0)} large />
              <StatCard label="Total\ncomisiones" value={formatMoney(metricas.totalComisiones ?? 0)} />
            </View>

            {metricas.categoria && (
              <View style={styles.categoriaBox}>
                <Text style={styles.categoriaLabel}>Categoría actual</Text>
                <Text style={styles.categoriaValue}>{metricas.categoria.toUpperCase()}</Text>
              </View>
            )}
          </>
        )}

        {!isLoading && !metricas && (
          <Text style={styles.empty}>No hay métricas disponibles.</Text>
        )}

        <View style={{ height: spacing(8) }} />
      </ScrollView>
    </Screen>
  );
}

function StatCard({ label, value, large }: { label: string; value: string; large?: boolean }) {
  return (
    <View style={statStyles.card}>
      <Text style={[statStyles.value, large && statStyles.valueSmall]}>{value}</Text>
      <Text style={statStyles.label}>{label}</Text>
    </View>
  );
}

const statStyles = StyleSheet.create({
  card: {
    flex: 1,
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    alignItems: 'center',
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  value: { fontFamily: fonts.interBold, fontSize: 28, color: colors.bordo, marginBottom: spacing(2) },
  valueSmall: { fontSize: 20 },
  label: { fontFamily: fonts.robotoRegular, fontSize: 12, color: colors.grisTexto, textAlign: 'center' },
});

const styles = StyleSheet.create({
  pageTitle: {
    fontFamily: fonts.interBold,
    fontSize: 36,
    color: colors.blanco,
    textAlign: 'center',
    letterSpacing: -0.96,
    position: 'absolute',
    top: -36,
    alignSelf: 'center',
  },
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(5), marginTop: spacing(4) },
  statRow: { flexDirection: 'row', gap: spacing(3), marginBottom: spacing(3) },
  categoriaBox: {
    backgroundColor: colors.bordo,
    borderRadius: 8,
    padding: spacing(5),
    alignItems: 'center',
    marginTop: spacing(4),
  },
  categoriaLabel: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.rosaClaro, marginBottom: spacing(2) },
  categoriaValue: { fontFamily: fonts.interBold, fontSize: 24, color: colors.blanco },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(10) },
});
