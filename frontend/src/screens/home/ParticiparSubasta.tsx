import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, ActivityIndicator, TouchableOpacity } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import {
  useSubasta, useItemsCatalogo, usePujos, useCrearPujo, useInscribirse, useMetodosPago,
} from '../../api';
import { useAuth } from '../../context/AuthContext';

export default function ParticiparSubastaScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { idSubasta } = useRoute<any>().params;
  const { idCliente } = useAuth();

  const { data: subasta } = useSubasta(idSubasta);
  const { data: todosItems } = useItemsCatalogo();
  const { data: pujos } = usePujos();
  const { data: metodos } = useMetodosPago(idCliente);
  const inscribirse = useInscribirse();
  const crearPujo = useCrearPujo();

  const [itemSeleccionado, setItemSeleccionado] = useState<number | null>(null);
  const [importe, setImporte] = useState('');
  const [inscrito, setInscrito] = useState(false);
  const [asistente, setAsistente] = useState<number | null>(null);

  const items = (todosItems ?? []).filter(i => i.catalogo === subasta?.catalogo && i.subastado !== 'si');

  const getPujaActual = (itemId: number) => {
    const ps = (pujos ?? []).filter(p => p.item === itemId);
    return ps.length > 0 ? Math.max(...ps.map(p => p.importe)) : 0;
  };

  const handleInscribirse = async () => {
    if (!idCliente) return;
    try {
      const result = await inscribirse.mutateAsync({ cliente: idCliente, subasta: idSubasta } as any);
      setAsistente((result as any).identificador);
      setInscrito(true);
      Toast.show({ type: 'success', text1: '¡Inscripto! Ya podés pujar.' });
    } catch (e: any) {
      if (e.message?.includes('ya') || e.message?.includes('409')) {
        setInscrito(true);
        Toast.show({ type: 'info', text1: 'Ya estabas inscripto.' });
      } else {
        Toast.show({ type: 'error', text1: e.message ?? 'Error al inscribirse' });
      }
    }
  };

  const handlePujar = async () => {
    if (!itemSeleccionado || !importe) {
      Toast.show({ type: 'error', text1: 'Seleccioná un item e ingresá un importe' });
      return;
    }
    const importeNum = parseFloat(importe.replace(',', '.'));
    if (isNaN(importeNum) || importeNum <= 0) {
      Toast.show({ type: 'error', text1: 'Importe inválido' });
      return;
    }
    try {
      await crearPujo.mutateAsync({ asistente: asistente!, item: itemSeleccionado, importe: importeNum, ganador: 'no' } as any);
      Toast.show({ type: 'success', text1: '¡Puja enviada!' });
      setImporte('');
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error al pujar' });
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>PARTICIPAR</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <Text style={styles.heading}>Participar en subasta</Text>
        <Text style={styles.sub}>{subasta?.ubicacion ?? `Subasta #${idSubasta}`}</Text>

        {!inscrito ? (
          <View style={styles.inscripcionBox}>
            <Text style={styles.info}>Primero debés inscribirte para poder pujar.</Text>
            {metodos && metodos.length === 0 && (
              <Text style={styles.warning}>Atención: no tenés métodos de pago registrados.</Text>
            )}
            <ButtonDanger
              label="Inscribirme"
              onPress={handleInscribirse}
              loading={inscribirse.isPending}
              fullWidth
            />
          </View>
        ) : (
          <>
            <Text style={styles.sectionTitle}>Seleccioná un item</Text>
            {items.length === 0 ? (
              <Text style={styles.empty}>No hay items disponibles para pujar.</Text>
            ) : (
              items.map(item => {
                const pujaActual = getPujaActual(item.identificador);
                const selected = itemSeleccionado === item.identificador;
                return (
                  <TouchableOpacity
                    key={item.identificador}
                    style={[styles.itemCard, selected && styles.itemSelected]}
                    onPress={() => setItemSeleccionado(item.identificador)}
                  >
                    <Text style={styles.itemPrecio}>Base: ${item.precioBase?.toLocaleString('es-AR')}</Text>
                    {pujaActual > 0 && (
                      <Text style={styles.itemPuja}>Puja actual: <Text style={styles.pujaValor}>${pujaActual.toLocaleString('es-AR')}</Text></Text>
                    )}
                    <Text style={styles.itemMin}>Mínimo: ${(pujaActual > 0 ? pujaActual + 1 : item.precioBase).toLocaleString('es-AR')}</Text>
                  </TouchableOpacity>
                );
              })
            )}

            {itemSeleccionado && (
              <View style={{ marginTop: spacing(4) }}>
                <InputChip
                  label="Tu oferta ($)"
                  placeholder={`Mínimo ${(getPujaActual(itemSeleccionado) > 0 ? getPujaActual(itemSeleccionado) + 1 : items.find(i => i.identificador === itemSeleccionado)?.precioBase ?? 0).toLocaleString('es-AR')}`}
                  value={importe}
                  onChangeText={setImporte}
                  keyboardType="numeric"
                />
                <ButtonDanger
                  label="Enviar puja"
                  onPress={handlePujar}
                  loading={crearPujo.isPending}
                  fullWidth
                />
              </View>
            )}
          </>
        )}
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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(1) },
  sub: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto, marginBottom: spacing(5) },
  inscripcionBox: { backgroundColor: colors.grisInput + '44', borderRadius: 8, padding: spacing(4), marginBottom: spacing(4) },
  info: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.negro, marginBottom: spacing(3) },
  warning: { fontFamily: fonts.robotoRegular, fontSize: 13, color: '#B45309', marginBottom: spacing(3) },
  sectionTitle: { fontFamily: fonts.robotoMedium, fontSize: 16, color: colors.negro, marginBottom: spacing(3) },
  itemCard: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(3),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  itemSelected: { borderColor: colors.bordo, borderWidth: 2 },
  itemPrecio: { fontFamily: fonts.robotoMedium, fontSize: 15, color: colors.negro, marginBottom: spacing(1) },
  itemPuja: { fontFamily: fonts.robotoRegular, fontSize: 13, color: colors.grisTexto, marginBottom: spacing(1) },
  pujaValor: { fontFamily: fonts.robotoBold, color: colors.bordo },
  itemMin: { fontFamily: fonts.robotoRegular, fontSize: 12, color: colors.grisBorde },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(4) },
});
