package uel.ProjetoFinalBD.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestaoDao {

    public record Tema(int id, String nome) {}

    public List<Tema> listarTemas() throws SQLException {
        String sql = """
            SELECT id_tema, nome
            FROM tema
            ORDER BY nome
            """;

        List<Tema> temas = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql);
            var resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                temas.add(new Tema(
                    resultado.getInt("id_tema"),
                    resultado.getString("nome")
                ));
            }
        }

        return temas;
    }

    public void cadastrarQuestao(String enunciado, String tipo, int idTema)
        throws SQLException {
        String sql = """
            INSERT INTO questao (enunciado, tipo, id_tema)
            VALUES (?, ?, ?)
            """;

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, enunciado);
            comando.setString(2, tipo);
            comando.setInt(3, idTema);
            comando.executeUpdate();
        }
    }

    public boolean vincularAoQuestionario(int idQuestionario, int idQuestao)
        throws SQLException {
        String sql = """
            INSERT INTO questionario_questao (id_questionario, id_questao)
            VALUES (?, ?)
            ON CONFLICT (id_questionario, id_questao) DO NOTHING
            """;

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql)
        ) {
            comando.setInt(1, idQuestionario);
            comando.setInt(2, idQuestao);
            return comando.executeUpdate() == 1;
        }
    }

    public record Questionario(int id, String titulo) {}

    public record Questao(int id, String enunciado, String tema) {}

    public List<Questionario> listarQuestionarios() throws SQLException {
        String sql = """
            SELECT id_questionario, titulo
            FROM questionario
            ORDER BY titulo
            """;

        List<Questionario> questionarios = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql);
            var resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                questionarios.add(new Questionario(
                    resultado.getInt("id_questionario"),
                    resultado.getString("titulo")
                ));
            }
        }

        return questionarios;
    }

    public List<Questao> listarQuestoes() throws SQLException {
        String sql = """
            SELECT q.id_questao, q.enunciado, t.nome AS tema
            FROM questao q
            JOIN tema t ON t.id_tema = q.id_tema
            ORDER BY t.nome, q.enunciado
            """;

        List<Questao> questoes = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql);
            var resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                questoes.add(new Questao(
                    resultado.getInt("id_questao"),
                    resultado.getString("enunciado"),
                    resultado.getString("tema")
                ));
            }
        }

        return questoes;
    }
}