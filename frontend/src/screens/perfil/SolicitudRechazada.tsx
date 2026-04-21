import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import ButtonDanger from '../../components/ButtonDanger';
import ButtonSecondary from '../../components/ButtonSecondary';
import { colors, fonts, spacing } from '../../theme';

export default function SolicitudRechazadaScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();

  return (
    <Screen>
      <Text style={styles.pageTitle}>RECHAZADO</Text>
      <View style={styles.content}>
        <Text style={styles.icon}>✗</Text>
        <Text style={styles.heading}>Solicitud rechazada</Text>
        <Text style={styles.body}>
          Tu producto no fue aprobado en esta ocasión. Podés revisar los requisitos y volver a enviarlo.
        </Text>
        <ButtonDanger
          label="Volver a intentar"
          onPress={() => navigation.navigate('SubastarAlgoPropio')}
          fullWidth
          style={{ marginBottom: spacing(3) }}
        />
        <ButtonSecondary
          label="Ir al inicio"
          onPress={() => navigation.navigate('Home')}
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
  icon: { fontSize: 64, color: '#DC2626', marginBottom: spacing(5) },
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: '#DC2626', marginBottom: spacing(3), textAlign: 'center' },
  body: {
    fontFamily: fonts.robotoRegular,
    fontSize: 15,
    color: colors.grisTexto,
    textAlign: 'center',
    lineHeight: 22,
    marginBottom: spacing(8),
  },
});
