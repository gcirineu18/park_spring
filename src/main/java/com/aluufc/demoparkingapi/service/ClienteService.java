package com.aluufc.demoparkingapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aluufc.demoparkingapi.entity.Cliente;
import com.aluufc.demoparkingapi.exception.CpfUniqueViolationException;
import com.aluufc.demoparkingapi.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        try{
            return clienteRepository.save(cliente);
        } 
        catch(DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(
                String.format("CPF '%s', não pode ser cadastrado, não existe no sistema",
                 cliente.getCpf())); 
        }

    }
}
