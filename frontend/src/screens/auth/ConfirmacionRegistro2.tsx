import React, { useState } from 'react';
import { Text, StyleSheet } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { createPersona, createCliente, agregarMetodo } from '../../api';

export default function ConfirmacionRegistro2Screen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const route = useRoute<any>();
  const { formData, emailPass } = route.params;

  const [proveedor, setProveedor] = useState('');
  const [ultDigitos, setUltDigitos] = useState('');
  const [loading, setLoading] = useState(false);

  const handleFinalizar = async () => {
    setLoading(true);
    try {
      await createPersona({
        documento: formData.documento,
        nombre: formData.nombre,
        direccion: formData.direccion,
        email: emailPass.email,
        contrasenia: emailPass.contrasenia,
        fechaNacimiento: formData.fechaNacimiento || undefined,
      });

      const personas = await import('../../api/client').then(m => m.default.get('/personas').then(r => r.data));
    } catch (_) {}

    try {
      const personaResp = await import('../../api/client').then(m =>
        m.default.get<any[]>('/personas').then(r => r.data.find((p: any) => p.documento === formData.documento))
      );
      if (!personaResp) throw new Error('No se pudo encontrar la persona creada');

      await createCliente({ identificador: personaResp.identificador, numeroPais: formData.paisId ? Number(formData.paisId) : undefined });

      if (proveedor && ultDigitos) {
        await agregarMetodo({ cliente: personaResp.identificador, proveedor, ultimosDigitos: ultDigitos });
      }

      Toast.show({ type: 'success', text1: '¡Cuenta creada! Iniciá sesión' });
      navigation.navigate('Login');
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error al registrarse' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>CONFIRMACION DEL REGISTRO 2/2</Text>
      <Text style={styles.heading}>Método de Pago</Text>
      <Text style={styles.step}>Paso 2 de 2 — Opcional</Text>

      <InputChip label="Proveedor" placeholder="Ej: Mercado Pago, Visa" value={proveedor} onChangeText={setProveedor} />
      <InputChip label="Últimos 4 dígitos" placeholder="1234" value={ultDigitos} onChangeText={setUltDigitos} keyboardType="numeric" maxLength={4} />

      <ButtonDanger label="Finalizar registro" onPress={handleFinalizar} loading={loading} fullWidth />
    </Screen>
  );
}

const styles = StyleSheet.create({
  pageTitle: {
    fontFamily: fonts.interBold,
    fontSize: 26,
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
