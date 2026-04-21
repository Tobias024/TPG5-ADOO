export type RootStackParamList = {
  Splash: undefined;
  Auth: undefined;
  Main: undefined;
};

export type AuthStackParamList = {
  Login: undefined;
  Registro: undefined;
  ConfirmacionRegistro1: { formData: Record<string, string> };
  ConfirmacionRegistro2: { formData: Record<string, string>; emailPass: Record<string, string> };
};

export type HomeStackParamList = {
  Home: undefined;
  SubastasActivas: undefined;
  DetalleSubasta: { idSubasta: number };
  ParticiparSubasta: { idSubasta: number; idItem: number };
  DetalleItemVendido: { idItem: number };
  CasoAdquisicion: { idRegistro: number };
};

export type DescubrirStackParamList = {
  Descubrir: undefined;
  DetalleSubasta: { idSubasta: number };
  ParticiparSubasta: { idSubasta: number; idItem: number };
};

export type PerfilStackParamList = {
  Perfil: undefined;
  EditarPerfil: undefined;
  AgregarMetodoPago: undefined;
  Metricas: undefined;
  MisSubastas: undefined;
  SubastarAlgoPropio: undefined;
  SolicitudEnviada: undefined;
  SolicitudAceptada: { idProducto: number };
  SolicitudRechazada: undefined;
  Notificaciones: undefined;
  AlertaNuevaSubasta: { idSubasta: number };
};
