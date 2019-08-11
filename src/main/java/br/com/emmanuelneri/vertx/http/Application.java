package br.com.emmanuelneri.vertx.http;

import br.com.emmanuelneri.vertx.http.verticle.HTTPServerVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Application {

    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();

        final ConfigRetriever configRetriever = createConfigurationRetriever(vertx);

        configRetriever.getConfig(handler -> {
            final JsonObject configuration = handler.result();

            vertx.deployVerticle(new HTTPServerVerticle(configuration));
        });
    }

    private static ConfigRetriever createConfigurationRetriever(final Vertx vertx) {
        final ConfigStoreOptions store = new ConfigStoreOptions()
                .setType("file")
                .setFormat("properties")
                .setConfig(new JsonObject().put("path", "conf/config.properties"));

        return ConfigRetriever.create(vertx,
                new ConfigRetrieverOptions().addStore(store));
    }

}
