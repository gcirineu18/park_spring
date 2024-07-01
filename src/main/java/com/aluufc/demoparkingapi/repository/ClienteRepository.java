package com.aluufc.demoparkingapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluufc.demoparkingapi.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    
}
