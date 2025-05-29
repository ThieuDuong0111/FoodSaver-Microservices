package com.thieuduong.api_gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.thieuduong.api_gateway.repositories.IRoleRepository;
import com.thieuduong.api_gateway.repositories.IUserRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements ReactiveUserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public Mono<UserDetails> findByUsername(String name) {
		return userRepository.findByName(name).flatMap(user -> roleRepository.findById(user.getRoleId()).map(
				role -> User.withUsername(user.getName()).password(user.getPassword()).roles(role.getName()).build()));
	}
}
