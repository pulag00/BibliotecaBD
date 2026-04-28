# BibliotecaBD

Backend REST para la gestión de una biblioteca digital desarrollado con **Java 17**, **Spring Boot 3.3.5**, **Spring Data JPA**, **Hibernate** y **MySQL**.

---

## Descripción

BibliotecaBD es una API REST diseñada para administrar de forma integral una biblioteca digital. El sistema permite gestionar usuarios, libros, préstamos, reservas, categorías, configuración del sistema, auditorías y componentes de seguridad.

Funcionalidades principales del sistema:

- Gestión de usuarios
- Administración de libros
- Gestión de préstamos
- Gestión de reservas
- Clasificación por categorías
- Manejo de preguntas y respuestas de seguridad
- Configuración del sistema
- Auditoría de operaciones sobre usuarios y libros

---

## Objetivo del proyecto

Implementar un backend completo para una biblioteca digital utilizando arquitectura por capas y una base de datos relacional.

El proyecto integra:

- API REST para comunicación cliente-servidor
- Persistencia con Spring Data JPA
- ORM con Hibernate
- MySQL como sistema gestor de base de datos
- Docker Compose para despliegue de base de datos
- Seguridad mediante hash de contraseñas con BCrypt

---

## Características principales

- API REST desarrollada con Spring Boot
- Arquitectura por capas
- Persistencia con Spring Data JPA
- Mapeo objeto-relacional con Hibernate
- Base de datos MySQL
- Base de datos H2 para pruebas
- Docker Compose para MySQL
- Maven Wrapper incluido
- Preparación para cifrado de contraseñas
- Auditoría de usuarios y libros

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal |
| Spring Boot 3.3.5 | Framework backend |
| Spring Web | Desarrollo de API REST |
| Spring Data JPA | Persistencia de datos |
| Hibernate | ORM |
| MySQL | Base de datos principal |
| H2 Database | Base de datos para pruebas |
| Lombok | Reducción de código repetitivo |
| Maven | Gestión de dependencias |
| Docker Compose | Contenerización de MySQL |
| BCrypt | Hash de contraseñas |
| Postman | Pruebas de endpoints |

---

## Arquitectura del proyecto

El sistema sigue una arquitectura por capas:

```text
Cliente / Frontend / Postman
        │
        ▼
Controller
        │
        ▼
Service
        │
        ▼
Repository
        │
        ▼
MySQL Database
```

### Capas

**Controller**  
Gestiona las peticiones HTTP y respuestas.

**Service**  
Contiene la lógica de negocio.

**Repository**  
Acceso a datos mediante Spring Data JPA.

**Model**  
Entidades JPA asociadas a tablas de base de datos.

---

## Estructura del proyecto

```text
BibliotecaBD/
├── .mvn/
├── SQL/
│   ├── borrado.sql
│   ├── creacion.sql
│   ├── datos.sql
│   ├── indices.sql
│   ├── limpieza.sql
│   ├── respaldo.sql
│   └── tablas.sql
│
├── src/
│   ├── main/
│   │   ├── java/co/edu/javaeriana/biblioteca/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── BibliotecaApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## Seguridad

El proyecto implementa almacenamiento seguro de contraseñas mediante hash.

Las contraseñas no se almacenan en texto plano. Se recomienda usar BCrypt.

Dependencia requerida:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

## Instalación y ejecución

### 1. Clonar repositorio

```bash
git clone https://github.com/pulag00/BibliotecaBD.git
cd BibliotecaBD
```

### 2. Levantar MySQL con Docker

```bash
docker compose up -d
docker ps
```

### 3. Ejecutar aplicación

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

**Windows**

```bash
mvnw.cmd spring-boot:run
```

---

## URL base

```text
http://localhost:8085
```

---

## Endpoints principales

### Usuario

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/usuario` | Listar usuarios |
| POST | `/api/usuario` | Crear usuario |
| GET | `/api/usuario/id/{id}` | Buscar por ID |
| PUT | `/api/usuario/{id}` | Actualizar usuario |
| DELETE | `/api/usuario/{id}` | Eliminar usuario |

### Libro

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/libro` | Listar libros |
| POST | `/api/libro` | Crear libro |
| GET | `/api/libro/{id}` | Buscar libro |
| PUT | `/api/libro/{id}` | Actualizar libro |
| DELETE | `/api/libro/{id}` | Eliminar libro |

### Préstamo

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/prestamo` | Listar préstamos |
| POST | `/api/prestamo` | Crear préstamo |
| GET | `/api/prestamo/{id}` | Buscar préstamo |
| PUT | `/api/prestamo/{id}` | Actualizar préstamo |
| DELETE | `/api/prestamo/{id}` | Eliminar préstamo |

### Reserva

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/reserva` | Listar reservas |
| POST | `/api/reserva` | Crear reserva |
| GET | `/api/reserva/{id}` | Buscar reserva |
| PUT | `/api/reserva/{id}` | Actualizar reserva |
| DELETE | `/api/reserva/{id}` | Eliminar reserva |

---

## Flujo interno de una petición

Ejemplo: creación de usuario

```text
POST /api/usuario
        │
        ▼
UsuarioController
        │
        ▼
UsuarioService
        │
        ▼
UsuarioRepository
        │
        ▼
MySQL Database
        │
        ▼
JSON Response
```

---

## Tests automatizados

El proyecto incluye pruebas automatizadas desarrolladas con **Spring Boot Test** para validar el funcionamiento de las entidades principales del sistema.

Estas pruebas permiten verificar que los componentes del backend funcionen correctamente y que las entidades puedan interactuar con la capa de persistencia de forma adecuada.

Se realizaron tests automatizados para las entidades del sistema:

- `Usuario`
- `Libro`
- `Prestamo`
- `Reserva`
- `Categoria`
- `Configuracion`
- `PreguntaSeguridad`
- `UsuarioRespuesta`
- `TipoUsuarioCatalogo`
- `EstadoUsuarioCatalogo`
- `EstadoPrestamoCatalogo`
- `EstadoReservaCatalogo`
- `AuditoriaUsuario`
- `AuditoriaLibro`

Objetivo de las pruebas

Los tests automatizados permiten validar:

- La creación de entidades.
- La persistencia de datos.
- La consulta de registros.
- La actualización de información.
- La eliminación de registros.
- La relación entre entidades.
- El correcto funcionamiento de los repositorios JPA.
- La integración básica con el contexto de Spring Boot.

Tecnología usada para pruebas. Las pruebas fueron desarrolladas usando:

- Spring Boot Test
- JUnit
- Spring Data JPA
- H2 Database para entorno de pruebas
- Maven

## Pruebas con Postman

Pasos para probar la API:

1. Ejecutar MySQL con Docker
2. Ejecutar aplicación Spring Boot
3. Abrir Postman
4. Consumir endpoints usando:

```text
http://localhost:8085
```

Ejemplos:

```http
GET /api/usuario
GET /api/libro
GET /api/prestamo
GET /api/reserva
```

---

## Comandos útiles

### Compilar proyecto

```bash
./mvnw clean install
```

Windows:

```bash
mvnw.cmd clean install
```

### Ejecutar pruebas

```bash
./mvnw test
```

### Ejecutar aplicación

```bash
./mvnw spring-boot:run
```

### Levantar MySQL

```bash
docker compose up -d
```

### Detener MySQL

```bash
docker compose down
```

### Ver logs

```bash
docker logs mysql5
```

### Ver contenedores activos

```bash
docker ps
```

---


Proyecto académico desarrollado para prácticas de backend con Spring Boot y bases de datos relacionales.
