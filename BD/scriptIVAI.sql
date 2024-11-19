#CREATE DATABASE dbo;
#CREATE USER 'IVAI_db'@'localhost' IDENTIFIED BY 'trans_ivai';
#GRANT ALL PRIVILEGES ON dbo.* TO 'IVAI_db'@'localhost';
#FLUSH PRIVILEGES;
use dbo;
#Atributos con 255 cmabiar a Maxima cantidad de carácteres
#Registro Cursos
#DROP TABLE Curso;
CREATE TABLE Curso (
IdCurso INT AUTO_INCREMENT NOT NULL, 
NombreCurso VARCHAR(200) NULL, 
Fecha VARCHAR(200) NULL,
Hora VARCHAR(50) NULL, 
Imparte VARCHAR(200) NULL, 
Cupo INT NULL,
EstatusCupo INT NULL,
EstatusCurso VARCHAR(255) NULL,
Modalidad VARCHAR(250) NULL, 
Direccion VARCHAR(200) NULL,
CorreoSeguimiento VARCHAR(200) NULL,
Tipo VARCHAR(20) NULL,
Curso VARCHAR(100) NULL,
LigaTeams VARCHAR(255) NOT NULL,
ValorCurricular VARCHAR(10) NULL,
PRIMARY KEY (IdCurso)
);
delete from curso;

#Registro Estados
##DROP TABLE Estados;
CREATE TABLE Estados(
Id INT NOT NULL,
Estado VARCHAR(100) NOT NULL,
PRIMARY KEY (Estado)
);

#Registro Tipos de Cursos
DROP TABLE TIPOCURSO;
CREATE TABLE TIPOCURSO(
Id INT AUTO_INCREMENT,
Tipo VARCHAR(255),
PRIMARY KEY (Id)
);

#Regitro Registro
#DROP TABLE Registro;
CREATE TABLE Registro(
IdRegistro INT AUTO_INCREMENT NOT NULL UNIQUE,
Nombre VARCHAR(100) NOT NULL,
#Anexo atributo APELLIDOS
Apellidos VARCHAR(100) NOT NULL,
SO varchar(255) NULL,
Telefono VARCHAR(30) NULL,
Correo VARCHAR(200) NOT NULL,
IdCurso INT NOT NULL,
Procedencia VARCHAR(300) NULL,
GradoEstudios VARCHAR (50) NULL,
OrdenGobierno VARCHAR(50) NULL,
Area VARCHAR(250) NULL, 
Cargo VARCHAR(250) NULL,
Genero VARCHAR(255) NULL,
Estado VARCHAR(50) NULL,
Fecha VARCHAR(200) NULL,
InfoEventos VARCHAR(5) NULL,
Interprete VARCHAR(5) NULL,
Asistencia VARCHAR(5) DEFAULT FALSE,
PRIMARY KEY (Correo, IdCurso),
FOREIGN KEY (IdCurso) REFERENCES Curso(IdCurso),
FOREIGN KEY(Estado) REFERENCES Estados(Estado)
);

#Registro Usuario
#DROP TABLE Usuario;
CREATE TABLE Usuario(
IdUsuario INT AUTO_INCREMENT NOT NULL,
Usuario VARCHAR(50),
Pass VARCHAR(50) NULL,
Nombre VARCHAR (5000) NULL,
Rol VARCHAR (1) NULL,
PRIMARY KEY (IdUsuario,Usuario)
);

INSERT INTO Usuario ( Usuario, Pass, Nombre, Rol) values ('Angel','1234','Angel Diego', '1');

insert into TIPOCURSO ( Tipo ) values ('Seleccionar Curso');
insert into TIPOCURSO ( Tipo ) values ('Aviso de Privacidad');
insert into TIPOCURSO ( Tipo ) values ('Clasificación y Desclasificación de Información');
insert into TIPOCURSO ( Tipo ) values ('Consejos Consultivos de Gobierno Abierto P1');
insert into TIPOCURSO ( Tipo ) values ('Criterios de Investigación en Materia de Transparencia');
insert into TIPOCURSO ( Tipo ) values ('Elaboración y Registro de los Sistemas de Datos');
insert into TIPOCURSO ( Tipo ) values ('Encuentro Intercultural');
insert into TIPOCURSO ( Tipo ) values ('Inclusión Lineamientos para Organización y Conservación de Archivos');
insert into TIPOCURSO ( Tipo ) values ('INFOMEX y PNT');
insert into TIPOCURSO ( Tipo ) values ('Instrumento Consulta y Control Archivístico');
insert into TIPOCURSO ( Tipo ) values ('Introducción Ley General de Archivos');
insert into TIPOCURSO ( Tipo ) values ('Ley Gral. Protección de Datos Personales');
insert into TIPOCURSO ( Tipo ) values ('Lineamientos Cumplimiento Obligaciones de Transparencia');
insert into TIPOCURSO ( Tipo ) values ('Obligaciones Datos Personales');
insert into TIPOCURSO ( Tipo ) values ('Obligaciones Transparencia');
insert into TIPOCURSO ( Tipo ) values ('Organización de Archivos Públicos');
insert into TIPOCURSO ( Tipo ) values ('Plataforma Nacional de Transparencia');
insert into TIPOCURSO ( Tipo ) values ('Protección de Datos Personales');
insert into TIPOCURSO ( Tipo ) values ('Recurso de Revisión');
insert into TIPOCURSO ( Tipo ) values ('Sistema de Gestión Seguridad de Datos Personales');
insert into TIPOCURSO ( Tipo ) values ('Consulta de Información y Solicitudes de Información');
insert into TIPOCURSO ( Tipo ) values ('La Responsabilidad de Protejer DP ante COVID 19');
insert into TIPOCURSO ( Tipo ) values ('Gobierno Abierto y los Consejos Consultivos');
insert into TIPOCURSO ( Tipo ) values ('Gobierno Abierto Casos de Éxito P2');
insert into TIPOCURSO ( Tipo ) values ('Sistema de Solicitudes de Información SISAI2.0');
insert into TIPOCURSO ( Tipo ) values ('Otro "Nombre en Encabezado"');

INSERT INTO Estados (Id, Estado) VALUES
(1, 'Aguascalientes'),
(2, 'Baja California'),
(3, 'Baja California Sur'),
(4, 'Campeche'),
(5, 'Chiapas'),
(6, 'Chihuahua'),
(7, 'Ciudad de México'),
(8, 'Coahuila'),
(9, 'Colima'),
(10, 'Durango'),
(11, 'Estado de México'),
(12, 'Guanajuato'),
(13, 'Guerrero'),
(14, 'Hidalgo'),
(15, 'Jalisco'),
(16, 'Michoacán'),
(17, 'Morelos'),
(18, 'Nayarit'),
(19, 'Nuevo León'),
(20, 'Oaxaca'),
(21, 'Puebla'),
(22, 'Querétaro'),
(23, 'Quintana Roo'),
(24, 'San Luis Potosí'),
(25, 'Sinaloa'),
(26, 'Sonora'),
(27, 'Tabasco'),
(28, 'Tamaulipas'),
(29, 'Tlaxcala'),
(30, 'Veracruz'),
(31, 'Yucatán'),
(32, 'Zacatecas');