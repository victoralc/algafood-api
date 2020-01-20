package com.algaworks.algafood.di.controller;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    private AtivacaoClienteService ativacaoClienteService;

    @GetMapping("/ativacaoCliente")
    @ResponseBody
    public String ativarCliente(){
        ativacaoClienteService.ativar(new Cliente("Carlos Victor", "victoralcantara432@gmail.com", "34740419"));
        return "Ativando Cliente";
    }

}
