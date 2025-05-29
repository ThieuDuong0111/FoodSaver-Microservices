package com.thieuduong.api_gateway.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.thieuduong.api_gateway.models.MyUser;
import com.thieuduong.api_gateway.repositories.IUserRepository;
import com.thieuduong.commons.dto.SignInDTO;
import com.thieuduong.commons.dto.SignUpDTO;
import com.thieuduong.commons.utils.ValidationUtils;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Mono<SignUpDTO> existsByNameSignUp(SignUpDTO signUpDTO) {
		signUpDTO.setHasError(false);
		return userRepository.existsByName(signUpDTO.getName()).map(exists -> {
			if (exists) {
				signUpDTO.setNameError("Username is existed.");
				signUpDTO.setHasError(true);
			}
			return signUpDTO;
		});
	}

	@Override
	public Mono<SignUpDTO> existsByEmailSignUp(SignUpDTO signUpDTO) {
		signUpDTO.setHasError(false);
		return userRepository.existsByEmail(signUpDTO.getEmail()).map(exists -> {
			if (exists) {
				signUpDTO.setEmailError("Email is existed.");
				signUpDTO.setHasError(true);
			}
			return signUpDTO;
		});
	}

	@Override
	public Mono<SignUpDTO> existsByPhoneSignUp(SignUpDTO signUpDTO) {
		signUpDTO.setHasError(false);
		return userRepository.existsByPhone(signUpDTO.getPhone()).map(exists -> {
			if (exists) {
				signUpDTO.setPhoneError("Phone is existed.");
				signUpDTO.setHasError(true);
			}
			return signUpDTO;
		});
	}

	@Override
	public Mono<SignUpDTO> checkInformationSignUpValid(SignUpDTO signUpDTO) {
		signUpDTO.setHasError(false);

		if (ValidationUtils.isNullOrEmpty(signUpDTO.getName())) {
			signUpDTO.setNameError("Username can't be empty.");
			signUpDTO.setHasError(true);
		}
		if (ValidationUtils.isNullOrEmpty(signUpDTO.getPassword())) {
			signUpDTO.setPasswordError("Password can't be empty.");
			signUpDTO.setHasError(true);
		}
		if (ValidationUtils.isNullOrEmpty(signUpDTO.getConfirmPassword())) {
			signUpDTO.setConfirmPasswordError("Confirm Password can't be empty.");
			signUpDTO.setHasError(true);
		}
		if (ValidationUtils.isNullOrEmpty(signUpDTO.getEmail())) {
			signUpDTO.setEmailError("Email can't be empty.");
			signUpDTO.setHasError(true);
		}
		if (ValidationUtils.isNullOrEmpty(signUpDTO.getPhone())) {
			signUpDTO.setPhoneError("Phone can't be empty.");
			signUpDTO.setHasError(true);
		}
		if (ValidationUtils.isNullOrEmpty(signUpDTO.getAddress())) {
			signUpDTO.setAddressError("Address can't be empty.");
			signUpDTO.setHasError(true);
		}

		if (!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())) {
			signUpDTO.setPasswordError("Password and Confirm Password aren't matched.");
			signUpDTO.setConfirmPasswordError("Password and Confirm Password aren't matched.");
			signUpDTO.setHasError(true);
		}

		return Mono.just(signUpDTO);
	}

	@Override
	public Mono<SignUpDTO> signUp(SignUpDTO signUpDTO) {

		return Mono.fromRunnable(() -> {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
			MyUser user = modelMapper.map(signUpDTO, MyUser.class);
			user.setRoleId(3);
			userRepository.save(user);
		}).thenReturn(signUpDTO);
	}

	@Override
	public Mono<SignInDTO> checkInformationSignInValid(SignInDTO signInDTO) {
		signInDTO.setHasError(false);
		if (ValidationUtils.isNullOrEmpty(signInDTO.getName())
				|| ValidationUtils.isNullOrEmpty(signInDTO.getPassword())) {
			if (ValidationUtils.isNullOrEmpty(signInDTO.getName())) {
				signInDTO.setNameError("Username can't be empty.");
			}
			if (ValidationUtils.isNullOrEmpty(signInDTO.getPassword())) {
				signInDTO.setPasswordError("Password can't be empty.");
			}
			signInDTO.setHasError(true);
		}
		return Mono.just(signInDTO);
	}

	@Override
	public Mono<SignInDTO> existsByNameSignIn(SignInDTO signInDTO) {
		signInDTO.setHasError(false);
		return userRepository.existsByName(signInDTO.getName()).map(exists -> {
			if (!exists) {
				signInDTO.setNameError("Username or Password isn't correct.");
				signInDTO.setPasswordError("Username or Password isn't correct.");
				signInDTO.setHasError(true);
			}
			return signInDTO;
		});
	}

	@Override
	public Mono<SignInDTO> validatePasswordSignIn(SignInDTO signInDTO) {
		signInDTO.setHasError(false);

		return userRepository.findByName(signInDTO.getName()).map(user -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (!encoder.matches(signInDTO.getPassword(), user.getPassword())) {
				signInDTO.setNameError("Username or Password isn't correct.");
				signInDTO.setPasswordError("Username or Password isn't correct.");
				signInDTO.setHasError(true);
			}
			return signInDTO;
		}).defaultIfEmpty(signInDTO);
	}

}
