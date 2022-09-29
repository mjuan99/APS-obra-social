CREATE TABLE Clientes(
    apellido STRING,
    nombre STRING,
    nro_documento INTEGER PRIMARY KEY,
    cuil STRING,
    fecha_nacimiento DATE,
    plan STRING,
    es_titular BIT,
    fecha_alta_plan DATE,
    email STRING,
    contraseña STRING,

    CONSTRAINT FK_Clientes_Plan FOREIGN KEY (plan) REFERENCES Planes (nombre)
);
CREATE TABLE Familiares(
    dni_cliente_cabecera INTEGER,
    dni_cliente_familiar INTEGER,
    parentesco STRING,

    PRIMARY KEY (dni_cliente_cabecera, dni_cliente_familiar),

    CONSTRAINT FK_Familiares_Cabecera FOREIGN KEY (dni_cliente_cabecera) REFERENCES Clientes (nro_documento),
    CONSTRAINT FK_Familiares_Familiar FOREIGN KEY (dni_cliente_familiar) REFERENCES Clientes (nro_documento)

);
CREATE TABLE Empleados(
    apellido STRING,
    nombre STRING,
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
    tipo_plan STRING,
    cliente INTEGER,
    fecha DATE,

    PRIMARY KEY (tipo_plan, cliente),
    CONSTRAINT FK_SolicitudesAlta_Clientes FOREIGN KEY (cliente) REFERENCES Clientes (nro_documento)
);
CREATE TABLE Solicitudes_Reintegro(
    id_solicitud INTEGER PRIMARY KEY,
    cliente INTEGER,
    fecha DATE,
    razon STRING,
    practica STRING,

    CONSTRAINT FK_SolicitudesReintegro_Cliente FOREIGN KEY (cliente) REFERENCES Clientes (nro_documento)
);
CREATE TABLE Solicitudes_Prestaciones(
    id_solicitud INTEGER PRIMARY KEY,
    cliente INTEGER,
    fecha DATE,
    razon STRING,
    practica STRING,

    CONSTRAINT FK_SolicitudesPrestaciones_Cliente FOREIGN KEY (cliente) REFERENCES Clientes (nro_documento)
);