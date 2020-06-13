package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Estado;
import com.victor.learn.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadosService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }
    
    public Estado adicionar(Estado estado) {
        return estadoRepository.save(estado);
    }
    
    public Estado buscarPorId(Long estadoId){
        Estado encontrado = estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Nao foi possivel encontrar estado com id " + estadoId));
        return encontrado;
    }
    
    public void remover(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado de código %d nao pode ser removido, pois está em uso", estadoId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Estado de código %d nao foi encontrado", estadoId));
        }
    }
    
}
