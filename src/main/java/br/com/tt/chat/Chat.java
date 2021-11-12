package br.com.tt.chat;

import br.com.tt.chat.dao.ComunicadorDAO;
import br.com.tt.chat.excecoes.Excecoes;
import br.com.tt.chat.excecoes.MensagemExcecao;
import br.com.tt.chat.model.*;
import br.com.tt.chat.util.Conexao;
import br.com.tt.chat.util.UserInterface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Chat {
    public static ComunicadorDAO dao;

    public static void main(String[] args) throws SQLException {
        menuDeAtendimento();
    }

    private static void menuDeAtendimento() throws SQLException {
        int escolhaUsuarioMenu;
        Usuario usuario;
        Atendimento novoAtendimento = new Atendimento();
        Atendente novoAtendente = new Atendente();
        dao = new ComunicadorDAO(new Conexao());

        int posicaoFila = 0;
        do {
            System.out.println("Digite uma das alternativas abaixo:"
                    + "\n 0 - Entrar com um novo atendente;"
                    + "\n 1 - Iniciar novo atendimento;"
                    + "\n 2 - Enviar nova mensagem no atendimento em andamento;"
                    + "\n 3 - Finalizar atendimento em andamento;"
                    + "\n 4 - Listar atendentes ativos;"
                    + "\n 5 - Sair.");
            escolhaUsuarioMenu = new Scanner(System.in).nextInt();
            switch (escolhaUsuarioMenu) {
                case 0:
                    try {
                        adicionarAtendente(novoAtendente);
                    }catch (Excecoes e){
                        System.err.println("Ops! Algo deu errado " + e.getMessage());
                    }
                    break;
                case 1:
                    //iniciarNovoAtendimento(posicaoFila, novoAtendimento, novoAtendente);
                    break;
                case 2:
                    adicionarMensagem();
                    break;
                case 3:
                 //   encerrarAtendimento(novoAtendimento, mensagem, novoAtendente);
                    escolhaUsuarioMenu = 4;
                    break;
                case 4:
                    listarAtendentesAtivos();
                    break;
                case 5:
                    System.out.println("Tchau!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (escolhaUsuarioMenu != 5);
    }

    private static void adicionarMensagem() throws SQLException {



    }

    private static void adicionarAtendente(Atendente novoAtendente) throws SQLException {
        String nome = UserInterface.pedirTexto("Informe o nome do atendente:").orElseThrow(() -> new Excecoes("Nome do atendente não pode ser vazio"));
        Nivel nivel = UserInterface.pedirTexto("Informe o Nível do atendente: N1, N2 ou N3")
                .map(s -> Nivel.valueOf(s.toUpperCase()))
                .orElse(Nivel.N1);
        String senioridade = UserInterface.pedirTexto("Informe a Senioridade do atendente: JUNIOR, PLENO ou SENIOR").orElseThrow(() -> new Excecoes("Senioridade do atendente não pode ser vazio"));
        novoAtendente = new Atendente(0,nome, nivel, senioridade);
        System.out.println(novoAtendente.getNome());
        System.out.println(novoAtendente.getStatus());

        dao.salvarAtendente(novoAtendente);
    }

    private static void listarAtendentesAtivos() throws SQLException {
        List<Atendente> atendentes = dao.listarAtendentesPorStatus(Status.ATIVO);

        for (Atendente atend : atendentes) {
            UserInterface.imprimir(atend.getNome() + " - " + atend.getSenioridade() + " - " + atend.getNivel().name());
        }

    }

    private static void encerrarAtendimento(Atendimento novoAtendimento, Mensagem mensagem, Atendente atendente) {
        System.out.println("Vamos encerrar seu atendimento!");

        System.out.println("Antes, informe sua nota para o atendimento realizado, por favor:");
        novoAtendimento.setNota(new Scanner(System.in).nextInt());

        novoAtendimento.finaliza();

        System.out.println("OBRIGADA! SEGUE RESUMO DO SEU ATENDIMENTO:");
        System.out.println(atendente.getNome());
        System.out.println(atendente.getStatus());
        System.out.println("Remetente: " + mensagem.getRemetente());
        System.out.println("Nota: " + novoAtendimento.getNota());
        System.out.println("Data/Hora início do atendimento: " + novoAtendimento.getDataHoraInicio());
        System.out.println("Data/Hora fim do atendimento: " + novoAtendimento.getDataHoraResolucao());
        System.out.println("Tempo de atendimento (em segundos): " + novoAtendimento.calculaDuracaoEmSegundos());
        System.out.println("Mensagem: " + mensagem.getTexto());
    }

    /*
    private static void iniciarNovoAtendimento(int posicaoFila, Atendimento novoAtendimento, Mensagem mensagem, Atendente atendente) throws SQLException {
        posicaoFila = novoAtendimento.getPosicaoFila();

        if (posicaoFila < 1) {
            if (atendente.getNome() != null) {
                System.out.println("Legal! Chegou sua vez :)");
                System.out.println("Seu atendente é: " + atendente.getNome());

                novoAtendimento.inicia();

                String nomeUsuario = UserInterface.pedirTexto("Informe seu NOME, por favor:")
                        .filter(texto -> texto.length() > 100)
                        .orElseThrow(() -> new RuntimeException("Nome do usuário não pode ser vazio, nem maior que 100"));

                String cpfUsuario = UserInterface.pedirTexto("Informe seu CPF, por favor:").orElseThrow(() -> new RuntimeException("CPF do usuário não pode ser vazio"));
                String dataNascimento = UserInterface.pedirTexto("Digite sua data de nascimento, nesse formato: DD/MM/YYYY, por favor:").orElseThrow(() -> new RuntimeException("Data de nascimento não pode ser vazio"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNascimentoFormatada = LocalDate.parse(dataNascimento,formatter);

                usuario = new Usuario(nomeUsuario, cpfUsuario, dataNascimentoFormatada);

                int idade = usuario.obterIdadePorDataNascimento(dataNascimento);
                System.out.printf("Sua idade é: %s\n", idade);

                System.out.println("Informe sua mensagem:");
                //mensagem.enviaMensagem(new Scanner(System.in).nextLine());

                //mensagem.setRemetente(usuario.getNome());

                dao.salvarUsuario(usuario);

            } else {
                System.out.println("Ops! Nenhum atendente entrou no sistema para te atender ainda :(");
            }
        } else {
            System.out.println("Sua posição na fila é " + posicaoFila + " Aguarde!");
        }
    }

     */
}
