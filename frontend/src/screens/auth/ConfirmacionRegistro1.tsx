import React, { useState } from 'react';
import { Text, StyleSheet } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';

export default function ConfirmacionRegistro1Screen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const route = useRoute<any>();
  const { formData } = route.params;

  const [email, setEmail] = useState('');
  const [contrasenia, setContrasenia] = useState('');
  const [confirmar, setConfirmar] = useState('');

  const handleContinuar = () => {
    if (!email || !contrasenia) {
      Toast.show({ type: 'error', text1: 'Email y contraseña son requeridos' });
      return;
    }
    if (contrasenia !== confirmar) {
      Toast.show({ type: 'error', text1: 'Las contraseñas no coinciden' });
      return;
    }
    navigation.navigate('ConfirmacionRegistro2', {
      formData,
      emailPass: { email, contrasenia },
    });
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>CONFIRMACION DEL REGISTRO 1/2</Text>
      <Text style={styles.heading}>Confirmación de Registro</Text>
      <Text style={styles.step}>Paso 1 de 2</Text>

      <InputChip label="Email" placeholder="juan@mail.com" value={email} onChangeText={setEmail} keyboardType="email-address" autoCapitalize="none" />
      <InputChip label="Contraseña" placeholder="Mínimo 6 caracteres" value={contrasenia} onChangeText={setContrasenia} secureTextEntry />
      <InputChip label="Confirmar contraseña" placeholder="Repetí tu contraseña" value={confirmar} onChangeText={setConfirmar} secureTextEntry />

      <ButtonDanger label="Continuar" onPress={handleContinuar} fullWidth />
    </Screen>
  );
}

const styles = StyleSheet.create({
  pageTitle: {
    fontFamily: fonts.interBold,
    fontSize: 28,
    color: colors.blanco,
    textAlign: 'center',
    letterSpacing: -0.5,
    position: 'absolute',
    top: -44,
    alignSelf: 'center',
    width: '100%',
  },
  heading: {
    fontFamily: fonts.robotoMedium,
    fontSize: 24,
    color: colors.bordo,
    marginBottom: spacing(1),
    marginTop: spacing(4),
  },
  step: {
    fontFamily: fonts.robotoRegular,
    fontSize: 14,
    color: colors.grisTexto,
    marginBottom: spacing(5),
  },
});
