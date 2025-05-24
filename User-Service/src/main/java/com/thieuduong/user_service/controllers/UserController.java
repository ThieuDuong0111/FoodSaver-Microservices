package com.thieuduong.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.user_service.services.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping({ "/user/{id}" })
	public ResponseEntity<?> getUserDetail(@PathVariable int id) {
		UserDTO UserDTO = userServiceImpl.convertToDto(userServiceImpl.getUserById(id));
		if (UserDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(UserDTO);
	}

//	@GetMapping("/user-info")
//	public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
//		MyUser user = userServiceImpl.getUserByToken(request);
//		return ResponseEntity.ok(userServiceImpl.convertToDto(user));
//	}
//
//	@PutMapping(path = "/update-info", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//	public ResponseEntity<?> updateUserInfo(HttpServletRequest request, @ModelAttribute UserDTO userDTO) {
//		UserDTO responseUserDTO = userServiceImpl.updateUserInfoMobile(request, userDTO);
//		return ResponseEntity.ok(responseUserDTO);
//	}

}
