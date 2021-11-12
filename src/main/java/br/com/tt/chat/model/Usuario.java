package br.com.tt.chat.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private int id;
    private String nome;
    private Sexo sexo;
    private Perfil perfil;
    private LocalDate nascimento; // Date é antigo e Calendar é complicado de usar // LocalDate é muito melhor de trabalhar, mais utilizado.
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;

    public Usuario(int id, String nome, Sexo sexo, Perfil perfil, LocalDate nascimento, String cpf, String telefone, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.perfil = perfil;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }
    public Usuario(String nome, String cpf, LocalDate nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.cpf = cpf;
    }
    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public Usuario(){
    }
    public String getDadosUsuario(){
        return "Nome: " + getNome()
                + " - Sexo: " + getSexo()
                + " - Perfil: " + getPerfil()
                + " - Data de nascimento: " + getNascimento()
                + " - Cpf: " + getCpf()
                + " - Telefone: " + getTelefone()
                + " - E-mail: " + getEmail()
                + " - Endereco: " + getEndereco();
    }
    public String getDadosUsuarioResumo(){
        return "ID: " + getId()
                + " - Nome: " + getNome();
    }

    public int getId() { return id; }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public int obterIdadePorDataNascimento(String nascimento){
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimentoFormatada = LocalDate.parse(nascimento,formatter);

        int idade = Period.between(nascimentoFormatada, today).getYears();

        return idade;
    }
}
