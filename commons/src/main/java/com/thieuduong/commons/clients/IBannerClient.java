package com.thieuduong.commons.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.thieuduong.commons.dto.BannerDTO;

@FeignClient(name = "banner-service", url = "http://localhost:8083", path = "/api")
public interface IBannerClient {
	@GetMapping("/banners/all")
	List<BannerDTO> getAllBanners();
}
