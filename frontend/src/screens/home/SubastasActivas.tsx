import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, TextInput, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import AuctionCard from '../../components/AuctionCard';
import CategoryBadge from '../../components/CategoryBadge';
import { colors, fonts, spacing } from '../../theme';
import { useSubastas } from '../../api';
import { CategoriaSubasta } from '../../types';

const CATEGORIAS: CategoriaSubasta[] = ['comun', 'especial', 'plata', 'oro', 'platino'];

export default function SubastasActivasScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { data: subastas, isLoading } = useSubastas();
  const [filtro, setFiltro] = useState<CategoriaSubasta | null>(null);
  const [busqueda, setBusqueda] = useState('');

  const lista = (subastas ?? []).filter(s => {
    const matchCat = !filtro || s.categoria === filtro;
    const matchBusq = !busqueda || s.ubicacion?.toLowerCase().includes(busqueda.toLowerCase());
    return matchCat && matchBusq;
  });

  return (
    <Screen>
      <Text style={styles.pageTitle}>SUBASTAS</Text>
      <Text style={styles.heading}>Todas las subastas</Text>

      <TextInput
        style={styles.searchInput}
        placeholder="Buscar por ubicación..."
        placeholderTextColor={colors.grisTexto}
        value={busqueda}
        onChangeText={setBusqueda}
      />

      <View style={styles.filtros}>
        <TouchableOpacity onPress={() => setFiltro(null)}>
          <View style={[styles.todosBadge, !filtro && styles.todosActive]}>
            <Text style={[styles.todosText, !filtro && styles.todosActiveText]}>Todas</Text>
          </View>
        </TouchableOpacity>
        {CATEGORIAS.map(cat => (
          <TouchableOpacity key={cat} onPress={() => setFiltro(filtro === cat ? null : cat)}>
            <CategoryBadge categoria={cat} style={filtro === cat ? { opacity: 1 } : { opacity: 0.5 }} />
          </TouchableOpacity>
        ))}
      </View>

      {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

      <FlatList
        data={lista}
        keyExtractor={s => String(s.identificador)}
        renderItem={({ item }) => (
          <AuctionCard
            subasta={item}
            onPress={() => navigation.navigate('DetalleSubasta', { idSubasta: item.identificador })}
          />
        )}
        ListEmptyComponent={!isLoading ? <Text style={styles.empty}>No hay subastas.</Text> : null}
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingBottom: spacing(8) }}
      />
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
  heading: {
    fontFamily: fonts.robotoMedium,
    fontSize: 24,
    color: colors.bordo,
    marginBottom: spacing(4),
    marginTop: spacing(4),
  },
  searchInput: {
    backgroundColor: colors.grisInput,
    borderRadius: 8,
    paddingHorizontal: spacing(4),
    paddingVertical: spacing(3),
    fontFamily: fonts.robotoRegular,
    fontSize: 14,
    color: colors.negro,
    marginBottom: spacing(3),
    borderWidth: 1,
    borderColor: colors.grisBorde,
  },
  filtros: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: spacing(2),
    marginBottom: spacing(4),
  },
  todosBadge: {
    paddingHorizontal: spacing(3),
    paddingVertical: spacing(1),
    borderRadius: 20,
    borderWidth: 1,
    borderColor: colors.grisBorde,
  },
  todosActive: { backgroundColor: colors.bordo, borderColor: colors.bordo },
  todosText: { fontFamily: fonts.robotoMedium, fontSize: 12, color: colors.grisTexto },
  todosActiveText: { color: colors.blanco },
  empty: {
    fontFamily: fonts.robotoRegular,
    fontSize: 15,
    color: colors.grisTexto,
    textAlign: 'center',
    marginTop: spacing(10),
  },
});
