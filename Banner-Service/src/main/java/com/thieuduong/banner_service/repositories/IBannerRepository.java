package com.thieuduong.banner_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.banner_service.models.Banner;

@Repository
public interface IBannerRepository extends JpaRepository<Banner, Integer> {
	Optional<Banner> findByImageUrl(String imageUrl);
}
