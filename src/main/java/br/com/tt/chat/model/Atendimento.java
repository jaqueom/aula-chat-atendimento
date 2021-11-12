package br.com.tt.chat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Atendimento {
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraResolucao;
    private int posicaoFila;
    private Usuario usuario;
    private Atendente atendente;
    private Mensagem[] mensagens;
    private int protocolo;
    private int nota;

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraResolucao() {
        return dataHoraResolucao;
    }

    public void setDataHoraResolucao(LocalDateTime dataHoraResolucao) {
        this.dataHoraResolucao = dataHoraResolucao;
    }

    public int getPosicaoFila() {
        return posicaoFila;
    }

    public void setPosicaoFila(int posicaoFila) {
        this.posicaoFila = posicaoFila;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public Mensagem[] getMensagens() {
        return mensagens;
    }

    public void setMensagens(Mensagem[] mensagens) {
        this.mensagens = mensagens;
    }

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void inicia(){
        int posicaoFila = getPosicaoFila();
        setPosicaoFila(++posicaoFila);
        setDataHoraInicio(LocalDateTime.now());
    }
    public void finaliza(){
        setPosicaoFila(0);
        setDataHoraResolucao(LocalDateTime.now());
    }
    public int calculaDuracaoEmSegundos(){
        return (getDataHoraResolucao().getSecond() - getDataHoraInicio().getSecond());
    }
}
