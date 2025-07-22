package com.guilherme.ClientSupport.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título vazio!")
    private String titulo;

    @NotBlank(message = "Descrição vazia!")
    private String descricao;

    private String resposta;

    @ManyToOne
    @NotNull(message = "Usuário vazio!")
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    public Chamado (){}

    public Chamado (String titulo, String descricao, Usuario usuario)
    {
        this.titulo = titulo;
        this.descricao = descricao;
        this.resposta = "Aguardando Resposta.";
        this.usuario = usuario;
        this.status = StatusChamado.ABERTO;
    }
    
    public Long getId (){return this.id;}

    public String getTitulo (){return this.titulo;}

    public String getDescricao (){return this.descricao;}

    public String getResposta (){return this.resposta;}

    public Usuario getUser (){return this.usuario;}

    public StatusChamado getStatus (){return this.status;}



    public void setTitulo (String titulo) {this.titulo = titulo;}

    public void setDescricao (String descricao) {this.descricao = descricao;}
    
    public void setResposta (String resposta) {this.resposta = resposta;}

    public void setUser (Usuario usuario) {this.usuario = usuario;}

    public void setStatus (StatusChamado status) {this.status = status;}
}
