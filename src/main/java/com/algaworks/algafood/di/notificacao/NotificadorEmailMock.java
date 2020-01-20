package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@TipoDeNotificador(NivelDeUrgencia.URGENTE)
@Component
public class NotificadorEmailMock implements Notificador {

    public NotificadorEmailMock() {
        System.out.println("Notificador Mock");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificador MOCK. %s através do email %s: %s \n", cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
