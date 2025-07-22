package com.guilherme.ClientSupport.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tipo de Usuário faltando!")
    @Enumerated(EnumType.STRING)
    private TipoUser role;

    @NotBlank (message = "Nome inválido!")
    private String nome;

    @Email (message = "E-mail inválido!")
    @NotBlank (message = "E-mail vazio!")
    private String email;
    
    @NotBlank(message = "Senha vazia!")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha; 

    public Usuario(){}

    public Usuario (TipoUser role, String nome, String email, String senha)
    {
        this.role = role;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId(){return this.id;}

    public TipoUser getRole(){return this.role;}

    public String getNome(){return this.nome;}

    public String getEmail(){return this.email;}

    public String getSenha(){return this.senha;}


    public void setRole(TipoUser role){this.role = role;}

    public void setNome(String nome){this.nome = nome;}

    public void setEmail(String email){this.email = email;}

    public void setSenha(String senha){this.senha = senha;}
}
