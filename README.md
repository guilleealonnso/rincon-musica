Para ejecutar el proyecto es necesario tener instalado:

- Java 17 o superior
- Maven
- Node.js y npm
- MySQL
- DBeaver u otro gestor de base de datos



Configuración de la base de datos

Crear una base de datos en MySQL llamada:
CREATE DATABASE rincon_musica;
Importar primero el script de creación de tablas:
ddl-rincon-musica.sql
Importar después el script de datos:
rincon_musica-data.sql
Configuración del backend

El archivo application-local.properties no se incluye en la entrega porque contiene configuración local y credenciales.

Debe crearse manualmente en la siguiente ruta:

backend/rincon-musica-api/src/main/resources/application-local.properties

Contenido de ejemplo:

spring.datasource.url=jdbc:mysql://localhost:3306/rincon_musica
spring.datasource.username=TU_USUARIO_MYSQL
spring.datasource.password=TU_PASSWORD_MYSQL

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spotify.client.id=TU_CLIENT_ID_SPOTIFY
spotify.client.secret=TU_CLIENT_SECRET_SPOTIFY
Ejecución del backend

Desde la raíz del proyecto:

cd backend/rincon-musica-api
mvn spring-boot:run -Dspring-boot.run.profiles=local

El backend quedará disponible en:

http://localhost:8080

Para comprobar que funciona:

http://localhost:8080/api/canciones
Ejecución del frontend

Desde la raíz del proyecto, en otra terminal:

cd frontend/rincon-musica-web
npm install
npm run dev

El frontend quedará disponible en:

http://localhost:5173
Orden recomendado de ejecución
Iniciar MySQL.
Crear la base de datos rincon_musica.
Importar ddl-rincon-musica.sql.
Importar rincon_musica-data.sql.
Crear application-local.properties.
Ejecutar el backend.
Ejecutar el frontend.
Abrir http://localhost:5173 en el navegador.
Archivos no incluidos

No se incluyen las siguientes carpetas o archivos porque son generados automáticamente o contienen configuración local:

backend/rincon-musica-api/target/
frontend/rincon-musica-web/node_modules/
frontend/rincon-musica-web/dist/
backend/rincon-musica-api/src/main/resources/application-local.properties
