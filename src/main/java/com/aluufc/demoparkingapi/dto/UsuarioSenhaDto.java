package com.aluufc.demoparkingapi.dto;



import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UsuarioSenhaDto {

    private String senhaAtual ;
    private String novaSenha;
    private String confirmaSenha;
}
