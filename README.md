# Proyecto Integrado - Gestión de Publicaciones IES Jándula

Este proyecto ha sido desarrollado como parte del módulo de Proyecto Integrado del segundo curso del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Web (2º DAW). Bajo la tutela del profesor Manuel Arroyo Juan, el alumno **[Tu Nombre Aquí]** ha diseñado y construido una solución web completa para la gestión de publicaciones del Instituto IES Jándula.

El objetivo principal del proyecto es proporcionar una herramienta digital que permita al centro educativo administrar sus publicaciones institucionales de forma sencilla, eficiente y segura. El sistema cuenta con dos vistas diferenciadas:

- **Vista de Administración:** Desde aquí el administrador puede visualizar todas las publicaciones realizadas, y tiene la capacidad de cambiar su estado (por ejemplo: publicada, pendiente, rechazada).
- **Vista de Usuario:** Donde, tras autenticarse mediante un sistema de login con Firebase, los usuarios pueden crear nuevas publicaciones para ser revisadas por los administradores.

La plataforma está compuesta por un **frontend** desarrollado en **Vue.js**, un **backend** implementado en **Java con Spring Boot**, y el sistema de autenticación se gestiona con **Firebase Authentication**. Para los estilos se ha utilizado **CSS puro**.

Gracias a esta solución, el IES Jándula puede optimizar la creación y control de sus publicaciones digitales, asegurando una mejor organización y visibilidad del contenido institucional.

Este repositorio contiene todo el código fuente, documentación y detalles técnicos del sistema desarrollado.

---

##  Manual para su instalación y ejecución

###  Backend (Spring Boot)

**Requisitos Previos:**
- Java 17 o superior
- Maven
- Spring Boot

---

###  Frontend (Vue.js)

**Requisitos Previos:**
- Node.js (versión recomendada: 18 o superior)
- NPM (gestor de paquetes incluido con Node.js)
  

**Descarga del Proyecto:**
 
git clone https://github.com/IESJandula/gestion_publicaciones.git

Edita el fichero application.properties con tus datos de conexión:
spring.datasource.url=jdbc:mysql://localhost:3306/gestionpublicaciones
spring.datasource.username=jjr
spring.datasource.password=1234

cd backend-publicaciones
npm install
npm run dev
El frontend se iniciará en: http://localhost:5173

###  Galeria de imagenes

·Login-
![image](https://github.com/user-attachments/assets/cb812b64-5c05-4597-96ba-c296d91b2a45)


·Vista crear publicaciones-
![image](https://github.com/user-attachments/assets/f4cc6e36-a8bb-41d2-977c-8765b15d8d53)


·Vista admin-
![image](https://github.com/user-attachments/assets/5a63dea5-e1a6-4595-a076-7134ed7f8278)

