DROP TABLE IF EXISTS usuario cascade;
DROP TABLE IF EXISTS producto cascade; 
DROP TABLE IF EXISTS stock cascade;
DROP TABLE IF EXISTS categoria cascade;
CREATE TABLE categoria(  
	cat_id INT NOT null AUTO_INCREMENT primary key,  
	nombre VARCHAR(50) NOT NULL,  
	descripcion VARCHAR(150),
	nemonico VARCHAR(20) NOT NULL unique,
	fecha date,
	padre_id INT,
	CONSTRAINT fk_categoria_padre FOREIGN KEY (padre_id) REFERENCES categoria(cat_id)
);
CREATE TABLE producto (  
	pro_id INT NOT null AUTO_INCREMENT  PRIMARY KEY,  
	nombre VARCHAR(50) NOT NULL,  
	descripcion VARCHAR(150),
	icon varchar(100),
	cantidad INT,
	valor_unitario decimal(10,2),
	fecha date,
	cat_id INT NOT NULL,
	estado boolean,
	CONSTRAINT fk_categoria_producto FOREIGN KEY (cat_id) REFERENCES categoria(cat_id)
);  
CREATE TABLE stock (  
	sto_id INT AUTO_INCREMENT  PRIMARY KEY,  
	observacion VARCHAR(150),
	cantidad INT,
	cantidad_anterior INT,
	cantidad_actual INT,
	valor_unitario decimal(10,2),
	fecha_entrada date,
	fecha_salida date,
	pro_id INT NOT NULL,
	estado boolean,
	CONSTRAINT fk_producto_stock FOREIGN KEY (pro_id) REFERENCES producto(pro_id)
); 
DROP TABLE IF EXISTS transaccion; 
CREATE TABLE transaccion (  
	tra_id INT AUTO_INCREMENT  PRIMARY KEY,  
	pro_id INT NOT NULL,  
	cantidad INT,
	valor_total decimal(10,2),	
	fecha date,
	CONSTRAINT fk_transaccion_producto FOREIGN KEY (pro_id) REFERENCES producto(pro_id)
);  
CREATE TABLE usuario (  
	usu_id INT AUTO_INCREMENT  PRIMARY KEY,  
	nombre VARCHAR(50) NOT NULL,  
	login VARCHAR(50) NOT NULL,
	clave VARCHAR(50) NOT NULL,
	estado boolean NOT NULL,
	fecha date,
	tipo_usuario INT NOT NULL,
	CONSTRAINT fk_usuario_tipo FOREIGN KEY (tipo_usuario) REFERENCES categoria(cat_id)
);  