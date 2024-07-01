package com.aluufc.demoparkingapi.web.dto.mapper;

import org.modelmapper.ModelMapper;
import com.aluufc.demoparkingapi.entity.Cliente;
import com.aluufc.demoparkingapi.web.dto.ClienteCreateDto;
import com.aluufc.demoparkingapi.web.dto.ClienteResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {
    
    public static Cliente toCliente(ClienteCreateDto dto){
        return new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDto tDto(Cliente cliente){
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
}
