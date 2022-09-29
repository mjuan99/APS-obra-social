CREATE TABLE Clientes(
    apellido STRING,
    nombre STRING,
    tipo_documento STRING,
    nro_documento INTEGER PRIMARY KEY,
    cuil INTEGER,
    fecha_nacimiento DATE,
    plan STRING,
    parentesco STRING,
    fecha_alta_plan DATE,
    email STRING,
    contraseña STRING
);
CREATE TABLE Empleados(
    apellido STRING,
    nombre STRING,
    tipo_documento STRING,
    nro_documento INTEGER PRIMARY KEY,
    telefono INTEGER,
    email STRING,
    cargo STRING,
    usuario STRING,
    contraseña STRING
);
CREATE TABLE Planes(
    nombre STRING PRIMARY KEY,
    costo DECIMAL(16, 2),
    beneficios STRING
);
CREATE TABLE Solicitudes_Alta(
    codigo INTEGER PRIMARY KEY,
    tipo_plan STRING,
    cliente INTEGER,
    fecha DATE
);
CREATE TABLE Solicitudes_Reintegro(
    codigo INTEGER PRIMARY KEY,
    cliente INTEGER,
    fecha DATE,
    razon STRING,
    practica STRING
);
CREATE TABLE Solicitudes_Prestaciones(
    codigo INTEGER PRIMARY KEY,
    cliente INTEGER,
    fecha DATE,
    razon STRING,
    practica STRING
);