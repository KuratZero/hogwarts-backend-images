package com.hogwarts.imagess3.repositories.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@RequiredArgsConstructor
public class JWTEmployeeRepository {
    private final WebClient webClient;

    public Long getUserIdByJwt(String jwt) {
        return webClient.post()
                .uri("/1/user/id")
                .bodyValue(jwt)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }
}
