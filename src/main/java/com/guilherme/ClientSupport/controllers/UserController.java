package com.guilherme.ClientSupport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilherme.ClientSupport.entities.Usuario;
import com.guilherme.ClientSupport.entities.UsuarioDTO;
import com.guilherme.ClientSupport.repositories.RepoUsuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UserController 
{
    @Autowired
    private RepoUsuario userRepo;

    @Autowired
    private PasswordEncoder codificadorSenha;

    @GetMapping
    public ResponseEntity <List <Usuario>> listaUsuarios()
    {
        List<Usuario> usuarios = userRepo.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/post")
    public ResponseEntity<Usuario> criaUser (@Valid @RequestBody Usuario novo)
    {
        String senhaCriptografada = codificadorSenha.encode(novo.getSenha());
        novo.setSenha(senhaCriptografada);
        Usuario salvo = userRepo.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> buscaRole (Authentication authentication)
    {
        String email = authentication.getName();
        Usuario user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String role = user.getRole().toString();

        UsuarioDTO dto = new UsuarioDTO(user.getEmail(), role, user.getNome());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscaUser (@PathVariable Long id)
    {
        return userRepo.findById(id)
            .map(usuario -> ResponseEntity.ok(usuario))   
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaUser (@PathVariable Long id)
    {
        if (userRepo.existsById(id)) 
        {
            userRepo.deleteById(id);
            return ResponseEntity.noContent().build();      
        }
        return ResponseEntity.notFound().build();            
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizaUser (@PathVariable Long id, @Valid @RequestBody Usuario atualizado)
    {
        return userRepo.findById(id)
            .map(existente -> {
                existente.setNome(atualizado.getNome());
                existente.setEmail(atualizado.getEmail());
                existente.setSenha(atualizado.getSenha());
                existente.setRole(atualizado.getRole());
                Usuario salvo = userRepo.save(existente);
                return ResponseEntity.ok(salvo);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
