# 💻 Tienda de Informática - Gestión CRUD con Hibernate

Este proyecto es una aplicación de consola en Java diseñada para gestionar el inventario de una tienda de informática. Implementa un sistema completo de operaciones **CRUD** (Crear, Leer, Actualizar y Borrar) utilizando **Hibernate ORM** para la persistencia de datos y el patrón de diseño **DAO** (Data Access Object) para separar la lógica de negocio del acceso a la base de datos.

## ⚙️ Tecnologías Utilizadas

* **Lenguaje:** Java
* **ORM:** Hibernate 6.x
* **Base de Datos:** MySQL
* **Gestor de Dependencias:** Maven
* **Arquitectura:** Patrón DAO (Data Access Object)

## 🚀 Funcionalidades Principales

El sistema se divide en dos módulos principales accesibles a través de un menú interactivo por consola:

### 1. Gestión de Fabricantes
Permite administrar las marcas o fabricantes de los componentes.
* **Crear:** Añade un nuevo fabricante a la base de datos verificando que no exista previamente.
* **Leer / Buscar:** Lista todos los fabricantes registrados o busca un fabricante específico (por ID o por Nombre).
* **Actualizar:** Modifica el nombre de un fabricante existente.
* **Borrar:** Elimina un fabricante (incluyendo un aviso de confirmación y borrando en cascada sus productos asociados si está configurado).
* **Ver Productos:** Busca un fabricante por nombre y muestra una lista completa de todos los productos asociados a él.

### 2. Gestión de Productos
Permite administrar el inventario de artículos de la tienda.
* **Crear:** Añade un nuevo producto. Si el fabricante introducido no existe en el sistema, lo crea automáticamente antes de vincular el producto.
* **Leer / Buscar:** Muestra el listado de productos junto con su precio y el nombre de su fabricante. También permite buscar quién fabrica un producto específico.
* **Actualizar:** Permite modificar el precio de un producto existente introduciendo su ID.
* **Borrar:** Elimina un producto del inventario mediante su ID.
