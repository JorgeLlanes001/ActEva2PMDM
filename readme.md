# Gestión de Clientes - Android App

Aplicación Android para gestionar clientes con persistencia local usando SQLite, desarrollada con Kotlin y XML.

## Características

### Funcionalidades Principales
-  Lista de clientes en RecyclerView con diseño CardView
-  Agregar nuevos clientes mediante FloatingActionButton
-  Editar clientes existentes
-  Eliminar clientes con diálogo de confirmación
-  Persistencia de datos con SQLite
-  Validaciones de formulario completas
-  Búsqueda en tiempo real por nombre o correo
-  Contador total de clientes
-  Interfaz profesional con Material Design

### Validaciones Implementadas
- **Campos obligatorios**: Nombre, email y teléfono
- **Email**: Validación de formato correcto usando Patterns
- **Teléfono**: Mínimo 9 dígitos
- **Feedback visual**: Mensajes de error en cada campo

##  Requisitos del Sistema

- **Android Studio**: Arctic Fox o superior
- **SDK mínimo**: API 24 (Android 7.0)
- **SDK objetivo**: API 34 (Android 14)
- **Lenguaje**: Kotlin
- **Gradle**: 7.0+

##  Instalación y Configuración

### 1. Clonar o Descargar el Proyecto
```bash
git clone [URL_DEL_REPOSITORIO]
```
O descarga el proyecto como ZIP y descomprímelo.

### 2. Abrir en Android Studio
1. Abre Android Studio
2. Selecciona `File > Open`
3. Navega hasta la carpeta del proyecto
4. Selecciona la carpeta raíz y haz clic en `OK`

### 3. Sincronizar Gradle
- Android Studio sincronizará automáticamente el proyecto
- Si no lo hace, haz clic en `File > Sync Project with Gradle Files`

### 4. Configurar Emulador o Dispositivo
**Opción A - Emulador:**
1. Ve a `Tools > Device Manager`
2. Crea un nuevo dispositivo virtual (recomendado: Pixel 5, API 34)

**Opción B - Dispositivo físico:**
1. Activa las opciones de desarrollador en tu dispositivo
2. Activa la depuración USB
3. Conecta el dispositivo por USB

### 5. Ejecutar la Aplicación
1. Selecciona el dispositivo o emulador
2. Haz clic en el botón `Run` o presiona `Shift + F10`

##  Estructura del Proyecto

```
app/src/main/
├── java/com/example/gestionclientes/
│   ├── adapters/
│   │   └── ClienteAdapter.kt          # Adaptador del RecyclerView
│   ├── database/
│   │   └── DatabaseHelper.kt          # Gestión de SQLite
│   ├── models/
│   │   └── Cliente.kt                 # Modelo de datos
│   ├── MainActivity.kt                # Pantalla principal
│   └── FormularioClienteActivity.kt   # Formulario de cliente
│
└── res/
    ├── layout/
    │   ├── activity_main.xml              # Layout principal
    │   ├── activity_formulario_cliente.xml # Layout formulario
    │   └── item_cliente.xml               # Layout item RecyclerView
    └── values/
        ├── strings.xml
        ├── colors.xml
        └── themes.xml
```

##  Modelo de Datos

### Tabla: `clientes`

| Campo    | Tipo    | Descripción                    |
|----------|---------|--------------------------------|
| id       | INTEGER | Clave primaria (autoincremental)|
| nombre   | TEXT    | Nombre completo del cliente    |
| email    | TEXT    | Correo electrónico             |
| telefono | TEXT    | Número de teléfono             |

### Clase Cliente (Kotlin)
```kotlin
data class Cliente(
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val telefono: String
) : Parcelable
```

##  Uso de la Aplicación

### Agregar Cliente
1. Toca el botón flotante `+` en la esquina inferior derecha
2. Completa todos los campos obligatorios
3. Toca `GUARDAR CLIENTE`

### Editar Cliente
1. Toca el ícono de edición (lápiz) en la tarjeta del cliente
2. Modifica los campos necesarios
3. Toca `ACTUALIZAR CLIENTE`

### Eliminar Cliente
1. Toca el ícono de eliminación (papelera) en la tarjeta del cliente
2. Confirma la eliminación en el diálogo

### Buscar Clientes
1. Escribe en el campo de búsqueda en la parte superior
2. La lista se filtrará automáticamente por nombre o correo

##  Capturas de Pantalla

### Pantalla Principal
Muestra la lista de clientes con opciones de editar y eliminar.

### Pantalla Vacía
Cuando no hay clientes registrados, se muestra un mensaje informativo.

### Formulario de Cliente
Formulario con validaciones en tiempo real y mensajes de error claros.

### Búsqueda en Tiempo Real
Filtrado instantáneo de clientes mientras escribes.

### Diálogo de Confirmación
Confirmación antes de eliminar un cliente para evitar eliminaciones accidentales.

##  Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI**: XML Layouts
- **Base de datos**: SQLite con SQLiteOpenHelper
- **Componentes Android**:
  - RecyclerView
  - CardView
  - FloatingActionButton
  - Material Design Components
  - AlertDialog
- **Patrones**: ViewHolder pattern, MVC

##  Checklist de Requisitos

### Requisitos Funcionales
-  RecyclerView con lista de clientes
-  FloatingActionButton para agregar
-  Formulario con validaciones
-  Persistencia con SQLite
-  Editar y eliminar registros
-  Carga automática al iniciar

### Extras Implementados
-  Búsqueda en tiempo real
-  Contador total de clientes
-  AlertDialog de confirmación
-  Mensaje cuando la lista está vacía

### Documentación
-  README.md completo
-  Instrucciones de instalación
-  Explicación del modelo de datos
-  Estructura del proyecto

##  Solución de Problemas

### Error de compilación
- Asegúrate de tener instalado el SDK de Android API 34
- Verifica que Gradle esté actualizado
- Invalida caché: `File > Invalidate Caches / Restart`

### La app no se instala
- Verifica que la depuración USB esté habilitada
- Comprueba que el dispositivo tenga suficiente espacio
- Desinstala versiones anteriores de la app

### Error de base de datos
- La base de datos se crea automáticamente en el primer inicio
- Si hay problemas, desinstala la app y vuelve a instalarla

##  Licencia

Este proyecto es de uso educativo.

##  Autor

Proyecto de gestión de clientes desarrollado con Kotlin y SQLite para Android.

---

**Fecha de creación**: Noviembre 2025  
**Versión**: 1.0
