package com.thieuduong.product_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping
	public ResponseEntity<?> getHome() {
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setBanners(bannerClient.getAllBanners());
		homeDTO.setCategories(categoryServiceImpl.getAllCategories());
		homeDTO.setStores(userClient.get10NewestStore());
		homeDTO.setMostRatingProducts(productServiceImpl.getTop5MostPurchaseProducts());
		homeDTO.setNewestProducts(productServiceImpl.getTop20NewestProducts());
		return ResponseEntity.ok(homeDTO);
	}
}
