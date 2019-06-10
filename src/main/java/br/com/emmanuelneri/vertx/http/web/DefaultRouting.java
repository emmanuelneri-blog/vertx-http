package br.com.emmanuelneri.vertx.http.web;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public final class DefaultRouting {

    private DefaultRouting() {}

    public static Handler<RoutingContext> welcome() {
        return routingContext -> routingContext
                .response().end("Welcome to Vert.x HTTP Server");
    }
}
