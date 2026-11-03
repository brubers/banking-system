package com.bruna.model;

public class Client {
    private Integer id;
    private final String name;
    private final String cpf; //final cuz no need to change it later

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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
