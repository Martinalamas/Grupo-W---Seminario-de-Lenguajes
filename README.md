# Grupo-W---Seminario-de-Lenguajes

# GrupoW-SeminarioDeLenguajes

### Integrantes
* Lamas Martina Lujan (@Martinalamas)
* Mastroberti Ludmila (@luddmastro)
* Merched Abril (@abrildmerched)

### Temática elegida
Música. Simulamos la aplicación "Spotify", utilizando su logo, colores e imitando su interfaz. La aplicación simula una experiencia musical, permitiendo a los usuarios interactuar con un listado de las 10 canciones más populares

### ENTREGA 1 - 09/09/25
#### Contribuciones de cada integrante
* **Abril Merched**: Se encargó de desarrollar la sección del Top 10 de canciones y la funcionalidad para ver los detalles de cada canción. También creó la pantalla de agradecimiento por usar la app.
* **Lamas Martina**: Desarrolló el sistema de registro de usuarios y la sección de términos y condiciones.
* **Mastroberti Ludmila**: Implementó la pantalla de bienvenida y la de inicio de sesión.

#### Funcionalidades principales
* **Pantalla de bienvenida**: Una interfaz amigable que da la bienvenida al usuario. Cuenta con dos opciones a seleccionar: iniciar sesión o registrarse.
* **Iniciar sesión**: Permite a los usuarios iniciar sesión, de forma segura, si ya tienen una cuenta a su nombre. Deben ingresar nombre, contraseña y tienen la opción de marcar que se recuerde su usuario. Al tocar el botón "Iniciar sesion", se valida que:  
  - Ambos campos están completados \
Si lo están, continúan a la siguiente pantalla. En caso contrario, un cartel de error aparecerá. 
* **Registro**: Permite a los usuarios registrarse en la aplicación. Para hacerlo, deben ingresar nombre, apellido, mail, fecha de nacimiento (Pantalla 1 de Registro), contraseña y repetir contraseña (Pantalla 2). En la primer pantalla, al clickear en el botón "Continuar", se valida que:
  - Todos los campos estén completos 
  - El mail contenga "@" 
  - El usuario sea mayor de 13 años\
En la segunda pantalla, al clickear el botón "Registrarse", se valida que:  
  - Ambas contraseñas sean iguales 
* **Términos y condiciones**: Una sección dedicada a informar al usuario sobre las políticas de uso de la aplicación. Para continuar, deben si o si apretar el CheckBox que indica que han leído y acepta las políticas de la aplicación. 
* **Top 10 de canciones**: Muestra un listado de las 10 canciones más populares de la aplicación del 1 al 10. De cada una, se indica el nombre de la canción y del artista. 
* **Detalle de canción**: Al seleccionar una canción, el usuario puede ver información detallada como el artista, el título y otros datos relevantes.
* **Mensaje final**: Una pantalla de agradecimiento por usar la aplicación. Además, cuenta con un botón que permite volver a visualizar el top 10.

### ENTREGA 2 - 30/09/25
#### Contribuciones de cada integrante
* **Abril Merched**: Se encargó de desarrollar el toolbar y el menu, utilizando imagenes nativas. Tambien actualizó el Top 10 de canciones y la funcionalidad para ver los detalles de cada canción. 
* **Lamas Martina**: Implementó la utilización de base de datos en la pantalla agregar canción y playlist. Tambien agregó la funcionalidad recordar usuario en el inicio de sesion.
* **Mastroberti Ludmila**: Implementó la utilización de hilos y corutinas en la pantalla descargar canción. También agregó la funcionalidad recordar usuario en el registro. 

#### Funcionalidades principales
* **Iniciar sesión**: Permite a los usuarios iniciar sesión y seleccionar la opción "Recordar Usuario". Si lo hacen, la proxima vez que inicien la aplicación, los campos de nombre de usuario y contraseña aparecerán completos. 
* **Registro**: Permite a los usuarios registrarse en la aplicación, eligiendo nombre de usuario y contraseña. También pueden seleccionar la opcion "Recordar Usuario". Si lo hacen, cuando se dirigen a Iniciar Sesion, los campos de nombre de usuario y contraseña aparecerán completados.
* **Descargar Canción**: Permite a los usuarios escuchar o descargar varias canciones a la vez. Muestra un mensaje cada vez que se inicia una escucha/descarga y cuando se finaliza la misma.  
* **Bievenida**: Una sección dedicada a informar al usuario sobre todas las acciones que puede realizar en la aplicación. Cuenta con 4 opciones:
   - TOP 10
   - AGREGAR CANCIÓN
   - PLAYLIST
   - VOLVER ATRÁS
* **Top 10 de canciones**: Muestra un listado de las 10 canciones más populares de la aplicación del 1 al 10. De cada una, se indica el nombre de la canción y del artista. Incluye un toolbar con un menú incorporado, que cuenta con dos opciones:
   - Playlist: dirige a la pantalla "Playlist", donde los usuarios agregaron sus canciones favoritas. 
   - Cerrar Sesión: vuelve a la pantalla de inicio.
* **Detalle de canción**: Al seleccionar una canción, el usuario puede ver información detallada como el artista, el título y la duración de la canción. Además, cuenta con un botón para volver al Top 10.
* **Agregar cancion**: Para agregar una canción a la playlist, los usuarios deben escribir el nombre de la canción y del artista. Si alguno de los campos no esta completo, aparece un mensaje de error.
* **Playlist**: Las canciones agregadas se visualizan en la playlist. A medida que son agregadas, van siendo incorporadas al final de la lista. 
* **Mensaje final**: Una pantalla de agradecimiento por usar la aplicación. Además, cuenta con un botón que permite volver a visualizar el top 10.
