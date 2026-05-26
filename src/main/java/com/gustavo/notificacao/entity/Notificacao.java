package com.gustavo.notificacao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoRastreio;
    private String mensagem;

    // Como é um projeto voltado para testes, vamos deixar aberto a inserção de qualquer String
    // Mas o melhor seria tornar statusEnvio como um Enum com status já padronizados
    private String statusEnvio; // PENDENTE, ENVIADO, FALHA

    public Notificacao() {
    }

    public Notificacao(String codigoRastreio, String mensagem, String statusEnvio) {
        this.codigoRastreio = codigoRastreio;
        this.mensagem = mensagem;
        this.statusEnvio = statusEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(String statusEnvio) {
        this.statusEnvio = statusEnvio;
    }
}