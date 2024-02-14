package com.aluufc.demoparkingapi.dto.mapper;

import com.aluufc.demoparkingapi.dto.UsuarioCreateDto;
import com.aluufc.demoparkingapi.dto.UsuarioResponseDto;
import com.aluufc.demoparkingapi.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

      public static Usuario toUsuario(UsuarioCreateDto createDto){
          return new ModelMapper().map(createDto, Usuario.class);
      }

      public static UsuarioResponseDto toDto(Usuario usuario){

          String role = usuario.getRole().name().substring("ROLE_".length());

          // Necessário pegar o valor da variável role e inserir em ResponseDTO
          PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
              @Override
              protected void configure() {
                  map().setRole(role);
              }
          };
          ModelMapper mapper = new ModelMapper();
          mapper.addMappings(props);

          return mapper.map(usuario, UsuarioResponseDto.class);

      }

      public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){

          return usuarios.stream().map( user -> UsuarioMapper.toDto(user)).collect(Collectors.toList());
      }

}
