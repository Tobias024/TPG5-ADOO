import React from 'react';
import { View, Text, StyleSheet, ScrollView, ActivityIndicator } from 'react-native';
import { useRoute } from '@react-navigation/native';
import Screen from '../../components/Screen';
import { colors, fonts, spacing } from '../../theme';
import { useItemCatalogo, usePujos } from '../../api';

export default function DetalleItemVendidoScreen() {
  const { idItem } = useRoute<any>().params;
  const { data: item, isLoading } = useItemCatalogo(idItem);
  const { data: pujos } = usePujos();

  const pujasItem = (pujos ?? [])
    .filter(p => p.item === idItem)
    .sort((a, b) => b.importe - a.importe);

  const pujaGanadora = pujasItem.find(p => p.ganador === 'si') ?? pujasItem[0];

  if (isLoading) {
    return (
      <Screen>
        <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(10) }} />
      </Screen>
    );
  }

  return (
    <Screen>
      <Text style={styles.pageTitle}>ITEM</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <Text style={styles.heading}>Detalle del Item</Text>

        {item && (
          <View style={styles.infoBox}>
            <Row label="Precio base" value={`$${item.precioBase?.toLocaleString('es-AR')}`} />
            <Row label="Comisión" value={`${item.comision}%`} />
            <Row label="Estado" value={item.subastado === 'si' ? 'Subastado' : 'En oferta'} highlight={item.subastado === 'si'} />
          </View>
        )}

        <Text style={styles.sectionTitle}>Historial de pujas</Text>
        {pujasItem.length === 0 ? (
          <Text style={styles.empty}>No hay pujas registradas.</Text>
        ) : (
          pujasItem.map((p, idx) => (
            <View key={p.identificador ?? idx} style={[styles.pujaCard, p.ganador === 'si' && styles.pujaGanadora]}>
              <Text style={styles.pujaImporte}>${p.importe?.toLocaleString('es-AR')}</Text>
              {p.ganador === 'si' && <Text style={styles.ganadorLabel}>✓ Ganadora</Text>}
            </View>
          ))
        )}

        {pujaGanadora && (
          <View style={styles.resumenBox}>
            <Text style={styles.resumenTitle}>Puja más alta</Text>
            <Text style={styles.resumenValor}>${pujaGanadora.importe?.toLocaleString('es-AR')}</Text>
          </View>
        )}

        <View style={{ height: spacing(8) }} />
      </ScrollView>
    </Screen>
  );
}

function Row({ label, value, highlight }: { label: string; value: string; highlight?: boolean }) {
  return (
    <View style={rowStyles.row}>
      <Text style={rowStyles.label}>{label}</Text>
      <Text style={[rowStyles.value, highlight && rowStyles.highlight]}>{value}</Text>
    </View>
  );
}

const rowStyles = StyleSheet.create({
  row: { flexDirection: 'row', justifyContent: 'space-between', paddingVertical: spacing(2) },
  label: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto },
  value: { fontFamily: fonts.robotoMedium, fontSize: 14, color: colors.negro },
  highlight: { color: colors.bordo },
});

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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(4), marginTop: spacing(4) },
  infoBox: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(4),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  sectionTitle: { fontFamily: fonts.robotoMedium, fontSize: 16, color: colors.negro, marginBottom: spacing(3) },
  pujaCard: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(3),
    marginBottom: spacing(2),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  pujaGanadora: { borderColor: colors.bordo, borderWidth: 2 },
  pujaImporte: { fontFamily: fonts.robotoMedium, fontSize: 15, color: colors.negro },
  ganadorLabel: { fontFamily: fonts.robotoMedium, fontSize: 12, color: colors.bordo },
  resumenBox: {
    backgroundColor: colors.bordo,
    borderRadius: 8,
    padding: spacing(4),
    alignItems: 'center',
    marginTop: spacing(4),
  },
  resumenTitle: { fontFamily: fonts.robotoMedium, fontSize: 14, color: colors.rosaClaro, marginBottom: spacing(1) },
  resumenValor: { fontFamily: fonts.interBold, fontSize: 28, color: colors.blanco },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(4) },
});
