DROP TABLE IF EXISTS `Ncc_Clientes`;
CREATE TABLE `Ncc_Clientes`  (
  `Client_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Client_Nombre` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_Calle` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_Colonia` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_Ciudad` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_Cp` int(11) NULL DEFAULT NULL,
  `Client_Estado` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_Telefono1` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_Telefono2` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_RFC` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_FechaAdicion` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `Client_Saldo` double(11, 2) NULL DEFAULT NULL,
  `Client_SaldoVencido` double(11, 2) NULL DEFAULT NULL,
  `Client_Tipo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Client_IdMPago` int(11) NULL DEFAULT NULL,
  `Client_img` longblob NULL,
  `Client_status` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`Client_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Departamentos`;
CREATE TABLE `Ncc_Departamentos`  (
  `Depto_id` int(11) NOT NULL AUTO_INCREMENT,
  `Depto_descripcion` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Depto_fechaAdicion` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `Depto_Status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`Depto_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_GroupPermisos`;
CREATE TABLE `Ncc_GroupPermisos`  (
  `GP_Id` int(11) NOT NULL AUTO_INCREMENT,
  `GP_IdPermiso` int(11) NULL DEFAULT NULL,
  `GP_IdGroup` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`GP_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Grupos`;
CREATE TABLE `Ncc_Grupos`  (
  `Group_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Group_Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Group_FechaAlta` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `Group_UserAlta` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`Group_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Inventario`;
CREATE TABLE `Ncc_Inventario`  (
  `Inv_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Inv_IdProducto` int(10) NULL DEFAULT NULL,
  `Inv_Precio1` float(11, 2) NULL DEFAULT NULL,
  `Inv_Precio2` float(11, 2) NULL DEFAULT NULL,
  `Inv_Precio3` float(11, 2) NULL DEFAULT NULL,
  `Inv_Precio4` float(11, 2) NULL DEFAULT NULL,
  `Inv_Precio5` float(11, 2) NULL DEFAULT NULL,
  `Inv_Promocion` double(11, 2) NULL DEFAULT NULL,
  `Inv_PromoFechaInicio` date NULL DEFAULT NULL,
  `Inv_PromoFechaTermino` date NULL DEFAULT NULL,
  `Inv_Existencia` int(11) NULL DEFAULT NULL,
  `Inv_Apartado` int(11) NULL DEFAULT NULL,
  `Inv_StockMinimo` int(11) NULL DEFAULT NULL,
  `Inv_fechaAdicion` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `Inv_CostoCompra` double(15, 2) NULL DEFAULT NULL,
  `Inv_PrecioMostrador` double(15, 2) NULL DEFAULT NULL,
  `Inv_PromoCant` int(11) NULL DEFAULT NULL,
  `Inv_IDSucursal` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`Inv_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_MetodosPago`;
CREATE TABLE `Ncc_MetodosPago`  (
  `MPag_IdMPagos` int(11) NOT NULL AUTO_INCREMENT,
  `MPag_Codigo` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MPag_Descripcion` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MPag_Tipo` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`MPag_IdMPagos`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_PermisoUsuario`;
CREATE TABLE `Ncc_PermisoUsuario`  (
  `UP_Id` int(11) NOT NULL AUTO_INCREMENT,
  `UP_IdUser` int(11) NULL DEFAULT NULL,
  `UP_Id_Permiso` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`UP_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Permisos`;
CREATE TABLE `Ncc_Permisos`  (
  `Per_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Per_Serie` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Per_Descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`Per_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Productos`;
CREATE TABLE `Ncc_Productos`  (
  `Prod_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Prod_CodInterno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '000-000-000',
  `Prod_CodBarras` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '000000000000',
  `Prod_Descripcion` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Prod_idDepto` int(11) NULL DEFAULT NULL,
  `Prod_idUnidad` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Prod_Status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`Prod_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Sucursales`;
CREATE TABLE `Ncc_Sucursales`  (
  `Suc_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Suc_Nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Suc_Direccion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Suc_Eslogan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Suc_telefono` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Suc_RFC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Suc_Id_MetPago` int(11) NULL DEFAULT NULL,
  `Suc_FechaAlta` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `Suc_Db` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'NCC',
  PRIMARY KEY (`Suc_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_Unidades`;
CREATE TABLE `Ncc_Unidades`  (
  `Unidad_ClaveUnidad` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Unidad_Descripcion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Unidad_Status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`Unidad_ClaveUnidad`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_User`;
CREATE TABLE `Ncc_User`  (
  `User_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `User_Password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '123',
  `User_FechaAlta` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `User_Vigencia` datetime(0) NULL DEFAULT NULL,
  `User_ConexionesPermitidas` int(11) NULL DEFAULT NULL,
  `User_UltimaEntrada` datetime(0) NULL DEFAULT NULL,
  `User_VerlListaSuc` tinyint(11) NULL DEFAULT 0,
  `User_IdSuc` int(11) NULL DEFAULT NULL,
  `User_Usuario` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `User_CambioPassword` tinyint(11) NULL DEFAULT 1,
  `User_BdLigada` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'NCC',
  PRIMARY KEY (`User_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `Ncc_UsuarioGrupo`;
CREATE TABLE `Ncc_UsuarioGrupo`  (
  `GU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GU_IdUser` int(11) NULL DEFAULT NULL,
  `GU_IdGroup` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`GU_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
