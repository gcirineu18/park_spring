package com.mballem.parkapi.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*A injeção de dependências para UserService
* será via método contrutor. */
@RequiredArgsConstructor
/*
* Com @RestController o Spring entende que essa
* classe é um Bean gerenciado por ele e um Bean
* do tipo Controller
* */
@RestController

/*
@RequestMapping é o caminho base dos recursos
que nós vamos ter em controller usuário
 */
@RequestMapping("api/v1/usuarios")

public class UsuarioController {

}
