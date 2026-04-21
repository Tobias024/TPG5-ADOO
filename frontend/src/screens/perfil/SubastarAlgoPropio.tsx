import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useCrearProducto } from '../../api';
import { useAuth } from '../../context/AuthContext';

export default function SubastarAlgoPropioScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { idPersona } = useAuth();
  const crearProducto = useCrearProducto();

  const [descripcionCatalogo, setDescripcionCatalogo] = useState('');
  const [descripcionCompleta, setDescripcionCompleta] = useState('');
  const [seguro, setSeguro] = useState('');

  const handleEnviar = async () => {
    if (!descripcionCatalogo) {
      Toast.show({ type: 'error', text1: 'La descripción es requerida' });
      return;
    }
    try {
      await crearProducto.mutateAsync({
        duenio: idPersona!,
        descripcionCatalogo,
        descripcionCompleta: descripcionCompleta || undefined,
        seguro: seguro ? parseFloat(seguro.replace(',', '.')) : undefined,
        disponible: 'no',
      } as any);
      navigation.replace('SolicitudEnviada');
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error al enviar' });
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>SUBASTAR</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <Text style={styles.heading}>Subastar algo propio</Text>
        <Text style={styles.sub}>Completá los datos de tu producto para enviarlo a revisión.</Text>

        <InputChip
          label="Descripción corta (catálogo)"
          placeholder="Ej: Guitarra eléctrica Fender"
          value={descripcionCatalogo}
          onChangeText={setDescripcionCatalogo}
        />
        <InputChip
          label="Descripción completa"
          placeholder="Detallá el estado, historia, etc."
          value={descripcionCompleta}
          onChangeText={setDescripcionCompleta}
        />
        <InputChip
          label="Valor del seguro ($)"
          placeholder="Opcional"
          value={seguro}
          onChangeText={setSeguro}
          keyboardType="numeric"
        />

        <ButtonDanger label="Enviar solicitud" onPress={handleEnviar} loading={crearProducto.isPending} fullWidth />
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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(2), marginTop: spacing(4) },
  sub: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto, marginBottom: spacing(5) },
});
