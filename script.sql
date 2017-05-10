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

create table usuario(
id int primary key auto_increment,
correo varchar(128) not null unique,
password varchar(128) not null
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
usuario_id int not null,
foreign key(mercado_id) references mercado(id),
foreign key(categoria_id) references categoria(id),
foreign key(usuario_id) references usuario(id)
);


insert into usuario(correo,password) values
('u1@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u2@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u3@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u4@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u5@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u6@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u7@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
('u8@gmail.com','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');

insert into categoria(nombre) values
('Frutas y verduras'),
('Artesanóas'),
('Comida');

insert into mercado(nombre,direccion) values 
('Mercado 20 de noviembre', 'dirección'),
('Mercado Benito Juórez', 'dirección'),
('Mercado de Artesanóas', 'dirección'),
('Mercado Hidalgo', 'dirección');

insert into puesto(mercado_id,usuario_id,nombre,num_puesto,propietario,productos,telefono,categoria_id,descripcion) values
(1,1,'puesto1',1,'prop1','prod1,prod2,prod3','1234',1,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(1,2,'puesto2',2,'prop2','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,3,'puesto3',3,'prop3','prod1,prod2,prod3','1234',3,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,4,'puesto4',4,'prop4','prod1,prod2,prod3','1234',1,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,5,'puesto7',7,'prop7','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(2,6,'puesto8',8,'prop8','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(3,7,'puesto5',5,'prop3','prod1,prod2,prod3','1234',2,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.'),
(3,8,'puesto6',6,'prop3','prod1,prod2,prod3','1234',3,'Con tres generaciones de experiencia en la cocina oaxaqueóas, siendo su fundadora la Sra. Juana Sónchez Pacheco, una de las pioneras del mercado 20 de Nov.');


