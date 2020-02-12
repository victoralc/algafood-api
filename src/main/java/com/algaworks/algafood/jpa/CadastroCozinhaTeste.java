package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class CadastroCozinhaTeste {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        final CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
        Cozinha brasileira = new Cozinha();
        brasileira.setNome("Braisleira");

        Cozinha japonesa = new Cozinha();
        japonesa.setNome("Japonesa");

        cadastroCozinha.adicionar(brasileira);
        cadastroCozinha.adicionar(japonesa);
    }
}
