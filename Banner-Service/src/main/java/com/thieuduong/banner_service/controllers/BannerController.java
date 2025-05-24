package com.thieuduong.banner_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.banner_service.services.*;

@RestController
@RequestMapping("/api")
public class BannerController {
	@Autowired
	private BannerServiceImpl BannerServiceImpl;

	@GetMapping({ "/banners/all" })
	public ResponseEntity<?> getAllBanners() {
		return ResponseEntity.ok(BannerServiceImpl.getAllBanners());
	}
}
