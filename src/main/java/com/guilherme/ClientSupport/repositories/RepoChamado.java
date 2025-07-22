package com.guilherme.ClientSupport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.ClientSupport.entities.Chamado;
import com.guilherme.ClientSupport.entities.Usuario;

public interface RepoChamado extends JpaRepository<Chamado, Long>
{
    List<Chamado> findByUsuario(Usuario usuario);
}
