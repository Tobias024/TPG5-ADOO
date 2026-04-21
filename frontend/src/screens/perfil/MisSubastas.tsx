import React from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useMisProductos } from '../../api';
import { useAuth } from '../../context/AuthContext';

const estadoLabel: Record<string, string> = {
  si: 'Disponible',
  no: 'No disponible',
};

export default function MisSubastasScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { idPersona } = useAuth();
  const { data: productos, isLoading } = useMisProductos(idPersona);

  return (
    <Screen>
      <Text style={styles.pageTitle}>MIS\nPRODUCTOS</Text>
      <Text style={styles.heading}>Mis productos</Text>

      {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

      <FlatList
        data={productos ?? []}
        keyExtractor={p => String(p.identificador)}
        renderItem={({ item: p }) => (
          <View style={styles.card}>
            <View style={{ flex: 1 }}>
              <Text style={styles.descripcion} numberOfLines={2}>{p.descripcionCatalogo ?? 'Producto sin descripción'}</Text>
              <Text style={styles.estado}>{estadoLabel[p.disponible ?? ''] ?? p.disponible}</Text>
              {p.seguro != null && <Text style={styles.seguro}>Seguro: ${p.seguro?.toLocaleString('es-AR')}</Text>}
            </View>
          </View>
        )}
        ListEmptyComponent={
          !isLoading ? <Text style={styles.empty}>No tenés productos registrados.</Text> : null
        }
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingBottom: spacing(4) }}
        style={{ flex: 1 }}
      />

      <ButtonDanger
        label="Subastar algo propio"
        onPress={() => navigation.navigate('SubastarAlgoPropio')}
        fullWidth
        style={{ marginTop: spacing(4) }}
      />
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
    top: -44,
    alignSelf: 'center',
    width: '100%',
  },
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(4), marginTop: spacing(4) },
  card: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(3),
    borderWidth: 1,
    borderColor: colors.grisInput,
    flexDirection: 'row',
    alignItems: 'center',
  },
  descripcion: { fontFamily: fonts.robotoMedium, fontSize: 15, color: colors.negro, marginBottom: spacing(1) },
  estado: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.grisTexto, marginBottom: spacing(1) },
  seguro: { fontFamily: fonts.robotoRegular, fontSize: 12, color: colors.grisBorde },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(6) },
});
