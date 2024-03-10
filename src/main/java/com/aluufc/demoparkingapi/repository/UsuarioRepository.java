package com.aluufc.demoparkingapi.repository;

import com.aluufc.demoparkingapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}