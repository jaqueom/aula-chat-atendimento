package br.com.tt.chat.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem {
    private String texto;
    private LocalDateTime dataHoraEnvio;
    private String remetente;
    private String destinatario;
    private LocalDateTime dataHoraLeitura;

    public Mensagem(String texto, String remetente, String destinatario, LocalDateTime dataHoraLeitura) {
        this.texto = texto;
        this.dataHoraEnvio = LocalDateTime.now();
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getDataHoraEnvio() {
        return dataHoraEnvio;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public LocalDateTime getDataHoraLeitura() {
        return dataHoraLeitura;
    }

    public String listar() {
        return "\nRemetente: " + getRemetente()
                + "\nDestinatário: " + getDestinatario()
                + "\nTexto: " + getTexto()
                + "\nData/Hora Envio: " + getDataHoraEnvio()
                + "\nData/Hora Leitura: " + getDataHoraLeitura();
    }

    public String getDescricao() {
        String dataHoraFormatada = dataHoraEnvio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
        return String.format("%s (%s)", texto, dataHoraFormatada);
        /*
        return "Mensagem{" +
                "texto='" + texto + '\'' +
                ", dataHoraEnvio=" + dataHoraEnvio +
                ", remetente='" + remetente + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", dataHoraLeitura=" + dataHoraLeitura +
                '}';

         */
    }
}
