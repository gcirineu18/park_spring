package com.aluufc.demoparkingapi;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.aluufc.demoparkingapi.jwt.JwtToken;
import com.aluufc.demoparkingapi.web.dto.UsuarioLoginDTO;

public class JwtAuthentication {
    
    @SuppressWarnings("null")
    public static Consumer<HttpHeaders>  getHeaderAuthorization( WebTestClient client,
    String password, String username){
        String token = client
                .post() 
                .uri("/api/v1/auth")
                .bodyValue(new UsuarioLoginDTO(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
                
        return header -> header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
