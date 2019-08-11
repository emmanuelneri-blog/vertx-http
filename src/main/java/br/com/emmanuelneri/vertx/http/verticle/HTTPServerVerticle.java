package br.com.emmanuelneri.vertx.http.verticle;

import br.com.emmanuelneri.vertx.http.web.CompanyRouting;
import br.com.emmanuelneri.vertx.http.web.DefaultRouting;
import br.com.emmanuelneri.vertx.http.web.FailureHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class HTTPServerVerticle extends AbstractVerticle {

    private final Logger LOGGER = LoggerFactory.getLogger(HTTPServerVerticle.class);

    private final JsonObject configuration;

    public HTTPServerVerticle(final JsonObject configuration) {
        this.configuration = configuration;
    }

    @Override
    public void start(final Future<Void> startFuture) {
        final HttpServer httpServer = vertx.createHttpServer();

        final Router router =  Router.router(vertx);
        final FailureHandler failureHandler = new FailureHandler();
        router.get("/").handler(DefaultRouting.welcome()).failureHandler(failureHandler);
        router.get("/companies").handler(CompanyRouting.findAll()).failureHandler(failureHandler);

        final Integer port = getServerPort();
        httpServer.requestHandler(router)
                .listen(port, listenHandler -> {
                    if(listenHandler.failed()) {
                        LOGGER.error("HTTP Server error", listenHandler.cause());
                        return;
                    }
                    LOGGER.info("HTTP Server started on port " + port);
                });
    }

    private Integer getServerPort() {
        return configuration.getInteger("server.port");
    }
}
