package com.thieuduong.unit_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.dto.UnitDTO;
import com.thieuduong.unit_service.services.UnitServiceImpl;

@RestController
@RequestMapping("/api")
public class UnitController {

	@Autowired
	private UnitServiceImpl UnitServiceImpl;

	@GetMapping({ "/unit/{id}" })
	public ResponseEntity<?> getUnitDetail(@PathVariable int id) {
		UnitDTO UnitDTO = UnitServiceImpl.convertToDto(UnitServiceImpl.getUnitById(id));
		if (UnitDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(UnitDTO);
	}
}
