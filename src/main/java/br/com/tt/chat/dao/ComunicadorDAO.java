package br.com.tt.chat.dao;

import br.com.tt.chat.model.*;
import br.com.tt.chat.util.Conexao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ComunicadorDAO {

    Conexao conexao;

    public ComunicadorDAO(Conexao conexao) throws SQLException {
        this.conexao = conexao;
        conexao.conecta();
    }

    public List<Atendente> listarAtendentesPorStatus(Status status) throws SQLException {

        String query = "SELECT id, nome, nivel, senioridade FROM atendente WHERE status = ?";
        PreparedStatement ps = conexao.getConexao().prepareStatement(query);
        ps.setString(1, status.name());
        ResultSet resultados = ps.executeQuery();

        List<Atendente> atendentes = new LinkedList<>();

        //Verifica se há primeira tupla
        while(resultados.next()){
            //Obtem um campo da primeira tupla
            int id = resultados.getInt("id");
            String nome = resultados.getString("nome").toUpperCase();
            String nivel = resultados.getString("nivel").toUpperCase();
            Nivel nivelEnum = Nivel.valueOf(nivel.toUpperCase());
            String senioridade = resultados.getString("senioridade").toUpperCase();
            Atendente atendente = new Atendente(id,nome,nivelEnum,senioridade);
            atendentes.add(atendente);
        }
        return atendentes;
    }

    public void salvarAtendente(Atendente atendente) throws SQLException, SQLException {

        String sql = "insert into atendente (nome, status, nivel, senioridade) values (?,?,?,?)";
        PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
        ps.setString(1, atendente.getNome());
        ps.setString(2, atendente.getStatus().name());
        ps.setString(3, atendente.getNivel().name());
        ps.setString(4, atendente.getSenioridade());
        ps.execute();
    }

    public void salvarUsuario(Usuario usuario) throws SQLException, SQLException {

        String sql = "insert into usuario (nome, nascimento, cpf, telefone, email, endereco, sexo, perfil)" +
                " values (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conexao.getConexao().prepareStatement(sql);

        ps.setString(1, usuario.getNome());
        ps.setDate(2, Date.valueOf(usuario.getNascimento()));
        ps.setString(3, usuario.getCpf());
        ps.setString(4, usuario.getTelefone());
        ps.setString(5, usuario.getEmail());
        ps.setString(6, usuario.getEndereco());
        ps.setString(7, usuario.getSexo().name());
        ps.setString(8, usuario.getPerfil().name());
        ps.execute();

    }

    public List<Usuario> listarUsuarios() throws SQLException {
        String query = "SELECT id, nome, nascimento, cpf, telefone, email, endereco, sexo, perfil FROM usuario";
        PreparedStatement ps = conexao.getConexao().prepareStatement(query);
        ResultSet resultados = ps.executeQuery();

        List<Usuario> usuarios = new LinkedList<>();

        //Verifica se há primeira tupla
        while(resultados.next()){
            //Obtem um campo da primeira tupla
            int id = resultados.getInt("id");
            String nome = resultados.getString("nome").toUpperCase();
            LocalDate nascimento = resultados.getDate("nascimento").toLocalDate();
            String cpf = resultados.getString("cpf");
            String telefone = resultados.getString("telefone");
            String email = resultados.getString("email");
            String endereco = resultados.getString("endereco");
            /*
            OPÇÃO 1
            Sexo sexo = Sexo.NAO_INFORMADO;
            if (resultados.getString("sexo") != null) {
                sexo = Sexo.valueOf(resultados.getString("sexo").toUpperCase());
            }
            */
            //OPÇÃO 2:
            Sexo sexo = Optional.ofNullable(resultados.getString("sexo"))
                    .map(sexoString -> Sexo.valueOf(sexoString))
                    .orElse(Sexo.NAO_INFORMADO);

            /*
            OPÇÃO 1
            Perfil perfil = Perfil.NORMAL;
            if (resultados.getString("perfil") != null) {
                perfil = Perfil.valueOf(resultados.getString("perfil").toUpperCase());
            }
            */
            //OPÇÃO 2:
            Perfil perfil = Optional.ofNullable(resultados.getString("perfil"))
                    .map(perfilString -> Perfil.valueOf(perfilString))
                    .orElse(Perfil.NORMAL);

            Usuario usuario = new Usuario(id, nome, sexo, perfil, nascimento, cpf, telefone, email, endereco);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public List<Usuario> obterListaDeUsuariosResumido() throws SQLException {
        String query = "SELECT id, nome FROM usuario";
        PreparedStatement ps = conexao.getConexao().prepareStatement(query);
        ResultSet resultados = ps.executeQuery();

        List<Usuario> usuarios = new LinkedList<>();

        //Verifica se há primeira tupla
        while(resultados.next()){
            //Obtem um campo da primeira tupla
            int id = resultados.getInt("id");
            String nome = resultados.getString("nome").toUpperCase();

            Usuario usuario = new Usuario(id, nome);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public void salvarMensagem(Mensagem mensagem) throws SQLException, SQLException {

        String sql = "insert into mensagem (texto, dataHoraEnvio, remetente, destinatario, dataHoraLeitura)" +
                " values (?,?,?,?,?)";

        PreparedStatement ps = conexao.getConexao().prepareStatement(sql);

        ps.setString(1, mensagem.getTexto());
        ps.setTimestamp(2, converterParaTimestamp(mensagem.getDataHoraEnvio()));
        ps.setString(3, mensagem.getRemetente());
        ps.setString(4, mensagem.getDestinatario());
        ps.setTimestamp(5, converterParaTimestamp(mensagem.getDataHoraLeitura()));
        ps.execute();

    }

    public void salvarTextoMensagem(String texto) throws SQLException, SQLException {

        String sql = "insert into mensagem (texto, dataHoraEnvio, remetente, destinatario, dataHoraLeitura)" +
                " values (?,?,null,null,null)";

        PreparedStatement ps = conexao.getConexao().prepareStatement(sql);

        ps.setString(1, texto);
        ps.setTimestamp(2, converterParaTimestamp(LocalDateTime.now()));
        ps.execute();

    }

    private Timestamp converterParaTimestamp(LocalDateTime dataHora) {
        if (dataHora != null){
            return Timestamp.from(dataHora.toInstant(ZoneOffset.UTC));
        }else{
            return null;
        }

    }

    public List<Mensagem> listarMensagens() throws SQLException {
        String query = "SELECT * FROM mensagem";
        PreparedStatement ps = conexao.getConexao().prepareStatement(query);
        ResultSet resultados = ps.executeQuery();

        List<Mensagem> mensagens = new LinkedList<>();

        while(resultados.next()){
            String texto = resultados.getString("texto");
            LocalDateTime dataHoraEnvio = converteParaLocalDateTime(resultados.getTimestamp("dataHoraEnvio"));
            String remetente = resultados.getString("remetente");
            String destinatario = resultados.getString("destinatario");
            LocalDateTime dataHoraLeitura = converteParaLocalDateTime(resultados.getTimestamp("dataHoraLeitura"));

            Mensagem mensagem = new Mensagem(texto, remetente, destinatario, dataHoraLeitura);
            mensagens.add(mensagem);
        }
        return mensagens;
    }

    private LocalDateTime converteParaLocalDateTime (Timestamp timestamp) throws SQLException {
        if (timestamp != null){
            return timestamp.toLocalDateTime();
        }
        return null;
    }

}
