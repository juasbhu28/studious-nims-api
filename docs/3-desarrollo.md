# Documento de desarrollo

Este documento describe el proceso de desarrollo del proyecto.

## 📖 Roadmap

- [x] Crear proyecto en Github
- [x] Crear carpeta de Documentación y Readme
- [x] Crear proyecto en Spring Boot con las dependencias definidas
- [x] Inciar gitflow
- [x] Crear Dockerfile
- [x] Crear file makefile
  - [x] make build
  - [x] make test
  - [x] make run
- [x] Crear CI/CD github actions. GitFlow procces
  - [x] Over features - Pass tests - % Covertura - Merge to develop
  - [x] Over develop - Pass test - Merge to main
- [x] Definición de tasking


[Volver al inicio](/README.md)

## 🚀 Desarrollo

Formato de Rama y de  Pull Request #[número-tarea]-[nombre] (ejemplo: #1-crear-dockerfile)

### Tasking

| Task | Description | Definition of Done |
| ---- | ----------- | ------------------- |
| #01-workflow_files_configs | Crear archivos de configuración | Archivos de configuración creados |
| #02-Auth_component | Crear componente de autenticación | Componente de autenticación creado |
| #03-Swagger | Implementar Swagger | Swagger implementado |
| #04-User_component | Crear componente de usuario | Componente de usuario creado |

### Cobertura

La cobertura del código se hace mediante jacoco y gradlew se encarga de evaluarlo en la tarea 

### Reporte

El reporte de cobertura se realizo con Jacoco y se encuentra en la carpeta `build/reports/jacoco/test/html/index.html`

### 📚 Referencias

- Arquitectura por capas orientada al dominio. [Link]()
- Git Flow. [Link]()

###   📖 Contribution and Roadmap

---

> [!Note]
> Coming soon
