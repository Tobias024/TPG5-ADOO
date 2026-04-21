import React from 'react';
import { View, Text, StyleSheet, ViewStyle } from 'react-native';
import { colors, fonts, radii, spacing, categoryColors } from '../theme';
import { CategoriaSubasta } from '../types';

export default function CategoryBadge({ categoria, style }: { categoria: CategoriaSubasta; style?: ViewStyle }) {
  const bg = categoryColors[categoria] ?? colors.grisTexto;
  return (
    <View style={[styles.badge, { backgroundColor: bg + '22', borderColor: bg }, style]}>
      <Text style={[styles.text, { color: bg }]}>{categoria.toUpperCase()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  badge: {
    borderRadius: radii.xs,
    borderWidth: 1,
    paddingHorizontal: spacing(2),
    paddingVertical: 2,
    alignSelf: 'flex-start',
  },
  text: { fontFamily: fonts.robotoMedium, fontSize: 11, letterSpacing: 0.5 },
});
