/*puebla las tablas */
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'David', 'Mondragón', 'david@mail.com', '1976-09-19', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Gabriela', 'Esparta', 'gaby@mail.com', '1980-02-10', '');

INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Ana', 'Uno', 'Ana@mail.com', '1995-09-12', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Benito', 'Dos', 'benito@mail.com', '1980-02-11', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Clara', 'Tres', 'clara@mail.com', '1995-09-10', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Dani', 'Cuatro', 'dani@mail.com', '1980-02-09', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Ester', 'Cinco', 'ester@mail.com', '1995-09-08', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Fernando', 'Seis', 'fernando@mail.com', '1980-02-07', '');

INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Soto', 'Micro', 'soto@mail.com', '1995-09-06', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Olga', 'Pasos', 'olga@mail.com', '1980-02-05', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Paco', 'Tres', 'paco@mail.com', '1995-09-04', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Tonio', 'Press', 'tonio@mail.com', '1980-02-03', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Raul', 'Lisalde', 'raul@mail.com', '1995-09-02', '');
INSERT INTO clientes( nombre, apellido, email,created_at, foto) VALUES( 'Petra', 'Oreon', 'petra@mail.com', '1980-02-01', '');


/* Puebla la tabla productos */
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Panasonic Pantalla LCD', 30000, NOW());
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Sony Camara Digital', 12500, NOW());
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Apple IPod Shuffle', 14000, NOW());
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Sony Notebook', 37000, NOW());
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Hewlet Packard Multifuncional', 6900, NOW());
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Bianchi Bicicleta', 7000, NOW());
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CREATED_AT) VALUES('Comoda 5 Cajones', 4500, NOW());

INSERT INTO facturas(descripcion, observacion, cliente_id, created_at) values('Factura equipos de oficina', null, 1, NOW())
INSERT INTO facturas_items(cantidad, factura_id, producto_id) values( 1, 1, 1);
INSERT INTO facturas_items(cantidad, factura_id, producto_id) values( 2, 1, 4);
INSERT INTO facturas_items(cantidad, factura_id, producto_id) values( 1, 1, 5);
INSERT INTO facturas_items(cantidad, factura_id, producto_id) values( 1, 1, 7);
INSERT INTO facturas(descripcion, observacion, cliente_id, created_at) values('Factura Bicicleta', 'Nota de ejemplo importantw', 1,NOW())
INSERT INTO facturas_items(cantidad, factura_id, producto_id) values( 3, 2, 6);


