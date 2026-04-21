export type EstadoPersona = 'activo' | 'inactivo';
export type EstadoSubasta = 'abierta' | 'cerrada';
export type CategoriaSubasta = 'comun' | 'especial' | 'plata' | 'oro' | 'platino';
export type SiNo = 'si' | 'no';
export type TipoNotificacion = 'solicitud_aceptada' | 'solicitud_rechazada' | 'nueva_subasta' | 'subasta_adquirida';
export type DecisionProducto = 'aceptar' | 'rechazar';

export interface LoginRequest { documento: string; contrasenia: string; }
export interface LoginResponse { token: string; idPersona: number; }

export interface Pais {
  numero: number;
  nombre: string;
  nombreCorto?: string;
  capital: string;
  nacionalidad: string;
  idiomas: string;
}

export interface Persona {
  identificador?: number;
  documento: string;
  nombre: string;
  direccion?: string;
  estado?: EstadoPersona;
  foto?: string;
  email?: string;
  contrasenia?: string;
  fechaNacimiento?: string;
}

export interface ClienteRegistro { identificador: number; numeroPais?: number; }

export interface Cliente {
  identificador: number;
  numeroPais?: number;
  admitido?: SiNo;
  categoria?: CategoriaSubasta;
  verificador?: number;
}

export interface MetricasCliente {
  totalGastado: number;
  subastasParticipadas: number;
  subastasGanadas: number;
  tasaExito: number;
  mayorPuja: number;
}

export interface Subasta {
  identificador: number;
  fecha: string;
  hora: string;
  estado: EstadoSubasta;
  subastador?: number;
  ubicacion?: string;
  capacidadAsistentes?: number;
  categoria: CategoriaSubasta;
}

export interface Catalogo {
  identificador: number;
  descripcion?: string;
  subasta?: number;
  responsable?: number;
}

export interface ItemCatalogo {
  identificador: number;
  catalogo?: number;
  producto?: number;
  precioBase?: number;
  comision?: number;
  subastado?: SiNo;
}

export interface Asistente {
  identificador?: number;
  numeroPostor?: number;
  cliente: number;
  subasta: number;
}

export interface Pujo {
  identificador?: number;
  asistente: number;
  item: number;
  importe: number;
  ganador?: SiNo;
}

export interface Producto {
  identificador?: number;
  fecha?: string;
  disponible?: SiNo;
  descripcionCatalogo?: string;
  descripcionCompleta: string;
  duenio: number;
  seguro?: string;
}

export interface Foto {
  identificador?: number;
  producto: number;
  foto: string;
}

export interface RegistroSubasta {
  identificador: number;
  subasta?: number;
  duenio?: number;
  producto?: number;
  cliente?: number;
  importe?: number;
  comision?: number;
}

export interface MetodoPago {
  identificador?: number;
  cliente: number;
  proveedor: string;
  ultimosDigitos: string;
}

export interface Notificacion {
  identificador: number;
  cliente?: number;
  tipo: TipoNotificacion;
  mensaje?: string;
  leida?: SiNo;
  fecha?: string;
}
