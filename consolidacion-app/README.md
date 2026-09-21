# Consolidación · Iglesia Cristiana La Roca

Backend en Spring Boot + MySQL para el panel de consolidación
(registro de asistentes mes a mes). El HTML y CSS que ya tenías
están servidos directamente por esta misma aplicación — no hay que
tocarlos ni montarlos aparte.

## Requisitos

- Java 17 o superior
- Maven (o usa el `mvnw` incluido si lo generas con Spring Initializr)
- MySQL 8 corriendo en tu máquina o servidor

## 1. Crear la base de datos

Solo necesitas crear la base vacía; las tablas las crea la aplicación
sola la primera vez que arranca:

```sql
CREATE DATABASE consolidacion_la_roca;
```

## 2. Configurar la conexión

Abre `src/main/resources/application.properties` y cambia:

```properties
spring.datasource.username=root
spring.datasource.password=CAMBIA_ESTA_CLAVE
```

por tu usuario y clave reales de MySQL.

## 3. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

La aplicación queda escuchando en `http://localhost:8080`.

## 4. Abrir el panel

- Página de la iglesia: `http://localhost:8080/index.html`
- **Formulario público** (cualquiera lo puede llenar, sin ver la lista): `http://localhost:8080/registro.html`
- **Panel de administrador** (requiere iniciar sesión): `http://localhost:8080/consolidacion.html`

Al entrar a `/consolidacion.html` sin haber iniciado sesión, te redirige
automáticamente a `/login.html`.

## 5. Iniciar sesión por primera vez

La primera vez que corres la app, si no hay ningún usuario en la base de
datos, se crea uno automáticamente con las credenciales que pusiste en
`application.properties`:

```properties
app.admin.correo=admin@iglesialaroca.org
app.admin.password=CambiaEstaClave123
```

**Cambia esa contraseña antes de usar esto en producción.** Por ahora,
para cambiarla o crear más usuarios del equipo, hazlo directamente en
la tabla `usuarios` de MySQL (la contraseña debe guardarse encriptada
con BCrypt — más adelante podemos agregarle una pantalla para
gestionar usuarios desde el navegador).

El formulario y la tabla ya están conectados a la API real —
cada registro que añadas queda guardado en MySQL, y si cierras
el navegador o entras desde otro computador, los datos siguen ahí.

## Cómo quedaron separados los dos lados

| Página | Quién puede entrar | Qué hace |
|---|---|---|
| `/index.html` | Cualquiera | Página pública de la iglesia |
| `/registro.html` | Cualquiera | Formulario de registro — no ve la lista de nadie |
| `/login.html` | Cualquiera | Inicio de sesión del equipo |
| `/consolidacion.html` | Solo con sesión iniciada | Tabla completa + seguimiento |

## Endpoints disponibles

| Método | Ruta | Acceso | Para qué |
|---|---|---|---|
| POST | `/api/asistentes` | Público | Registra un nuevo asistente (usado por `registro.html`) |
| GET | `/api/asistentes` | Requiere sesión | Lista todos los asistentes |
| GET | `/api/asistentes?mes=Enero%202026` | Requiere sesión | Filtra por mes |
| PATCH | `/api/asistentes/{id}/contactado` | Requiere sesión | Marca contactado/pendiente |
| DELETE | `/api/asistentes/{id}` | Requiere sesión | Elimina un registro |

## Próximos pasos (todavía no incluidos)

- Página para crear/editar usuarios del equipo desde el navegador (hoy solo por MySQL directo).
- Exportar a Excel/CSV desde el panel de administrador.
- Roles distintos (ej. alguien que solo pueda ver, sin marcar contactado).
