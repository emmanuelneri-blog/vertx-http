package br.com.emmanuelneri.vertx.http.web;

import br.com.emmanuelneri.vertx.http.model.Company;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class CompanyRouting {

    private CompanyRouting() {}

    public static Handler<RoutingContext> findAll() {
        return routingContext -> {
            final List<Company> companies = new ArrayList<>();
            companies.add(new Company(new Random().nextLong(), "Company 1"));
            companies.add(new Company(new Random().nextLong(), "Company 2"));

            routingContext.response().end(Json.encode(companies));
        };
    }

}
