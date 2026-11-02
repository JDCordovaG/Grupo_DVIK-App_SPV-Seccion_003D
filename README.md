## Grupo_DVIK-App_SPV-Seccion_003D

#  Mente en Equilibrio

Aplicación móvil desarrollada para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles**.

El proyecto fue desarrollado utilizando **Kotlin y Jetpack Compose**, aplicando navegación entre pantallas, validaciones, arquitectura basada en ViewModel, persistencia local con Room/SQLite, manejo de estados con StateFlow y utilización de un recurso nativo mediante la cámara del dispositivo.

---

##  Integrantes

- Juan David Cordova
- Vicente Pizarro
- Kevin Reyes
- Ignacio Castro

---

#  Objetivo de la aplicación

**Mente en Equilibrio** es una aplicación de autorregistro personal que permite almacenar información relacionada con distintos ámbitos.

Los ámbitos disponibles son:

- Neurodesarrollo
- DBT
- Estado de Ánimo
- Adicciones e Impulsos

Cada registro contiene:

- ID del registro
- ID del usuario
- Ámbito
- Fecha y hora
- Estado de sincronización

Los estados disponibles son:

- Pendiente
- Sincronizado

> Actualmente la aplicación utiliza persistencia local.  
> El estado de sincronización corresponde a un dato almacenado en el registro y no representa una sincronización real con un servidor externo.

---

#  Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- StateFlow
- Flow
- Room
- SQLite
- Coroutines
- Activity Result API
- Git
- GitHub

---

#  Funcionalidades principales

La aplicación permite:

- Iniciar sesión mediante nombre y contraseña.
- Validar la información ingresada.
- Navegar entre Login y Administración.
- Crear nuevos registros.
- Consultar registros almacenados.
- Editar registros.
- Eliminar registros.
- Confirmar antes de eliminar información.
- Seleccionar un ámbito.
- Seleccionar un estado.
- Registrar fecha y hora.
- Mantener información persistente con Room.
- Mostrar mensajes de respuesta al usuario.
- Mostrar animaciones simples.
- Tomar una fotografía utilizando la cámara.
- Mostrar el nombre y fotografía del usuario en el perfil.
- Utilizar un icono personalizado para la aplicación.

---

#  Arquitectura general

El proyecto mantiene una separación de responsabilidades.

```text
Interfaz de usuario
        ↓
ViewModel
        ↓
Validaciones
        ↓
Repository
        ↓
DAO
        ↓
Room
        ↓
SQLite