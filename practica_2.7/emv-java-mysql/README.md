# PRACTICA 2.7: Crea tu propio EMV
## DISEÑO DEL PROMPT:
Quiero que generes un archivo docker-compose.yml completo y funcional para un entorno de desarrollo mínimo viable basado en: - Java (OpenJDK) - MySQL como base de datos Objetivo: poder levantar el entorno, ejecutar un programa simple en Java (“Hola mundo” + prueba de conexión a MySQL) y comprobar que todo funciona. REQUISITOS DEL DOCKER-COMPOSE: 1) Versión - Usa version: "3.9" 2) Servicio de base de datos (mysql) - Imagen estable de MySQL (indica la versión exacta, por ejemplo mysql:8.0) - Variables de entorno: - MYSQL_ROOT_PASSWORD - MYSQL_DATABASE - MYSQL_USER - MYSQL_PASSWORD - Persistencia de datos usando volúmenes - Exponer el puerto 3306 en local - Charset y collation en UTF-8 3) Servicio app (java) - Imagen basada en openjdk (indica versión exacta, por ejemplo openjdk:21) - Directorio de trabajo /app - Montar un volumen local ./app en /app - Debe compilar y ejecutar un archivo Main.java automáticamente - El contenedor debe esperar a que MySQL esté listo antes de ejecutar - Debe pasar las variables de conexión como variables de entorno: - DB_HOST - DB_PORT - DB_NAME - DB_USER - DB_PASSWORD 4) Networking - Ambos servicios deben estar en la misma red interna por defecto - El contenedor Java debe conectarse a MySQL usando el hostname del servicio (mysql) 5) Comentarios Incluye comentarios explicando cada sección y cada parámetro, para que pueda entender claramente qué hace cada cosa. 6) Validación El resultado debe ser un docker-compose.yml que pueda: - Levantarse con “docker compose up” - Permitir ejecutar un Main.java que imprima “Hola mundo” - Intentar conectarse a la base de datos MySQL usando JDBC. Entrega solo el archivo docker-compose.yml, bien formateado y comprobado.


## Definición del EMV y su Arquitectura

El EMV que diseño tiene como objetivo proporcionar un entorno de desarrollo sencillo, reproducible y aislado para aplicaciones Java que utilicen una base de datos MySQL, utilizando contenedores Docker gestionados mediante Docker Compose.

**Este EMV permite:**

Ejecutar código Java sin instalar Java en el sistema anfitrión.

Disponer de una base de datos MySQL lista para usar.

Probar conexión entre aplicación y base de datos.

Levantar todo el entorno con un solo comando (docker compose up).

Mantener los datos de la base de datos de manera persistente mediante volúmenes.

Este entorno será la base sobre la que se pueden construir proyectos más complejos posteriormente.

## Objetivo del entorno

Como prueba mínima:

✔ la aplicación Java imprimirá un mensaje “Hola mundo”
✔ intentará conectarse a MySQL mediante JDBC
✔ y mostrará si la conexión es correcta

## Servicios que componen el EMV

El EMV está formado por dos servicios principales:

### Servicio 1: Base de datos — MySQL

**Función:**
Proporciona una base de datos relacional para la aplicación.

**Características:**

Imagen: mysql:8.0

Configuración mediante variables de entorno

Puerto expuesto localmente (3306)

Persistencia de datos con volúmenes

Configuración UTF-8 para evitar problemas con acentos/caracteres

Este servicio actúa como backend de datos para la aplicación Java.

###Servicio 2: Aplicación — Java (OpenJDK)###

**Función:**
Ejecutar el código fuente Java.

**Características:**

Imagen: openjdk:21

Directorio de trabajo /app

Montaje de código desde el host (./app)

Compilación automática de Main.java

Conexión a la base de datos mediante variables de entorno

La aplicación se comunica con MySQL a través de la red interna creada por Docker Compose.

## Arquitectura general

+-----------------------+
|      Host / PC        |
+-----------------------+
           |
           |  docker compose
           v
+-------------------------------+
|        Red interna Docker     |
+-------------------------------+
        |                 |
        |                 |
+-------+--------+   +----+----------------+
|   MySQL (DB)   |   |   Java App (OpenJDK)|
|  mysql:8.0     |   |  openjdk:21         |
+----------------+   +---------------------+
        ^                     |
        | JDBC                |
        +---------------------+


**Relaciones:**

La aplicación se conecta a MySQL usando el hostname mysql

La red interna evita exposición innecesaria

MySQL expone el puerto 3306 solo para pruebas desde el host

Los datos de MySQL se guardan en un volumen persistente

## Posibles extensiones futuras

Este EMV permite crecer fácilmente:

añadir interfaz web (Spring Boot)

añadir phpMyAdmin

añadir contenedor para tests automatizados

añadir contenedor backend adicional

separar app en microservicios

Pero para esta práctica, se mantiene mínimo y funcional.


# ERRORES

## Error 1: No se encontraba la imagen java
**Problema:** La imagen "openjdk:21" cambió de nombre en Docker Hub, por lo cual me daba un error a la hora de hacer el *docker compose up*.

**Solución:** La forma correcta ahora es usar "eclipse-temurin:21" que es la opcion recomendada de Java en este caso.


## Error 2: Falta de un driver

**Problema:** Al ejecutar el codigo Java, que en mi caso se hacia automaticamente al iniciar la EMV, me daba un error al no encontrar un driver, esto es normal, ya que no habia instalado el driver, y el codigo era para hacer una prueba de ejecucion de codigo java desde la docker (advertencia, no es un error como tal, simplemente algo que no esta instalado para comprobar que el codigo java funcionaba correctamente)
