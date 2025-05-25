package com.thieuduong.product_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.product_service.models.Unit;

@Repository
public interface IUnitRepository extends JpaRepository<Unit, Integer> {
}