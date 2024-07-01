package com.aluufc.demoparkingapi;


import com.aluufc.demoparkingapi.web.dto.UsuarioCreateDto;
import com.aluufc.demoparkingapi.web.dto.UsuarioResponseDto;
import com.aluufc.demoparkingapi.web.dto.UsuarioSenhaDto;
import com.aluufc.demoparkingapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {
        UsuarioResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void createUsuario_ComUsernameInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUsuario_ComPasswordInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tod@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tod@email.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tod@email.com", "12345689"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUsuario_ComEmailRepetido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("ana@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);


    }


    @Test
    public void buscarUsuario_ComIdExistente_RetornarUsuarioStatus200() {

        

        UsuarioResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId().toString()).isEqualTo("07ce9e8f-c278-42e5-80a5-5b028bc6d55e");
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/5896f50a-45f2-4780-8367-bc3a667ddd74")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId().toString()).isEqualTo("5896f50a-45f2-4780-8367-bc3a667ddd74");
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("bia@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");


        responseBody = testClient
                       .get()
                       .uri("/api/v1/usuarios/5896f50a-45f2-4780-8367-bc3a667ddd74")
                       .headers(JwtAuthentication.getHeaderAuthorization(testClient, "123456", "bia@email.com"))
                       .exchange()
                       .expectStatus().isOk()
                       .expectBody(UsuarioResponseDto.class)
                       .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId().toString()).isEqualTo("5896f50a-45f2-4780-8367-bc3a667ddd74");
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("bia@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");             
    }


    @Test
    public void buscarUsuario_ComIdNaoExistente_RetornarErroMessageStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/5896f50a-45f2-4780-8367-bc3a667ddd78")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarUsuario_ComUsuarioClienteBuscandoOutroCliente_RetornarErroMessageStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/23af9fde-1159-4d50-8775-d2f72365a810")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "bia@email.com"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }


    @Test
    public void editarSenha_ComDadosValidos_RetornarUsuarioCriadoComStatus204() {
     // Como não haverá retorno algum, é possível remover a atribuição
     // e as testagens
      testClient
                .patch()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isNoContent();


                testClient
                .patch()
                .uri("/api/v1/usuarios/5896f50a-45f2-4780-8367-bc3a667ddd74")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "bia@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isNoContent();        

    }


    @Test
    public void editarSenha_ComUsuariosDiferentes_RetornarErroMessageStatus403() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/5896f50a-45f2-4780-8367-bc3a667ddd75")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/5896f50a-45f2-4780-8367-bc3a667ddd75")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "bia@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);


    }


    @Test
    public void editarSenha_ComCamposInvalidos_RetornarErroMessageStatus422() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


         responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("1234", "1234", "1234"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("12345678", "12345678", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }


    @Test
    public void editarSenha_ComSenhaInvalida_RetornarErroMessageStatus400() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123457", "654321", "654321"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/07ce9e8f-c278-42e5-80a5-5b028bc6d55e")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "578576", "654321"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

    }

    @Test
    public void buscarUsuarios_RetornarUsuarioStatus200() {
        List<UsuarioResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "ana@email.com"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.isEmpty()).isNotEqualTo(true);
        Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }


    @Test
    public void buscarUsuarios_RetornarUsuarioStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"123456", "bia@email.com"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        

    }



}