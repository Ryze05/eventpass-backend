# EventPass - Sistema de Gestión de Eventos

## Descripción de la aplicación

EventPass es una plataforma para la gestión y consulta de eventos. Mientras que la interfaz móvil se centra en ofrecer al usuario una experiencia fluida para descubrir eventos y registrarse en ellos, el backend actúa como el núcleo de la plataforma.

Su función principal es gestionar la lógica de negocio y la persistencia de datos, permitiendo un control total sobre el catálogo de eventos. El sistema facilita la administración de eventos, categorías, ubicaciones y registros de asistentes, además de ofrecer un sección de estadísticas para analizar el impacto de los eventos gestionados.

## Requisitos para su funcionamiento

Para asegurar el correcto despliegue y ejecución de la aplicación, es necesario disponer del siguiente entorno técnico:

- Java SDK 21 o superior

- Kotlin como lenguaje de programación principal utilizado para el backend.

- Spring Boot 4.0.2 como framework utilizado para la implementación del modelo MVC

- Maven como gestor de dependencias

- Docker / Docker Desktop para levantar el contenedor de la base de datos

## Base de datos

La aplicación utiliza una base de datos relacional MySQL desplegada mediante un contenedor Docker.

### Descripción de las tablas

- `categorias`: Define el tipo de evento (Concierto, Teatro, Festival, etc.). Permite organizar y filtrar el catálogo de eventos.


- `ubicaciones`: Almacena información sobre los recintos, incluyendo nombre, dirección y capacidad máxima.


- `eventos`: Tabla central que vincula una Categoría y una Ubicación. Contiene detalles como título, descripción, fecha y la referencia a la imagen.


- `asistentes`: Gestiona el registro de usuarios (nombre, email y teléfono), vinculándolos directamente a un Evento específico.

### Cómo replicar la BD en otro servidor

Para garantizar la replicabilidad en cualquier equipo, se utiliza un despliegue basado en Docker.

1. Configuración de variables de entorno `.env`: Crea un archivo llamado .env en la raíz del proyecto con el siguiente contenido (cambiar el puerto si se encuentra ocupado, si se cambia también debe ser cambiado en el archivo `application.properties` del proyecto):


    ```env
    MYSQL_DATABASE=eventpass
    MYSQL_USER=user
    MYSQL_PASSWORD=user
    MYSQL_ROOT_PASSWORD=user
    MYSQL_PORT=3307
    ```

2. Definición del servicio: Crea un archivo `docker-compose.yml` con el siguiente contenido:


    ```yml
    services:
    mysql:
        image: ryze05/eventpass-db
        container_name: eventpass-db

        env_file:
        - .env

        ports:
        - "${MYSQL_PORT}:3306"

        volumes:
        - mysql_data:/var/lib/mysql

    volumes:
    mysql_data:

    ```

3. Despliegue: Ejecuta el siguiente comando en tu terminal para levantar el contenedor en segundo plano:


    ```bash
    docker-compose up -d
    ```
    
Se adjunta el archivo `eventpass.sql` con la estructura completa y datos de prueba para su ejecución.

```sql
CREATE DATABASE IF NOT EXISTS eventpass;
USE eventpass;

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE ubicaciones (
    id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    capacidad INT NOT NULL
);

CREATE TABLE eventos (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha DATETIME NOT NULL,
    imagen_res VARCHAR(100) DEFAULT 'default_event',
    id_ubicacion INT,
    id_categoria INT,
    CONSTRAINT fk_ubicacion FOREIGN KEY (id_ubicacion)
        REFERENCES ubicaciones(id_ubicacion) ON DELETE CASCADE,
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria) ON DELETE SET NULL
);

CREATE TABLE asistentes (
    id_asistente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    id_evento INT,
    CONSTRAINT fk_evento FOREIGN KEY (id_evento)
        REFERENCES eventos(id_evento) ON DELETE CASCADE
);

INSERT INTO categorias (nombre) VALUES ('Concierto'), ('Teatro'), ('Taller');
INSERT INTO ubicaciones (nombre, direccion, capacidad) VALUES ('Auditorio Municipal', 'Calle Mayor 1', 500);
INSERT INTO eventos (titulo, descripcion, fecha, id_ubicacion, id_categoria) 
VALUES ('Concierto Rock', 'Evento benéfico', '2026-05-20 20:00:00', 1, 1);
INSERT INTO asistentes (nombre, email, telefono, id_evento) VALUES ('Juan Pérez', 'juan@email.com', '600123456', 1);

 
ALTER USER 'user'@'%' IDENTIFIED WITH mysql_native_password BY 'user';
FLUSH PRIVILEGES;
```


## Instrucciones claras sobre cómo ejecutar la aplicación

Para poner en marcha la aplicación hay que seguir los siguientes pasos:

1. Levantar el contenedor docker:

    ```bash
    docker-compose up -d
    ```

2. Configuración del proyecto en el IDE: Procede a abrir el IDE, espera a que se descarguen todas las dependencias y verifica en `src/main/resources/application.properties` que el puerto de conexión coincide con el definido en su archivo `.env`:

    ```bash
    spring.application.name=eventpass
    spring.datasource.url=jdbc:mysql://localhost:3307/eventpass
    spring.datasource.username=user
    spring.datasource.password=user
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

3. Ejecución de la aplicación: Localiza la clase principal `EventpassApplication.kt` y pulsa el botón Run.

4. Acceso a la interfaz: Una vez este inizializada la aplicación puedes acceder a ella con la siguiente URL [http://localhost:8080/admin/dashboard](http://localhost:8080/admin/dashboard).

    | Sección | Ruta URL | Descripción |
    | :--- | :--- | :--- |
    | **Dashboard** | `/admin/dashboard` | Vista general, estadísticas y atajos. |
    | **Eventos** | `/admin/eventos` | Listado y gestión de eventos. |
    | **Asistentes** | `/admin/asistentes` | Gestión de usuarios inscritos por evento. |
    | **Categorías** | `/admin/categorias` | Gestión de tipos de eventos. |
    | **Ubicaciones** | `/admin/ubicaciones` | Gestión de recintos y aforos. |

## Opciones del programa y ejemplos de uso

### Opciones del programa

La aplicación implementa un sistema CRUD completo para gestionar la información de la base de datos:

- **Gestión de Eventos, Categorías, Ubicaciones y Asistentes**: Para cada una de estas secciones, el administrador puede :
  - **Leer**: Visualizar listados detallados de todos los registros.
  - **Crear**: Añadir nuevos elementos mediante formularios.
  - **Actualizar**: Editar cualquier registro existente localizándolo por su ID.
  - **Eliminar**: Borrar registros de la base de datos mediante su ID.

- **Dashboard de Control**: Una interfaz donde se muestra el estado global de la plataforma mediante:
  - **Indicadores rápidos**: Conteo total de eventos y asistentes registrados.
  - **Clasificación temporal**: Distinción automática entre eventos futuros y eventos pasados.
  - **Opciones rápidas**: El panel incluye una sección de acceso directo para:
    - Crear nuevo evento
    - Registrar asistente
    - Crear nueva categoría
    - Crear nueva ubicación
  
- **Análisis Visual y Estadísticas:**
  - **Top 5 de Popularidad**: Gráfica de barras con los eventos que han captado más asistentes.
  - **Distribución por Categoría**: Gráfico que muestra visualmente las categorías más populares según su número de asistentes.

- **Registro de Actividad**: Tabla que muestra los últimos usuarios inscritos

### Ejemplos de uso

#### Vista administrador

Para realizar operaciones en la plataforma puedes seguir los siguientes flujos de trabajo:

- **Gestión de eventos**:
  1. Dirijete a la sección **Eventos** mediante la barra de navegacion o la ruta `/admin/eventos`.
  2. Para crear uno nuevo, realiza clic en el botón de **Nuevo Evento**, rellena el formulario incluyendo un nombre, una descripción, seleccionando una ubicación y categoría existentes, y porcede a guardar.
  3. Para modificar un evento, realiza clic sobre el botón **Editar** situado en la card del evento a modificar.
  4. Para borrar un evento, haz clic sobre el botón **Borrar** situado en la card del evento a borrar (se mostrará una modal de confirmación antes de ejecutar la acción).
- **Gestión de Categorías, Ubicaciones y Asistentes**: Estas secciones siguen la misma lógica CRUD que la de eventos. El administrador puede listar, crear, editar y eliminar registros de forma intuitiva a través de sus respectivas rutas (`/admin/categorias`, `/admin/ubicaciones`, `/admin/asistentes`).
- **Dashboard de control**: Es la pantalla principal de administración (`/admin/dashboard`) y sirve como centro de mando:
  1. **Métricas**: Visualiza el total de eventos y asistentes registrados de un vistazo. 
  2. **Análisis Visual**: Esta pantalla posea un grafico de barras para consultar el el top cinco eventos más populares y un gráfico de tarta destribuido por categorías para saber cuales funcionan mejor.
  3. **Menú de acciones rápidas**: Utiliza los botones de acceso directo para crear entidades (Eventos, Asistentes, etc.) sin tener que navegar por las diferentes secciones.

#### API REST

Se proporcionan los siguientes endponints:

- **Eventos**:
  - `GET /api/eventos`: Lista todos los eventos. Soporta filtrado opcional por categoría: `/api/eventos?categoria={id}`.
  - `GET /api/eventos/proximos`: Obtiene los eventos más cercanos (por defecto los 5 primeros). Tiene un parámetro opcional para especificar la cantidad de eventos deseada: `/api/eventos/proximos?limit={cantidad}`.
  - `GET /api/eventos/populares`: Lista los eventos con mayor número de asistentes registrados. Tiene un parámetro opcional para especificar la cantidad de eventos deseada: `/api/eventos/populares?limit={cantidad}`.
  - `GET /api/eventos/{id}`: Devuelve la información detallada de un evento específico por su ID.
- **Categorías**:
  - `GET /api/categorias`: Lista todas las categorías disponibles.
- **Asistentes**:
  - `POST /api/asistentes`: Permite el registro de un nuevo asistente.

## Notas importantes

- **Despliegue y Replicabilidad**: Para replicar la base de datos de forma sencilla se ha creado una imagen docker con todo ya montado, con su estructura y datos. Por lo que para replicarlo lo único que hay que hacer es copiar el **docker-compose.yml** previamente mostrado y ejecutarlo. Cabe recalcar que si se cambia de puerto también debe ser cambiado en el archivo **application.properties**.
