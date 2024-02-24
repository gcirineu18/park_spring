package com.aluufc.demoparkingapi.web.controller;
import com.aluufc.demoparkingapi.web.dto.UsuarioCreateDto;
import com.aluufc.demoparkingapi.web.dto.UsuarioResponseDto;
import com.aluufc.demoparkingapi.web.dto.UsuarioSenhaDto;
import com.aluufc.demoparkingapi.web.dto.mapper.UsuarioMapper;
import com.aluufc.demoparkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aluufc.demoparkingapi.entity.Usuario;
import com.aluufc.demoparkingapi.service.UsuarioService;
import java.util.List;



@Tag(name="Usuarios", description = "Mapeamento de rotas para operações CRUD.")
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
     @Operation(summary = "Criar um novo usuário.", description = "Recurso para criar um novo usuário.",
                responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema =  @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                                 content = @Content(mediaType = "application.json", schema = @Schema(implementation =  ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos.",
                                content = @Content(mediaType = "application.json", schema = @Schema(implementation =  ErrorMessage.class)))
                })
     public ResponseEntity<?> create(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto){


        try{
            Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
        }
        catch ( Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

     }


    @Operation(summary = "Recuperar um usuário pelo id.", description = "Recurso para recuperar um usuário pelo id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema =  @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application.json", schema = @Schema(implementation =  ErrorMessage.class)))
            })
     @GetMapping("/{id}")
     public ResponseEntity<UsuarioResponseDto> getById( /* Indica o valor a ser recuperado na url para o controller*/@PathVariable Long id){
               Usuario user = usuarioService.buscarPorId(id);
               return ResponseEntity.ok(UsuarioMapper.toDto(user));
     }


    @Operation(summary = "Atualizar senha.", description = "Recurso para atualizar senha.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso.",
                            content = @Content(mediaType = "application/json", schema =  @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application.json", schema = @Schema(implementation =  ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere.",
                            content = @Content(mediaType = "application.json", schema = @Schema(implementation =  ErrorMessage.class)))
            })
     @PatchMapping("/{id}")
     public ResponseEntity<String> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto usuarioSenhaDto){
        Usuario user = usuarioService.editarSenha(id, usuarioSenhaDto.getSenhaAtual(),usuarioSenhaDto.getNovaSenha(), usuarioSenhaDto.getConfirmaSenha() );


        if(user != null){
            return ResponseEntity.noContent().build();
            //return ResponseEntity.ok("Senha atualizada");
        }

        return ResponseEntity.badRequest().body("Falha para atualizar a senha");
     }

     @Operation(summary = "Recuperar todos os usuário cadastrados no sistema.", description = "Recurso para recuperar uma lista de todos os Usuário.",
                responses = {
                     @ApiResponse(responseCode = "200", description = "Recuperado com sucesso.",
                             content = @Content(mediaType = "application/json",
                                     array = @ArraySchema( schema = @Schema(implementation = UsuarioResponseDto.class))))
                })
     @GetMapping
     public ResponseEntity <List<UsuarioResponseDto>> getAll(){
         List<Usuario> users = usuarioService.getAllUsers();
          return ResponseEntity.ok(UsuarioMapper.toListDto(users));
     }

}