package com.aluufc.demoparkingapi.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.aluufc.demoparkingapi.entity.Usuario;
import com.aluufc.demoparkingapi.respository.UsuarioRepositorio;

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
       
        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario buscarPorId(Long id){
        return usuarioRepositorio.findById(id).orElseThrow(
            () -> new RuntimeException("User not found")
        );
    }

}
