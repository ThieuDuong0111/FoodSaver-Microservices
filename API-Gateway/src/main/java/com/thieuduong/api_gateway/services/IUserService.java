package com.thieuduong.api_gateway.services;

import com.thieuduong.commons.dto.SignInDTO;
import com.thieuduong.commons.dto.SignUpDTO;

import reactor.core.publisher.Mono;

public interface IUserService {

	// Sign-Up

	Mono<SignUpDTO> checkInformationSignUpValid(SignUpDTO signUpDTO);

	Mono<SignUpDTO> existsByNameSignUp(SignUpDTO signUpDTO);

	Mono<SignUpDTO> existsByEmailSignUp(SignUpDTO signUpDTO);

	Mono<SignUpDTO> existsByPhoneSignUp(SignUpDTO signUpDTO);

	Mono<SignUpDTO> signUp(SignUpDTO signUpDTO);

	// Sign-In

	Mono<SignInDTO> checkInformationSignInValid(SignInDTO ignInDTO);

	Mono<SignInDTO> existsByNameSignIn(SignInDTO signInDTO);

	Mono<SignInDTO> validatePasswordSignIn(SignInDTO signInDTO);

}
