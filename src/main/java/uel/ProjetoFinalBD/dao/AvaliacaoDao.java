package uel.ProjetoFinalBD.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AvaliacaoDao {

    public record Pergunta(int id, String enunciado, String tipo) {}

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
}