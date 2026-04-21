import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Toast from 'react-native-toast-message';
import Screen from '../../components/Screen';
import InputChip from '../../components/InputChip';
import ButtonDanger from '../../components/ButtonDanger';
import { colors, fonts, spacing } from '../../theme';
import { useUpdatePersona } from '../../api';
import { useAuth } from '../../context/AuthContext';

export default function EditarPerfilScreen() {
  const navigation = useNavigation<any>();
  const { persona, idPersona } = useAuth();
  const updatePersona = useUpdatePersona();

  const [nombre, setNombre] = useState(persona?.nombre ?? '');
  const [direccion, setDireccion] = useState(persona?.direccion ?? '');
  const [fechaNacimiento, setFechaNacimiento] = useState(persona?.fechaNacimiento ?? '');

  const handleGuardar = async () => {
    if (!nombre) {
      Toast.show({ type: 'error', text1: 'El nombre es requerido' });
      return;
    }
    try {
      await updatePersona.mutateAsync({
        id: idPersona!,
        data: { ...persona!, nombre, direccion, fechaNacimiento: fechaNacimiento || undefined },
      });
      Toast.show({ type: 'success', text1: 'Perfil actualizado' });
      navigation.goBack();
    } catch (e: any) {
      Toast.show({ type: 'error', text1: e.message ?? 'Error al actualizar' });
    }
  };

  return (
    <Screen>
      <Text style={styles.pageTitle}>EDITAR</Text>
      <ScrollView showsVerticalScrollIndicator={false} style={{ marginTop: spacing(4) }}>
        <Text style={styles.heading}>Editar perfil</Text>

        <InputChip label="Nombre completo" value={nombre} onChangeText={setNombre} placeholder="Juan Pérez" />
        <InputChip label="Dirección" value={direccion} onChangeText={setDireccion} placeholder="Av. Corrientes 1234" />
        <InputChip label="Fecha de nacimiento" value={fechaNacimiento} onChangeText={setFechaNacimiento} placeholder="DD/MM/AAAA" />

        <ButtonDanger label="Guardar cambios" onPress={handleGuardar} loading={updatePersona.isPending} fullWidth />
        <View style={{ height: spacing(8) }} />
      </ScrollView>
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
  heading: { fontFamily: fonts.robotoMedium, fontSize: 24, color: colors.bordo, marginBottom: spacing(5), marginTop: spacing(4) },
});
