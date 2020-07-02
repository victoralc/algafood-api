package com.victor.learn.algafoodapi.api.controller;

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
    public Cidade buscarPorId(@PathVariable Long cidadeId) {
        return cadastroCidadesService.buscarPorId(cidadeId);
    }

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade) {
        cidade = cadastroCidadesService.salvar(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        Cidade encontrada = cadastroCidadesService.buscarPorId(cidadeId);
        BeanUtils.copyProperties(cidade, encontrada, "id");
        return cadastroCidadesService.salvar(encontrada);
    }

    @DeleteMapping("/{cidadeId}")
    public void remove(@PathVariable Long cidadeId) {
        cadastroCidadesService.remover(cidadeId);
    }

}
