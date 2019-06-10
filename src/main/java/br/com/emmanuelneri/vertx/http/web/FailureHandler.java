package br.com.emmanuelneri.vertx.http.web;

import br.com.emmanuelneri.vertx.http.verticle.HTTPServerVerticle;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class FailureHandler implements Handler<RoutingContext> {

    private final Logger LOGGER = LoggerFactory.getLogger(HTTPServerVerticle.class);

    @Override
    public void handle(final RoutingContext routingContext) {
        LOGGER.error("Router error", routingContext.failure());
        routingContext.response()
                .setStatusCode(routingContext.statusCode())
                .end();
    }
}
