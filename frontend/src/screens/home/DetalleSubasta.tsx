import React from 'react';
import { View, Text, StyleSheet, ScrollView, ActivityIndicator, TouchableOpacity } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import CategoryBadge from '../../components/CategoryBadge';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useSubasta, useCatalogo, useItemsCatalogo, usePujos } from '../../api';

export default function DetalleSubastaScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { idSubasta } = useRoute<any>().params;

  const { data: subasta, isLoading: loadSub } = useSubasta(idSubasta);
  const { data: catalogo, isLoading: loadCat } = useCatalogo(subasta?.catalogo ?? null);
  const { data: todosItems } = useItemsCatalogo();
  const { data: pujos } = usePujos();

  const items = (todosItems ?? []).filter(i => i.catalogo === subasta?.catalogo);

  const getPujaActual = (itemId: number) => {
    const ps = (pujos ?? []).filter(p => p.item === itemId);
    return ps.length > 0 ? Math.max(...ps.map(p => p.importe)) : null;
  };

  if (loadSub) {
    return (
      <Screen>
        <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(10) }} />
      </Screen>
    );
  }

  if (!subasta) {
    return (
      <Screen>
        <Text style={styles.error}>Subasta no encontrada.</Text>
      </Screen>
    );
  }

  return (
    <Screen>
      <Text style={styles.pageTitle}>DETALLE</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <View style={styles.headerRow}>
          <CategoryBadge categoria={subasta.categoria} />
          <View style={[styles.estadoBadge, subasta.estado === 'abierta' ? styles.abierta : styles.cerrada]}>
            <Text style={styles.estadoText}>{subasta.estado === 'abierta' ? 'ACTIVA' : 'FINALIZADA'}</Text>
          </View>
        </View>

        <Text style={styles.ubicacion}>{subasta.ubicacion ?? 'Subasta'}</Text>
        <Text style={styles.meta}>{subasta.fecha} {subasta.hora ? `• ${subasta.hora}` : ''}</Text>
        {subasta.capacidadAsistentes != null && (
          <Text style={styles.meta}>Capacidad: {subasta.capacidadAsistentes} asistentes</Text>
        )}

        {loadCat ? (
          <ActivityIndicator color={colors.bordo} style={{ marginVertical: spacing(4) }} />
        ) : catalogo ? (
          <View style={styles.catalogoBox}>
            <Text style={styles.sectionTitle}>Catálogo</Text>
            <Text style={styles.catalogoDesc}>{catalogo.descripcion}</Text>
          </View>
        ) : null}

        {items.length > 0 && (
          <>
            <Text style={styles.sectionTitle}>Items ({items.length})</Text>
            {items.map(item => {
              const puja = getPujaActual(item.identificador);
              return (
                <TouchableOpacity
                  key={item.identificador}
                  style={styles.itemCard}
                  onPress={() => navigation.navigate('DetalleItemVendido', { idItem: item.identificador })}
                >
                  <View style={styles.itemRow}>
                    <View style={{ flex: 1 }}>
                      <Text style={styles.itemPrecio}>Base: ${item.precioBase?.toLocaleString('es-AR')}</Text>
                      {puja != null && (
                        <Text style={styles.itemPuja}>Puja actual: <Text style={styles.pujaValor}>${puja.toLocaleString('es-AR')}</Text></Text>
                      )}
                      <Text style={styles.itemComision}>Comisión: {item.comision}%</Text>
                    </View>
                    <Text style={styles.itemEstado}>{item.subastado === 'si' ? '✓ Vendido' : 'En oferta'}</Text>
                  </View>
                </TouchableOpacity>
              );
            })}
          </>
        )}

        {subasta.estado === 'abierta' && (
          <ButtonDanger
            label="Participar en esta subasta"
            onPress={() => navigation.navigate('ParticiparSubasta', { idSubasta: subasta.identificador })}
            fullWidth
            style={{ marginTop: spacing(5) }}
          />
        )}

        <View style={{ height: spacing(8) }} />
      </ScrollView>
    </Screen>
  );
}

const styles = StyleSheet.create({
  pageTitle: {
    fontFamily: fonts.interBold,
    fontSize: 40,
    color: colors.blanco,
    textAlign: 'center',
    letterSpacing: -0.96,
    position: 'absolute',
    top: -36,
    alignSelf: 'center',
  },
  headerRow: { flexDirection: 'row', alignItems: 'center', gap: spacing(3), marginBottom: spacing(3) },
  estadoBadge: { borderRadius: 4, paddingHorizontal: spacing(2), paddingVertical: 3 },
  abierta: { backgroundColor: '#E8F5E9' },
  cerrada: { backgroundColor: '#FFEBEE' },
  estadoText: { fontFamily: fonts.robotoMedium, fontSize: 11, letterSpacing: 0.5 },
  ubicacion: { fontFamily: fonts.robotoMedium, fontSize: 22, color: colors.bordo, marginBottom: spacing(2) },
  meta: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto, marginBottom: spacing(1) },
  catalogoBox: {
    backgroundColor: colors.grisInput + '55',
    borderRadius: 8,
    padding: spacing(4),
    marginVertical: spacing(4),
  },
  sectionTitle: {
    fontFamily: fonts.robotoMedium,
    fontSize: 16,
    color: colors.negro,
    marginBottom: spacing(3),
    marginTop: spacing(4),
  },
  catalogoDesc: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto },
  itemCard: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(3),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  itemRow: { flexDirection: 'row', alignItems: 'center' },
  itemPrecio: { fontFamily: fonts.robotoMedium, fontSize: 15, color: colors.negro, marginBottom: spacing(1) },
  itemPuja: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.grisTexto, marginBottom: spacing(1) },
  pujaValor: { fontFamily: fonts.robotoBold, color: colors.bordo },
  itemComision: { fontFamily: fonts.robotoRegular, fontSize: 12, color: colors.grisBorde },
  itemEstado: { fontFamily: fonts.robotoMedium, fontSize: 12, color: colors.grisTexto },
  error: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(10) },
});
