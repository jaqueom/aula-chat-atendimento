package br.com.tt.chat.model;

public class Atendente {
    private int id;
    private String nome;
    private String senioridade;
    private Nivel nivel; // N1, N2 E N3
    private Status status; //ATIVO E INATIVO

    public Atendente(int id, String nome, Nivel nivel, String senioridade) {
        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.senioridade = senioridade;
        this.status = Status.ATIVO;
    }
    public Atendente() {
    }
    public Atendente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getSenioridade() {
        return senioridade;
    }

    public Nivel getNivel() { return nivel;}

    public Status getStatus() {
        return status;
    }

    public int getId() { return id;}

}
