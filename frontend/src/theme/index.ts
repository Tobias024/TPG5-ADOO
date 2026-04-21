export const colors = {
  bordo: '#710000',
  crema: '#F5ECE4',
  rosaClaro: '#FEE9E7',
  grisInput: '#D9D9D9',
  grisBorde: '#9D9D9D',
  grisTexto: '#727272',
  negro: '#000000',
  blanco: '#FFFFFF',
  dorado: '#D4A84B',
  violeta: '#6B4BA8',
  teal: '#2BA89A',
  success: '#2E7D32',
  error: '#C62828',
};

export const fonts = {
  interBold: 'Inter_700Bold',
  interRegular: 'Inter_400Regular',
  robotoBold: 'Roboto_700Bold',
  robotoMedium: 'Roboto_500Medium',
  robotoRegular: 'Roboto_400Regular',
};

export const typography = {
  titlePage: {
    fontFamily: fonts.interBold,
    fontSize: 48,
    lineHeight: 58,
    letterSpacing: -0.96,
    color: colors.blanco,
  },
  displaySmall: {
    fontFamily: fonts.robotoMedium,
    fontSize: 36,
    lineHeight: 44,
    color: colors.bordo,
  },
  headlineSmall: {
    fontFamily: fonts.robotoMedium,
    fontSize: 24,
    lineHeight: 32,
    color: colors.negro,
  },
  titleLarge: {
    fontFamily: fonts.robotoRegular,
    fontSize: 22,
    lineHeight: 28,
    color: colors.negro,
  },
  titleMedium: {
    fontFamily: fonts.robotoMedium,
    fontSize: 16,
    lineHeight: 24,
    letterSpacing: 0.15,
    color: colors.negro,
  },
  labelLarge: {
    fontFamily: fonts.robotoMedium,
    fontSize: 14,
    lineHeight: 20,
    letterSpacing: 0.1,
    color: colors.grisTexto,
  },
  body: {
    fontFamily: fonts.interRegular,
    fontSize: 16,
    lineHeight: 22,
    color: colors.negro,
  },
  bodySmall: {
    fontFamily: fonts.interRegular,
    fontSize: 14,
    lineHeight: 20,
    color: colors.grisTexto,
  },
};

export const radii = { xs: 4, sm: 8, md: 16, lg: 50, xl: 64 };

export const spacing = (n: number) => n * 4;

export const categoryColors: Record<string, string> = {
  comun: colors.grisTexto,
  especial: colors.teal,
  plata: '#90A4AE',
  oro: colors.dorado,
  platino: colors.violeta,
};
