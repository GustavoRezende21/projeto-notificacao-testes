package com.gustavo.notificacao.service;

import com.gustavo.notificacao.entity.Notificacao;
import com.gustavo.notificacao.mock.GatewayEnvioClient;
import com.gustavo.notificacao.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repository;
    private final GatewayEnvioClient gatewayClient;

    public NotificacaoService(NotificacaoRepository repository, GatewayEnvioClient gatewayClient) {
        this.repository = repository;
        this.gatewayClient = gatewayClient;
    }

    public Notificacao criarNotificacao(Notificacao notificacao) {
        // Regra de Negócio simples para testar o Mock Object
        boolean enviado = gatewayClient.enviarSmsExterno(notificacao.getMensagem());

        if (enviado) {
            notificacao.setStatusEnvio("ENVIADO");
        } else {
            notificacao.setStatusEnvio("FALHA");
        }
        return repository.save(notificacao);
    }

    public Optional<Notificacao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Optional<Notificacao> atualizar(Long id, Notificacao notificacaoAlterada) {
        return repository.findById(id).map(notificacaoExistente -> {

            // Altera os dados da notificação
            notificacaoExistente.setCodigoRastreio(notificacaoAlterada.getCodigoRastreio());
            notificacaoExistente.setMensagem(notificacaoAlterada.getMensagem());

            // Quando alterada, reprocessamos para o gateway externo
            boolean enviado = gatewayClient.enviarSmsExterno(notificacaoAlterada.getMensagem());
            notificacaoExistente.setStatusEnvio(enviado ? "ENVIADO" : "FALHA");

            return repository.save(notificacaoExistente);
        });
    }

    public List<Notificacao> listar() {
        return repository.findAll();
    }
}