package com.guilherme.ClientSupport.services;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guilherme.ClientSupport.entities.Usuario;
import com.guilherme.ClientSupport.repositories.RepoUsuario;

@Service
public class CarregaUsers implements UserDetailsService {

    private final RepoUsuario repoUsuario;

    public CarregaUsers (RepoUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repoUsuario.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com esse email: " + email));

        List<GrantedAuthority> autoridades = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getSenha(),
            autoridades
        );
    }
}
