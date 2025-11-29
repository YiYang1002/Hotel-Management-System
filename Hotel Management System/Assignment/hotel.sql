-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 03, 2025 at 03:24 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `Employee_ID` int(11) UNSIGNED NOT NULL,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Salary` decimal(10,2) UNSIGNED NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`Employee_ID`, `Name`, `Role`, `Salary`) VALUES
(1002, 'Yiyang', 'Manager', 10000.00),
(1103, 'Joey', 'Housekeeping Staff', 5000.00),
(3318, 'Zhiwei', 'Front Desk Staff', 4000.00),
(8166, 'Zhanxiang', 'Housekeeping Staff', 5000.00);

-- --------------------------------------------------------

--
-- Table structure for table `guest`
--

CREATE TABLE `guest` (
  `Guest_ID` int(4) UNSIGNED NOT NULL,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Age` varchar(2) NOT NULL,
  `Contact_Number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Check_In_Date` date NOT NULL,
  `Check_Out_Date` date NOT NULL,
  `Check_In_Time` time(5) NOT NULL DEFAULT '00:00:00.00000',
  `Check_Out_Time` time(5) NOT NULL DEFAULT '00:00:00.00000'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `guest`
--

INSERT INTO `guest` (`Guest_ID`, `Name`, `Age`, `Contact_Number`, `Check_In_Date`, `Check_Out_Date`, `Check_In_Time`, `Check_Out_Time`) VALUES
(1000, 'Jennie', '25', '1233456789', '2025-05-11', '2025-05-15', '17:00:00.00000', '08:00:00.00000'),
(1001, 'John', '40', '1234456789', '2025-05-12', '2025-05-13', '21:00:00.00000', '08:00:00.00000'),
(1002, 'Rose', '25', '1234556789', '2025-05-11', '2025-05-13', '18:00:00.00000', '09:00:00.00000'),
(1003, 'Horcus', '39', '1234566789', '2025-05-15', '2025-05-25', '07:00:00.00000', '13:00:00.00000');

-- --------------------------------------------------------

--
-- Table structure for table `payment_transactions`
--

CREATE TABLE `payment_transactions` (
  `Payment_ID` int(10) UNSIGNED NOT NULL,
  `Guest_ID` int(11) UNSIGNED NOT NULL COMMENT 'Links to guests.Guest_ID',
  `Room_ID` int(11) UNSIGNED NOT NULL COMMENT 'Link to room.Room_ID',
  `Amount_Paid` decimal(10,0) NOT NULL,
  `Payment_Method` varchar(50) NOT NULL,
  `Transaction_Status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment_transactions`
--

INSERT INTO `payment_transactions` (`Payment_ID`, `Guest_ID`, `Room_ID`, `Amount_Paid`, `Payment_Method`, `Transaction_Status`) VALUES
(1433, 1002, 202, 508, 'Touch n\' Go', 'Paid'),
(2739, 1001, 103, 138, 'Cash on Arrival', 'Pending Payment'),
(4489, 1003, 304, 3932, 'Credit or Debit Card', 'Paid'),
(6814, 1000, 201, 1016, 'Credit or Debit Card', 'Paid');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `Room_ID` int(3) UNSIGNED NOT NULL,
  `Room_Type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Price_Per_Night` double(10,2) UNSIGNED NOT NULL DEFAULT 0.00,
  `Available_Status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Cleaned_Status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`Room_ID`, `Room_Type`, `Price_Per_Night`, `Available_Status`, `Cleaned_Status`) VALUES
(101, 'Single', 119.00, 'Available', 'Cleaned'),
(102, 'Single', 119.00, 'Available', 'Cleaned'),
(103, 'Single', 119.00, 'Unavailable', 'Booked'),
(104, 'Single', 119.00, 'Available', 'Cleaned'),
(201, 'Double', 219.00, 'Unavailable', 'Booked'),
(202, 'Double', 219.00, 'Unavailable', 'Booked'),
(203, 'Double', 219.00, 'Available', 'Cleaned'),
(204, 'Double', 219.00, 'Available', 'Cleaned'),
(301, 'Suite', 339.00, 'Available', 'Cleaned'),
(302, 'Suite', 339.00, 'Available', 'Cleaned'),
(303, 'Suite', 339.00, 'Available', 'Cleaned'),
(304, 'Suite', 339.00, 'Unavailable', 'Booked');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`Employee_ID`);

--
-- Indexes for table `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`Guest_ID`);

--
-- Indexes for table `payment_transactions`
--
ALTER TABLE `payment_transactions`
  ADD PRIMARY KEY (`Payment_ID`),
  ADD UNIQUE KEY `Payment ID` (`Payment_ID`),
  ADD UNIQUE KEY `Guest_ID` (`Guest_ID`),
  ADD UNIQUE KEY `Room_ID` (`Room_ID`) USING BTREE,
  ADD KEY `Payment Method` (`Payment_Method`),
  ADD KEY `Transaction Status` (`Transaction_Status`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`Room_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `payment_transactions`
--
ALTER TABLE `payment_transactions`
  ADD CONSTRAINT `fk_room_id` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`),
  ADD CONSTRAINT `payment_transactions_ibfk_1` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
