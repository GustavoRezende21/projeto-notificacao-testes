package com.gustavo.notificacao;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class TestesApi {

    private static final String BASE_URL = "http://localhost:8080";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testListarTodos() {
        given()
                .log().all()
                .when()
                .get("/notificacoes")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].codigoRastreio", notNullValue())
                .body("[0].mensagem", notNullValue())
                .body("[0].statusEnvio", notNullValue());
    }

    @Test
    public void testBuscarPorId() {
        int id = 1;

        given()
                .pathParam("id", id)
                .log().all()
                .when()
                .get("/notificacoes/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    @Test
    void testCriarNotificacao() {
        String corpoRequisicao = """
            {
              "codigoRastreio": "BR00001",
              "mensagem": "Sua encomenda foi postada",
              "statusEnvio": "PENDENTE"
            }
            """;

        given()
                .contentType("application/json")
                .body(corpoRequisicao)
                .log().all()
                .when()
                .post("/notificacoes")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    void testeEditarNotificacao() {
        int id = 1;

        String corpoRequisicao = """
            {
              "codigoRastreio": "BR00001",
              "mensagem": "Sua encomenda está sendo enviada",
              "statusEnvio": "ENVIADO"
            }
            """;

        given()
                .pathParam("id", id)
                .contentType("application/json")
                .body(corpoRequisicao)
                .log().all()
                .when()
                .put("/notificacoes/{id}")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void testDeletar() {
        int id = 1;

        given()
                .pathParam("id", id)
                .log().all()
                .when()
                .delete("/notificacoes/{id}")
                .then()
                .log().all()
                .statusCode(200);
    }

}