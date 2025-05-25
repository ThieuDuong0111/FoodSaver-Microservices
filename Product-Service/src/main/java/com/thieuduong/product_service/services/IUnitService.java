package com.thieuduong.product_service.services;

import com.thieuduong.commons.dto.UnitDTO;
import com.thieuduong.product_service.models.Unit;

public interface IUnitService {
	UnitDTO convertToDto(Unit Unit);

	Unit getUnitById(int id);

}
