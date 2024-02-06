package com.aluufc.demoparkingapi.webcontroller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluufc.demoparkingapi.entity.Usuario;
import com.aluufc.demoparkingapi.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




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

    
     private final UsuarioService usuarioService;

     /* O ResponseEntity encapsula a resposta. Então
     a nossa resposta para requisição vai ser um objeto
     do tipo usuário. Depois esse objeto usuário será transformado
     num JSON e enviado lá para o cliente.
      */
     @PostMapping
     public ResponseEntity<Usuario> create( @RequestBody Usuario usuario){

          Usuario user = usuarioService.salvar(usuario);

          return ResponseEntity.status(HttpStatus.CREATED).body(user);
     }

     @GetMapping("/{id}")
     public ResponseEntity<Usuario> getById(@PathVariable Long id){
               Usuario user = usuarioService.buscarPorId(id);
               return ResponseEntity.ok(user);
     }
    
     

}