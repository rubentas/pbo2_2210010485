-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 15, 2024 at 03:46 AM
-- Server version: 8.0.30
-- PHP Version: 8.3.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2210010485_pbo2`
--

-- --------------------------------------------------------

--
-- Table structure for table `t_desa`
--

CREATE TABLE `t_desa` (
  `id_desa` int NOT NULL,
  `nama_desa` varchar(100) NOT NULL,
  `id_kecamatan` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `t_desa`
--

INSERT INTO `t_desa` (`id_desa`, `nama_desa`, `id_kecamatan`) VALUES
(12, 'bjb', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `t_hutanrakyat`
--

CREATE TABLE `t_hutanrakyat` (
  `id_hutanrakyat` int NOT NULL,
  `id_desa` int DEFAULT NULL,
  `luas` varchar(100) DEFAULT NULL,
  `longitude` text,
  `latitude` text,
  `id_klasifikasi` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `t_hutanrakyat`
--

INSERT INTO `t_hutanrakyat` (`id_hutanrakyat`, `id_desa`, `luas`, `longitude`, `latitude`, `id_klasifikasi`) VALUES
(8, NULL, '80', '80', '80', NULL),
(10, NULL, '10', '10', '10', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `t_kecamatan`
--

CREATE TABLE `t_kecamatan` (
  `id_kecamatan` int NOT NULL,
  `nama_kecamatan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `t_kecamatan`
--

INSERT INTO `t_kecamatan` (`id_kecamatan`, `nama_kecamatan`) VALUES
(1, 'bjb'),
(4, 'banjarmasin'),
(12, 'Banjar');

-- --------------------------------------------------------

--
-- Table structure for table `t_klasifikasi`
--

CREATE TABLE `t_klasifikasi` (
  `id_klasifikasi` int NOT NULL,
  `jenis_potensi` varchar(50) NOT NULL,
  `marker` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `t_klasifikasi`
--

INSERT INTO `t_klasifikasi` (`id_klasifikasi`, `jenis_potensi`, `marker`) VALUES
(2, 'www', 1),
(4, 'bbb', 4);

-- --------------------------------------------------------

--
-- Table structure for table `t_potensi`
--

CREATE TABLE `t_potensi` (
  `id_potensi` int NOT NULL,
  `id_desa` int DEFAULT NULL,
  `jenis_potensi` varchar(50) DEFAULT NULL,
  `luas_potensi` int DEFAULT NULL,
  `potensi_produksi` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `t_potensi`
--

INSERT INTO `t_potensi` (`id_potensi`, `id_desa`, `jenis_potensi`, `luas_potensi`, `potensi_produksi`) VALUES
(7, NULL, 'bbb', 10, 'bbb');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_desa`
--
ALTER TABLE `t_desa`
  ADD PRIMARY KEY (`id_desa`),
  ADD KEY `id_kecamatan` (`id_kecamatan`);

--
-- Indexes for table `t_hutanrakyat`
--
ALTER TABLE `t_hutanrakyat`
  ADD PRIMARY KEY (`id_hutanrakyat`),
  ADD KEY `id_desa` (`id_desa`),
  ADD KEY `id_klasifikasi` (`id_klasifikasi`);

--
-- Indexes for table `t_kecamatan`
--
ALTER TABLE `t_kecamatan`
  ADD PRIMARY KEY (`id_kecamatan`);

--
-- Indexes for table `t_klasifikasi`
--
ALTER TABLE `t_klasifikasi`
  ADD PRIMARY KEY (`id_klasifikasi`);

--
-- Indexes for table `t_potensi`
--
ALTER TABLE `t_potensi`
  ADD PRIMARY KEY (`id_potensi`),
  ADD KEY `id_desa` (`id_desa`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `t_desa`
--
ALTER TABLE `t_desa`
  MODIFY `id_desa` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `t_hutanrakyat`
--
ALTER TABLE `t_hutanrakyat`
  MODIFY `id_hutanrakyat` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `t_kecamatan`
--
ALTER TABLE `t_kecamatan`
  MODIFY `id_kecamatan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `t_klasifikasi`
--
ALTER TABLE `t_klasifikasi`
  MODIFY `id_klasifikasi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `t_potensi`
--
ALTER TABLE `t_potensi`
  MODIFY `id_potensi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `t_desa`
--
ALTER TABLE `t_desa`
  ADD CONSTRAINT `t_desa_ibfk_1` FOREIGN KEY (`id_kecamatan`) REFERENCES `t_kecamatan` (`id_kecamatan`) ON DELETE CASCADE;

--
-- Constraints for table `t_hutanrakyat`
--
ALTER TABLE `t_hutanrakyat`
  ADD CONSTRAINT `t_hutanrakyat_ibfk_1` FOREIGN KEY (`id_desa`) REFERENCES `t_desa` (`id_desa`) ON DELETE CASCADE,
  ADD CONSTRAINT `t_hutanrakyat_ibfk_2` FOREIGN KEY (`id_klasifikasi`) REFERENCES `t_klasifikasi` (`id_klasifikasi`) ON DELETE CASCADE;

--
-- Constraints for table `t_potensi`
--
ALTER TABLE `t_potensi`
  ADD CONSTRAINT `t_potensi_ibfk_1` FOREIGN KEY (`id_desa`) REFERENCES `t_desa` (`id_desa`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
