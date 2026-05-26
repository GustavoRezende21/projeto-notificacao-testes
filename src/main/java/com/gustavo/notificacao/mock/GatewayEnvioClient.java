package com.gustavo.notificacao.mock;

import org.springframework.stereotype.Component;

@Component
public class GatewayEnvioClient {

    // Mock Object
    // Esse componente faria uma requisição HTTP para uma API externa
    // Para os testes, vamos forçar o comportamento desse metodo para verificar seu funcionamento
    public boolean enviarSmsExterno(String mensagem) {
        // Simula o envio bem sucedido na "requisição"
        return true;
    }
}