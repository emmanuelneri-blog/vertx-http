package br.com.emmanuelneri.vertx.http;

import br.com.emmanuelneri.vertx.http.verticle.HTTPServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class HTTPServerVerticleTest {

    @Test
    public void rootPathShouldReturnWelcomeMessage(final TestContext context) {
        final Vertx vertx = Vertx.vertx();
        final Async async = context.async();

        vertx.deployVerticle(new HTTPServerVerticle(), handler -> {
            if(handler.failed()) {
                context.fail("deploy error");
            }

            final WebClient client = WebClient.create(vertx);
            client.get(8080, "localhost", "/").send(responseHandler -> {
                context.assertEquals(200, responseHandler.result().statusCode());
                context.assertEquals("Welcome to Vert.x HTTP Server", responseHandler.result().body().toString());
                async.complete();
            });
        });

    }

}
