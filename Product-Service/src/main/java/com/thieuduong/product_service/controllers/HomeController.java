package com.thieuduong.product_service.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.clients.IBannerClient;
import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.HomeDTO;
import com.thieuduong.product_service.services.CategoryServiceImpl;
import com.thieuduong.product_service.services.ProductServiceImpl;

@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private IBannerClient bannerClient;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String HOME_CACHE_KEY = "home:data";

	@GetMapping
	public ResponseEntity<?> getHome() {
		// 1. Kiểm tra cache trước
//		HomeDTO cachedHome = (HomeDTO) redisTemplate.opsForValue().get(HOME_CACHE_KEY);
//		if (cachedHome != null) {
//			return ResponseEntity.ok(cachedHome);
//		}
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setBanners(bannerClient.getAllBanners());
		homeDTO.setCategories(categoryServiceImpl.getAllCategories());
		homeDTO.setStores(userClient.get10NewestStore());
		homeDTO.setMostRatingProducts(productServiceImpl.getTop5MostPurchaseProducts());
		homeDTO.setNewestProducts(productServiceImpl.getTop20NewestProducts());

		// 3. Lưu cache lại vào Redis (ví dụ: 5 phút)
//		redisTemplate.opsForValue().set(HOME_CACHE_KEY, homeDTO, Duration.ofMinutes(5));

		return ResponseEntity.ok(homeDTO);
	}
}
