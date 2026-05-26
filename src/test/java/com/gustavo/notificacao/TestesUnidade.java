package com.gustavo.notificacao;

import com.gustavo.notificacao.entity.Notificacao;
import com.gustavo.notificacao.mock.GatewayEnvioClient;
import com.gustavo.notificacao.repository.NotificacaoRepository;
import com.gustavo.notificacao.service.NotificacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TestesUnidade {


    // Mocka a Notificação e Gateway de Envio
    @Mock
    private NotificacaoRepository repository;

    @Mock
    private GatewayEnvioClient gatewayClient;

    // Injeta uma Notificação
    @InjectMocks
    private NotificacaoService service;

    @Test
    void testSalvarComStatusEnviadoQuandoGatewayRetornarTrue() {

        // Cria uma Notificação
        Notificacao notificacao = new Notificacao(
                "BR123456789BR",
                "Sua encomenda foi confirmada",
                "PENDENTE");

        // Mockando o comportamento do Gateway Externo para retornar um TRUE
        Mockito.when(gatewayClient.enviarSmsExterno("Sua encomenda foi confirmada")).thenReturn(true);

        // Mockando o comportamento da Repository para retornar a própria entidade simulando o salvamento
        Mockito.when(repository.save(any(Notificacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Cria a notificação
        Notificacao resultado = service.criarNotificacao(notificacao);

        assertNotNull(resultado);
        assertEquals("ENVIADO", resultado.getStatusEnvio());

        // Verifica se o metodo do gateway foi chamado exatamente 1 vez com a mensagem correta
        Mockito.verify(gatewayClient, Mockito.times(1)).enviarSmsExterno("Sua encomenda foi confirmada");

        // Verifica se a Repository salvou as alterações
        Mockito.verify(repository, Mockito.times(1)).save(notificacao);
    }

    @Test
    void testSalvarComStatusFalseQuandoGatewayRetornarFalse() {
        Notificacao notificacao = new Notificacao(
                "BR987654321BR",
                "Sua encomenda saiu para entrega",
                "PENDENTE");

        Mockito.when(gatewayClient.enviarSmsExterno("Sua encomenda saiu para entrega")).thenReturn(false);

        Mockito.when(repository.save(any(Notificacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Notificacao resultado = service.criarNotificacao(notificacao);

        assertNotNull(resultado);
        assertEquals("FALHA", resultado.getStatusEnvio());

        Mockito.verify(gatewayClient, Mockito.times(1)).enviarSmsExterno("Sua encomenda saiu para entrega");
        Mockito.verify(repository, Mockito.times(1)).save(notificacao);
    }

    @Test
    void testListarTodasAsNotificacoes() {
        List<Notificacao> listaNotificacoesMock = List.of(
                new Notificacao("BR123456789BR", "Pedido enviado", "ENVIADO"),
                new Notificacao("BR123454321BR", "Pedido pendente", "PENDENTE"),
                new Notificacao("BR987654321BR", "Falha ao Processar o Pedido", "FALHA")
        );
        Mockito.when(repository.findAll()).thenReturn(listaNotificacoesMock);

        List<Notificacao> resultado = service.listar();

        assertEquals(3, resultado.size());
        assertEquals("Pedido enviado", resultado.get(0).getMensagem());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}