import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useAuth } from '../../context/AuthContext';

export default function LoginScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { login } = useAuth();
  const [documento, setDocumento] = useState('');
  const [contrasenia, setContrasenia] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    if (!documento || !contrasenia) {
      Toast.show({ type: 'error', text1: 'Completá todos los campos' });
      return;
    }
    setLoading(true);
    try {
      await login(documento, contrasenia);
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error al iniciar sesión' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>LOGIN</Text>
      <Text style={styles.welcome}>¡Bienvenido!</Text>
      <Text style={styles.subtitle}>INICIA SESIÓN</Text>

      <InputChip
        label="Documento"
        placeholder="Ingresá tu documento"
        value={documento}
        onChangeText={setDocumento}
        keyboardType="numeric"
        autoCapitalize="none"
      />
      <InputChip
        label="Contraseña"
        placeholder="Ingresá tu contraseña"
        value={contrasenia}
        onChangeText={setContrasenia}
        secureTextEntry
      />

      <TouchableOpacity style={styles.forgotLink}>
        <Text style={styles.forgotText}>olvidé mi contraseña</Text>
      </TouchableOpacity>

      <ButtonDanger label="Ingresar" onPress={handleLogin} loading={loading} fullWidth style={styles.btnLogin} />

      <View style={styles.registerRow}>
        <Text style={styles.registerText}>¿no tenés una cuenta? </Text>
        <TouchableOpacity onPress={() => navigation.navigate('Registro')}>
          <Text style={styles.registerLink}>Registrate</Text>
        </TouchableOpacity>
      </View>
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
  welcome: {
    fontFamily: fonts.robotoMedium,
    fontSize: 36,
    lineHeight: 44,
    color: colors.bordo,
    marginBottom: spacing(1),
    marginTop: spacing(4),
  },
  subtitle: {
    fontFamily: fonts.robotoMedium,
    fontSize: 24,
    lineHeight: 32,
    color: colors.negro,
    marginBottom: spacing(6),
  },
  forgotLink: { alignSelf: 'flex-end', marginBottom: spacing(6) },
  forgotText: {
    fontFamily: fonts.robotoMedium,
    fontSize: 16,
    color: colors.negro,
    letterSpacing: 0.15,
  },
  btnLogin: { marginBottom: spacing(6) },
  registerRow: { flexDirection: 'row', justifyContent: 'center', alignItems: 'center' },
  registerText: { fontFamily: fonts.robotoRegular, fontSize: 16, color: colors.negro },
  registerLink: { fontFamily: fonts.robotoBold, fontSize: 16, color: colors.bordo },
});
