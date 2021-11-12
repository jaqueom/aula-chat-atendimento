package br.com.tt.chat;

import br.com.tt.chat.dao.ComunicadorDAO;
import br.com.tt.chat.model.Perfil;
import br.com.tt.chat.model.Sexo;
import br.com.tt.chat.model.Usuario;
import br.com.tt.chat.util.Conexao;
import br.com.tt.chat.util.UserInterface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProgramaUsuario {
    public static ComunicadorDAO dao;

    public static void main(String[] args) throws SQLException {
        Usuario usuario = new Usuario();
        dao = new ComunicadorDAO(new Conexao());

        int escolhaUsuarioMenu;
        do {
            escolhaUsuarioMenu = UserInterface.pedirInt("Digite uma das alternativas abaixo:"
                    + "\n 1 - Adicionar um novo Usuario;"
                    + "\n 2 - Listar usuários adicionados;"
                    + "\n 3 - Sair.");
            switch (escolhaUsuarioMenu) {
                case 1:
                    adicionarNovoUsuario(usuario);
                    break;
                case 2:
                    listarUsuarios();
                    break;
                default:
                    break;
            }

        } while (escolhaUsuarioMenu != 3);

    }

    private static void listarUsuarios() throws SQLException {
        List<Usuario> usuarios = dao.listarUsuarios();

        UserInterface.imprimir("Lista de usuários cadastrados na base:");
        for (Usuario user : usuarios) {
            UserInterface.imprimir(user.getDadosUsuario());
        }

    }


    private static void adicionarNovoUsuario(Usuario usuario) throws SQLException {
        String nome = UserInterface.pedirString("Informe o nome:");
        Perfil perfil = Perfil.valueOf(UserInterface.pedirString("Informe o perfil, digitando uma das opções abaixo: \n"
                + "    PRIORITARIO, NORMAL").toUpperCase());

        String dataNascimento = UserInterface.pedirString("Digite sua data de nascimento, nesse formato: DD/MM/YYYY, por favor:");
        String cpf = UserInterface.pedirString("Informe o cpf:");
        String telefone = UserInterface.pedirString("Informe o telefone:");
        String email = UserInterface.pedirString("Informe o e-mail:");
        String endereco = UserInterface.pedirString("Informe o endereco:");
        Sexo sexo = Sexo.valueOf(UserInterface.pedirString("Informe o sexo, digitando uma das opções abaixo: \n"
                + "    FEMININO, MASCULINO, NAO_BINARIO, NAO_INFORMADO").toUpperCase());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimentoFormatada = LocalDate.parse(dataNascimento, formatter);

        usuario = new Usuario(0, nome, sexo, perfil, nascimentoFormatada, cpf, telefone, email, endereco);
        dao.salvarUsuario(usuario);

        // EXTRA
        int idade = usuario.obterIdadePorDataNascimento(dataNascimento);
        UserInterface.imprimir("Sua idade é: " + idade);
    }

}