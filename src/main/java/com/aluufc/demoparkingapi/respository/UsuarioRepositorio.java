package com.aluufc.demoparkingapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluufc.demoparkingapi.entity.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {


}