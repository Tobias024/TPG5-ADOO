import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useConfirmarProducto } from '../../api';

export default function SolicitudAceptadaScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { idProducto } = useRoute<any>().params ?? {};
  const confirmarProducto = useConfirmarProducto();

  const handleAceptar = async () => {
    if (!idProducto) {
      navigation.navigate('MisSubastas');
      return;
    }
    try {
      await confirmarProducto.mutateAsync({ id: idProducto, decision: 'aceptar' });
      Toast.show({ type: 'success', text1: 'Producto aceptado exitosamente' });
      navigation.navigate('MisSubastas');
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error' });
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>ACEPTADO</Text>
      <View style={styles.content}>
        <Text style={styles.icon}>🎉</Text>
        <Text style={styles.heading}>¡Solicitud aceptada!</Text>
        <Text style={styles.body}>
          Tu producto fue aprobado y será incluido en el catálogo de subasta.
        </Text>
        <ButtonDanger
          label="Confirmar aceptación"
          onPress={handleAceptar}
          loading={confirmarProducto.isPending}
          fullWidth
        />
      </View>
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
  content: { flex: 1, alignItems: 'center', justifyContent: 'center', paddingHorizontal: spacing(4) },
  icon: { fontSize: 64, marginBottom: spacing(5) },
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(3), textAlign: 'center' },
  body: {
    fontFamily: fonts.robotoRegular,
    fontSize: 15,
    color: colors.grisTexto,
    textAlign: 'center',
    lineHeight: 22,
    marginBottom: spacing(8),
  },
});
