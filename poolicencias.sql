create database licencias;
use licencias;
-- cédula, nombre, tipo de licencia, fecha solicitud (auto).
create table usuariosPlataforma(
id int primary key auto_increment,
cedula char(10) not null,
clave varchar(300) not null,
rol varchar(15) not null,
estadoUsuario varchar(15) not null default 'ACTIVO',
intentos int default 0
);

create table usuariosSolicitantes(
id int auto_increment primary key,
cedula char(10) unique check (length(cedula)=10),
clave varchar(300),
nombre varchar(100) not null,
edad int not null check(edad>=18),
tipoLicencia char(1) not null,
fechaSolicitud date not null, -- cambios
estadoUsuario varchar(15) default 'ACTIVO',
estadoTramite varchar(15) default 'Pendiente',
intentos int default 0
);

-- certificado médico, pago, multas (checkboxes), observaciones
create table requisitos(
cedula char(10) primary key,
constraint FK_cedulaR foreign key(cedula) references usuariosSolicitantes(cedula) ON DELETE CASCADE ON UPDATE CASCADE,
certificadoMedico bool default false,
pago bool default false,
multas bool default false,
observaciones varchar(200) default 'Ninguna'
);

create table examen(
cedula char(10) primary key,
constraint FK_cedulaE foreign key(cedula) references usuariosSolicitantes(cedula) ON DELETE CASCADE ON UPDATE CASCADE,
practica decimal(4,2) default 0.00,
teorica decimal(4,2) default 0.00,
promedio decimal(4,2) GENERATED ALWAYS AS ((practica + teorica) / 2) STORED,
estado char(2) default 'NO'
);

create table licencia(
cedula char(10) primary key,
constraint FK_cedulaL foreign key(cedula) references usuariosSolicitantes(cedula) ON DELETE CASCADE ON UPDATE CASCADE,
numeroLicencia int unique,
fechaEmision DATE not null, -- cambios
fechaVencimiento DATE GENERATED ALWAYS AS (DATE_ADD(fechaEmision, INTERVAL 5 YEAR)) STORED
);

create view tramites as select id,cedula,nombre,tipoLicencia,fechaSolicitud,estadoTramite from usuariosSolicitantes;

delimiter //
create function insertarUsuario(cedulaI char(10),nombreI varchar(100),edadI int,tipoLicenciaI char(1),claveI varchar(300)) returns varchar(15) deterministic
begin
	if edadI>=18 then
		insert into usuariosSolicitantes(cedula,clave,nombre,edad,tipoLicencia,fechaSolicitud)values(cedulaI,claveI,nombreI,edadI,tipoLicenciaI,curdate()); -- cambios
        insert into requisitos(cedula) values(cedulaI);
        insert into examen(cedula) values(cedulaI);
		return 'Exitoso';
	else
		return 'Error';
	end if;
end//

delimiter ;

create table auditoriaUsuario(
id int auto_increment primary key,
fecha_hora datetime default current_timestamp,
cedula char(10) unique check (length(cedula)=10),
nombre varchar(100) not null,
edad int not null check(edad>=18),
tipoLicencia char(1) not null,
fechaSolicitud date not null, -- cambios
estadoUsuario varchar(15) default 'ACTIVO',
estadoTramite varchar(15) default 'Pendiente'
);
DELIMITER //

CREATE TRIGGER auditoria_insertar_usuario 
AFTER INSERT ON usuariosSolicitantes
FOR EACH ROW
BEGIN
    INSERT INTO auditoriaUsuario(
        fecha_hora, cedula, nombre, edad, tipoLicencia, 
        fechaSolicitud, estadoUsuario, estadoTramite
    ) 
    VALUES (
        curdate(), NEW.cedula, NEW.nombre, NEW.edad, NEW.tipoLicencia, -- cambios
        NEW.fechaSolicitud, NEW.estadoUsuario, NEW.estadoTramite
    );
END //

DELIMITER ;

create table auditoriaLicencias(
id int primary key auto_increment,
cedula char(10),
numeroLicencia int unique,
fechaCreacion date not null, -- cambios
fechaEmision DATE,-- cambios
fechaVencimiento DATE-- cambios
);


DELIMITER //

CREATE TRIGGER auditoria_insertar_licencia 
AFTER INSERT ON licencia
FOR EACH ROW
BEGIN
    INSERT INTO auditoriaLicencias(
        cedula, numeroLicencia, fechaCreacion, 
        fechaEmision, fechaVencimiento
    ) 
    VALUES (
        NEW.cedula, NEW.numeroLicencia, curdate(), -- cambios
        NEW.fechaEmision, NEW.fechaVencimiento
    );
END //

DELIMITER ;


ALTER TABLE requisitos
 drop estadoRequisitos,
ADD estadoRequisitos VARCHAR(20)
 GENERATED ALWAYS AS (
    CASE
      WHEN certificadoMedico=1 and pago=1 and multas=0 THEN 'OK'
        ELSE 'NO'
    END
) STORED;


ALTER TABLE examen
DROP COLUMN estado,
ADD estado VARCHAR(20)
GENERATED ALWAYS AS (
    CASE
        WHEN promedio >= 7 THEN 'APROBADO'
        WHEN practica=0 and teorica=0 and promedio=0 then 'Pendiente'
        ELSE 'REPROBADO'
    END
) STORED;


