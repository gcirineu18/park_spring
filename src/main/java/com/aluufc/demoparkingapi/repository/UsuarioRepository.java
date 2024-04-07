package com.aluufc.demoparkingapi.repository;

import com.aluufc.demoparkingapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
   Optional<Usuario > findByUsername(String username);

    @Query("select u.role from Usuario  u where u.username like :username ")
    Usuario.Role findRoleByUsername(String username);
}