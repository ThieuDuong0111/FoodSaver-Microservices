package com.thieuduong.api_gateway.services;

import reactor.core.publisher.Mono;

public interface MyReactiveUserDetailsService {
	Mono<MyUserDetails> findByUsername(String username);
}
