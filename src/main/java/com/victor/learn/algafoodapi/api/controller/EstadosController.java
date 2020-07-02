package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Estado;
import com.victor.learn.algafoodapi.domain.service.CadastroEstadosService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadosController {

    @Autowired
    private CadastroEstadosService service;

    @GetMapping
    public List<Estado> listar() {
        return this.service.listarTodos();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long estadoId) {
        try {
            Estado encontrado = service.buscarOuFalhar(estadoId);
            return ResponseEntity.ok(encontrado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
        estado = service.adicionar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        try {
            Estado encontrado = service.buscarOuFalhar(estadoId);
            BeanUtils.copyProperties(estado, encontrado, "id");
            Estado atualizado = service.adicionar(encontrado);
            return ResponseEntity.ok(atualizado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Estado> remove(@PathVariable Long estadoId) {
        try {
            service.remover(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
