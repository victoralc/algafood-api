package com.victor.learn.algafoodapi.domain.service;

import com.victor.learn.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Cidade;
import com.victor.learn.algafoodapi.domain.model.Estado;
import com.victor.learn.algafoodapi.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadesService {

    private static final String MSG_CIDADE_NAO_ENCONTRADA = "Nao foi possivel encontrar cidade com id %d";
    private static final String MSG_CIDADE_EM_USO_EXCEPTION = "Cidade de código %d nao pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired 
    private CadastroEstadosService cadastroEstado;

    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }
    
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
    
    public Cidade buscarPorId(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }

    public void remover(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO_EXCEPTION, cidadeId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
        }
    }

}
