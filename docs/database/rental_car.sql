-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th9 09, 2025 lúc 07:55 AM
-- Phiên bản máy phục vụ: 8.0.41
-- Phiên bản PHP: 8.3.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `rental_car`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `user_id` bigint NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` enum('active','locked') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active',
  `created_at` datetime NOT NULL,
  `role_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`user_id`, `email`, `password`, `status`, `created_at`, `role_id`) VALUES
(1, 'honhan3062002@gmail.com', '1', 'active', '2025-09-07 01:49:26', 1),
(2, 'driver@gmail.com', '2', 'active', '2025-09-07 01:55:17', 2),
(3, 'a@gmail.com', '3', 'active', '2025-09-07 01:57:04', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `booking`
--

CREATE TABLE `booking` (
  `booking_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `car_id` bigint NOT NULL,
  `driver_id` bigint DEFAULT NULL,
  `discount_id` bigint DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `pickup_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dropoff_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `total_price` bigint DEFAULT NULL,
  `status` enum('pending','confirmed','cancelled','completed') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending',
  `trip_status` enum('assigned','in_progress','completed','cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'assigned',
  `created_at` datetime DEFAULT NULL,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `booking`
--

INSERT INTO `booking` (`booking_id`, `user_id`, `car_id`, `driver_id`, `discount_id`, `start_date`, `end_date`, `pickup_location`, `dropoff_location`, `total_price`, `status`, `trip_status`, `created_at`, `note`) VALUES
(1, 3, 1, 1, 2, '2025-10-01 07:30:00', '2025-09-08 07:30:00', '97 Man thiện, phường Hiệp Phú, tp. Thủ Đức, tp. Hồ Chí Minh', '97 Man thiện, phường Hiệp Phú, tp. Thủ Đức, tp. Hồ Chí Minh', NULL, 'pending', 'assigned', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `car`
--

CREATE TABLE `car` (
  `car_id` bigint NOT NULL,
  `plate_number` varchar(20) NOT NULL,
  `brand_id` bigint NOT NULL,
  `type_id` bigint NOT NULL,
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `year` int DEFAULT NULL,
  `status` enum('available','maintenance','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'available',
  `rating` varchar(20) DEFAULT NULL,
  `main_image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `car`
--

INSERT INTO `car` (`car_id`, `plate_number`, `brand_id`, `type_id`, `model_name`, `year`, `status`, `rating`, `main_image`) VALUES
(1, '37P-12345', 1, 1, 'Toyota Vios', 2022, 'available', NULL, NULL),
(2, '51H-56789', 2, 2, 'Hyundai SantaFe', NULL, 'available', NULL, NULL),
(3, '72A-99999', 3, 2, 'Kia Sorento', NULL, 'available', NULL, NULL),
(4, '60B-88888', 4, 3, 'Ford Transit', NULL, 'available', NULL, NULL),
(5, '43A-55555', 5, 4, 'Mercedes-Benz ', 2022, 'available', NULL, NULL),
(6, '30F-11111', 6, 1, 'VinFast Lux A2.0', 2021, 'available', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `car_brand`
--

CREATE TABLE `car_brand` (
  `brand_id` bigint NOT NULL,
  `brand_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `car_brand`
--

INSERT INTO `car_brand` (`brand_id`, `brand_name`) VALUES
(1, 'Toyota'),
(2, 'Hyundai'),
(3, 'Kia'),
(4, 'Ford'),
(5, 'Mercedes-Benz'),
(6, 'VinFast');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `car_image`
--

CREATE TABLE `car_image` (
  `image_id` bigint NOT NULL,
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `car_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `car_type`
--

CREATE TABLE `car_type` (
  `type_id` bigint NOT NULL,
  `type_name` varchar(255) NOT NULL,
  `seat_count` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `car_type`
--

INSERT INTO `car_type` (`type_id`, `type_name`, `seat_count`, `description`) VALUES
(1, 'Sedan', 4, 'Sedan 4 chỗ ngồi'),
(2, 'Sedan', 5, 'Sedan 5 chỗ ngồi'),
(3, 'SUV', 5, 'SUV 5 chỗ ngồi, gầm cao'),
(4, 'SUV', 7, 'SUV 7 chỗ ngồi, gầm cao, rộng rãi'),
(5, 'Minivan', 16, 'Xe khách 16 chỗ'),
(6, 'Limousine', 9, 'Xe Limousine cao cấp, 9 chỗ'),
(7, 'Bus', 29, 'Xe bus 29 chỗ '),
(8, 'Bus', 45, 'Xe bus lớn 45 chỗ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contract`
--

CREATE TABLE `contract` (
  `contract_id` bigint NOT NULL,
  `booking_id` bigint NOT NULL,
  `return_date` datetime DEFAULT NULL,
  `total_price` bigint DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` enum('pending','confirmed','active','completed','paid','cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending',
  `signed_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `contract`
--

INSERT INTO `contract` (`contract_id`, `booking_id`, `return_date`, `total_price`, `created_date`, `details`, `status`, `signed_by`) VALUES
(1, 1, NULL, NULL, '2025-09-07 03:26:33', NULL, 'pending', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discount`
--

CREATE TABLE `discount` (
  `discount_id` bigint NOT NULL,
  `discount_code` varchar(20) NOT NULL,
  `discount_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `discount_type` enum('percent','amount') NOT NULL,
  `discount_value` int NOT NULL,
  `status` enum('active','inactive','expired') NOT NULL,
  `min_order_value` int NOT NULL,
  `max_discount_value` int NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `discount`
--

INSERT INTO `discount` (`discount_id`, `discount_code`, `discount_name`, `discount_type`, `discount_value`, `status`, `min_order_value`, `max_discount_value`, `start_date`, `end_date`) VALUES
(1, 'SUMMER2025', 'Khuyến mãi mùa hè 2025', 'percent', 20, 'expired', 1000, 20000, '2025-06-01 00:00:00', '2025-08-31 00:00:00'),
(2, 'NEWCUSTOMER100', 'Giảm 100k cho khách hàng mới', 'amount', 100000, 'active', 0, 100000, '2025-01-01 00:00:00', '2028-12-31 00:00:00'),
(3, 'TET2026', 'Khuyến mãi Tết 2026', 'percent', 15, 'inactive', 1000000, 500000, '2025-12-25 00:00:00', '2026-01-15 00:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `driver`
--

CREATE TABLE `driver` (
  `driver_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `exp_years` int DEFAULT NULL,
  `rating` varchar(20) DEFAULT NULL,
  `status` enum('available','busy','inactive') NOT NULL DEFAULT 'available',
  `license_num` varchar(20) NOT NULL,
  `license_type` enum('B','D1','D2','D') NOT NULL,
  `expiry_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `driver`
--

INSERT INTO `driver` (`driver_id`, `user_id`, `address`, `exp_years`, `rating`, `status`, `license_num`, `license_type`, `expiry_date`) VALUES
(1, 2, 'Quận 7, tp. Hồ Chí Minh', 2, NULL, 'available', '123456789', 'B', '2028-09-01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payment`
--

CREATE TABLE `payment` (
  `payment_id` bigint NOT NULL,
  `contract_id` bigint NOT NULL,
  `amount` bigint DEFAULT NULL,
  `payment_method` enum('cash','online') NOT NULL,
  `status` enum('pending','completed','failed','refunded') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `payment`
--

INSERT INTO `payment` (`payment_id`, `contract_id`, `amount`, `payment_method`, `status`) VALUES
(1, 1, NULL, 'cash', 'pending');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `pricing_rule`
--

CREATE TABLE `pricing_rule` (
  `pricing_rule_id` bigint NOT NULL,
  `brand_id` bigint NOT NULL,
  `type_id` bigint NOT NULL,
  `created_date` datetime NOT NULL,
  `effective_from` datetime NOT NULL,
  `effective_to` datetime NOT NULL,
  `status` enum('active','inactive') NOT NULL DEFAULT 'inactive',
  `price_per_hour` int NOT NULL,
  `price_per_day` int NOT NULL,
  `overtime_rate` int NOT NULL,
  `holiday_surcharge` int NOT NULL,
  `wekend_surcharge` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `pricing_rule`
--

INSERT INTO `pricing_rule` (`pricing_rule_id`, `brand_id`, `type_id`, `created_date`, `effective_from`, `effective_to`, `status`, `price_per_hour`, `price_per_day`, `overtime_rate`, `holiday_surcharge`, `wekend_surcharge`) VALUES
(1, 1, 1, '2025-09-07 02:52:55', '2025-09-08 00:00:00', '2025-09-30 00:00:00', 'inactive', 150000, 1000000, 10, 20, 5),
(2, 4, 3, '2025-09-07 02:52:55', '2025-09-08 00:00:00', '2026-12-31 00:00:00', 'inactive', 250000, 1500000, 10, 20, 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `role_id` bigint NOT NULL,
  `role_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`role_id`, `role_name`) VALUES
(1, 'admin'),
(2, 'driver'),
(3, 'customer');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` bigint NOT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`user_id`, `full_name`, `phone`, `dob`, `gender`, `avatar`) VALUES
(1, 'Hồ Đức Nhân', NULL, NULL, NULL, NULL),
(2, 'hdndriver', NULL, NULL, NULL, NULL),
(3, 'Nguyễn Văn A', NULL, NULL, NULL, NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UK_email` (`email`) USING BTREE,
  ADD KEY `role_id` (`role_id`);

--
-- Chỉ mục cho bảng `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `car_id` (`car_id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `discount_id` (`discount_id`);

--
-- Chỉ mục cho bảng `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`car_id`),
  ADD UNIQUE KEY `UK_plate_number` (`plate_number`),
  ADD KEY `brand_id` (`brand_id`),
  ADD KEY `type_id` (`type_id`);

--
-- Chỉ mục cho bảng `car_brand`
--
ALTER TABLE `car_brand`
  ADD PRIMARY KEY (`brand_id`);

--
-- Chỉ mục cho bảng `car_image`
--
ALTER TABLE `car_image`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `car_id` (`car_id`);

--
-- Chỉ mục cho bảng `car_type`
--
ALTER TABLE `car_type`
  ADD PRIMARY KEY (`type_id`);

--
-- Chỉ mục cho bảng `contract`
--
ALTER TABLE `contract`
  ADD PRIMARY KEY (`contract_id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Chỉ mục cho bảng `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`discount_id`),
  ADD UNIQUE KEY `UK_discount_code` (`discount_code`);

--
-- Chỉ mục cho bảng `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driver_id`),
  ADD UNIQUE KEY `UK_license_num` (`license_num`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `contract_id` (`contract_id`);

--
-- Chỉ mục cho bảng `pricing_rule`
--
ALTER TABLE `pricing_rule`
  ADD PRIMARY KEY (`pricing_rule_id`),
  ADD KEY `brand_id` (`brand_id`),
  ADD KEY `type_id` (`type_id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `booking`
--
ALTER TABLE `booking`
  MODIFY `booking_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `car`
--
ALTER TABLE `car`
  MODIFY `car_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `car_brand`
--
ALTER TABLE `car_brand`
  MODIFY `brand_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `car_image`
--
ALTER TABLE `car_image`
  MODIFY `image_id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `car_type`
--
ALTER TABLE `car_type`
  MODIFY `type_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `contract`
--
ALTER TABLE `contract`
  MODIFY `contract_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `discount`
--
ALTER TABLE `discount`
  MODIFY `discount_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `driver`
--
ALTER TABLE `driver`
  MODIFY `driver_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `pricing_rule`
--
ALTER TABLE `pricing_rule`
  MODIFY `pricing_rule_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `role_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ràng buộc đối với các bảng kết xuất
--

--
-- Ràng buộc cho bảng `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `account_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`discount_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `car`
--
ALTER TABLE `car`
  ADD CONSTRAINT `car_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `car_brand` (`brand_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `car_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `car_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `car_image`
--
ALTER TABLE `car_image`
  ADD CONSTRAINT `car_image_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `contract`
--
ALTER TABLE `contract`
  ADD CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `driver`
--
ALTER TABLE `driver`
  ADD CONSTRAINT `driver_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`contract_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Ràng buộc cho bảng `pricing_rule`
--
ALTER TABLE `pricing_rule`
  ADD CONSTRAINT `pricing_rule_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `car_brand` (`brand_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `pricing_rule_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `car_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
