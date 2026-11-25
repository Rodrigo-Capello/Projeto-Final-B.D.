package uel.ProjetoFinalBD.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AvaliacaoDao {

    public record Pergunta(int id, String enunciado, String tipo) {}

    public record RespostaEnviada(int idQuestao, Integer nota, String comentario) {}

    public List<Pergunta> listarPerguntasPendentes(UUID codigoAvaliacao) throws SQLException {
        String sql = """
            SELECT q.id_questao, q.enunciado, q.tipo
            FROM avaliacao a
            JOIN questionario_questao qq ON qq.id_questionario = a.id_questionario
            JOIN questao q ON q.id_questao = qq.id_questao
            WHERE a.codigo_avaliacao = ?
              AND a.data_conclusao IS NULL
            ORDER BY q.id_questao
            """;

        List<Pergunta> perguntas = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql)
        ) {
            comando.setObject(1, codigoAvaliacao);

            try (var resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    perguntas.add(new Pergunta(
                        resultado.getInt("id_questao"),
                        resultado.getString("enunciado"),
                        resultado.getString("tipo")
                    ));
                }
            }
        }

        return perguntas;
    }

    public boolean salvarRespostas(
        UUID codigoAvaliacao,
        List<RespostaEnviada> respostas
    ) throws SQLException {
        if (respostas.isEmpty()) {
            throw new IllegalArgumentException("A avaliação deve ter pelo menos uma resposta.");
        }

        String atualizarAvaliacao = """
            UPDATE avaliacao
            SET data_conclusao = CURRENT_DATE
            WHERE codigo_avaliacao = ?
            AND data_conclusao IS NULL
            """;

        String inserirResposta = """
            INSERT INTO resposta (codigo_avaliacao, id_questao, nota, comentario)
            VALUES (?, ?, ?, ?)
            """;

        try (var conexao = Conexao.abrir()) {
            conexao.setAutoCommit(false);

            try (
                var atualizacao = conexao.prepareStatement(atualizarAvaliacao);
                var insercao = conexao.prepareStatement(inserirResposta)
            ) {
                atualizacao.setObject(1, codigoAvaliacao);
                if (atualizacao.executeUpdate() == 0) {
                    conexao.rollback();
                    return false;
                }

                for (var resposta : respostas) {
                    insercao.setObject(1, codigoAvaliacao);
                    insercao.setInt(2, resposta.idQuestao());
                    insercao.setObject(3, resposta.nota(), java.sql.Types.INTEGER);
                    insercao.setString(4, resposta.comentario());
                    insercao.executeUpdate();
                }

                conexao.commit();
                return true;
            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            }
        }
    }
}