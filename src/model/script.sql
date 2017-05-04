drop table if exists dependencia cascade;
drop table if exists area cascade;
drop table if exists persona cascade;
drop table if exists usuario cascade;
drop table if exists rol cascade;
drop table if exists usuario_rol cascade;
drop table if exists asunto cascade;
drop table if exists tarea cascade;
drop table if exists anexo_doc cascade;
drop table if exists anexo_turno cascade;
drop table if exists doc_respuesta cascade;
drop table if exists anexo_respuesta cascade;
drop table if exists respuesta cascade;
drop table if exists respuesta_rol cascade;
drop table if exists instruccion cascade;
drop table if exists atencion cascade;
drop table if exists estado cascade;

drop sequence if exists sec_dependencia cascade;
create sequence sec_dependencia;
create table dependencia(
id integer default nextval('sec_dependencia') primary key,
nombre varchar(256),
domicilio varchar(512),
correo varchar(128),
telefono varchar(16)
);

drop sequence if exists sec_area cascade;
create sequence sec_area;
create table area(
id integer default nextval('sec_area') primary key,
nombre varchar(32) not null,
area_id integer,
foreign key(area_id) references area(id)
);

drop sequence if exists sec_persona cascade;
create sequence sec_persona;
create table persona(
id int default nextval('sec_persona') primary key,
nombre varchar(256) not null,
apellido_paterno varchar(256) not null,
apellido_materno varchar(256),
domicilio varchar(512),
correo varchar(128),
telefono varchar(16),
area_id int,
foreign key (area_id) references area(id)
);

drop sequence if exists sec_usuario cascade;
create sequence sec_usuario;
create table usuario(
id int default nextval('sec_usuario') primary key,
username varchar(128) not null,
password varchar(128) not null,
persona_id int not null,
foreign key (persona_id) references persona(id)
);

drop sequence if exists sec_rol cascade;
create sequence sec_rol;
create table rol(
id int default nextval('sec_rol') primary key,
nombre varchar(128) not null,
metodo varchar(128)
);

drop sequence if exists sec_usuario_rol cascade;
create sequence sec_usuario_rol;
create table usuario_rol(
id int default nextval('sec_usuario_rol') primary key,
usuario_id int not null,
rol_id int not null,
foreign key (usuario_id) references usuario(id),
foreign key (rol_id) references rol(id)
);

drop sequence if exists sec_asunto cascade;
create sequence sec_asunto;
create table asunto(
id int default nextval('sec_asunto') primary key,
folio varchar(16),
fecha_limite date null,
fecha_documento date null,
fecha_atencion date null,
fecha_recepcion date null,
titulo varchar(128) not null,
descripcion varchar(512) not null,
descripcion_anexos varchar(512),
observaciones varchar(512),
status int not null default 1,
remitente_id int,
destinatario_id int,
foreign key (remitente_id) references persona(id),
foreign key (destinatario_id) references persona(id)
);

drop sequence if exists sec_instruccion cascade;
create sequence sec_instruccion;
create table instruccion(
id int default nextval('sec_instruccion') primary key,
nombre varchar(64) not null
);

drop sequence if exists sec_estado cascade;
create sequence sec_estado;
create table estado(
id int default nextval('sec_estado') primary key,
nombre varchar(64) not null
);

drop sequence if exists sec_atencion cascade;
create sequence sec_atencion;
create table atencion(
id int default nextval('sec_atencion') primary key,
instruccion_id int,
detalle varchar(512),
respuesta varchar(512),
estado_id int,
foreign key (instruccion_id) references instruccion(id),
foreign key (estado_id) references estado(id)
);

drop sequence if exists sec_tarea cascade;
create sequence sec_tarea;
create table tarea(
id int default nextval('sec_tarea') primary key,
asunto_id int not null,
atencion_id int,
remitente_id int,
responsable_id int not null,
destinatario_id int,
fecha_recibido timestamp default CURRENT_TIMESTAMP,
fecha_compromiso timestamp null,
fecha_compromiso2 timestamp null,
fecha_atencion timestamp null,
rol_id int not null,
foreign key(asunto_id) references asunto(id),
foreign key(remitente_id) references persona(id),
foreign key(responsable_id) references persona(id),
foreign key(destinatario_id) references persona(id),
foreign key(atencion_id) references atencion(id),
foreign key(rol_id) references rol(id)
);

drop sequence if exists sec_anexo_doc cascade;
create sequence sec_anexo_doc;
create table anexo_doc(
id int default nextval('sec_anexo_doc') primary key,
ruta varchar(512) not null,
size int,
asunto_id int not null,
foreign key (asunto_id) references asunto(id)
);

drop sequence if exists sec_anexo_turno cascade;
create sequence sec_anexo_turno;
create table anexo_turno(
id int default nextval('sec_anexo_turno') primary key,
ruta varchar(512) not null,
size int,
tarea_id int not null,
foreign key (tarea_id) references tarea(id)
);

drop sequence if exists sec_doc_respuesta cascade;
create sequence sec_doc_respuesta;
create table doc_respuesta(
id int default nextval('sec_doc_respuesta') primary key,
fecha_documento date null,
fecha_gestion date null,
descripcion varchar(512) not null,
descripcion_anexos varchar(512),
observaciones varchar(512),
asunto_id int not null,
foreign key(asunto_id) references asunto(id)
);

drop sequence if exists sec_anexo_respuesta cascade;
create sequence sec_anexo_respuesta;
create table anexo_respuesta(
id int default nextval('sec_anexo_respuesta') primary key,
nombre varchar(512) not null,
size int,
doc_respuesta_id int not null,
foreign key (doc_respuesta_id) references doc_respuesta(id)
);

drop sequence if exists sec_respuesta cascade;
create sequence sec_respuesta;
create table respuesta(
id int default nextval('sec_respuesta') primary key,
nombre varchar(64) not null,
metodo varchar(64) not null
);

drop sequence if exists sec_respuesta_rol cascade;
create sequence sec_respuesta_rol;
create table respuesta_rol(
id int default nextval('sec_respuesta_rol') primary key,
respuesta_id int not null,
rol_id int not null,
foreign key (respuesta_id) references respuesta(id),
foreign key (rol_id) references rol(id)
);

insert into area(id,nombre) values(nextval('sec_area'),'Dirección general');
insert into area(id,nombre) values(nextval('sec_area'),'Dirección adm');
insert into area(id,nombre) values(nextval('sec_area'),'Dirección académica');
insert into area(id,nombre) values(nextval('sec_area'),'Subdirección adm');
insert into area(id,nombre) values(nextval('sec_area'),'Subdirección académica');
insert into area(id,nombre) values(nextval('sec_area'),'Coordinación sistemas');
insert into area(id,nombre) values(nextval('sec_area'),'Coordinación sicología');
insert into area(id,nombre) values(nextval('sec_area'),'Coordinación comunicación');
update area set area_id=1 where id IN (2,3);
update area set area_id=2 where id=4;
update area set area_id=3 where id=5;
update area set area_id=5 where id IN (6,7,8);

insert into area(id,nombre,area_id) values(nextval('sec_area'),'Control escolar',4);
insert into area(id,nombre,area_id) values(nextval('sec_area'),'Becas',4);
insert into area(id,nombre,area_id) values(nextval('sec_area'),'Contabilidad',4);

INSERT INTO instruccion VALUES (1,'Atención');
insert into estado(nombre) values ('Iniciado'),('En proceso'),('Atendido'),('Rechazado'),('Cancelado');
INSERT INTO persona VALUES (1,'Luis','Hernández','Moreno','dom1','cor1','tel1',1),(2,'Juan','Pérez','Méndez','dom2','cor2','tel2',2),(3,'Raul','Sánchez','Gómez','dom3','cor3','tel3',3),(4,'Jose','Mendez','Juarez','dom4','cor4','tel4',3);
INSERT INTO respuesta VALUES (1,'Enviar a Revisión','enviarARevision'),(3,'Enviar a turnar','enviarATurnar'),(4,'Corregir asunto','enviarACorregir'),(6,'Turnar y dar seguimiento','enviarTurnado'),(7,'Responder turno','responderTurno'),(8,'Rechazar turno','rechazarTurno'),(9,'Enviar a gestión externa','enviarAGestionExterna'),(11,'Registrar documento de respuesta','concluirTurnado'),(15,'Cerrar asunto','cerrarAsunto'),(16,'Cancelar asunto','cancelarAsunto');
INSERT INTO rol VALUES (0,'Corrección','corregirAsunto'),(1,'Recepción','getAsuntosRecepcion'),(2,'Revisión','revisarAsunto'),(3,'Turnado','turnarAsunto'),(4,'Atención','atenderAsunto'),(5,'Seguimiento','seguimientoAsunto'),(6,'Respuesta','respuestaAsunto'),(11,'Notificación de respuesta',NULL);
INSERT INTO respuesta_rol VALUES (1,1,1),(4,3,2),(5,4,2),(8,6,3),(10,7,4),(11,8,4),(12,15,1),(13,15,2),(14,15,3),(15,16,2),(16,16,3),(17,11,5),(18,15,5),(19,16,5),(24,9,6),(25,15,6);
INSERT INTO usuario VALUES (1,'luis','123',1),(2,'juan','123',2),(3,'raul','123',3),(4,'jose','123',4);
INSERT INTO usuario_rol VALUES (1,1,1),(2,2,1),(3,2,2),(4,3,2),(5,3,3),(6,4,4);