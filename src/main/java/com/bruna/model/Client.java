package com.bruna.model;

public class Client {
    private Integer id;
    private String name;
    private String cpf;

    public Client(String name, String cpf) { //id is going to be created on mysql
        this.name = name;
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Integer getId() {
        return id;
    }
}
