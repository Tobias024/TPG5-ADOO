import React from 'react';
import { TouchableOpacity, Text, StyleSheet, ActivityIndicator, ViewStyle } from 'react-native';
import { colors, fonts, radii, spacing } from '../theme';

interface Props {
  label: string;
  onPress: () => void;
  loading?: boolean;
  disabled?: boolean;
  style?: ViewStyle;
  fullWidth?: boolean;
}

export default function ButtonDanger({ label, onPress, loading, disabled, style, fullWidth }: Props) {
  return (
    <TouchableOpacity
      style={[styles.btn, fullWidth && styles.full, (disabled || loading) && styles.disabled, style]}
      onPress={onPress}
      disabled={disabled || loading}
      activeOpacity={0.8}
    >
      {loading ? (
        <ActivityIndicator color={colors.rosaClaro} size="small" />
      ) : (
        <Text style={styles.label}>{label}</Text>
      )}
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  btn: {
    backgroundColor: colors.bordo,
    borderColor: colors.bordo,
    borderWidth: 1,
    borderRadius: radii.sm,
    paddingVertical: spacing(3),
    paddingHorizontal: spacing(3),
    alignItems: 'center',
    justifyContent: 'center',
    minWidth: 120,
  },
  full: { width: '100%' },
  disabled: { opacity: 0.6 },
  label: {
    fontFamily: fonts.interRegular,
    fontSize: 16,
    color: colors.rosaClaro,
    textAlign: 'center',
  },
});
