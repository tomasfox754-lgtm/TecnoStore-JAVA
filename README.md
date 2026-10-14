# TecnoStore Tomas Zorro

Sistema de consola en Java para gestionar el inventario, clientes y ventas de una tienda de celulares.

## Funcionalidades

- Registrar, listar, actualizar y eliminar celulares.
- Registrar y listar clientes, validando el correo y la identificacion unica.
- Registrar ventas con varios celulares, calcular IVA del 19 % y descontar stock.
- Consultar celulares con stock bajo, top 3 mas vendidos y ventas totales por mes.
- Exportar el resumen de ventas a `reporte_ventas.txt`.
- Impedir la eliminacion de celulares asociados a ventas.

## Tecnologias y conceptos

Java 17, Maven, MySQL y JDBC. El proyecto utiliza colecciones, Stream API, excepciones, try-with-resources, encapsulamiento, herencia, composicion y el patron Singleton.

El codigo se organiza en modelos, vistas, controladores, servicios, persistencia y utilidades. `Main` inicializa la aplicacion.

## Ejecucion

1. Abrir la carpeta `mavenproject1` como proyecto Maven en NetBeans.
2. Tener MySQL iniciado y la base `tecnostore_db` con las tablas `celulares`, `clientes`, `ventas` y `detalle_ventas`.
3. Configurar las variables de entorno `DB_USER` y `DB_PASSWORD` para la ejecucion.
4. Verificar la URL de conexion en `ConexionDB.java`.
5. Compilar y ejecutar `com.tecnostore.principal.Main`.

La opcion de exportacion muestra la ruta del archivo generado. Las credenciales y los datos de MySQL no se incluyen en el repositorio.


<img width="1272" height="601" alt="image" src="https://github.com/user-attachments/assets/7b71531e-bd09-4760-b65b-ed9d3f1636ee" />
<img width="1434" height="639" alt="image" src="https://github.com/user-attachments/assets/da0b3f53-6e71-4ac6-a022-d4aefdcde838" />
<img width="1701" height="634" alt="image" src="https://github.com/user-attachments/assets/27acf3b5-949b-4ca9-9dca-3172fea6aeb2" />
<img width="775" height="621" alt="image" src="https://github.com/user-attachments/assets/d44fcc84-a0a3-4d68-b7aa-58be2e14240e" />

SCRIPT MYSQL
CREATE DATABASE IF NOT EXISTS tecnostore_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE tecnostore_db;

CREATE TABLE celulares (
    id INT NOT NULL AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    sistema_operativo VARCHAR(50) NOT NULL,
    gama ENUM('ALTA', 'MEDIA', 'BAJA') NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE clientes (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(30) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY identificacion (identificacion)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE ventas (
    id INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    fecha DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    KEY id_cliente (id_cliente),
    CONSTRAINT ventas_ibfk_1
        FOREIGN KEY (id_cliente)
        REFERENCES clientes (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE detalle_ventas (
    id INT NOT NULL AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_celular INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    KEY id_venta (id_venta),
    KEY id_celular (id_celular),
    CONSTRAINT detalle_ventas_ibfk_1
        FOREIGN KEY (id_venta)
        REFERENCES ventas (id),
    CONSTRAINT detalle_ventas_ibfk_2
        FOREIGN KEY (id_celular)
        REFERENCES celulares (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
