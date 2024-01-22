-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 26, 2023 at 07:45 PM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oop2`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
CREATE TABLE IF NOT EXISTS `cars` (
  `CarID` int NOT NULL,
  `Brand` varchar(30) DEFAULT NULL,
  `Model` varchar(30) DEFAULT NULL,
  `Year` int DEFAULT NULL,
  `Color` varchar(30) DEFAULT NULL,
  `RentalRate` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`CarID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`CarID`, `Brand`, `Model`, `Year`, `Color`, `RentalRate`) VALUES
(1113, 'Nissan', 'March', 2009, 'Light Blue', '100.00'),
(1112, 'Toyota ', 'Corolla', 2010, 'Black', '150.00'),
(1111, 'Toyota ', 'Prius', 2001, 'Blue', '200.00');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `CustomerID` int NOT NULL,
  `FirstName` varchar(20) DEFAULT NULL,
  `LastName` varchar(20) DEFAULT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `Phone` int DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CustomerID`, `FirstName`, `LastName`, `Email`, `Phone`, `Address`) VALUES
(2223, 'Jesus', 'Christ', 'realjesus@gmail.com', 3334313, '21st Holy Street, Trinity'),
(2222, 'Daniel', 'Jones', 'danjones@gmail.com', 3214161, '21st Real Street, St.Augustine'),
(2224, 'Evan', 'Smith', 'evsmith@yahoo.com', 2251343, '45th Avenue, Tobago');

-- --------------------------------------------------------

--
-- Table structure for table `rentals`
--

DROP TABLE IF EXISTS `rentals`;
CREATE TABLE IF NOT EXISTS `rentals` (
  `RentalID` int NOT NULL,
  `CustomerID` int DEFAULT NULL,
  `CarID` int DEFAULT NULL,
  `RentalDate` date DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  `TotalCost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`RentalID`),
  KEY `CustomerID` (`CustomerID`),
  KEY `CarID` (`CarID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rentals`
--

INSERT INTO `rentals` (`RentalID`, `CustomerID`, `CarID`, `RentalDate`, `ReturnDate`, `TotalCost`) VALUES
(3331, 2222, 1111, '2023-12-19', '2023-12-26', '1400.00'),
(3332, 2223, 1112, '2023-12-19', '2023-12-26', '1050.00'),
(3333, 2224, 1113, '2023-12-19', '2023-12-26', '700.00');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
