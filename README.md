
# Mente en Equilibrio

### Proyecto Semestral — DSY1105 Desarrollo de Aplicaciones Móviles

**Institución:** Duoc UC  
**Nombre del proyecto:** Mente en Equilibrio  
**Repositorio:** Grupo_DVIK-App_SPV-Seccion_003D  
**Plataforma:** Android  
**Lenguaje:** Kotlin

## 1. Integrantes del equipo

- Juan David Cordova
- Kevin Reyes
- Vicente Pizarro
- Ignacio Castro

## 2. Descripción del proyecto

Mente en Equilibrio es una aplicación móvil desarrollada para Android, cuyo propósito es facilitar el autorregistro personal mediante una interfaz sencilla y organizada.

La aplicación permite registrar experiencias y actividades relacionadas con cuatro ámbitos principales:

- Neurodesarrollo.
- Terapia Dialéctico-Conductual (DBT).
- Estado de ánimo.
- Adicciones e impulsos.

Los usuarios pueden crear una cuenta, iniciar sesión, realizar registros, consultar su historial personal y administrar la información almacenada.

El proyecto utiliza datos ficticios con fines académicos y educativos. No realiza diagnósticos ni reemplaza la atención de profesionales de la salud.

## 3. Problemática

Actualmente, las personas pueden utilizar diferentes aplicaciones, notas o herramientas para registrar experiencias personales.

Esta información puede quedar dispersa, dificultando su organización y consulta posterior.

Mente en Equilibrio busca ofrecer un espacio centralizado para realizar autorregistros, consultar experiencias anteriores y mantener un historial organizado.

## 4. Objetivo general

Desarrollar una aplicación móvil Android que permita centralizar y administrar registros personales relacionados con neurodesarrollo, DBT, estado de ánimo y adicciones e impulsos, utilizando Kotlin, Jetpack Compose y persistencia local mediante Room.

### Objetivos específicos

- Implementar un sistema de registro e inicio de sesión.
- Desarrollar formularios específicos para los cuatro ámbitos.
- Permitir crear, consultar, modificar y eliminar registros.
- Almacenar la información localmente mediante Room.
- Mantener separados los registros de cada usuario.
- Incorporar una sección educativa sobre los ámbitos.
- Desarrollar una interfaz sencilla, organizada y navegable.
- Validar el funcionamiento mediante pruebas automatizadas.


## 5. Requerimientos funcionales implementados

Los siguientes requerimientos corresponden a las funcionalidades desarrolladas en la versión actual del proyecto Mente en Equilibrio.

| ID | Requerimiento | Implementación |
|---|---|---|
| RF1 | Registro de Estado de Ánimo | Permite seleccionar categoría, intensidad, contexto y nota opcional. |
| RF2 | Registro de Habilidades DBT | Permite seleccionar una habilidad, registrar situación o emoción, intensidad y utilidad percibida. |
| RF3 | Registro de Neurodesarrollo | Permite registrar rutinas, tareas, estrategias utilizadas y resultados percibidos. |
| RF4 | Registro de Adicciones e Impulsos | Permite registrar nivel de impulso, desencadenantes, acciones protectoras y estado del objetivo. |
| RF5 | Gestión del Historial | Permite consultar, filtrar, editar y eliminar registros personales. |
| RF6 | Consulta de Contenidos | Permite acceder a información educativa sobre los cuatro ámbitos y consultar habilidades DBT. |
| RF7 | Registro e Inicio de Sesión | Permite crear cuentas mediante nombre de usuario y contraseña, además de iniciar y cerrar sesión. |
| RF8 | Gestión de Perfil | Permite consultar los datos del usuario y tomar o cambiar su fotografía de perfil. |
| RF9 | Resumen de Registros | Muestra la cantidad total de registros del usuario y su distribución por ámbito. |
| RF10 | Navegación entre Pantallas | Permite navegar entre Inicio, Mis registros, Centro de Información y Perfil. |

**Nota:** Esta numeración corresponde a los requerimientos implementados en la versión actual de la aplicación y reemplaza la numeración utilizada en el levantamiento inicial.


## 6. Requerimientos no funcionales

| ID | Requerimiento | Implementación |
|---|---|---|
| RNF1 | Plataforma Android e interfaz con Jetpack Compose | Aplicación desarrollada en Kotlin y Jetpack Compose. |
| RNF2 | Almacenamiento y funcionamiento local | Persistencia de registros mediante Room y SQLite. |
| RNF3 | Privacidad y minimización de datos | Uso de alias y datos ficticios para el desarrollo y las pruebas. |

## 7. Tecnologías utilizadas

| Tecnología | Utilización |
|---|---|
| Kotlin | Lenguaje de programación principal. |
| Android Studio | Entorno de desarrollo. |
| Jetpack Compose | Construcción de interfaces. |
| Material Design 3 | Componentes visuales. |
| MVVM | Organización de la aplicación. |
| Room | Persistencia de información. |
| SQLite | Base de datos local. |
| Navigation Compose | Navegación entre pantallas. |
| StateFlow | Manejo de estados observables. |
| Coroutines | Operaciones asíncronas. |
| JUnit 4 | Pruebas unitarias. |
| AndroidJUnit4 | Pruebas instrumentadas. |
| Git y GitHub | Control de versiones y trabajo colaborativo. |

### Configuración del proyecto

- Android Gradle Plugin: 9.3.3.
- Kotlin: 2.2.10.
- Room: 2.8.5.
- Navigation Compose: 2.10.2.
- API mínima de Android: 24.
- API de compilación: 37.
- API objetivo: 37.

## 8. Arquitectura MVVM

El proyecto utiliza una organización basada en MVVM (Model-View-ViewModel).

### Model

Contiene los modelos de datos, entidades Room y clases relacionadas con la información de la aplicación.

### View

Corresponde a las pantallas desarrolladas mediante Jetpack Compose, encargadas de mostrar formularios, registros, botones y mensajes.

### ViewModel

Contiene los estados y la lógica utilizada para gestionar las operaciones entre las pantallas y el almacenamiento.

### Repository y DAO

Los repositorios organizan el acceso a la información, mientras que los DAO realizan las operaciones sobre la base de datos Room.


## 9. Estructura del proyecto

El proyecto Mente en Equilibrio está organizado en diferentes paquetes para separar las pantallas, la lógica de la aplicación y el almacenamiento de datos.

```text
Grupo_DVIK-App_SPV-Seccion_003D/
│
├── app/
│   ├── build.gradle.kts
│   │
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   │
│       │   ├── java/com/example/grupo_dvik_app_spv_seccion_003d/
│       │   │   │
│       │   │   ├── MainActivity.kt
│       │   │   │
│       │   │   ├── data/
│       │   │   │   ├── dao/
│       │   │   │   │   ├── RegistroDao.kt
│       │   │   │   │   ├── UsuarioDao.kt
│       │   │   │   │   └── DetalleRegistroDao.kt
│       │   │   │   │
│       │   │   │   ├── database/
│       │   │   │   │   └── AppDatabase.kt
│       │   │   │   │
│       │   │   │   └── entity/
│       │   │   │       ├── UsuarioEntity.kt
│       │   │   │       ├── RegistroEntity.kt
│       │   │   │       ├── DetalleNeurodesarrolloEntity.kt
│       │   │   │       ├── DetalleDbtEntity.kt
│       │   │   │       ├── DetalleAnimoEntity.kt
│       │   │   │       ├── DetalleAdiccionesEntity.kt
│       │   │   │       └── HabilidadDbtEntity.kt
│       │   │   │
│       │   │   ├── model/
│       │   │   │   ├── Ambito.kt
│       │   │   │   ├── Registro.kt
│       │   │   │   └── Usuario.kt
│       │   │   │
│       │   │   ├── navigation/
│       │   │   │   └── AppNavigation.kt
│       │   │   │
│       │   │   ├── repository/
│       │   │   │   ├── RegistroRepository.kt
│       │   │   │   └── UsuarioRepository.kt
│       │   │   │
│       │   │   ├── secutity/
│       │   │   │   └── ProteccionContrasena.kt
│       │   │   │
│       │   │   ├── validation/
│       │   │   │   └── Validaciones.kt
│       │   │   │
│       │   │   ├── viewmodel/
│       │   │   │   ├── AdminViewModel.kt
│       │   │   │   ├── DetalleRegistroViewModel.kt
│       │   │   │   ├── InicioResumenViewModel.kt
│       │   │   │   ├── LoginViewModel.kt
│       │   │   │   ├── NuevoRegistroViewModel.kt
│       │   │   │   └── RegistroUsuarioViewModel.kt
│       │   │   │
│       │   │   └── ui/
│       │   │       ├── AdminScreen.kt
│       │   │       ├── CentroInformacionScreen.kt
│       │   │       ├── DetalleRegistroScreen.kt
│       │   │       ├── HomeScreen.kt
│       │   │       ├── LoginScreen.kt
│       │   │       ├── NuevoRegistroScreen.kt
│       │   │       ├── PerfilScreen.kt
│       │   │       ├── RegistroUsuarioScreen.kt
│       │   │       │
│       │   │       ├── components/
│       │   │       │   ├── BarraInterior.kt
│       │   │       │   ├── ConfirmarEliminacionDialog.kt
│       │   │       │   ├── FotoPerfilCard.kt
│       │   │       │   ├── RegistroCard.kt
│       │   │       │   ├── SelectorAmbito.kt
│       │   │       │   └── SelectorEstado.kt
│       │   │       │
│       │   │       ├── screens/
│       │   │       │   └── RegistroScreen.kt
│       │   │       │
│       │   │       ├── viewmodel/
│       │   │       │   ├── PerfilViewModel.kt
│       │   │       │   └── RegistroViewModel.kt
│       │   │       │
│       │   │       └── theme/
│       │   │           ├── Color.kt
│       │   │           ├── Theme.kt
│       │   │           └── Type.kt
│       │   │
│       │   ├── res/
│       │   │   ├── drawable/
│       │   │   ├── mipmap-anydpi-v26/
│       │   │   ├── mipmap-hdpi/
│       │   │   ├── mipmap-mdpi/
│       │   │   ├── mipmap-xhdpi/
│       │   │   ├── mipmap-xxhdpi/
│       │   │   ├── mipmap-xxxhdpi/
│       │   │   ├── values/
│       │   │   └── xml/
│       │   │
│       │   └── keepRules/
│       │       └── rules.keep
│       │
│       ├── test/
│       │   └── java/com/example/grupo_dvik_app_spv_seccion_003d/
│       │       └── ExampleUnitTest.kt
│       │
│       └── androidTest/
│           └── java/com/example/grupo_dvik_app_spv_seccion_003d/
│               └── ExampleInstrumentedTest.kt
│
├── gradle/
│   └── libs.versions.toml
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

### Descripción de los paquetes principales

| Paquete | Función |
|---|---|
| `data/dao` | Consultas y operaciones sobre la base de datos. |
| `data/database` | Configuración de Room y migraciones. |
| `data/entity` | Entidades utilizadas para representar las tablas. |
| `model` | Modelos y enumeración de ámbitos. |
| `navigation` | Navegación entre pantallas. |
| `repository` | Comunicación entre ViewModels y base de datos. |
| `secutity` | Archivo encargado de proteger las contraseñas. |
| `validation` | Validaciones de los datos ingresados. |
| `viewmodel` | Lógica y estados de las pantallas. |
| `ui` | Interfaces desarrolladas mediante Jetpack Compose. |
| `ui/components` | Componentes reutilizables de interfaz. |
| `ui/theme` | Colores, tipografía y tema visual. |
| `res` | Recursos gráficos y configuración de Android. |
| `test` | Pruebas unitarias. |
| `androidTest` | Pruebas instrumentadas en dispositivos o emuladores. |

Esta estructura permite organizar las responsabilidades del proyecto y facilita el mantenimiento, las pruebas y el trabajo colaborativo.


### Pantallas principales

| Pantalla | Función |
|---|---|
| LoginScreen | Inicio de sesión. |
| RegistroUsuarioScreen | Creación de cuentas. |
| HomeScreen | Menú y resumen personalizado. |
| NuevoRegistroScreen | Formularios de los cuatro ámbitos. |
| AdminScreen | Historial y filtros. |
| DetalleRegistroScreen | Consulta y edición de registros. |
| CentroInformacionScreen | Información educativa. |
| PerfilScreen | Datos y fotografía del usuario. |

## 10. Base de datos Room

La aplicación utiliza una base de datos SQLite administrada mediante Room.

**Nombre:** `mente_en_equilibrio.db`  
**Versión:** 3

### Tablas principales

| Tabla | Descripción |
|---|---|
| usuarios | Información de cuentas y referencia de fotografías. |
| registros | Información general de los registros. |
| detalle_neurodesarrollo | Datos particulares de Neurodesarrollo. |
| detalle_dbt | Datos particulares de DBT. |
| detalle_animo | Datos particulares de Estado de Ánimo. |
| detalle_adicciones | Datos particulares de Adicciones e Impulsos. |
| catalogo_habilidades_dbt | Catálogo de habilidades DBT. |

### Relaciones

Cada registro general contiene un identificador de usuario y un identificador de ámbito.

Los registros de los cuatro ámbitos utilizan tablas de detalle para almacenar su información específica.

Las tablas de detalle están relacionadas con la tabla `registros` mediante `idRegistro`.

### Persistencia local

Los registros se almacenan en el dispositivo mediante Room.

La aplicación utiliza migraciones de base de datos para conservar la compatibilidad entre versiones del esquema:

- Migración de versión 1 a 2.
- Migración de versión 2 a 3.

Las fotografías del perfil se guardan como archivos locales y su ruta se registra en la base de datos.

## 11. Funcionalidades principales

### Registro e inicio de sesión

- Crear cuentas utilizando un nombre de usuario.
- Validar contraseñas.
- Verificar las credenciales al iniciar sesión.
- Mantener historiales asociados a cada usuario.
- Cerrar sesión.

Las contraseñas se protegen mediante un hash PBKDF2 y una sal individual.

### Formularios de autorregistro

La aplicación incluye formularios independientes para:

1. Neurodesarrollo.
2. DBT.
3. Estado de ánimo.
4. Adicciones e impulsos.

Cada formulario valida sus campos obligatorios antes de guardar la información.

### Gestión de registros

El usuario puede:

- Crear registros.
- Consultar detalles.
- Editar información previamente guardada.
- Eliminar registros mediante confirmación.
- Filtrar el historial por ámbito.

### Perfil personal

La pantalla de perfil muestra los datos del usuario y permite capturar o cambiar una fotografía.

### Centro de Información

Incluye explicaciones educativas sobre cada ámbito, ejemplos y acceso a los formularios correspondientes.

### Resumen de Inicio

La pantalla principal muestra el total de registros guardados y la cantidad correspondiente a cada ámbito.

## 12. Pruebas automatizadas

Se ejecutaron pruebas unitarias e instrumentadas para comprobar las funcionalidades del proyecto.

### Pruebas unitarias

**Archivo:** `ValidacionesTest.kt`

Se realizaron 24 pruebas relacionadas con:

- Nombres de usuario.
- Contraseñas.
- Campos obligatorios.
- Estados.
- Ámbitos.
- Valores numéricos.
- Fechas y horas.

**Resultado obtenido: 24/24 pruebas aprobadas.**

### Pruebas Room

**Archivo:** `RoomRegistrosTest.kt`

Se realizaron 8 pruebas relacionadas con:

- Creación de registros de los cuatro ámbitos.
- Consulta de información guardada.
- Edición de registros.
- Eliminación de registros y detalles.
- Separación de registros por usuario.
- Restricción de eliminación de registros ajenos.

**Resultado obtenido: 8/8 pruebas aprobadas.**

### Pruebas de edición

**Archivo:** `RoomEdicionTest.kt`

Se realizaron 5 pruebas relacionadas con:

- Edición de Neurodesarrollo.
- Edición de DBT.
- Edición de Adicciones.
- Actualización de estados propios.
- Restricción de actualización de estados de otros usuarios.

**Resultado obtenido: 5/5 pruebas aprobadas.**

### Resumen de resultados

| Tipo | Pruebas | Aprobadas |
|---|---:|---:|
| Validaciones unitarias | 24 | 24 |
| Room y CRUD | 8 | 8 |
| Edición y permisos | 5 | 5 |
| **TOTAL** | **37** | **37** |

**Resultado general: 37 pruebas automatizadas aprobadas en las ejecuciones realizadas en Android Studio.**

Las pruebas instrumentadas de Room utilizaron una base de datos temporal en memoria.

Estos resultados corresponden a los casos automatizados implementados y no representan una cobertura completa de todas las interacciones visuales de la aplicación.

## 13. Instalación y ejecución

### Requisitos

- Android Studio.
- SDK de Android compatible.
- Git.
- Dispositivo Android o emulador.

### Clonar el repositorio

```bash
git clone https://github.com/JDCordovaG/Grupo_DVIK-App_SPV-Seccion_003D.git
```

### Seleccionar la rama de desarrollo

```bash
git checkout Kevin_Reyes
```

### Ejecutar

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Conectar un teléfono o iniciar un emulador.
4. Ejecutar la aplicación mediante Run.

### Ejecutar pruebas

Pruebas unitarias:

```powershell
.\gradlew.bat testDebugUnitTest
```

Pruebas instrumentadas:

```powershell
.\gradlew.bat connectedDebugAndroidTest
```

El segundo comando requiere un dispositivo o emulador conectado.

## 14. Control de versiones

El proyecto utiliza Git y GitHub para mantener un registro de los avances realizados.

El equipo trabaja mediante ramas de desarrollo, permitiendo identificar cambios y aportes individuales.

La integración de las funcionalidades se realiza de manera coordinada entre los integrantes.

Repositorio:

https://github.com/JDCordovaG/Grupo_DVIK-App_SPV-Seccion_003D

## 15. Consideraciones del proyecto

Mente en Equilibrio es una aplicación desarrollada con fines académicos.

Toda la información utilizada durante las pruebas y demostraciones debe ser ficticia.

La aplicación permite realizar autorregistros y consultar contenidos educativos, pero no constituye un servicio de atención de salud ni una herramienta de diagnóstico.

---

**Duoc UC — DSY1105 Desarrollo de Aplicaciones Móviles**

**Proyecto Semestral: Mente en Equilibrio**

**Equipo Grupo DVIK**
