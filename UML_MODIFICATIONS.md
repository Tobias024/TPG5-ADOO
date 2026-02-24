# Modificaciones al Modelo UML para la Implementación Web

Este documento describe las diferencias entre el diagrama de clases UML original (`Version2UML.mdj`) y la implementación web con Spring Boot + React + PostgreSQL. Los patrones de diseño del dominio (State, Strategy, Observer, Adapter) se mantuvieron fielmente; los cambios se limitan a la infraestructura.

---

## 1. Cambios Requeridos en el Diagrama UML

### Clases a ELIMINAR

| Clase | Motivo | Reemplazo en Web |
|-------|--------|------------------|
| `IVista` | Frontend separado | React (navegador) |
| `VistaConsola` | Frontend separado | React (navegador) |
| `DatosCreacionPartido` | DTO REST | `PartidoRequest` |
| `DatosRegistroUsuario` | DTO REST | `RegisterRequest` |
| `CredencialesLogin` | DTO REST | `LoginRequest` |
| `CupoPartido` | Absorbido | Atributos en `Partido` |

### Modificaciones a `Partido`

Absorber `CupoPartido` agregando:

| Tipo | Nombre | Firma |
|------|--------|-------|
| Atributo | `cantidadJugadores` | `-cantidadJugadores: int` |
| Atributo | `jugadores` | `-jugadores: List<Usuario>` |
| Método | `inscribirJugador` | `+inscribirJugador(Usuario): boolean` |
| Método | `cupoCompleto` | `+cupoCompleto(): boolean` |

Eliminar la asociación de composición `Partido ◆─ CupoPartido`.

### Modificación al Controlador

Mantener `OrganizadorDeporteController` y agregar una **Nota UML**:
> "En la implementación web se divide en: AuthController, PartidoController, UsuarioController, DeporteController, NotificacionController"

### Resumen de Cambios

| Acción | Cantidad | Elementos |
|--------|----------|-----------|
| Eliminar | 6 clases | `IVista`, `VistaConsola`, `DatosCreacionPartido`, `DatosRegistroUsuario`, `CredencialesLogin`, `CupoPartido` |
| Agregar a `Partido` | 2 atributos, 2 métodos | Ver tabla arriba |
| Agregar | 1 nota UML | División del controlador |

---

## 2. Justificación: MVC con React

```
UML Original (Monolítico):
┌─────────────────────────────────────┐
│           Aplicación Java           │
│  ┌─────────┐ ┌──────────┐ ┌──────┐ │
│  │  Vista  │→│Controller│→│Model │ │
│  │(Consola)│ │          │ │      │ │
│  └─────────┘ └──────────┘ └──────┘ │
└─────────────────────────────────────┘

Implementación Web (MVC Distribuido):
┌──────────────┐    HTTP/REST    ┌─────────────────────────┐
│   Navegador  │ ←────────────→  │     Spring Boot         │
│  ┌────────┐  │                 │  ┌──────────┐ ┌──────┐ │
│  │  Vista │  │                 │  │Controller│→│Model │ │
│  │(React) │  │                 │  │  (REST)  │ │      │ │
│  └────────┘  │                 │  └──────────┘ └──────┘ │
└──────────────┘                 └─────────────────────────┘
```

**React ES la capa de Vista** — ejecuta en el navegador en lugar del servidor. El patrón MVC no se rompe; la comunicación cambia de llamadas Java a peticiones HTTP/JSON.

---

## 3. Clases Sin Cambios (Patrones de Diseño)

| Patrón | Clases |
|--------|--------|
| **State** | `IEstadoPartido`, `EstadoFaltanJugadores`, `EstadoArmado`, `EstadoConfirmado`, `EstadoEnJuego`, `EstadoFinalizado`, `EstadoCancelado` |
| **Strategy** | `IEstrategiaEmparejamiento`, `EmparejamientoLibre`, `EmparejamientoPorNivel`, `EmparejamientoPorCercania`, `EmparejamientoPorHistorial` |
| **Observer** | `ISujeto`, `IObserver`, `GestorObservadores`, `NotificadorObserver` |
| **Adapter** | `IAdapterMail`, `IAdapterPush`, `AdapterJavaMail`, `AdapterFireBase`, `IEstrategiaNotificacion`, `NotificacionEmail`, `NotificacionPush`, `ServicioNotificaciones` |
| **Servicios** | `ServicioUsuarios`, `ServicioPartidos`, `ServicioInscripcionPartido`, `ServicioEstadoPartido`, `ServicioCancelacionPartido`, `GestorFlujoPartido`, `ValidadorInscripcion` |
| **Entidades** | `Usuario`, `Partido`, `Deporte`, `Notificacion` |
| **Niveles** | `INivel`, `NivelBase`, `NivelPrincipiante`, `NivelIntermedio`, `NivelAvanzado` |

---

## 4. Adaptaciones de Infraestructura (No afectan el UML)

Estos cambios son de implementación y **no requieren modificar el diagrama UML**:

### Repositorios
- UML: `IRepositorioUsuarios` + `RepositorioUsuariosMemoria`
- Web: `UsuarioRepository extends JpaRepository<Usuario, Long>` (Spring Data JPA)

### Entidades JPA
- **Usuario**: agregado `id`, `latitud`, `longitud`; password con BCrypt; nivel como String
- **Partido**: agregado `id`, `nivelMaximo`, `fechaHora` como `LocalDateTime`; estado como String con `@PostLoad`
- **Notificación**: agregado `id`, `leida`
- **Deporte**: agregado `id`

### Seguridad (clases nuevas, no en UML)
- `JwtTokenProvider`, `JwtAuthFilter`, `UserDetailsServiceImpl`, `SecurityConfig`

---

## 5. Tabla Comparativa

| Aspecto | UML Original | Implementación Web |
|---------|--------------|-------------------|
| Vista | `IVista` / `VistaConsola` | Frontend React |
| Controlador | `OrganizadorDeporteController` | 5 REST Controllers |
| Repositorios | Interfaces + Memoria | Spring Data JPA |
| Persistencia | HashMap | PostgreSQL |
| Autenticación | No contemplada | JWT + Spring Security |
| `CupoPartido` | Clase separada | Embebido en `Partido` |
| Patrones de diseño | State, Strategy, Observer, Adapter | **Idénticos** |
