import React from 'react';
import { View, ScrollView, StyleSheet, useWindowDimensions } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { colors, radii, spacing } from '../theme';

interface Props {
  children: React.ReactNode;
  scrollable?: boolean;
  noPadding?: boolean;
}

export default function Screen({ children, scrollable = true, noPadding = false }: Props) {
  const { height } = useWindowDimensions();
  const inner = (
    <View style={[styles.inner, noPadding && { padding: 0 }]}>
      {children}
    </View>
  );

  return (
    <SafeAreaView style={styles.outer} edges={['top', 'left', 'right']}>
      <View style={[styles.card, { minHeight: height * 0.88 }]}>
        {scrollable ? (
          <ScrollView showsVerticalScrollIndicator={false} contentContainerStyle={{ flexGrow: 1 }}>
            {inner}
          </ScrollView>
        ) : inner}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  outer: {
    flex: 1,
    backgroundColor: colors.bordo,
    alignItems: 'center',
    paddingTop: spacing(4),
  },
  card: {
    width: '94%',
    backgroundColor: colors.crema,
    borderRadius: radii.lg,
    overflow: 'hidden',
    flex: 1,
  },
  inner: {
    padding: spacing(6),
    flexGrow: 1,
  },
});
