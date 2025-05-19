# Sistema de requerimientos

## Descripcion

Sistema de gestion de requerimientos que permite iniciar sesion, registrar requerimientos, con categorias, archivos, comentarios, y mas datos. Tambien se pueden administrar los usuarios desde un panel de administrador

## Sreenshots

![image](https://github.com/user-attachments/assets/a14e708f-109d-4dea-86da-e60bec28e8b5)
![image](https://github.com/user-attachments/assets/55cf6c81-2dd0-4488-8c7b-fcbc917f46f7)
![image](https://github.com/user-attachments/assets/25c481e2-9c4f-4aca-bcb5-88b55b5a7a54)
![image](https://github.com/user-attachments/assets/8e62852a-8b64-44bf-bcc4-661e1a752551)
![image](https://github.com/user-attachments/assets/e4ccca03-27b0-44bb-b456-3e68cdfa2740)
![image](https://github.com/user-attachments/assets/a20721bc-73f9-480a-b5c7-3c0771488b64)
![image](https://github.com/user-attachments/assets/3a761e4b-a114-46dd-93be-e332ae8ff9ab)


## Correr el proyecto usando docker

### Requerimientos:
   - Docker instalado
   - Proyecto clonado

### Construir la imagen del backend

Posicionarse en la carpeta del proyecto clonado y ejecutar los siguientes comandos:


`docker build -t requerimientosbackend .` (debe ser el nombre de la imagen en dockerfile)

### Una vez se hayan creado las imagenes, en la carpeta raiz del proyecto ejecutar:
  
`docker-compose up -d` (descarga la base de datos, configura las variables de entorno, usuario, contraseña, puertos y ejecuta el back y la base de datos)

### Listo
#### Ya deberia estar funcionando el sistema en localhost:8080 y en la red lan. 192.168.X.X:8080. Tambien se puede acceder a la base de datos por separado usando algun administrador como adminer. Las credenciales, puerto y host estan en docker-compose.yml

### Comandos utiles (no obligatorios): 

`docker ps # mostrar los contenedores que estan corriendo`

`docker stop [nombre del proceso] # detener un contenedor`

## Postman para probar los endpoints
En la carpeta postman hay un json que se puede importar a la aplicacion postman para probar los endpoints. Tiene datos preconfigurados.

## Crear un proyecto java springboot

- (Opcional) Instalar IDE intellij idea

- Instalar JDK https://bell-sw.com/pages/downloads/#jdk-21-lts

- Generar un proyecto springboot en https://start.spring.io/ compatible con la version de JDK descargada
Al proyecto añadirle las siguientes dependencias
{lombok, Thymeleaf, devtools, Spring Web, Spring Boot DevTools}

## Abrir el proyecto desde un ide

- El proyecto se puede abrir desde el intellij idea.

- Para abrir el sistema de gestion de requerimientos, clonar el git y abrirlo desde algun IDE


### Info del Setup:

- Lenguaje: java
- Gestion de proyecto: maven
- Framework: Springboot
- Packaging: jar
- Version de Java (jdk): 22
- Base de datos: postgresql
- Mapeo objeto relacional: hibernate
- Servidor: Apache Tomcat
