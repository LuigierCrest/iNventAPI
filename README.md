# iNvent API
### Descripción
Este es un proyecto backend para dar servicio a una aplicación multiplataforma de inventario.
Está preparada para funcionar con una base de datos PostgreSQL montada en un contenedor Docker junto con un proxi-inverso NPM con cifrado TLS.
Esta API se ha desarrollado de forma independiente utilizando [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html), dando la posibilidad a futuro de integrar tanto cliente como servidor en un único proyecto.

### Instalación
Para ejecutar iNvent API es recomendable tener preparado un proxy inverso con cigrado TLS. Desde el directorio raíz del proyecto, se puede ejecutar el siguiente comando para levantar un contenedor Docker con la API, la base de datos PostgreSQL, Adminer y una red interna para conectar con el proxy:

```shell
docker compose up -d --build
```
### Licencia
Este proyecto está licenciado bajo la Licencia CC-BY-NC 4.0. Consulta el archivo [LICENSE](https://creativecommons.org/licenses/by-nc/4.0/legalcode.es) para más detalles.