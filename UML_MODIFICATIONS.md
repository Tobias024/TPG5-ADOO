# Modificaciones al Modelo UML para la Implementación Web

Este documento describe las diferencias entre el diagrama de clases UML original (`Version2UML.mdj`) y la implementación web con Spring Boot + React + PostgreSQL. Los patrones de diseño del dominio (State, Strategy, Observer, Adapter, Facade y Factory como apoyo) se mantuvieron fielmente; los cambios se limitan a la infraestructura.

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

### Clases a AGREGAR

| Clase | Responsabilidad |
|-------|-----------------|
| `PartidoSchedulerService` | Ejecuta periódicamente (ej. cada 30 s): inicia partidos cuando llega su `fechaHora` (CONFIRMADO → EN_JUEGO) y finaliza partidos cuando se cumple la `duracion` (EN_JUEGO → FINALIZADO). Depende de `PartidoRepository`, `ServicioEstadoPartido`, `GestorFlujoPartido`, `ServicioNotificaciones`. |

### Modificaciones a `GestorFlujoPartido`

| Tipo | Nombre | Firma / Descripción |
|------|--------|----------------------|
| Método | `finalizarPorTiempo` | `+finalizarPorTiempo(Partido): Partido` — Finaliza un partido en juego cuando se cumple la duración programada. Si no hay resultado/ganador definidos, asigna valores aleatorios. Usado por el scheduler. |

### Modificaciones al Repositorio de Partidos

En la implementación web, el repositorio expone una consulta para el scheduler:

| Tipo | Nombre | Descripción |
|------|--------|-------------|
| Consulta | `findConfirmadosParaIniciar(now)` | Devuelve partidos en estado CONFIRMADO cuya `fechaHora` es ≤ `now`, para que el scheduler los pase a EN_JUEGO. |

### Nota sobre la aplicación

Agregar **@EnableScheduling** en la clase principal (o equivalente en UML) para habilitar tareas programadas (scheduler).

### Resumen de Cambios

| Acción | Cantidad | Elementos |
|--------|----------|-----------|
| Eliminar | 6 clases | `IVista`, `VistaConsola`, `DatosCreacionPartido`, `DatosRegistroUsuario`, `CredencialesLogin`, `CupoPartido` |
| Agregar | 1 clase | `PartidoSchedulerService` |
| Agregar a `Partido` | 2 atributos, 2 métodos | Ver tabla arriba |
| Agregar a `GestorFlujoPartido` | 1 método | `finalizarPorTiempo(Partido)` |
| Agregar al repositorio de Partido | 1 consulta | `findConfirmadosParaIniciar(now)` |
| Agregar | 1 nota UML | División del controlador; @EnableScheduling |

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
| **Facade** | `GestorFlujoPartido` (interfaz unificada para avanzar estado, cancelar, finalizar con resultado y finalizar por tiempo) |
| **Factory** | `EstadoPartidoFactory` (creación de estados a partir del nombre), `EstrategiaEmparejamientoFactory` (obtención de estrategia por nombre), `NivelBase.crearPorNombre` (creación de nivel por nombre) |
| **Servicios** | `ServicioUsuarios`, `ServicioPartidos`, `ServicioInscripcionPartido`, `ServicioEstadoPartido`, `ServicioCancelacionPartido`, `GestorFlujoPartido`, `ValidadorInscripcion`, `PartidoSchedulerService` |
| **Entidades** | `Usuario`, `Partido`, `Deporte`, `Notificacion` |
| **Niveles (State + Factory)** | `INivel`, `NivelBase`, `NivelPrincipiante`, `NivelIntermedio`, `NivelAvanzado` |

---

## 4. Adaptaciones de Infraestructura (No afectan el UML)

Estos cambios son de implementación y **no requieren modificar el diagrama UML**:

### Repositorios
- UML: `IRepositorioUsuarios` + `RepositorioUsuariosMemoria`
- Web: `UsuarioRepository extends JpaRepository<Usuario, Long>` (Spring Data JPA)

### Entidades JPA
- **Usuario**: agregado `id`, `latitud`, `longitud`; password con BCrypt; nivel como String
- **Partido**: agregado `id`, `nivelMaximo`, `fechaHora` como `LocalDateTime`, `fechaHoraInicio` (cuando pasa a EN_JUEGO, para calcular duración); estado como String con `@PostLoad` y `EstadoPartidoFactory.crear()`
- **Notificación**: agregado `id`, `leida`
- **Deporte**: agregado `id`

### Seguridad (clases nuevas, no en UML)
- `JwtTokenProvider`, `JwtAuthFilter`, `UserDetailsServiceImpl`, `SecurityConfig`

### Programación de partidos (scheduler)
- La aplicación inicia partidos automáticamente cuando llega su `fechaHora` y los finaliza cuando se cumple la `duracion` (parseando cadenas como "2 min", "60 min"). Esto se implementa con `PartidoSchedulerService` y `@Scheduled` (Spring). Para pruebas se admite duración mínima de 2 minutos en el frontend y en el backend.

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
| Patrones de diseño | State, Strategy, Observer, Adapter, Facade, Factory (apoyo) | **Idénticos** |
| Inicio/fin por fecha y duración | No contemplado | Scheduler (`PartidoSchedulerService`) + `finalizarPorTiempo` en `GestorFlujoPartido` |
