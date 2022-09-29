CREATE TABLE Clientes(
    apellido STRING,
    nombre STRING,
    tipo_documento STRING,
    nro_documento INTEGER PRIMARY KEY,
    cuil STRING,
    fecha_nacimiento DATE,
    plan STRING,
    parentesco STRING,
    fecha_alta_plan DATE,
    email STRING,
    contraseña STRING,

    CONSTRAINT FK_Clientes_Plan FOREIGN KEY (plan) REFERENCES Planes (nombre)
);
CREATE TABLE Empleados(
    apellido STRING,
    nombre STRING,
    tipo_documento STRING,
    nro_documento INTEGER PRIMARY KEY,
    telefono STRING,
    email STRING,
    cargo STRING,
    usuario STRING,
    contraseña STRING
);
CREATE TABLE Planes(
    nombre STRING PRIMARY KEY,
    costo DECIMAL,
    beneficios STRING
);
CREATE TABLE Solicitudes_Alta(
    codigo INTEGER PRIMARY KEY,
    tipo_plan STRING,
    cliente INTEGER,
    fecha DATE,

    CONSTRAINT FK_SolicitudesAlta_Clientes FOREIGN KEY (cliente) REFERENCES Clientes (nro_documento)
);
CREATE TABLE Solicitudes_Reintegro(
    codigo INTEGER PRIMARY KEY,
    cliente INTEGER,
    fecha DATE,
    razon STRING,
    practica STRING,

    CONSTRAINT FK_SolicitudesReintegro_Cliente FOREIGN KEY (cliente) REFERENCES Clientes (nro_documento)
);
CREATE TABLE Solicitudes_Prestaciones(
    codigo INTEGER PRIMARY KEY,
    cliente INTEGER,
    fecha DATE,
    razon STRING,
    practica STRING,

    CONSTRAINT FK_SolicitudesPrestaciones_Cliente FOREIGN KEY (cliente) REFERENCES Clientes (nro_documento)
);