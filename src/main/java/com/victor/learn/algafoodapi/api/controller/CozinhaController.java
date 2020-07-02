package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Cozinha;
import com.victor.learn.algafoodapi.domain.repository.CozinhaRepository;
import com.victor.learn.algafoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
        return cadastroCozinhaService.buscarOuFalhar(id);
    }

    @GetMapping("/por-nome/{nomeCozinha}")
    public ResponseEntity<Cozinha> buscarPorNome(@PathVariable("nomeCozinha") String nomeCozinha) {
        Optional<Cozinha> cozinha = cozinhaRepository.findByNomeContaining(nomeCozinha);
        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
        Cozinha adicionada = cadastroCozinhaService.salvar(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(adicionada);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return  cozinhaRepository.save(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long cozinhaId) { 
        cadastroCozinhaService.excluir(cozinhaId);
    }

}
