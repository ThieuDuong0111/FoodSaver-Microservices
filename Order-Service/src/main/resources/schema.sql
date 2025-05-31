CREATE TABLE IF NOT EXISTS `my_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `creator_id` int DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `is_paid` bit(1) DEFAULT NULL,
  `order_code` varchar(100) DEFAULT NULL,
  `payment_type` int NOT NULL,
  `published_date` datetime(6) DEFAULT NULL,
  `shipping_type` int NOT NULL,
  `status_type` int NOT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` mediumblob,
  `image_type` varchar(20) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_image` text,
  `product_name` varchar(255) DEFAULT NULL,
  `unit_price` double NOT NULL,
  `unit_quantity` int NOT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5y9i4lwrtp140e2nrghy92yf` (`order_id`),
  CONSTRAINT `FKe5y9i4lwrtp140e2nrghy92yf` FOREIGN KEY (`order_id`) REFERENCES `my_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
