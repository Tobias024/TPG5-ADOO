import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import Screen from '../../components/Screen';
import ButtonDanger from '../../components/ButtonDanger';
import ButtonSecondary from '../../components/ButtonSecondary';
import { colors, fonts, spacing } from '../../theme';
import { useAuth } from '../../context/AuthContext';

export default function PerfilScreen() {
  const navigation = useNavigation<NativeStackNavigationProp<any>>();
  const { persona, cliente, idPersona, logout } = useAuth();

  if (!persona) {
    return (
      <Screen>
        <ActivityIndicator color={colors.bordo} style={{ marginTop: spacing(10) }} />
      </Screen>
    );
  }

  const menuItems = [
    { label: 'Editar perfil', screen: 'EditarPerfil' },
    { label: 'Métodos de pago', screen: 'AgregarMetodoPago' },
    { label: 'Mis métricas', screen: 'Metricas' },
    { label: 'Mis productos', screen: 'MisSubastas' },
    { label: 'Subastar algo propio', screen: 'SubastarAlgoPropio' },
    { label: 'Notificaciones', screen: 'Notificaciones' },
  ];

  return (
    <Screen>
      <Text style={styles.pageTitle}>PERFIL</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <View style={styles.avatarSection}>
          <View style={styles.avatar}>
            <Text style={styles.avatarInitial}>{persona.nombre?.charAt(0).toUpperCase() ?? '?'}</Text>
          </View>
          <Text style={styles.nombre}>{persona.nombre}</Text>
          <Text style={styles.email}>{persona.email}</Text>
          {cliente && (
            <View style={styles.categoriaBadge}>
              <Text style={styles.categoriaText}>{cliente.categoria?.toUpperCase() ?? 'COMÚN'}</Text>
            </View>
          )}
        </View>

        <View style={styles.infoBox}>
          <InfoRow label="Documento" value={persona.documento} />
          {persona.direccion && <InfoRow label="Dirección" value={persona.direccion} />}
          {persona.fechaNacimiento && <InfoRow label="Nacimiento" value={persona.fechaNacimiento} />}
          {cliente?.admitido && <InfoRow label="Estado" value={cliente.admitido === 'si' ? 'Habilitado' : 'Pendiente'} />}
        </View>

        <View style={styles.menu}>
          {menuItems.map(item => (
            <TouchableOpacity
              key={item.screen}
              style={styles.menuItem}
              onPress={() => navigation.navigate(item.screen)}
            >
              <Text style={styles.menuLabel}>{item.label}</Text>
              <Text style={styles.menuArrow}>›</Text>
            </TouchableOpacity>
          ))}
        </View>

        <ButtonSecondary label="Cerrar sesión" onPress={logout} fullWidth style={{ marginTop: spacing(4) }} />
        <View style={{ height: spacing(8) }} />
      </ScrollView>
    </Screen>
  );
}

function InfoRow({ label, value }: { label: string; value: string }) {
  return (
    <View style={infoRowStyles.row}>
      <Text style={infoRowStyles.label}>{label}</Text>
      <Text style={infoRowStyles.value}>{value}</Text>
    </View>
  );
}

const infoRowStyles = StyleSheet.create({
  row: { flexDirection: 'row', justifyContent: 'space-between', paddingVertical: spacing(2) },
  label: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto },
  value: { fontFamily: fonts.robotoMedium, fontSize: 14, color: colors.negro, flex: 1, textAlign: 'right' },
});

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
  avatarSection: { alignItems: 'center', marginTop: spacing(4), marginBottom: spacing(5) },
  avatar: {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: colors.bordo,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: spacing(3),
  },
  avatarInitial: { fontFamily: fonts.interBold, fontSize: 32, color: colors.blanco },
  nombre: { fontFamily: fonts.robotoMedium, fontSize: 20, color: colors.negro, marginBottom: spacing(1) },
  email: { fontFamily: fonts.robotoRegular, fontSize: 14, color: colors.grisTexto, marginBottom: spacing(2) },
  categoriaBadge: {
    backgroundColor: colors.bordo + '22',
    borderRadius: 12,
    paddingHorizontal: spacing(3),
    paddingVertical: spacing(1),
  },
  categoriaText: { fontFamily: fonts.robotoMedium, fontSize: 11, color: colors.bordo, letterSpacing: 0.5 },
  infoBox: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    padding: spacing(4),
    marginBottom: spacing(4),
    borderWidth: 1,
    borderColor: colors.grisInput,
  },
  menu: {
    backgroundColor: colors.blanco,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: colors.grisInput,
    overflow: 'hidden',
  },
  menuItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: spacing(4),
    paddingHorizontal: spacing(4),
    borderBottomWidth: 1,
    borderBottomColor: colors.grisInput,
  },
  menuLabel: { fontFamily: fonts.robotoRegular, fontSize: 15, color: colors.negro },
  menuArrow: { fontFamily: fonts.robotoMedium, fontSize: 20, color: colors.grisTexto },
});
