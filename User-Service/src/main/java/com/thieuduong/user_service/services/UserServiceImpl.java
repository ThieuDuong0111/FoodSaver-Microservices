package com.thieuduong.user_service.services;

import java.util.Base64;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;

import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.commons.utils.ImageUtils;
import com.thieuduong.commons.utils.JWTUtils;
import com.thieuduong.commons.utils.ParseUtils;
import com.thieuduong.commons.utils.ValidationUtils;
import com.thieuduong.user_service.models.MyUser;
import com.thieuduong.user_service.repositories.IUserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO convertToDto(MyUser user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	public Flux<MyUser> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public MyUser convertToEntity(UserDTO userDTO) {
		return modelMapper.map(userDTO, MyUser.class);
	}

	@Override
	public Mono<UserDTO> updateUserInfoMobile(ServerWebExchange request, UserDTO userDTO) {
		return getUserByToken(request).flatMap(user -> {
			// update Image
			if (userDTO.getImageFile() != null) {
				MultipartFile image = userDTO.getImageFile();
				try {
					user.setAvatar(
							Base64.getEncoder().encodeToString(ImageUtils.resizeImage(image.getBytes(), 500, 500)));
					user.setImageType("image/jpeg");
					user.setImageUrl(ParseUtils.parseImageUrl(image.getBytes()) + ".jpeg");
				} catch (Exception e) {
					System.out.println("Upload Image Exception: " + e.getMessage());
				}
			}
			// update email
			if (!ValidationUtils.isNullOrEmpty(userDTO.getEmail())) {
				user.setEmail(userDTO.getEmail());
			}
			// update phone
			if (!ValidationUtils.isNullOrEmpty(userDTO.getPhone())) {
				user.setPhone(userDTO.getPhone());
			}
			// update address
			if (!ValidationUtils.isNullOrEmpty(userDTO.getAddress())) {
				user.setAddress(userDTO.getAddress());
			}
			this.userRepository.save(user);
			return userRepository.save(user).map(this::convertToDto);
		});
	}

	@Override
	public Mono<MyUser> getUserById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public Mono<MyUser> getUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public Mono<MyUser> getUserByImageUrl(String url) {
		return userRepository.findByImageUrl(url);
	}

	@Override
	public Mono<MyUser> getUserByStoreImageUrl(String url) {
		return userRepository.findByStoreImageUrl(url);
	}

	@Override
	public Flux<UserDTO> get10NewestStore() {
		return userRepository.get10NewestStore().map(this::convertToDto);
	}

	@Override
	public Flux<UserDTO> getAllStores() {
		return userRepository.getAllStores().map(this::convertToDto);
	}

	@Override
	public Mono<MyUser> getUserByToken(ServerWebExchange request) throws IllegalArgumentException {
		String array[] = request.getRequest().getHeaders().getFirst("Authorization").split(" ");
		String name = JWTUtils.extractUsername(array[1]);
		return userRepository.findByName(name);
	}
}
