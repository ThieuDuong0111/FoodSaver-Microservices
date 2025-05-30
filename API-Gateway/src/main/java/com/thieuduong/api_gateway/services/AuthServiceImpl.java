package com.thieuduong.api_gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.api_gateway.repositories.IRoleRepository;
import com.thieuduong.api_gateway.repositories.IUserRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements MyReactiveUserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public Mono<MyUserDetails> findByUsername(String name) {
		return userRepository.findByName(name).flatMap(
				user -> roleRepository.findById(user.getRoleId()).map(role -> MyUserCustom.withUsername(user.getName())
						.id(user.getId()).password(user.getPassword()).roles(role.getName()).build()));
	}

//	public Mono<MyUser> findMyUserByUsername(String name) {
//		return userRepository.findByName(name);
//	}
}
