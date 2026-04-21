import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { colors, fonts, radii, spacing } from '../theme';
import { Notificacion, TipoNotificacion } from '../types';

const tipoLabels: Record<TipoNotificacion, string> = {
  solicitud_aceptada: '✅ Solicitud Aceptada',
  solicitud_rechazada: '❌ Solicitud Rechazada',
  nueva_subasta: '🔔 Nueva Subasta',
  subasta_adquirida: '🏆 Adquisición',
};

export default function NotificationItem({ notif, onPress }: { notif: Notificacion; onPress?: () => void }) {
  const [expanded, setExpanded] = useState(false);
  return (
    <TouchableOpacity
      style={[styles.card, notif.leida === 'no' && styles.unread]}
      onPress={() => { setExpanded(e => !e); onPress?.(); }}
      activeOpacity={0.8}
    >
      <Text style={styles.tipo}>{tipoLabels[notif.tipo]}</Text>
      {notif.fecha && <Text style={styles.fecha}>{new Date(notif.fecha).toLocaleDateString('es-AR')}</Text>}
      {expanded && notif.mensaje && <Text style={styles.mensaje}>{notif.mensaje}</Text>}
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.blanco,
    borderRadius: radii.sm,
    padding: spacing(4),
    marginBottom: spacing(3),
    borderLeftWidth: 3,
    borderLeftColor: colors.grisInput,
    shadowColor: '#000',
    shadowOpacity: 0.06,
    shadowRadius: 3,
    elevation: 1,
  },
  unread: { borderLeftColor: colors.bordo },
  tipo: { fontFamily: fonts.robotoMedium, fontSize: 14, color: colors.negro },
  fecha: { fontFamily: fonts.interRegular, fontSize: 12, color: colors.grisTexto, marginTop: 2 },
  mensaje: { fontFamily: fonts.interRegular, fontSize: 14, color: colors.negro, marginTop: spacing(2), lineHeight: 20 },
});
