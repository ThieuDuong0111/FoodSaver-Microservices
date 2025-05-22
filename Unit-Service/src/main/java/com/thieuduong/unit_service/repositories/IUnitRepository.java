package com.thieuduong.unit_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.unit_service.models.Unit;

@Repository
public interface IUnitRepository extends JpaRepository<Unit, Integer> {
}