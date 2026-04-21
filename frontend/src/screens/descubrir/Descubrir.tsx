import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TextInput, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import AuctionCard from '../../components/AuctionCard';
import CategoryBadge from '../../components/CategoryBadge';
import { colors, fonts, spacing, categoryColors } from '../../theme';
import { useSubastas } from '../../api';
import { CategoriaSubasta } from '../../types';

const CATEGORIAS: CategoriaSubasta[] = ['comun', 'especial', 'plata', 'oro', 'platino'];

export default function DescubrirScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { data: subastas, isLoading } = useSubastas();
  const [busqueda, setBusqueda] = useState('');

  const filtradas = (subastas ?? []).filter(s =>
    !busqueda || s.ubicacion?.toLowerCase().includes(busqueda.toLowerCase()) || s.categoria?.toLowerCase().includes(busqueda.toLowerCase())
  );

  const porCategoria = CATEGORIAS.reduce<Record<string, typeof filtradas>>((acc, cat) => {
    const grupo = filtradas.filter(s => s.categoria === cat);
    if (grupo.length > 0) acc[cat] = grupo;
    return acc;
  }, {});

  return (
    <Screen>
      <Text style={styles.pageTitle}>DESCUBRIR</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <Text style={styles.heading}>Explorar subastas</Text>

        <TextInput
          style={styles.searchInput}
          placeholder="Buscar por categoría o ubicación..."
          placeholderTextColor={colors.grisTexto}
          value={busqueda}
          onChangeText={setBusqueda}
        />

        {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

        {!isLoading && Object.keys(porCategoria).length === 0 && (
          <Text style={styles.empty}>No se encontraron subastas.</Text>
        )}

        {Object.entries(porCategoria).map(([cat, grupo]) => (
          <View key={cat} style={styles.categoriaSection}>
            <View style={styles.catHeader}>
              <View style={[styles.catDot, { backgroundColor: categoryColors[cat as CategoriaSubasta] ?? colors.grisTexto }]} />
              <Text style={styles.catTitle}>{cat.charAt(0).toUpperCase() + cat.slice(1)}</Text>
              <Text style={styles.catCount}>{grupo.length} subasta{grupo.length !== 1 ? 's' : ''}</Text>
            </View>
            {grupo.map(s => (
              <AuctionCard
                key={s.identificador}
                subasta={s}
                compact
                onPress={() => navigation.navigate('DetalleSubasta', { idSubasta: s.identificador })}
              />
            ))}
          </View>
        ))}

        <View style={{ height: spacing(8) }} />
      </ScrollView>
    </Screen>
  );
}

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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(4), marginTop: spacing(4) },
  searchInput: {
    backgroundColor: colors.grisInput,
    borderRadius: 8,
    paddingHorizontal: spacing(4),
    paddingVertical: spacing(3),
    fontFamily: fonts.robotoRegular,
    fontSize: 14,
    color: colors.negro,
    marginBottom: spacing(5),
    borderWidth: 1,
    borderColor: colors.grisBorde,
  },
  categoriaSection: { marginBottom: spacing(4) },
  catHeader: { flexDirection: 'row', alignItems: 'center', marginBottom: spacing(3) },
  catDot: { width: 12, height: 12, borderRadius: 6, marginRight: spacing(2) },
  catTitle: { fontFamily: fonts.robotoMedium, fontSize: 16, color: colors.negro, flex: 1 },
  catCount: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.grisTexto },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(10) },
});
