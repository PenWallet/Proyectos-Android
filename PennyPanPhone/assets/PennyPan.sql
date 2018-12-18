SET DATEFORMAT dmy
--CREATE DATABASE PennyPan
GO
USE PennyPan
GO

/*
	*********************************************************************************************
	*************************************** T A B L A S *****************************************
	*********************************************************************************************
*/

CREATE TABLE Clientes(
	Username char(15) NOT NULL,
	[Password] varbinary(max) NOT NULL,
	Nombre varchar(50) NOT NULL,
	Panadero bit NOT NULL DEFAULT 0,
	CONSTRAINT PKClientes PRIMARY KEY (Username)
)

CREATE TABLE Complementos(
	ID int IDENTITY(1,1) NOT NULL,
	Nombre char(20) NOT NULL,
	Precio smallmoney NOT NULL,
	CONSTRAINT PKComplementos PRIMARY KEY (ID),
	CONSTRAINT CHK_Complementos_Precio CHECK (Precio > 0)
)

CREATE TABLE Panes(
	ID int IDENTITY(1,1) NOT NULL,
	Nombre char(20) NOT NULL,
	Crujenticidad int NULL,
	Integral bit NOT NULL DEFAULT 0,
	Precio smallmoney NOT NULL,
	CONSTRAINT PKPanes PRIMARY KEY (ID),
	CONSTRAINT CHK_Panes_Precio CHECK (Precio > 0),
	CONSTRAINT CHK_Panes_Crujenticidad CHECK (Crujenticidad BETWEEN 0 AND 5)
)

CREATE TABLE Ingredientes(
	ID int IDENTITY(1,1) NOT NULL,
	Nombre char(20) NOT NULL,
	Precio smallmoney NOT NULL,
	CONSTRAINT PKIngredientes PRIMARY KEY (ID),
	CONSTRAINT CHK_Ingredientes_Precio CHECK (Precio > 0)
)

CREATE TABLE Pedidos(
	ID int IDENTITY(1,1) NOT NULL,
	ClienteUsername char(15) NOT NULL,
	FechaCompra date NOT NULL,
	ImporteTotal smallmoney NOT NULL DEFAULT 0,
	CONSTRAINT PKPedidos PRIMARY KEY (ID),
	CONSTRAINT FKPedidosClientes FOREIGN KEY (ClienteUsername) REFERENCES Clientes(Username) ON DELETE NO ACTION ON UPDATE CASCADE
)

CREATE TABLE PedidosComplementos(
	IDPedido int NOT NULL,
	IDComplemento int NOT NULL,
	Cantidad int NOT NULL,
	CONSTRAINT FKPCPedidos FOREIGN KEY (IDPedido) REFERENCES Pedidos(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT FKPCComplementos FOREIGN KEY (IDComplemento) REFERENCES Complementos(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT PKPC PRIMARY KEY (IDPedido, IDComplemento),
	CONSTRAINT CHK_PC_Cantidad CHECK (Cantidad > 0)
)

CREATE TABLE PedidosPanes(
	IDPedido int NOT NULL,
	IDPan int NOT NULL,
	Cantidad int NOT NULL,
	CONSTRAINT FKPPPedidos FOREIGN KEY (IDPedido) REFERENCES Pedidos(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT FKPPPanes FOREIGN KEY (IDPan) REFERENCES Panes(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT PKPedidosPanes PRIMARY KEY (IDPedido, IDPan),
	CONSTRAINT CHK_PP_Cantidad CHECK (Cantidad > 0)
)

CREATE TABLE Bocatas(
	ID int IDENTITY(1,1) NOT NULL,
	IDPedido int NOT NULL,
	IDPan int NOT NULL,
	CONSTRAINT PKBocatas PRIMARY KEY (ID),
	CONSTRAINT FKBocatasPedidos FOREIGN KEY (IDPedido) REFERENCES Pedidos(ID),
	CONSTRAINT FKBocatasPanes FOREIGN KEY (IDPan) REFERENCES Panes(ID)
)

CREATE TABLE BocatasIngredientes(
	IDBocata int NOT NULL,
	IDIngrediente int NOT NULL,
	Cantidad int NOT NULL,
	CONSTRAINT FKBIBocatas FOREIGN KEY (IDBocata) REFERENCES Bocatas(ID),
	CONSTRAINT FKBIIngredientes FOREIGN KEY (IDIngrediente) REFERENCES Ingredientes(ID),
	CONSTRAINT PKBocatasIngredientes PRIMARY KEY (IDBocata, IDIngrediente),
	CONSTRAINT CHK_BI_Cantidad CHECK (Cantidad > 0)
)

/*
	*********************************************************************************************
	******************* T A B L A S  D E  G U A R D A D O  D E  B A J A S ***********************
	*********************************************************************************************
*/

CREATE TABLE PedidosBorrados(
	ID int,
	IDCliente int,
	FechaCompra date,
	ImporteTotal smallmoney,
	CONSTRAINT PKPedidosBorrados PRIMARY KEY (ID),
)

CREATE TABLE PedidosComplementosBorrados(
	IDPedido int,
	IDComplemento int,
	Cantidad int,
	CONSTRAINT FKPCPedidosBorrados FOREIGN KEY (IDPedido) REFERENCES PedidosBorrados(ID),
	CONSTRAINT PKPCBorrados PRIMARY KEY (IDPedido, IDComplemento)
)

CREATE TABLE PedidosPanesBorrados(
	IDPedido int,
	IDPan int,
	Cantidad int,
	CONSTRAINT FKPPPedidosBorrados FOREIGN KEY (IDPedido) REFERENCES PedidosBorrados(ID),
	CONSTRAINT PKPedidosPanesBorrados PRIMARY KEY (IDPedido, IDPan)
)

CREATE TABLE BocatasBorrados(
	ID int,
	IDPedido int,
	IDPan int,
	CONSTRAINT PKBocatasBorrados PRIMARY KEY (ID),
	CONSTRAINT FKBocatasPedidosBorrados FOREIGN KEY (IDPedido) REFERENCES PedidosBorrados(ID)
)

CREATE TABLE BocatasIngredientesBorrados(
	IDBocata int,
	IDIngrediente int,
	Cantidad int,
	CONSTRAINT FKBIBocatasBorrados FOREIGN KEY (IDBocata) REFERENCES BocatasBorrados(ID),
	CONSTRAINT PKBocatasIngredientesBorrados PRIMARY KEY (IDBocata, IDIngrediente)
)

/*
	*********************************************************************************************
	******************** F U N C I O N E S  Y  P R O C E D I M I E N T O S **********************
	*********************************************************************************************
*/

/*
	Función que devuelve el valor total de los complementos de un pedido
	Entradas: ID del pedido
	Salida: smallmoney con el total
*/
GO
CREATE FUNCTION ImporteTotalComplementos (@IDPedido int) RETURNS smallmoney
AS
	BEGIN
		RETURN (SELECT ISNULL(SUM(C.Precio * PC.Cantidad),0)
							FROM PedidosComplementos AS PC
								INNER JOIN Complementos AS C
									ON PC.IDComplemento = C.ID
								WHERE PC.IDPedido = @IDPedido )
	END
GO

/*
	Función que devuelve el valor total de los panes de un pedido
	Entradas: ID del pedido
	Salida: smallmoney con el total
*/
GO
CREATE FUNCTION ImporteTotalPanes (@IDPedido int) RETURNS smallmoney
AS
	BEGIN
		RETURN (SELECT ISNULL(SUM(P.Precio * PP.Cantidad),0)
							FROM PedidosPanes AS PP
								INNER JOIN Panes AS P
									ON PP.IDPan = P.ID
								WHERE PP.IDPedido = @IDPedido )
	END
GO

/*
	Función que devuelve el valor total de los bocatas de un pedido
	Entradas: ID del pedido
	Salida: smallmoney con el total
*/
GO
CREATE FUNCTION ImporteTotalBocatas (@IDPedido int) RETURNS smallmoney
AS
	BEGIN
		RETURN (SELECT ISNULL((MAX(P.Precio) + SUM(I.Precio * BI.Cantidad)),0)
							FROM Bocatas AS B
								INNER JOIN Panes AS P
									ON B.IDPan = P.ID
								INNER JOIN BocatasIngredientes AS BI
									ON B.ID = BI.IDBocata
								INNER JOIN Ingredientes AS I
									ON BI.IDIngrediente = I.ID
							WHERE B.IDPedido = @IDPedido )
	END
GO

/*
	Procedimiento almacenado que obtiene el importe total de un pedido y actualiza la tabla ImporteTotal de dicho pedido.
	Usa las funciones escalares creadas anteriormente
	Entradas: ID del pedido
	Salidas: Ninguna
*/
GO
CREATE PROCEDURE CargarImportesTotales (@IDPedido int) AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal = (SELECT	dbo.ImporteTotalBocatas(ID)+
											dbo.ImporteTotalComplementos(ID)+
											dbo.ImporteTotalPanes(ID) )
		COMMIT
	END
GO

/*
	*********************************************************************************************
	******************** F U N C I O N E S  Y  P R O C E D I M I E N T O S **********************
	*********************************************************************************************
*/

/*
	Función que valida si un usuario existe
	Devuelve un bit con valor 0 si no existe, o un 1 si existe.
	Entradas: ID del cliente
	Salida: Bit
*/
GO
CREATE FUNCTION ValidarUsernameCliente (@Username char) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit
		IF(EXISTS(SELECT Username FROM Clientes WHERE Username = @Username))
			BEGIN
				SET @ret = 1
			END
		ELSE
			BEGIN
				SET @ret = 0
			END

		RETURN @ret
	END
GO

/*
	Función que valida si una ID de un pedido existe.
	Devuelve un bit con valor 0 si no existe, o un 1 si existe.
	Entradas: ID del pedido
	Salida: Bit
*/
GO
CREATE FUNCTION ValidarIDPedido (@IDPedido int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit
		IF(EXISTS(SELECT ID FROM Pedidos WHERE ID = @IDPedido))
			BEGIN
				SET @ret = 1
			END
		ELSE
			BEGIN
				SET @ret = 0
			END

		RETURN @ret
	END
GO

/*
	Función que valida si una ID de un pan existe
	Devuelve un bit con valor 0 si no existe, o un 1 si existe
	Entradas: ID del pan
	Salida: Bit
*/
GO
CREATE FUNCTION ValidarIDPan (@IDPan int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit
		IF(EXISTS(SELECT ID FROM Panes WHERE ID = @IDPan))
			BEGIN
				SET @ret = 1
			END
		ELSE
			BEGIN
				SET @ret = 0
			END

		RETURN @ret
	END
GO

/*
	Función que valida si una ID de un complemento existe
	Devuelve un bit con valor 0 si no existe, o un 1 si existe
	Entradas: ID del complemento
	Salida: Bit
*/
GO
CREATE FUNCTION ValidarIDComplemento (@IDComp int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit
		IF(EXISTS(SELECT ID FROM Complementos WHERE ID = @IDComp))
			BEGIN
				SET @ret = 1
			END
		ELSE
			BEGIN
				SET @ret = 0
			END

		RETURN @ret
	END
GO

/*
	Función que valida si una ID de un ingrediente existe
	Devuelve un bit con valor 0 si no existe, o un 1 si existe
	Entradas: ID del ingrediente
	Salida: Bit
*/
GO
CREATE FUNCTION ValidarIDIngrediente (@IDIngr int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit
		IF(EXISTS(SELECT ID FROM Ingredientes WHERE ID = @IDIngr))
			BEGIN
				SET @ret = 1
			END
		ELSE
			BEGIN
				SET @ret = 0
			END

		RETURN @ret
	END
GO

/*
	Función que valida si en un pedido ya tiene el pan con la ID que se le pasa
	Devuelve un bit con valor 0 si no lo ha pedido, 1 si ya lo ha pedido
	Entradas: ID del pedido, ID del pan
	Salidas: Bit
*/
GO
CREATE FUNCTION ValidarPanPedido (@IDPedido int, @IDPan int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit

		IF(EXISTS (SELECT IDPan FROM PedidosPanes WHERE IDPedido = @IDPedido AND IDPan = @IDPan))
			SET @ret = 1
		ELSE
			SET @ret = 0

		RETURN @ret
	END
GO

/*
	Función que valida si en un pedido ya tiene el complemento con la ID que se le pasa
	Devuelve un bit con valor 0 si no lo ha pedido, 1 si ya lo ha pedido
	Entradas: ID del pedido, ID del complemento
	Salidas: Bit
*/
GO
CREATE FUNCTION ValidarCompPedido (@IDPedido int, @IDComp int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit

		IF(EXISTS (SELECT IDComplemento FROM PedidosComplementos WHERE IDPedido = @IDPedido AND IDComplemento = @IDComp))
			SET @ret = 1
		ELSE
			SET @ret = 0

		RETURN @ret
	END
GO

/*
	Función que valida si un bocata ya tiene un ingrediente agregado a él
	Devuelve un bit con valor 0 si no lo tiene agregado, 1 en caso contrario
	Entradas: ID del bocata, ID del ingrediente
	Salidas: Bit
*/
GO
CREATE FUNCTION ValidarIngrBocata (@IDBocata int, @IDIngr int) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit

		IF(EXISTS (SELECT IDIngrediente FROM BocatasIngredientes WHERE IDBocata = @IDBocata AND IDIngrediente = @IDIngr))
			SET @ret = 1
		ELSE
			SET @ret = 0

		RETURN @ret
	END
GO

/*
	Procedimiento usado para añadir panes de manera más cómoda
	Entradas: char Nombre, bit Integral, int Crujenticidad, smallmoney Precio
	Salidas: Ninguna
*/
GO
CREATE PROCEDURE InsertarPan (@Nombre char(20), @Integral bit, @Crujenticidad int, @Precio smallmoney)
AS
	BEGIN
		INSERT INTO Panes (Nombre, Integral, Crujenticidad, Precio) VALUES (@Nombre, @Integral, @Crujenticidad, @Precio)
	END
GO

/*
	Procedimiento usado para añadir complementos de manera más cómoda
	Entradas: char Nombre, smallmoney Precio
	Salidas: Ninguna
*/
GO
CREATE PROCEDURE InsertarComplemento (@Nombre char(20), @Precio smallmoney)
AS
	BEGIN
		INSERT INTO Complementos (Nombre, Precio) VALUES (@Nombre, @Precio)
	END
GO

/*
	Procedimiento usado para añadir ingredientes de manera más cómoda
	Entradas: char Nombre, smallmoney Precio
	Salidas: Ninguna
*/
GO
CREATE PROCEDURE InsertarIngrediente (@Nombre char(20), @Precio smallmoney)
AS
	BEGIN
		INSERT INTO Ingredientes (Nombre, Precio) VALUES (@Nombre, @Precio)
	END
GO

/*
	Procedimiento usado para añadir clientes de manera más cómoda
*/
GO
CREATE PROCEDURE InsertarCliente (@Nombre char(20), @Apellidos char(30), @FechaNac date, @Ciudad char(20), @Direccion char(40), @Telefono char(9))
AS
	BEGIN
		INSERT INTO Clientes (Nombre, Apellidos, FechaNac, Ciudad, Direccion, Telefono) VALUES (@Nombre, @Apellidos, @FechaNac, @Ciudad, @Direccion, @Telefono)
	END
GO

/*
	Procedimiento almacenado que introduce un nuevo pedido en la base de datos, y devuelve la ID de dicho pedido
	Entradas: ID del cliente que ha hecho el pedido
	Salidas: ID del nuevo pedido
*/
GO
CREATE PROCEDURE CrearNuevoPedido (@Username char, @IDPedido int OUTPUT)
AS
	BEGIN
		INSERT INTO Pedidos (ClienteUsername, FechaCompra) VALUES (@Username, CAST(CURRENT_TIMESTAMP AS date))
		SET @IDPedido = @@IDENTITY
	END
GO

/*
	Procedimiento almacenado que introduce un nuevo bocata en la base de datos, y devuelve la ID de dicho bocata
	Entradas: ID del pedido, ID del pan
	Salidas: ID del nuevo bocata
*/
GO
CREATE PROCEDURE CrearNuevoBocata (@IDPedido int, @IDPan int, @IDBocata int OUTPUT)
AS
	BEGIN
		INSERT INTO Bocatas (IDPedido, IDPan) VALUES (@IDPedido, @IDPan)
		SET @IDBocata = @@IDENTITY
	END
GO

/*
	Función que devuelve 0 si la contraseña introducida es incorrecta, 1 si sí es correcta
	Entradas: Username, Contraseña
	Salidas: Bit
*/
GO
CREATE FUNCTION ValidarContrasena (@Username char(15), @Contrasena char) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit = PWDCOMPARE(@contrasena, (SELECT [Password] FROM Clientes WHERE Username = @Username))
		RETURN @ret
	END
GO


/*
	*********************************************************************************************
	************************************** T R I G G E R S **************************************
	*********************************************************************************************
*/

-- Trigger que actualiza la columna ImporteTotal de la tabla Pedidos después de que se actualice PedidosComplementos
GO
CREATE TRIGGER ImporteTotalAfterIUComp ON PedidosComplementos AFTER INSERT,UPDATE 
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal +=	(I.Cantidad * C.Precio)
			
				FROM inserted AS I
					INNER JOIN Pedidos AS P
						ON I.IDPedido = P.ID
					INNER JOIN Complementos AS C
						ON I.IDComplemento = C.ID
				
				WHERE P.ID IN (SELECT IDPedido FROM inserted)
		COMMIT
	END
GO

-- Trigger que actualiza la columna ImporteTotal de la tabla Pedidos después de que se actualice PedidosPanes
GO
CREATE TRIGGER ImporteTotalAfterIUPanes ON PedidosPanes AFTER INSERT,UPDATE 
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal += (I.Cantidad * Pa.Precio)
				
				FROM inserted AS I
					INNER JOIN Pedidos AS P
						ON I.IDPedido = P.ID
					INNER JOIN Panes AS Pa
						ON I.IDPan = Pa.ID

				WHERE P.ID IN (SELECT IDPedido FROM inserted)
		COMMIT
	END 
GO

-- Trigger que actualiza la columna ImporteTotal de la tabla Pedidos después de que se actualice Bocatas
GO
CREATE TRIGGER ImporteTotalAfterIUBocatas ON Bocatas AFTER INSERT,UPDATE 
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal += Pa.Precio
				
				FROM inserted AS I
					INNER JOIN Pedidos AS P
						ON I.IDPedido = P.ID
					INNER JOIN Panes AS Pa
						ON I.IDPan = Pa.ID
				
				WHERE P.ID IN (SELECT IDPedido FROM inserted)
		COMMIT
	END 
GO

-- Trigger que actualiza la columna ImporteTotal de la tabla Pedidos después de que se actualice BocatasIngredientes
GO
CREATE TRIGGER ImporteTotalAfterIUBocIngr ON BocatasIngredientes AFTER INSERT,UPDATE 
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal += (I.Cantidad * Ing.Precio)

				FROM Pedidos AS P
					INNER JOIN Bocatas AS B
						ON P.ID = B.IDPedido
					INNER JOIN inserted AS I
						ON B.ID = I.IDBocata
					INNER JOIN Ingredientes AS Ing
						ON I.IDIngrediente = Ing.ID
				
				WHERE B.ID IN (SELECT IDBocata FROM inserted)
		COMMIT
	END 
GO

-- Trigger que actualiza la tabla ImporteTotal restándole el precio después de borrar un Pan de un pedido
GO
CREATE TRIGGER ImporteTotalAfterDPanes ON PedidosPanes AFTER DELETE
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal -= (D.Cantidad * Pa.Precio)
				
				FROM deleted AS D
					INNER JOIN Pedidos AS P
						ON D.IDPedido = P.ID
					INNER JOIN Panes AS Pa
						ON D.IDPan = Pa.ID

				WHERE P.ID IN (SELECT IDPedido FROM deleted)
		COMMIT
	END
GO

-- Trigger que actualiza la tabla ImporteTotal restándole el precio después de borrar un Complemento de un pedido
GO
CREATE TRIGGER ImporteTotalAfterDComp ON PedidosComplementos AFTER DELETE
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal -=	(D.Cantidad * C.Precio)
			
				FROM deleted AS D
					INNER JOIN Pedidos AS P
						ON D.IDPedido = P.ID
					INNER JOIN Complementos AS C
						ON D.IDComplemento = C.ID
				
				WHERE P.ID IN (SELECT IDPedido FROM deleted)
		COMMIT
	END
GO

-- Trigger que actualiza la tabla ImporteTotal restándole el precio después de borrar un Ingrediente de un Bocata
GO
CREATE TRIGGER ImporteTotalAfterDBocIngr ON BocatasIngredientes AFTER DELETE
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal -= (D.Cantidad * Ing.Precio)

				FROM Pedidos AS P
					INNER JOIN Bocatas AS B
						ON P.ID = B.IDPedido
					INNER JOIN deleted AS D
						ON B.ID = D.IDBocata
					INNER JOIN Ingredientes AS Ing
						ON D.IDIngrediente = Ing.ID
				
				WHERE B.ID IN (SELECT IDBocata FROM deleted)
		COMMIT
	END 
GO

-- Trigger que actualiza la tabla ImporteTotal restándole el precio después de borrar un Bocata
GO
CREATE TRIGGER ImporteTotalAfterDBocatas ON Bocatas INSTEAD OF DELETE
AS
	BEGIN
		BEGIN TRANSACTION
			UPDATE Pedidos
				SET ImporteTotal -= Pa.Precio
				
				FROM deleted AS D
					INNER JOIN Pedidos AS P
						ON D.IDPedido = P.ID
					INNER JOIN Panes AS Pa
						ON D.IDPan = Pa.ID
				
				WHERE P.ID IN (SELECT IDPedido FROM deleted)

			UPDATE Pedidos
				SET ImporteTotal -= (SELECT ISNULL(SUM(BI.Cantidad * I.Precio),0)
										FROM deleted AS D
											INNER JOIN BocatasIngredientes AS BI
												ON D.ID = BI.IDBocata
											INNER JOIN Ingredientes AS I
												ON BI.IDIngrediente = I.ID 
										WHERE BI.IDBocata = D.ID )

				WHERE ID IN (SELECT IDPedido FROM deleted)

			-- Necesitamos deshabilitar temporalmente el trigger que se ejecuta sobre cada DELETE en la tabla BocatasIngredientes
			ALTER TABLE BocatasIngredientes DISABLE TRIGGER ImporteTotalAfterDBocIngr

			-- Borramos todos los ingredientes pertenecientes a ese bocata, y después el bocata
			DELETE FROM BocatasIngredientes WHERE IDBocata IN (SELECT ID FROM deleted)
			DELETE FROM Bocatas WHERE ID IN (SELECT ID FROM deleted)

			-- Volvemos a habilitar el trigger
			ALTER TABLE BocatasIngredientes ENABLE TRIGGER ImporteTotalAfterDBocIngr
		COMMIT
	END 
GO

/*
	Trigger que hace que al borrar un pedido, en vez de borrarlo directamente, primero guarda toda la información
	en las tablas de Borrados, porque nos gusta la información y no queremos perder nada :D
*/
GO
CREATE TRIGGER GuardarPedidoAfterD ON Pedidos INSTEAD OF DELETE
AS
	BEGIN
		DECLARE @IDPedidoBorrado int = (SELECT ID FROM deleted)

		INSERT INTO PedidosBorrados
			SELECT * FROM deleted

		INSERT INTO PedidosPanesBorrados
			SELECT * FROM PedidosPanes WHERE IDPedido = @IDPedidoBorrado

		INSERT INTO PedidosComplementosBorrados
			SELECT * FROM PedidosComplementos WHERE IDPedido = @IDPedidoBorrado

		INSERT INTO BocatasBorrados
			SELECT * FROM Bocatas WHERE IDPedido = @IDPedidoBorrado

		INSERT INTO BocatasIngredientesBorrados (IDBocata, IDIngrediente, Cantidad)
			SELECT BI.IDBocata, BI.IDIngrediente, BI.Cantidad
				FROM Bocatas AS B
					INNER JOIN BocatasIngredientes AS BI
						ON B.ID = BI.IDBocata
				WHERE B.IDPedido = @IDPedidoBorrado
		
		--Desactivamos temporalmente los triggers
		ALTER TABLE PedidosPanes DISABLE TRIGGER ImporteTotalAfterDPanes
		ALTER TABLE PedidosComplementos DISABLE TRIGGER ImporteTotalAfterDComp
		ALTER TABLE BocatasIngredientes DISABLE TRIGGER ImporteTotalAfterDBocIngr
		ALTER TABLE Bocatas DISABLE TRIGGER ImporteTotalAfterDBocatas

		--Borramos las filas deseadas
		DELETE FROM PedidosPanes WHERE IDPedido = @IDPedidoBorrado
		DELETE FROM PedidosComplementos WHERE IDPedido = @IDPedidoBorrado
		DELETE FROM BocatasIngredientes WHERE IDBocata IN (SELECT B.ID FROM deleted AS D INNER JOIN Bocatas AS B ON D.ID = B.IDPedido)
		DELETE FROM Bocatas WHERE IDPedido = @IDPedidoBorrado
		DELETE FROM Pedidos WHERE ID = @IDPedidoBorrado

		--Volvemos a activar los triggers
		ALTER TABLE PedidosPanes ENABLE TRIGGER ImporteTotalAfterDPanes
		ALTER TABLE PedidosComplementos ENABLE TRIGGER ImporteTotalAfterDComp
		ALTER TABLE BocatasIngredientes ENABLE TRIGGER ImporteTotalAfterDBocIngr
		ALTER TABLE Bocatas ENABLE TRIGGER ImporteTotalAfterDBocatas
	END
GO

/*
	*********************************************************************************************
	************************************** P O B L A R ******************************************
	*********************************************************************************************
*/
--Vamos a generar el hash para la contraseña 1234 para todos los clientes
DECLARE @pswd NVARCHAR(MAX) = '1234'; 
DECLARE @salt VARBINARY(4) = CRYPT_GEN_RANDOM(4);
DECLARE @hash VARBINARY(MAX) = 0x0200 + @salt + HASHBYTES('SHA2_512', CAST(@pswd AS VARBINARY(MAX)) + @salt);

INSERT INTO Clientes (Username, [Password], Nombre) VALUES
('yeray1', @hash, 'Yeray Campanario'),
('daniel1', @hash, 'Daniel Gordillo'),
('nacho1', @hash, 'Ignacio Van Loy'),
('tomas1', @hash,'Tomás Núñez'),
('raquel1', @hash, 'Raquel González'),
('david1', @hash, 'David Galván'),
('oscar1', @hash, 'Oscar Funes')

INSERT INTO Pedidos (ClienteUsername, FechaCompra) VALUES
('yeray1','9-3-2017'),('yeray1','25-5-2017'),('daniel1','3-8-2017'),('daniel1','9-8-2017'),('tomas1','9-9-2017'),('oscar1','21-10-2017'),('david1','30-11-2017'),
('yeray1','2-1-2018'),('yeray1','4-1-2018'),('yeray1','4-2-2018'),('raquel1','10-2-2018'),('nacho1','16-2-2018'),('david1','28-2-2018'),('raquel1','16-3-2018'),
('yeray1','13-4-2018'),('yeray1','5-5-2018')

INSERT INTO Panes (Nombre, Crujenticidad, Integral, Precio) VALUES
('Andaluza',2,0,0.2),('Baguette',4,0,0.5),('Bollo',5,0,0.25),('Pan de molde',0,0,1),('Pan de molde',0,1,1.2),
('Chapata',3,0,0.4),('Chapata',3,1,0.5),('Flauta',2,0,0.5),('Artesano',5,0,1),('Artesano',5,1,1.2)

INSERT INTO Complementos (Nombre, Precio) VALUES
('Doritos',1.35),('Patatas fritas',1),('Agua',0.5),('Coca-Cola',0.8),('Coca-Cola Zero',0.8),('Nachos',1.2),
('Palmera chocolate',1.2),('Polvorón',0.5),('Condón de fresa',1),('Nestea',1),('Bollicao',1),('Conchitas',1),
('Bits',0.35),('El Popper',500)

INSERT INTO Ingredientes (Nombre, Precio) VALUES
('Queso en loncha',0.3),('Bacon',0.5),('Lechuga',0.1),('Chorizo',0.4),('Pavo',0.3),('Pollo empanado',0.8),
('Tortilla',0.6),('Mayonesa',0.2),('Ketchup',0.2),('Ali-oli',0.2),('Jamón serrano',0.5),('Atún',0.4),('Mortadela',0.3),
('Jamón York',0.4),('Nacho',1),('Yeray',2),('Caña de lomo',0.4)

INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (1,2,3)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (1,3,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (2,6,3)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (3,6,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (3,5,2)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (3,7,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (5,2,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (6,2,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (9,9,2)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (9,8,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (10,10,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (10,9,9)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (11,1,6)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (11,3,4)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (12,5,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (14,5,1)
INSERT INTO PedidosPanes (IDPedido, IDPan, Cantidad) VALUES (16,9,2)

INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (1,6,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (3,8,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (3,9,10)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (4,12,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (5,3,2)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (7,11,2)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (7,7,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (7,10,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (8,5,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (11,1,1)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (13,1,3)
INSERT INTO PedidosComplementos (IDPedido, IDComplemento, Cantidad) VALUES (14,5,2)

INSERT INTO Bocatas (IDPedido, IDPan) VALUES (1,1)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (6,2)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (6,3)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (8,7)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (8,9)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (10,2)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (14,2)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (15,2)
INSERT INTO Bocatas (IDPedido, IDPan) VALUES (16,8)

INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (1,1,2)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (1,2,2)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (2,4,2)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (2,13,1)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (3,5,2)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (4,11,3)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (5,6,1)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (5,8,1)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (5,9,1)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (6,12,2)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (7,14,1)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (8,16,3)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (9,6,1)
INSERT INTO BocatasIngredientes (IDBocata, IDIngrediente, Cantidad) VALUES (9,10,1)

/*
	*********************************************************************************************
	************************************ U S U A R I O S ****************************************
	*********************************************************************************************


CREATE LOGIN panadero WITH PASSWORD = 'elmejorpanadero', DEFAULT_DATABASE = PennyPan
CREATE LOGIN invitado WITH PASSWORD = 'guest', DEFAULT_DATABASE = PennyPan */

--CREATE USER panadero FOR LOGIN panadero; GRANT SELECT, INSERT, DELETE, EXECUTE, ALTER TO panadero
--CREATE USER invitado FOR LOGIN invitado; GRANT SELECT, EXECUTE TO invitado

