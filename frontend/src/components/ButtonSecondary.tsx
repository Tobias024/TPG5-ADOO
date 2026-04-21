import React from 'react';
import { TouchableOpacity, Text, StyleSheet, ViewStyle } from 'react-native';
import { colors, fonts, radii, spacing } from '../theme';

interface Props {
  label: string;
  onPress: () => void;
  disabled?: boolean;
  style?: ViewStyle;
  fullWidth?: boolean;
}

export default function ButtonSecondary({ label, onPress, disabled, style, fullWidth }: Props) {
  return (
    <TouchableOpacity
      style={[styles.btn, fullWidth && styles.full, disabled && styles.disabled, style]}
      onPress={onPress}
      disabled={disabled}
      activeOpacity={0.8}
    >
      <Text style={styles.label}>{label}</Text>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  btn: {
    backgroundColor: 'transparent',
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
  disabled: { opacity: 0.5 },
  label: {
    fontFamily: fonts.interRegular,
    fontSize: 16,
    color: colors.bordo,
    textAlign: 'center',
  },
});
