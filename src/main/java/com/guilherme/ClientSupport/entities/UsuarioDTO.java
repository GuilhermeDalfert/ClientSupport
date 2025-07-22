package com.guilherme.ClientSupport.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {

    @Email (message = "E-mail inválido!")
    @NotBlank (message = "E-mail vazio!")
    private String email;

    @NotBlank (message = "Tipo de user não determinado!")
    private String role;

    @NotBlank (message = "Nome inválido!")
    private String nome;

    public UsuarioDTO(String email, String role, String nome) {
        this.email = email;
        this.role = role;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

     public void setRole(String role) {
        this.role = role;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

