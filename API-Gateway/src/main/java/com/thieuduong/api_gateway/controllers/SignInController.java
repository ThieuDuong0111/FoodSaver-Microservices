package com.thieuduong.api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.api_gateway.services.UserServiceImpl;
import com.thieuduong.commons.dto.SignInDTO;
import com.thieuduong.commons.utils.JWTUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class SignInController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/sign-in")
	public Mono<ResponseEntity<SignInDTO>> signInUser(@RequestBody SignInDTO signInDTO) {
		return userServiceImpl.checkInformationSignInValid(signInDTO).flatMap(validatedDTO -> {
			if (validatedDTO.getHasError()) {
				return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validatedDTO));
			}
			return userServiceImpl.existsByNameSignIn(validatedDTO).flatMap(existsDTO -> {
				if (existsDTO.getHasError()) {
					return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existsDTO));
				}
				return userServiceImpl.validatePasswordSignIn(existsDTO).map(validatedPasswordDTO -> {
					if (validatedPasswordDTO.getHasError()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validatedPasswordDTO);
					}
					// Tạo token sau khi xác thực thành công
					validatedPasswordDTO.setToken(authenticateAndGetToken(validatedPasswordDTO));
					return ResponseEntity.ok(validatedPasswordDTO);
				});
			});
		});
	}

	public String authenticateAndGetToken(SignInDTO signInDTO) {
		try {
			return JWTUtils.generateToken(signInDTO.getName());
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid user request!");
		}
	}
}
