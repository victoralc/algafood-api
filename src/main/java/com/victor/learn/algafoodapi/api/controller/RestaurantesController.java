package com.victor.learn.algafoodapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.learn.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.victor.learn.algafoodapi.domain.model.Restaurante;
import com.victor.learn.algafoodapi.domain.repository.RestauranteRepository;
import com.victor.learn.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantesController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return restaurantes;
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long restauranteId) {
        Optional<Restaurante> encontrado = restauranteRepository.findById(restauranteId);
        if (encontrado.isPresent()) {
            return ResponseEntity.ok(encontrado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
            if (restauranteAtual.isPresent()) {
                BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
                return ResponseEntity.ok(cadastroRestauranteService.salvar(restauranteAtual.get()));
            }
            
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
        if (restauranteAtual.isPresent()) {
            merge(campos, restauranteAtual.get());
            return atualizar(restauranteId, restauranteAtual.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/entre-taxa-frete/{taxaInicial}/{taxaFinal}")
    public ResponseEntity<?> buscarEntreTaxaFrete(@PathVariable BigDecimal taxaInicial, @PathVariable BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
        if (!restaurantes.isEmpty()) {
            return ResponseEntity.ok(restaurantes);
        }
        return ResponseEntity.notFound().build();
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
        
        campos.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });

    }
}
