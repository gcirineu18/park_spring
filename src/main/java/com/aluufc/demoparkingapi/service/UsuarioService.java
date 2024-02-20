package com.aluufc.demoparkingapi.service;


import com.aluufc.demoparkingapi.exception.UsernameUniqueViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aluufc.demoparkingapi.entity.Usuario;
import com.aluufc.demoparkingapi.respository.UsuarioRepositorio;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    //para que o processo do Lombok funcione como Spring
    // deve-se adicionar o final
    private final UsuarioRepositorio usuarioRepositorio;

    /* Essa anotação indica que o Spring vai tomar
     conta da parte referente à Transação. É ele que vai
     cuidar do recurso para abrir, gerenciar e fechar a
     transação do método save()
     */

    @Transactional
    public Usuario salvar(Usuario usuario){
       try{
           return usuarioRepositorio.save(usuario);
       }
       catch(Exception exception){
           String message = exception.getMessage();

           if (message.contains("ERRO: duplicar valor da chave viola a restrição de unicidade")) {
               throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
           }
           else{
               throw new DataIntegrityViolationException("");
           }

       }


    }

    @Transactional
    public Usuario buscarPorId(Long id){
        return usuarioRepositorio.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com a confirmação de senha.");
        }

        Usuario user = buscarPorId(id);

        if(!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere.");

        }
        user.setPassword(novaSenha);
        return user;
    }

    @Transactional
    public List<Usuario> getAllUsers(){
        return usuarioRepositorio.findAll();
    }
}
