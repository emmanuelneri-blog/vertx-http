package br.com.emmanuelneri.vertx.http;

import br.com.emmanuelneri.vertx.http.verticle.HTTPServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(VertxUnitRunner.class)
public class HTTPServerVerticleTest {

    private static final String PORT_CONFIGURATION_KEY = "server.port";

    @Test
    public void rootPathShouldReturnWelcomeMessage(final TestContext context) {
        final Vertx vertx = Vertx.vertx();
        final Async async = context.async();

        final JsonObject configuration = new JsonObject();
        configuration.put(PORT_CONFIGURATION_KEY, 9090);

        vertx.deployVerticle(new HTTPServerVerticle(configuration), handler -> {
            if(handler.failed()) {
                context.fail(handler.cause());
            }

            final WebClient client = WebClient.create(vertx);
            client.get(configuration.getInteger(PORT_CONFIGURATION_KEY), "localhost", "/").send(responseHandler -> {
                context.assertEquals(200, responseHandler.result().statusCode());
                context.assertEquals("Welcome to Vert.x HTTP Server", responseHandler.result().body().toString());
                async.complete();
            });
        });
    }

    @Test
    public void companiesPathShouldReturnWelcomeMessage(final TestContext context) {
        final Vertx vertx = Vertx.vertx();
        final Async async = context.async();

        final JsonObject configuration = new JsonObject();
        configuration.put(PORT_CONFIGURATION_KEY, 9091);

        vertx.deployVerticle(new HTTPServerVerticle(configuration), handler -> {
            if (handler.failed()) {
                context.fail(handler.cause());
            }

            final WebClient client = WebClient.create(vertx);
            client.get(configuration.getInteger(PORT_CONFIGURATION_KEY), "localhost", "/companies").send(responseHandler -> {
                context.assertEquals(200, responseHandler.result().statusCode());

                final JsonObject company1 = new JsonObject();
                company1.put("id", 1L);
                company1.put("name", "Company 1");

                final JsonObject company2 = new JsonObject();
                company2.put("id", 2L);
                company2.put("name", "Company 2");

                final String companiesJson = Json.encode(Arrays.asList(company1, company2));

                context.assertEquals(companiesJson, responseHandler.result().body().toJson().toString());
                async.complete();
            });
        });
    }

}
