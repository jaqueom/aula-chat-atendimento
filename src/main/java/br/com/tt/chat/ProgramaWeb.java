package br.com.tt.chat;

import br.com.tt.chat.dao.ComunicadorDAO;
import br.com.tt.chat.model.Mensagem;
import br.com.tt.chat.util.Conexao;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProgramaWeb {
    private static ComunicadorDAO dao;

    public static void main(String[] args) throws SQLException {
        Conexao conexao = new Conexao();
        conexao.conecta();
        dao = new ComunicadorDAO(conexao);

        //definir rotas para os servidores
        Spark.get("/",(req,resp) -> homePage());
        Spark.get("/mensagens"
                , (req, resp) -> listarMensagens()
                , new ThymeleafTemplateEngine());
        Spark.get("/adicionarMensagem",
                (req, resp) -> adicionarMensagem(req, resp), new ThymeleafTemplateEngine());
    }

    private static ModelAndView listarMensagens() throws SQLException {
        Map<String, Object> parametros = new HashMap<>();
       // parametros.put("titulo","Página de mensagens");

        parametros.put("mensagens", dao.listarMensagens());

        return new ModelAndView(parametros, "pagina-mensagens");
    }

    private static String homePage() {
        return "Olá! Bem vindo! <br> <a href='/mensagens'>Ir para mensagens</a>";
    }

    private static ModelAndView adicionarMensagem(Request req, Response resp) throws SQLException {
            String texto = req.queryParams("mensagem");
            dao.salvarTextoMensagem(texto);
            return listarMensagens();
    }
}
