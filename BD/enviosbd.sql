-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-07-2019 a las 00:07:07
-- Versión del servidor: 10.3.16-MariaDB
-- Versión de PHP: 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `enviosbd`
--
CREATE DATABASE IF NOT EXISTS `enviosbd` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `enviosbd`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudades`
--

CREATE TABLE `ciudades` (
  `id_ciudad` int(11) NOT NULL,
  `nom_ciudad` varchar(35) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `ciudades`
--

INSERT INTO `ciudades` (`id_ciudad`, `nom_ciudad`) VALUES
(1, 'Bogotá'),
(2, 'Medellín'),
(3, 'Cali'),
(4, 'Barranquilla'),
(5, 'Cartagena de Indias'),
(6, 'Soledad'),
(7, 'Soacha'),
(8, 'Ibagué'),
(9, 'Bucaramanga'),
(10, 'Villavicencio'),
(11, 'Santa Marta'),
(12, 'Bello'),
(13, 'Valledupar'),
(14, 'Pereira'),
(15, 'Buenaventura'),
(16, 'Pasto'),
(17, 'Manizales'),
(18, 'Montería'),
(19, 'Neiva');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `identificacion` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`identificacion`, `nombre`, `telefono`) VALUES
('123', 'Pedro perez', '30087812'),
('321', 'Maria', '123123'),
('456', 'Jose rodolfo', '123213123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `envios`
--

CREATE TABLE `envios` (
  `nro_envio` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `envio` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `id_cliente` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `nombre_cliente` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `cod_mercancia` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `des_mercancia` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `kilos` double NOT NULL,
  `origen` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `destino` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `valor_envio` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mercancia`
--

CREATE TABLE `mercancia` (
  `codigo` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `kilos` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `mercancia`
--

INSERT INTO `mercancia` (`codigo`, `descripcion`, `kilos`) VALUES
('123', 'papa', 12),
('321', 'carne', 10);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ciudades`
--
ALTER TABLE `ciudades`
  ADD PRIMARY KEY (`id_ciudad`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`identificacion`);

--
-- Indices de la tabla `envios`
--
ALTER TABLE `envios`
  ADD PRIMARY KEY (`nro_envio`);

--
-- Indices de la tabla `mercancia`
--
ALTER TABLE `mercancia`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ciudades`
--
ALTER TABLE `ciudades`
  MODIFY `id_ciudad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
