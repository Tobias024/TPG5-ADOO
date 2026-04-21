import React from 'react';
import { View, TextInput, Text, StyleSheet, TextInputProps } from 'react-native';
import { colors, fonts, radii, spacing } from '../theme';

interface Props extends TextInputProps {
  label?: string;
  error?: string;
}

export default function InputChip({ label, error, style, ...props }: Props) {
  return (
    <View style={styles.wrapper}>
      {label && <Text style={styles.label}>{label}</Text>}
      <TextInput
        style={[styles.input, error ? styles.inputError : {}, style as any]}
        placeholderTextColor={colors.grisBorde}
        {...props}
      />
      {error && <Text style={styles.error}>{error}</Text>}
    </View>
  );
}

const styles = StyleSheet.create({
  wrapper: { width: '100%', marginBottom: spacing(4) },
  label: {
    fontFamily: fonts.robotoMedium,
    fontSize: 14,
    lineHeight: 20,
    letterSpacing: 0.1,
    color: colors.negro,
    marginBottom: spacing(1),
  },
  input: {
    backgroundColor: colors.grisInput,
    borderColor: colors.grisBorde,
    borderWidth: 1,
    borderRadius: radii.sm,
    paddingHorizontal: spacing(4),
    paddingVertical: spacing(3),
    fontFamily: fonts.robotoMedium,
    fontSize: 14,
    color: colors.grisTexto,
    width: '100%',
  },
  inputError: { borderColor: colors.error },
  error: {
    fontFamily: fonts.interRegular,
    fontSize: 12,
    color: colors.error,
    marginTop: spacing(1),
  },
});
