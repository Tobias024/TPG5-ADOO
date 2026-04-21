import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import ButtonDanger from '../../components/ButtonDanger';
import ButtonSecondary from '../../components/ButtonSecondary';
import { colors, fonts, spacing } from '../../theme';

export default function AlertaNuevaSubastaScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { notif } = useRoute<any>().params ?? {};

  return (
    <Screen>
      <Text style={styles.pageTitle}>ALERTA</Text>
      <View style={styles.content}>
        <Text style={styles.icon}>🔔</Text>
        <Text style={styles.heading}>Nueva subasta disponible</Text>
        {notif?.mensaje && <Text style={styles.mensaje}>{notif.mensaje}</Text>}
        <ButtonDanger
          label="Ver subasta"
          onPress={() => {
            if (notif?.subasta) {
              navigation.navigate('DetalleSubasta', { idSubasta: notif.subasta });
            } else {
              navigation.navigate('SubastasActivas');
            }
          }}
          fullWidth
          style={{ marginBottom: spacing(3) }}
        />
        <ButtonSecondary
          label="Ver todas las subastas"
          onPress={() => navigation.navigate('SubastasActivas')}
          fullWidth
        />
      </View>
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
  content: { flex: 1, alignItems: 'center', justifyContent: 'center', paddingHorizontal: spacing(4) },
  icon: { fontSize: 64, marginBottom: spacing(5) },
  heading: { fontFamily: fonts.robotoMedium, fontSize: 22, color: colors.bordo, marginBottom: spacing(3), textAlign: 'center' },
  mensaje: {
    fontFamily: fonts.robotoRegular,
    fontSize: 15,
    color: colors.grisTexto,
    textAlign: 'center',
    lineHeight: 22,
    marginBottom: spacing(8),
  },
});
