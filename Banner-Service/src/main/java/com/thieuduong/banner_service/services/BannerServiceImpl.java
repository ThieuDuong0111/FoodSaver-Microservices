package com.thieuduong.banner_service.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.banner_service.models.Banner;
import com.thieuduong.banner_service.repositories.IBannerRepository;
import com.thieuduong.commons.dto.BannerDTO;

@Service
public class BannerServiceImpl implements IBannerService {

	@Autowired
	private IBannerRepository BannerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<BannerDTO> getAllBanners() {
		return BannerRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public Banner getBannerById(int id) {
		Optional<Banner> optionalBanner = BannerRepository.findById(id);
		Banner Banner = null;
		if (optionalBanner.isPresent()) {
			Banner = optionalBanner.get();
		}
		return Banner;
	}

	@Override
	public BannerDTO convertToDto(Banner Banner) {
		return modelMapper.map(Banner, BannerDTO.class);
	}

	@Override
	public Banner getBannerByImageUrl(String url) {
		Optional<Banner> optionalBanner = BannerRepository.findByImageUrl(url);
		Banner Banner = null;
		if (optionalBanner.isPresent()) {
			Banner = optionalBanner.get();
		}
		return Banner;
	}

}
