package com.thieuduong.banner_service.services;

import java.util.List;

import com.thieuduong.banner_service.models.Banner;
import com.thieuduong.commons.dto.BannerDTO;

public interface IBannerService {
	BannerDTO convertToDto(Banner Banner);

	List<BannerDTO> getAllBanners();

	Banner getBannerByImageUrl(String url);

	Banner getBannerById(int id);
}