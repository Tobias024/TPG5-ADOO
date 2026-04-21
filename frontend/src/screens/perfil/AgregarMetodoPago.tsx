import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ActivityIndicator, Alert } from 'react-native';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useMetodosPago, useAgregarMetodo, useEliminarMetodo } from '../../api';
import { useAuth } from '../../context/AuthContext';

export default function AgregarMetodoPagoScreen() {
  const { idCliente } = useAuth();
  const { data: metodos, isLoading } = useMetodosPago(idCliente);
  const agregarMetodo = useAgregarMetodo();
  const eliminarMetodo = useEliminarMetodo();

  const [proveedor, setProveedor] = useState('');
  const [ultDigitos, setUltDigitos] = useState('');

  const handleAgregar = async () => {
    if (!proveedor || !ultDigitos) {
      Toast.show({ type: 'error', text1: 'Completá todos los campos' });
      return;
    }
    try {
      await agregarMetodo.mutateAsync({ cliente: idCliente!, proveedor, ultimosDigitos: ultDigitos } as any);
      Toast.show({ type: 'success', text1: 'Método agregado' });
      setProveedor('');
      setUltDigitos('');
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error al agregar' });
    }
  };

  const handleEliminar = (id: number) => {
    Alert.alert('Eliminar método', '¿Estás seguro?', [
      { text: 'Cancelar', style: 'cancel' },
      {
        text: 'Eliminar',
        style: 'destructive',
        onPress: async () => {
          try {
            await eliminarMetodo.mutateAsync(id);
            Toast.show({ type: 'success', text1: 'Método eliminado' });
          } catch (e: any) {
            Toast.show({ type: 'error', text1: e.message ?? 'Error' });
          }
        },
      },
    ]);
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>PAGOS</Text>
      <Text style={styles.heading}>Métodos de pago</Text>

      {isLoading && <ActivityIndicator color={colors.bordo} />}

      <FlatList
        data={metodos ?? []}
        keyExtractor={m => String(m.identificador)}
        renderItem={({ item: m }) => (
          <View style={styles.card}>
            <View style={{ flex: 1 }}>
              <Text style={styles.proveedor}>{m.proveedor}</Text>
              <Text style={styles.digitos}>•••• {m.ultimosDigitos}</Text>
            </View>
            <TouchableOpacity onPress={() => handleEliminar(m.identificador!)} style={styles.deleteBtn}>
              <Text style={styles.deleteText}>Eliminar</Text>
            </TouchableOpacity>
          </View>
        )}
        ListEmptyComponent={!isLoading ? <Text style={styles.empty}>No tenés métodos registrados.</Text> : null}
        showsVerticalScrollIndicator={false}
        style={{ maxHeight: 220 }}
      />

      <Text style={styles.sectionTitle}>Agregar nuevo</Text>
      <InputChip label="Proveedor" placeholder="Ej: Visa, Mercado Pago" value={proveedor} onChangeText={setProveedor} />
      <InputChip label="Últimos 4 dígitos" placeholder="1234" value={ultDigitos} onChangeText={setUltDigitos} keyboardType="numeric" maxLength={4} />

      <ButtonDanger label="Agregar método" onPress={handleAgregar} loading={agregarMetodo.isPending} fullWidth />
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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(4), marginTop: spacing(4) },
  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(2),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  proveedor: { fontFamily: fonts.robotoMedium, fontSize: 15, color: colors.negro },
  digitos: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.grisTexto },
  deleteBtn: { padding: spacing(2) },
  deleteText: { fontFamily: fonts.robotoMedium, fontSize: 13, color: '#DC2626' },
  sectionTitle: { fontFamily: fonts.robotoMedium, fontSize: 16, color: colors.negro, marginBottom: spacing(3), marginTop: spacing(4) },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto, marginBottom: spacing(3) },
});
