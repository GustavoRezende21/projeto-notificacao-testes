package com.gustavo.notificacao;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestesIntegracao {

    public TestesIntegracao() {
    }

    private WebDriver driver;

    @BeforeAll
    public void setUpClass() {
        // 1. Configura o ChromeDriver de forma automática com o WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Configura opções para o Chrome
        // Nesse caso, inicializar maximizado e desabilita a feature que indica que o Chrome está sendo
        // controlado por automação
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // 2. Inicializa o WebDriver para o Google Chrome
        this.driver = new ChromeDriver(options);
    }

    @AfterAll
    public void tearDownClass() {
        // Fecha o navegador após rodar os testes
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testAutomacaoSwagger() throws Exception {

        // Acessa a página inicial do Swagger
        String urlSwagger = "http://localhost:8080/swagger-ui/index.html";
        this.driver.get(urlSwagger);

        // Aguarda o carregamento do HTML
        Thread.sleep(3000);

        // Clica no bloco do endpoint GET (/api/notificacoes) para expandi-lo
        // Procura estritamente pela PRIMEIRA caixinha do metodo GET que aparecer na tela
        // Nesse caso, o GetById
        WebElement getEndpoint = this.driver.findElement(
                By.xpath("(//div[contains(@class, 'opblock-summary-get')])[1]")
        );
        getEndpoint.click();
        Thread.sleep(1000);

        // Encontra e clica no botão 'Try it out'
        WebElement btnTryItOut = this.driver.findElement(
                By.xpath("//button[contains(@class, 'try-out__btn')]")
        );
        btnTryItOut.click();
        Thread.sleep(1000);

        // Encontra o campo de ID e digita o ID necessário
        WebElement inputId = this.driver.findElement(
                By.xpath("//input[@placeholder='id']")
        );

        // Rola a página para baixo para centralizar no campo de visão necessário
        ((org.openqa.selenium.JavascriptExecutor) this.driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", inputId
        );
        Thread.sleep(500);

        // Limpa o campo por garantia
        inputId.clear();
        // Digita o ID
        inputId.sendKeys("1");
        Thread.sleep(1000);

        // Encontra e clica no botão 'Execute' para executar a ação
        WebElement btnExecute = this.driver.findElement(
                By.xpath("//button[contains(@class, 'execute')]")
        );

        // Garante que a página desça até o botão 'Execute' antes de clicar
        ((org.openqa.selenium.JavascriptExecutor) this.driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", btnExecute
        );
        Thread.sleep(500);

        // Clica no botão de Executar a ação
        btnExecute.click();

        Thread.sleep(2000);

        // Faz a asserção: valida se o código de resposta esperado (HTTP 200) está visível na página
        String pageSource = this.driver.getPageSource();
        assertTrue(pageSource.contains("200"));
    }

}