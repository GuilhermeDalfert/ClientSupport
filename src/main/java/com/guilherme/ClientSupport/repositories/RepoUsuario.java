package com.guilherme.ClientSupport.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.ClientSupport.entities.Usuario;

public interface RepoUsuario extends JpaRepository<Usuario, Long>
{
     Optional<Usuario> findByEmail(String email);
}
    
