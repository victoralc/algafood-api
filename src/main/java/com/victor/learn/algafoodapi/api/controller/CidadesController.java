package com.victor.learn.algafoodapi.api.controller;

import com.victor.learn.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Cidade;
import com.victor.learn.algafoodapi.domain.service.CadastroCidadesService;
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
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    private CadastroCidadesService cadastroCidadesService;

    @GetMapping
    public List<Cidade> listar(){
        return this.cadastroCidadesService.listarTodas();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long cidadeId){
        try {
            Cidade encontrada = cadastroCidadesService.buscarPorId(cidadeId);
            return ResponseEntity.ok(encontrada);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@RequestBody Cidade estado) {
        estado = cadastroCidadesService.adicionar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade estado) {
        try {
            Cidade encontrado = cadastroCidadesService.buscarPorId(cidadeId);
            BeanUtils.copyProperties(estado, encontrado, "id");
            Cidade atualizado = cadastroCidadesService.adicionar(encontrado);
            return ResponseEntity.ok(atualizado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> remove(@PathVariable Long cidadeId) {
        try {
            cadastroCidadesService.remover(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
