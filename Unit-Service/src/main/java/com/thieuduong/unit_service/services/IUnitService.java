package com.thieuduong.unit_service.services;

import com.thieuduong.unit_service.dto.UnitDTO;
import com.thieuduong.unit_service.models.Unit;

public interface IUnitService {
	UnitDTO convertToDto(Unit Unit);

	Unit getUnitById(int id);

}
