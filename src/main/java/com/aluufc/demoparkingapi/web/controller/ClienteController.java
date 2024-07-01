package com.aluufc.demoparkingapi.web.controller;

import org.springframework.web.bind.annotation.RestController;

import com.aluufc.demoparkingapi.entity.Cliente;
import com.aluufc.demoparkingapi.jwt.JwtUserDetails;
import com.aluufc.demoparkingapi.service.ClienteService;
import com.aluufc.demoparkingapi.service.UsuarioService;
import com.aluufc.demoparkingapi.web.dto.ClienteCreateDto;
import com.aluufc.demoparkingapi.web.dto.ClienteResponseDto;
import com.aluufc.demoparkingapi.web.dto.mapper.ClienteMapper;

import io.micrometer.core.ipc.http.HttpSender.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    private final UsuarioService usuarioService; 
    
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClienteResponseDto> create( @Valid @RequestBody ClienteCreateDto dto, 
    @AuthenticationPrincipal JwtUserDetails userDetails){
        System.out.println("Payload recebido: " + dto.toString());
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);

        return ResponseEntity.status(201).body(ClienteMapper.tDto(cliente));
    }
}
