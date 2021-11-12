package br.com.tt.chat.excecoes;

public class MensagemExcecao extends RuntimeException{
    public MensagemExcecao (String mensagem){
        super(mensagem);
    }
}
