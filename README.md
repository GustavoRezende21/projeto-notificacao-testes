# Projeto de Notificações - Testes de Software

Projeto desenvolvido em **Java 21** utilizando **Spring Boot (3.5.14)**. O objetivo principal da aplicação é a demonstração e implementação de diferentes níveis e tipos de Testes de Software.
A Regra de Negócio gira em torno do gerenciamento e disparo de notificações de rastreamento de encomendas, simulando a integração com um Gateway Externo (Mock) de envios de SMS.

---

## Tecnologias Utilizadas

### Aplicação Backend
* **Java 21**
* **Spring Boot** (Spring Data JPA, Spring Web, Spring Boot DevTools)
* **Banco de Dados H2** (Banco de Dados em Memória para a execução dos Testes)
* **Springdoc OpenAPI (Swagger UI)** (Documentação de API)

### Dependências de Testes
* **JUnit Jupiter** - Framework para a Base dos Testes
* **Mockito** - Criação de *Mocks* de Teste para Isolamento de Componentes
* **RestAssured** - Validação e Testes Automatizados de Endpoints HTTP (API REST)
* **Hamcrest** - Biblioteca de matchers declarativos para asserções mais legíveis
* **Selenium WebDriver e WebDriverManager** - Automação de interações em Navegadores Web (Utilizando Driver do Google Chrome)

---

## Estrutura de Testes Implementados

### 1. Testes de Unidade
* O que faz: Testa a lógica de uma classe isolada (NotificacaoService.java)
* Como funciona: Usa Mocks para simular as respostas e focar só nas Regras de Negócio
### 2. Testes de API
* O que faz: Testa as rotas e endpoints HTTP (/notificacoes)
* Como funciona: Usa o RestAssured para enviar requisições reais e conferir se o status HTTP e o JSON retornados estão corretos
### 3. Testes de Integração
* O que faz: Testa o fluxo simulando a interação do usuário
* Como funciona: Usa o Selenium para abrir o Chrome automaticamente, clicar nos botões do Swagger UI e testar a tela visualmente

## Requisitos do Sistema

* Java 21 instalado e configurado na sua máquina
* Google Chrome instalado (necessário para a execução do teste do Selenium)
* IntelliJ Community (Recomendação de IDE para rodar o Projeto)
