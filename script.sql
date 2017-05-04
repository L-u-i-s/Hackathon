SET FOREIGN_KEY_CHECKS = 0;
drop table if exists mercado;
drop table if exists puesto;
drop table if exists categoria;
drop table if exists usuario;
SET FOREIGN_KEY_CHECKS = 1;

create table mercado(
id int primary key auto_increment,
nombre varchar(128) not null unique,
direccion varchar(512) not null
);

create table categoria(
id int primary key auto_increment,
nombre varchar(128) not null unique
);


create table puesto(
id int primary key auto_increment,
mercado_id int not null,
nombre varchar(128),
num_puesto varchar(8) not null,
propietario varchar(128),
productos varchar(1024) not null,
telefono varchar(16),
categoria_id int not null,
descripcion varchar(1024) not null,
foreign key(mercado_id) references mercado(id),
foreign key(categoria_id) references categoria(id)
);


create table usuario(
id int primary key auto_increment,
correo varchar(128) not null unique,
password varchar(128) not null
);

insert into categoria(nombre) values
('Frutas y verduras'),
('Artesanías'),
('Comida');

insert into mercado(nombre,direccion) values 
('Mercado 20 de noviembre', 'dirección'),
('Mercado Benito Juárez', 'dirección'),
('Mercado de Artesanías', 'dirección'),
('Mercado Hidalgo', 'dirección');

insert into puesto(mercado_id,nombre,num_puesto,propietario,productos,telefono,categoria_id,descripcion) values
(1,'puesto1',1,'prop1','prod1,prod2,prod3','1234',1,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(1,'puesto2',2,'prop2','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,'puesto3',3,'prop3','prod1,prod2,prod3','1234',3,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,'puesto4',4,'prop4','prod1,prod2,prod3','1234',1,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,'puesto7',7,'prop7','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,'puesto8',8,'prop8','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(3,'puesto5',5,'prop3','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(3,'puesto6',6,'prop3','prod1,prod2,prod3','1234',3,'Con tres generaciones de experiencia en la cocina oaxaqueñas, siendo su fundadora la Sra. Juana Sánchez Pacheco, una de las pioneras del mercado 20 de Nov.');


