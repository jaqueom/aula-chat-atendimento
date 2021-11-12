package br.com.tt.chat;

import br.com.tt.chat.dao.ComunicadorDAO;
import br.com.tt.chat.excecoes.MensagemExcecao;
import br.com.tt.chat.model.Atendente;
import br.com.tt.chat.model.Mensagem;
import br.com.tt.chat.model.Status;
import br.com.tt.chat.model.Usuario;
import br.com.tt.chat.util.Conexao;
import br.com.tt.chat.util.UserInterface;

import java.sql.SQLException;
import java.util.List;

public class ProgramaMensagem {

    public static final int LIMITE_MINIMO_DE_CARACTERES = 5;
    public static final int LIMITE_MAXIMO_DE_CARACTERES = 200;

    private static ComunicadorDAO dao;
    private static Usuario usuarioLogado;
    private static Atendente atendenteEscolhido;

    public static void main(String[] args) throws SQLException {
        Conexao conexao = new Conexao();
        conexao.conecta();
        dao = new ComunicadorDAO(conexao);

        login();
        escolherAtendente();
        adicionarMensagem();
        listarMensagens();
    }

    private static void escolherAtendente() throws SQLException {

        UserInterface.imprimir("Lista de atendentes ATIVOS:");

        List<Atendente> atendentes = dao.listarAtendentesPorStatus(Status.ATIVO);

        for (Atendente atend : atendentes) {
            UserInterface.imprimir("ID:" +  atend.getId() + " - " + atend.getNome());
        }

        int idAtendente = UserInterface.pedirInt("Informe o ID do atendente escolhido para realizar seu atendimento:");
        for (Atendente atend : atendentes) {
            if (atend.getId() == idAtendente){
                UserInterface.imprimir("Atendente escolhido: " + atend.getNome());
                atendenteEscolhido = new Atendente(atend.getId(),atend.getNome());
            }
        }

    }

    public static void login() throws SQLException {
        // Pedir pro usuario seu id
        // Buscar usuários na base (usuarioDao.listar()) e checa se o usuario existe com esse ID.
        // se não existir usuário, damos uma exceção
        // existir, loga

//        List<Usuario> lista;
//        Integer id;
//        lista.stream().filter(u -> u.getId() == id).findFirst()

        //usuarioLogado = ;//usuario que vem do Dao!

        List<Usuario> usuarios = dao.obterListaDeUsuariosResumido();
        String remetente;
        int idUsuario = UserInterface.pedirInt("Informe seu ID de usuário cadastrados na base:");
        for (Usuario user : usuarios) {
            if (idUsuario == user.getId()){
                UserInterface.imprimir("Usuario logado: " + user.getDadosUsuarioResumo());
                usuarioLogado = new Usuario(user.getId(),user.getNome());
            }
        }
    }

    public static void adicionarMensagem() throws SQLException {
        String texto = UserInterface.pedirString("Informe sua mensagem:");
        validaMensagem(texto);
        Mensagem mensagem = new Mensagem(texto, usuarioLogado.getNome(), atendenteEscolhido.getNome(),null);
        dao.salvarMensagem(mensagem);

        UserInterface.imprimir("Mensagem adicionada!");

    }

    public static void validaMensagem(String mensagem) {
        if(mensagem.length() < LIMITE_MINIMO_DE_CARACTERES){
            throw new MensagemExcecao(
                    String.format("A mensagem deve ter ao menos %s caracteres!", LIMITE_MINIMO_DE_CARACTERES));
        }
        if(mensagem.length() > LIMITE_MAXIMO_DE_CARACTERES) {
            throw new MensagemExcecao(
                    String.format("A mensagem deve no máximo %s caracteres!", LIMITE_MAXIMO_DE_CARACTERES));
        }
    }

    public static void listarMensagens() throws SQLException {
        //Listar as mensagens do usuário
        for (Mensagem mensagem : dao.listarMensagens()) {
            UserInterface.imprimir(mensagem.listar());
        }
    }
}
