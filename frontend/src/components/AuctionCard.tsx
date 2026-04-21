import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image } from 'react-native';
import { colors, fonts, radii, spacing, categoryColors } from '../theme';
import { Subasta } from '../types';

interface Props {
  subasta: Subasta;
  puja?: number;
  onPress: () => void;
  compact?: boolean;
}

export default function AuctionCard({ subasta, puja, onPress, compact }: Props) {
  const catColor = categoryColors[subasta.categoria] ?? colors.grisTexto;

  return (
    <TouchableOpacity style={[styles.card, compact && styles.compact]} onPress={onPress} activeOpacity={0.85}>
      <View style={[styles.categoryBar, { backgroundColor: catColor }]} />
      <View style={styles.content}>
        <View style={styles.header}>
          <Text style={styles.categoria}>{subasta.categoria.toUpperCase()}</Text>
          <View style={[styles.estadoBadge, subasta.estado === 'abierta' ? styles.abierta : styles.cerrada]}>
            <Text style={styles.estadoText}>{subasta.estado === 'abierta' ? 'ACTIVA' : 'FINALIZADA'}</Text>
          </View>
        </View>
        <Text style={styles.titulo} numberOfLines={2}>{subasta.ubicacion ?? 'Subasta'}</Text>
        {puja != null && (
          <Text style={styles.puja}>Puja actual: <Text style={styles.pujaValor}>${puja.toLocaleString('es-AR')}</Text></Text>
        )}
        <Text style={styles.fecha}>{subasta.fecha} {subasta.hora ? `• ${subasta.hora}` : ''}</Text>
        <TouchableOpacity style={styles.btn} onPress={onPress}>
          <Text style={styles.btnText}>{subasta.estado === 'abierta' ? 'Participar' : 'Ver detalle'}</Text>
        </TouchableOpacity>
      </View>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.blanco,
    borderRadius: radii.sm,
    marginBottom: spacing(4),
    flexDirection: 'row',
    overflow: 'hidden',
    shadowColor: '#000',
    shadowOpacity: 0.08,
    shadowRadius: 4,
    elevation: 2,
  },
  compact: { marginBottom: spacing(2) },
  categoryBar: { width: 6 },
  content: { flex: 1, padding: spacing(4) },
  header: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: spacing(2) },
  categoria: { fontFamily: fonts.robotoMedium, fontSize: 11, letterSpacing: 0.5, color: colors.grisTexto },
  estadoBadge: { borderRadius: 4, paddingHorizontal: spacing(2), paddingVertical: 2 },
  abierta: { backgroundColor: '#E8F5E9' },
  cerrada: { backgroundColor: '#FFEBEE' },
  estadoText: { fontFamily: fonts.robotoMedium, fontSize: 10, letterSpacing: 0.5 },
  titulo: { fontFamily: fonts.robotoMedium, fontSize: 15, color: colors.negro, marginBottom: spacing(2) },
  puja: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.grisTexto, marginBottom: spacing(1) },
  pujaValor: { fontFamily: fonts.robotoBold, color: colors.bordo },
  fecha: { fontFamily: fonts.interRegular, fontSize: 12, color: colors.grisBorde, marginBottom: spacing(3) },
  btn: {
    backgroundColor: colors.bordo,
    borderRadius: radii.sm,
    paddingVertical: spacing(2),
    paddingHorizontal: spacing(3),
    alignSelf: 'flex-start',
  },
  btnText: { fontFamily: fonts.interRegular, fontSize: 13, color: colors.rosaClaro },
});
