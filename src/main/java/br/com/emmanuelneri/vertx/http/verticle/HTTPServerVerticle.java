package br.com.emmanuelneri.vertx.http.verticle;

import br.com.emmanuelneri.vertx.http.web.CompanyRouting;
import br.com.emmanuelneri.vertx.http.web.DefaultRouting;
import br.com.emmanuelneri.vertx.http.web.FailureHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;


public class HTTPServerVerticle extends AbstractVerticle {

    private final Logger LOGGER = LoggerFactory.getLogger(HTTPServerVerticle.class);

    @Override
    public void start(final Future<Void> startFuture) {
        final HttpServer httpServer = vertx.createHttpServer();

        final Router router =  Router.router(vertx);
        final FailureHandler failureHandler = new FailureHandler();
        router.get("/").handler(DefaultRouting.welcome()).failureHandler(failureHandler);
        router.get("/companies").handler(CompanyRouting.findAll()).failureHandler(failureHandler);

        httpServer.requestHandler(router)
                .listen(8080,  listenHandler -> {
                    if (listenHandler.succeeded()) {
                        LOGGER.info("HTTP Server started on port: 8080");
                        startFuture.complete();
                    }
                    if(listenHandler.failed()) {
                        startFuture.fail(listenHandler.cause());
                    }
                });

    }
}
