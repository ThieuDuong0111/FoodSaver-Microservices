package com.thieuduong.api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.api_gateway.services.UserServiceImpl;
import com.thieuduong.commons.dto.SignUpDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class SignUpController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/sign-up")
	public Mono<ResponseEntity<SignUpDTO>> signUpUser(@RequestBody SignUpDTO signUpDTO) {
		return userServiceImpl.checkInformationSignUpValid(signUpDTO).flatMap(validatedDTO -> {
			if (validatedDTO.getHasError()) {
				return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validatedDTO));
			}
			return userServiceImpl.existsByNameSignUp(validatedDTO).flatMap(nameCheckedDTO -> {
				if (nameCheckedDTO.getHasError()) {
					return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(nameCheckedDTO));
				}
				return userServiceImpl.existsByEmailSignUp(nameCheckedDTO).flatMap(emailCheckedDTO -> {
					if (emailCheckedDTO.getHasError()) {
						return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailCheckedDTO));
					}
					return userServiceImpl.existsByPhoneSignUp(emailCheckedDTO).flatMap(phoneCheckedDTO -> {
						if (phoneCheckedDTO.getHasError()) {
							return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(phoneCheckedDTO));
						}
						// Thực hiện signUp trả về Mono<SignUpDTO>
						return userServiceImpl.signUp(phoneCheckedDTO)
								.map(signedUpDTO -> ResponseEntity.ok(signedUpDTO));
					});
				});
			});
		});
	}
}
