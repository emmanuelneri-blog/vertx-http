package br.com.emmanuelneri.vertx.http.model;

public class Company {

    private Long id;
    private String name;

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
