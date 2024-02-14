package com.aluufc.demoparkingapi.webcontroller;
import com.aluufc.demoparkingapi.dto.UsuarioResponseDto;
import com.aluufc.demoparkingapi.dto.UsuarioSenhaDto;
import com.aluufc.demoparkingapi.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aluufc.demoparkingapi.entity.Usuario;
import com.aluufc.demoparkingapi.service.UsuarioService;

import java.util.List;


/*A injeção de dependências para UserService
* será via método construtor. */
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
     public ResponseEntity<UsuarioResponseDto> getById( /* Indica o valor a ser recuperado na url para o controller*/@PathVariable Long id){
               Usuario user = usuarioService.buscarPorId(id);
               return ResponseEntity.ok(UsuarioMapper.toDto(user));
     }

     @PatchMapping("/{id}")
     public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto usuarioSenhaDto){
        Usuario user = usuarioService.editarSenha(id, usuarioSenhaDto.getSenhaAtual(),usuarioSenhaDto.getNovaSenha(), usuarioSenhaDto.getConfirmaSenha() );


        if(user != null){
            return ResponseEntity.noContent().build();
            //return ResponseEntity.ok("Senha atualizada");
        }

        return ResponseEntity.badRequest().body("Falha para atualizar a senha");
     }

     @GetMapping
     public ResponseEntity <List<UsuarioResponseDto>> getAll(){
         List<Usuario> users = usuarioService.getAllUsers();
          return ResponseEntity.ok(UsuarioMapper.toListDto(users));
     }

}