package com.mballem.parkapi.service;

import com.mballem.parkapi.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    //para que o processo do Lombok funcione como Spring
    // deve-se adicionar o final
    private final UsuarioRepositorio usuarioRepositorio;
}
