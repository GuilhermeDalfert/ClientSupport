package com.guilherme.ClientSupport.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.guilherme.ClientSupport.entities.Chamado;
import com.guilherme.ClientSupport.entities.TipoUser;
import com.guilherme.ClientSupport.entities.Usuario;
import com.guilherme.ClientSupport.repositories.RepoChamado;
import com.guilherme.ClientSupport.repositories.RepoUsuario;


@Component
public class InicializaData implements CommandLineRunner 
{
    @Autowired
    private RepoUsuario userRepo;

    @Autowired
    private RepoChamado chamadoRepo;

    @Autowired
    private PasswordEncoder codificadorSenha; 

    @Override
    public void run(String... args) throws Exception {
        Usuario cliente1 = new Usuario(TipoUser.CLIENTE, "Roberto Silva", "robertoS@gmail.com", codificadorSenha.encode("robertsss123"));
        Usuario cliente2 = new Usuario(TipoUser.CLIENTE, "Carolina", "carolina@gmail.com", codificadorSenha.encode("carol123"));
        Usuario adm = new Usuario(TipoUser.ADMINISTRADOR, "Guilherme Az","guiaz@gmail.com", codificadorSenha.encode("azguiaz$$"));

        userRepo.save(cliente1);
        userRepo.save(cliente2);
        userRepo.save(adm);

        Chamado chama1 = new Chamado("Erro no site! Não consigo acessar.", "Estou tendo problemas para acessar o site.", cliente1);
        Chamado chama2 = new Chamado("Tela azul!", "Meu pc está travado tela azul!", cliente2);

        chamadoRepo.save(chama1);
        chamadoRepo.save(chama2);
    }
}
