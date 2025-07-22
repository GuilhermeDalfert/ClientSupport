package com.guilherme.ClientSupport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.guilherme.ClientSupport.entities.Chamado;
import com.guilherme.ClientSupport.entities.ChamadoDTO;
import com.guilherme.ClientSupport.entities.RespostaDTO;
import com.guilherme.ClientSupport.entities.StatusChamado;
import com.guilherme.ClientSupport.entities.Usuario;
import com.guilherme.ClientSupport.repositories.RepoChamado;
import com.guilherme.ClientSupport.repositories.RepoUsuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/chamados")
public class ChamadoController 
{
    @Autowired
    private RepoChamado chamadoRepo;

    @Autowired
    private RepoUsuario userRepo;

    @GetMapping("/todoschamados")
    public ResponseEntity <List<Chamado>> listaChamados()
    {
        List<Chamado> chamados = chamadoRepo.findAll();
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/meuschamados")
    public ResponseEntity <List<Chamado>> listaMeusChamados(Authentication auth)
    {
        String email = auth.getName();
        Usuario eu = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        
        List<Chamado> meusChamados = chamadoRepo.findByUsuario(eu);
        return ResponseEntity.ok(meusChamados);
    }

    @PostMapping
    public ResponseEntity<Chamado> criaChamado (@Valid @RequestBody  ChamadoDTO chamadoDTO, Authentication auth)
    {
        String email = auth.getName();
        Usuario logado = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        Chamado novo = new Chamado(chamadoDTO.getTitulo(), chamadoDTO.getDescricao(), logado);

        Chamado salvo = chamadoRepo.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscaChamado (@PathVariable Long id)
    {
        return chamadoRepo.findById(id)
            .map(chamado -> ResponseEntity.ok(chamado))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaChamado (@PathVariable Long id)
    {
        if (chamadoRepo.existsById(id)) 
        {
            chamadoRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/responde")
    public ResponseEntity<Void> atualizaResposta (@PathVariable Long id, @Valid @RequestBody RespostaDTO resposta)
    {
        Chamado chama = chamadoRepo.findById(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado"));

        StatusChamado status = StatusChamado.valueOf(resposta.getStatus());
        chama.setResposta(resposta.getResposta());
        chama.setStatus(status);

        chamadoRepo.save(chama);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chamado> atualizaChamado (@PathVariable Long id, @Valid @RequestBody Chamado atualizado)
    {
        return chamadoRepo.findById(id)
            .map(existente -> {
                existente.setTitulo(atualizado.getTitulo());
                existente.setDescricao(atualizado.getDescricao());
                existente.setResposta(atualizado.getResposta());
                existente.setUser(atualizado.getUser());
                existente.setStatus(atualizado.getStatus());
                Chamado salvo = chamadoRepo.save(existente);
                return ResponseEntity.ok(salvo);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

