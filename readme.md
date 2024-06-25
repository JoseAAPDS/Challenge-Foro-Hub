# Challenge-Foro Hub

## Descripción
El proyecto consiste en desarrollar una API REST para un foro, la API implementa también seguridad a través de JWT, autenticación de usuarios por contraseña y autorización de los endpoints
por roles de usuario. Para este proyecto se usó una base de datos en mysql. El proyecto es un desafío que es parte de la formación **Java y Spring Boot**, impartido por **Alura Latam** como 
parte del programa **Oracle Next Education**.  
El objetivo es profundizar y poner en práctica los conocimentos adquiridos en los cursos como:
  - Uso de Spring Boot.
  - Uso de los repositorios Spring Data JPA y el driver de mysql.
  - Modelar y mapear relaciones entre entidades.
  - Mapear endpoints y probarlos con Postman.
  - Validar datos recibidos por los requests.
  - Uso de querries JPQL.
  - Uso de Spring web y Spring security.
  - Generar y validar Json Web Tokens.
  - Documentación de la API usando springdoc y swagger.

## Funcionalidades básicas (requeridas para pasar el desafío)
  - Implementar endpoint para Registrar un nuevo tópico (solicitud **POST**).  Todos los campos son obligatorios y no se perimiten tópicos duplicados (con el mismo título y mensaje). 
  - Endpoint para mostrar una lista de todos los tópicos, debe aceptar solicitude **GET** para la URI **/topicos**. También debe aceptar solicitudes **GET** para la URI **/topicos/{id}**.  - 
  - Endpoint para actualizar un tópico, debe aceptar solicitudes del tipo **PUT** para la URI **/topicos/{id}**.
  - Endpoint para la eliminación de un tópico específico, debe aceptar solicitudes **DELETE** para la URI **/topicos/{id}**.
  - Autenticación con Spring Security.
  - Generar un token y autenticar con JWT.
    
  ### Funcionalidades opcionales
  - Implementar otras rutas /usuario, /respuesta y /curso.
  - Mostrar el listado de tópicos ordenados por fecha de creación ASC.
  - Mostrar el listado de tópicos usando un criterio de búsqueda como: listar por nombre de curso y año específico.
  - Documentación de la API con Springdoc y Swagger.

## Documentación de la API
Para acceder a la documentación de la api están disponibles las siguientes URLs:
  - http://server:port/swagger-ui.html
  - http://server:port/v3/api-docs
  
## Entidades y Tablas

![image](https://github.com/JoseAAPDS/Challenge-Foro-Hub/assets/147453435/edba3ce3-2fbd-45b2-8516-bc07db1c8eeb)


## Autor

  ### José Armando Acevedo
  
  www.linkedin.com/in/josé-acevedo-pilz-136179246
