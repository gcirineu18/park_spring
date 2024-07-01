package com.aluufc.demoparkingapi;

import org.assertj.core.api.Assertions;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.aluufc.demoparkingapi.jwt.JwtToken;
import com.aluufc.demoparkingapi.web.dto.UsuarioLoginDTO;
import com.aluufc.demoparkingapi.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {
  @Autowired
  WebTestClient testClient;

 @Test
  public void autenticar_comCredenciaisValidas_RetornarTokenComStatus200(){
   JwtToken responseBody = testClient
    .post()
    .uri("/api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("ana@email.com", "123456"))
    .exchange()
    .expectStatus().isOk()
    .expectBody(JwtToken.class)
    .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
  }

 
   @Test
  public void autenticar_comCredenciaisInvalidas_RetornarErrorMessageComStatus400(){
    ErrorMessage responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("ana@email.com", "123457"))
    .exchange()
    .expectStatus().isBadRequest()
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

  
    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);  
    }
    


    responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("invalido@email.com", "123456"))
    .exchange()
    .expectStatus().isBadRequest()
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
      Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);  
      }
  }


  @Test
  public void autenticar_comUsernameInvalido_RetornarErrorMessageComStatus422(){
    ErrorMessage responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("", "123456"))
    .exchange()
    .expectStatus().isEqualTo(422)
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

  
    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);  
    }
    


    responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("@email.com", "123456"))
    .exchange()
    .expectStatus().isEqualTo(422)
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

  
    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);  
    }
  }


  @Test
  public void autenticar_comPasswordInvalido_RetornarErrorMessageComStatus422(){
    ErrorMessage responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("ana@email.com", ""))
    .exchange()
    .expectStatus().isEqualTo(422)
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

  
    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);  
    }
    


    responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("ana@email.com", "156"))
    .exchange()
    .expectStatus().isEqualTo(422)
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

  
    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);  
    }

    responseBody = testClient
    .post()
    .uri("api/v1/auth")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new UsuarioLoginDTO("ana@email.com", "156897880"))
    .exchange()
    .expectStatus().isEqualTo(422)
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();

  
    Assertions.assertThat(responseBody).isNotNull();
    if(responseBody!=null){
    Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);  
    }
  }

}
