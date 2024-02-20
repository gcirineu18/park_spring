package com.aluufc.demoparkingapi.web.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UsuarioResponseDto {
    private Long id;
    private String username;
    private String role;
}
