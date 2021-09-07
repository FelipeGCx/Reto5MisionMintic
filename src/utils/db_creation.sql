CREATE SCHEMA RESTAURANTE;
USE RESTAURANTE;

CREATE TABLE IF NOT EXISTS TipoPlato (
  TP_Id INT NOT NULL AUTO_INCREMENT,
  TP_Nombre VARCHAR(100) NOT NULL,
  PRIMARY KEY (TP_Id)
);

INSERT INTO tipoplato (TP_Nombre) VALUES ('Entrada');
INSERT INTO tipoplato (TP_Nombre) VALUES ('Plato Principal');
INSERT INTO tipoplato (TP_Nombre) VALUES ('Postres');
INSERT INTO tipoplato (TP_Nombre) VALUES ('Sopas');

CREATE TABLE IF NOT EXISTS Plato (
  Pl_Id INT NOT NULL AUTO_INCREMENT,
  Pl_Nombre VARCHAR(100) NOT NULL,
  Pl_Precio DOUBLE(6,1) NOT NULL,
  Pl_TipoPlato INT,
  PRIMARY KEY (Pl_Id),
  INDEX (Pl_TipoPlato),
  CONSTRAINT fk_Plato_TipoPlato1 
    FOREIGN KEY (Pl_TipoPlato) 
    REFERENCES TipoPlato (TP_Id)
    ON UPDATE SET NULL
    ON DELETE SET NULL
);

INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES("Chicharrones",19000,1);
INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES("Ojo de Bife",45000,2);
INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES("Chuletón",48000,2);
INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES("Napoleón",9500,3);
INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES("Sopa de Cuchuco grande",26000,4);
INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES("Mojarra",29000,2);

CREATE TABLE IF NOT EXISTS Mesero (
  Me_Id INT NOT NULL AUTO_INCREMENT,
  Me_Nombres VARCHAR(200) NOT NULL,
  Me_Apellidos VARCHAR(200) NOT NULL,
  Me_Documento VARCHAR(20) NOT NULL UNIQUE,
  Me_Correo VARCHAR (200) NULL,
  Me_Telefono VARCHAR(20) NULL,
  PRIMARY KEY (Me_Id)
);

INSERT INTO Mesero (Me_Nombres,Me_Apellidos,Me_Documento,Me_Correo,Me_Telefono) VALUES ("Juan","Perez","110000","juanperez112a@google.com","338771220");
INSERT INTO Mesero (Me_Nombres,Me_Apellidos,Me_Documento,Me_Correo,Me_Telefono) VALUES ("Maria","Jimenez","131420","ma665re@yihoo.com","258695531");
INSERT INTO Mesero (Me_Nombres,Me_Apellidos,Me_Documento,Me_Correo,Me_Telefono) VALUES ("Esther","Hernández","156179","esther21@hotmailcito.com","490113762");

CREATE TABLE IF NOT EXISTS Cliente (
  Cl_Id INT NOT NULL AUTO_INCREMENT,
  Cl_Nombres VARCHAR(200) NOT NULL,
  Cl_Apellidos VARCHAR(200) NOT NULL,
  Cl_Documento VARCHAR(20) NOT NULL,
  Cl_Telefono VARCHAR(20),
  PRIMARY KEY (Cl_Id)
);

INSERT INTO Cliente (Cl_Nombres,Cl_Apellidos,Cl_Documento,Cl_Telefono) VALUES ("Marta","Gomez","1542300","478585971");
INSERT INTO Cliente (Cl_Nombres,Cl_Apellidos,Cl_Documento,Cl_Telefono) VALUES ("Roberto","Malagón","3578300","344116771");
INSERT INTO Cliente (Cl_Nombres,Cl_Apellidos,Cl_Documento,Cl_Telefono) VALUES ("Verónica","Salcedo","9373047","293565431");
INSERT INTO Cliente (Cl_Nombres,Cl_Apellidos,Cl_Documento,Cl_Telefono) VALUES ("Pedro","Lizarazo","9371641","478585771");

CREATE TABLE IF NOT EXISTS Orden (
  Or_Id INT NOT NULL AUTO_INCREMENT,
  Or_FechaServicio DATE NOT NULL,
  Or_IdPlato INT,
  Or_IdCliente INT,
  Or_IdMesero INT,
  PRIMARY KEY (Or_Id),
  INDEX (Or_IdPlato),
  CONSTRAINT fk_Orden_Plato1
  FOREIGN KEY (Or_IdPlato) REFERENCES Plato (Pl_Id)
  ON UPDATE SET NULL
  ON DELETE SET NULL,
  INDEX (Or_IdCliente),
  CONSTRAINT fk_Orden_Cliente1
  FOREIGN KEY (Or_IdCliente) REFERENCES Cliente (Cl_Id)
  ON UPDATE SET NULL
  ON DELETE SET NULL,
  INDEX (Or_IdMesero),
  CONSTRAINT fk_Orden_Mesero1
  FOREIGN KEY (Or_IdMesero) REFERENCES Mesero (Me_Id)
  ON UPDATE SET NULL
  ON DELETE SET NULL
);

INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-01-12',2,1,1);
INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-02-11',3,2,2);
INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-02-15',4,2,1);
INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-03-01',3,3,3);
INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-01-11',4,4,1);
INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-08-11',2,2,3);
INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES ('2021-07-01',1,2,3);