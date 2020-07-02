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

    private static final String MSG_ESTADO_EM_USO =
            "Estado de código %d não pode ser removido, pois está em uso";

    private static final String MSG_ESTADO_NAO_ENCONTRADO =
            "Não existe um cadastro de estado com código %d";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
    }

    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    public Estado adicionar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remover(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }

}
