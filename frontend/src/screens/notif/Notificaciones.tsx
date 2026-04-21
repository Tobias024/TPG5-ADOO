import React from 'react';
import { Text, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import NotificationItem from '../../components/NotificationItem';
import { colors, fonts, spacing } from '../../theme';
import { useNotificaciones } from '../../api';
import { useAuth } from '../../context/AuthContext';

export default function NotificacionesScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { idCliente } = useAuth();
  const { data: notifs, isLoading } = useNotificaciones(idCliente);

  const handlePress = (tipo: string, notif: any) => {
    if (tipo === 'nueva_subasta') {
      navigation.navigate('DetalleSubasta', { idSubasta: notif.subasta });
    } else if (tipo === 'solicitud_aceptada') {
      navigation.navigate('SolicitudAceptada', { idProducto: notif.producto });
    } else if (tipo === 'solicitud_rechazada') {
      navigation.navigate('SolicitudRechazada');
    } else if (tipo === 'subasta_adquirida') {
      navigation.navigate('CasoAdquisicion');
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>NOTIF.</Text>
      <Text style={styles.heading}>Notificaciones</Text>

      {isLoading && <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(8) }} />}

      <FlatList
        data={notifs ?? []}
        keyExtractor={n => String(n.identificador)}
        renderItem={({ item: n }) => (
          <NotificationItem notif={n} onPress={() => handlePress(n.tipo, n)} />
        )}
        ListEmptyComponent={
          !isLoading ? <Text style={styles.empty}>No tenés notificaciones.</Text> : null
        }
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingBottom: spacing(8) }}
      />
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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(4), marginTop: spacing(4) },
  empty: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.grisTexto, textAlign: 'center', marginTop: spacing(10) },
});
