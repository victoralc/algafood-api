package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Cidade;
import com.victor.learn.algafoodapi.domain.model.Cidade;
import com.victor.learn.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroCidadesService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }

    public Cidade adicionar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade buscarPorId(Long cidadeId){
        Cidade encontrada = cidadeRepository
                .findById(cidadeId).orElseThrow(() -> new EntidadeNaoEncontradaException("Nao foi possivel encontrar cidade com id " + cidadeId));
        return encontrada;
    }

    public void remover(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d nao pode ser removida, pois está em uso", cidadeId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d nao foi encontrado", cidadeId));
        }
    }
    
}
