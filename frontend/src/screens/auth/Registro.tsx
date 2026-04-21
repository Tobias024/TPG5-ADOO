import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { usePaises } from '../../api/paises';

export default function RegistroScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { data: paises } = usePaises();

  const [nombre, setNombre] = useState('');
  const [documento, setDocumento] = useState('');
  const [direccion, setDireccion] = useState('');
  const [fechaNacimiento, setFechaNacimiento] = useState('');
  const [paisId, setPaisId] = useState('');

  const handleContinuar = () => {
    if (!nombre || !documento) {
      Toast.show({ type: 'error', text1: 'Nombre y documento son requeridos' });
      return;
    }
    navigation.navigate('ConfirmacionRegistro1', {
      formData: { nombre, documento, direccion, fechaNacimiento, paisId },
    });
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>REGISTRO</Text>
      <Text style={styles.heading}>Registrate</Text>

      <InputChip label="Nombre completo" placeholder="Juan Pérez" value={nombre} onChangeText={setNombre} />
      <InputChip label="Documento" placeholder="DNI / Pasaporte" value={documento} onChangeText={setDocumento} keyboardType="numeric" />
      <InputChip label="Dirección" placeholder="Av. Corrientes 1234" value={direccion} onChangeText={setDireccion} />
      <InputChip label="Fecha de nacimiento" placeholder="DD/MM/AAAA" value={fechaNacimiento} onChangeText={setFechaNacimiento} />
      <InputChip label="País (número)" placeholder="Ej: 1 = Argentina" value={paisId} onChangeText={setPaisId} keyboardType="numeric" />

      <ButtonDanger label="Continuar" onPress={handleContinuar} fullWidth />
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
    fontSize: 36,
    color: colors.bordo,
    marginBottom: spacing(5),
    marginTop: spacing(4),
  },
});
