# Challenge-Foro Hub (En desarrollo)

## Descripción
El proyecto consiste en desarrollar una API REST para un foro, la API implementa también seguridad a través de JWT y autenticación de usuarios por contraseña. Para este proyecto se usó
una base de datos en mysql. El proyecto es un desafio que es parte de la formación **Java y Spring Boot**, impartido por **Alura Latam** como parte del programa **Oracle Next Education**.  
El objetivo es profundizar y poner en practia los conocimentos adquirido en los cursos como:
  - Uso de Spring Boot.
  - Uso de los repositorios Spring Data JPA y el driver de mysql.
  - Modelar relaciones entre entidades, uso de Hibernate para el mapeo.
  - Mapear endpoints y probarlos con Postman.
  - Validar datos recibidos por los requests.
  - Uso de querries JPQL.
  - Uso de Spring web y Spring security.
  - Generar y validar Web Tokens.

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
  - Documentación de la API con Swagger.
  
## Entidades y Tablas

![image](https://github.com/JoseAAPDS/Challenge-Foro-Hub/assets/147453435/edba3ce3-2fbd-45b2-8516-bc07db1c8eeb)


## Autor

  ### José Armando Acevedo
  
  www.linkedin.com/in/josé-acevedo-pilz-136179246
