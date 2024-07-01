package com.aluufc.demoparkingapi.web.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClienteResponseDto {

    private UUID id;
    private String nome;
    private String cpf;
}
