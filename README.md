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
