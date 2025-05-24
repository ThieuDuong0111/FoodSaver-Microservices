package com.thieuduong.unit_service.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.dto.UnitDTO;
import com.thieuduong.unit_service.models.Unit;
import com.thieuduong.unit_service.repositories.IUnitRepository;

@Service
public class UnitServiceImpl implements IUnitService {

	@Autowired
	private IUnitRepository unitRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UnitDTO convertToDto(Unit unit) {
		return modelMapper.map(unit, UnitDTO.class);
	}

	@Override
	public Unit getUnitById(int id) {
		Optional<Unit> optionalUnit = unitRepository.findById(id);
		Unit unit = null;
		if (optionalUnit.isPresent()) {
			unit = optionalUnit.get();
		}
		return unit;
	}

}