package com.thieuduong.user_service.services;

import java.util.List;

import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.user_service.models.MyUser;

public interface IUserService {
	UserDTO convertToDto(MyUser user);

	MyUser convertToEntity(UserDTO userDTO);

	List<MyUser> getAllUsers();

	List<UserDTO> getAllStores();

	List<UserDTO> get10NewestStore();

//	UserDTO updateUserInfo(HttpServletRequest request, UserDTO userDTO);
//
//	UserDTO updateUserInfoMobile(HttpServletRequest request, UserDTO userDTO);

	MyUser getUserById(int id);

	MyUser getUserByName(String name);

	MyUser getUserByImageUrl(String url);

	MyUser getUserByStoreImageUrl(String url);

//	MyUser getUserByToken(HttpServletRequest request) throws IllegalArgumentException;

}
