package uel.ProjetoFinalBD.dao;

import java.sql.SQLException;
import java.time.LocalDate;
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

    public UUID gerarAvaliacao(int idCompra) throws SQLException {
        String sql = """
            INSERT INTO avaliacao (id_compra, id_questionario, data_envio)
            SELECT c.id_compra, q.id_questionario, CURRENT_DATE
            FROM compra c
            JOIN questionario q
            ON q.categoria = c.categoria
            AND q.ocasiao = c.ocasiao
            WHERE c.id_compra = ?
            AND c.data_entrega IS NOT NULL
            ON CONFLICT (id_compra) DO NOTHING
            RETURNING codigo_avaliacao
            """;

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql)
        ) {
            comando.setInt(1, idCompra);

            try (var resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getObject("codigo_avaliacao", UUID.class);
                }
                return null;
            }
        }
    }

    public record CompraElegivel(
        int id,
        String categoria,
        String ocasiao,
        LocalDate dataEntrega
    ) {}

    public List<CompraElegivel> listarComprasElegiveis() throws SQLException {
        String sql = """
            SELECT c.id_compra, c.categoria, c.ocasiao, c.data_entrega
            FROM compra c
            JOIN questionario q
            ON q.categoria = c.categoria
            AND q.ocasiao = c.ocasiao
            WHERE c.data_entrega IS NOT NULL
            AND NOT EXISTS (
                SELECT 1
                FROM avaliacao a
                WHERE a.id_compra = c.id_compra
            )
            ORDER BY c.data_entrega DESC, c.id_compra DESC
            """;

        List<CompraElegivel> compras = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql);
            var resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                compras.add(new CompraElegivel(
                    resultado.getInt("id_compra"),
                    resultado.getString("categoria"),
                    resultado.getString("ocasiao"),
                    resultado.getObject("data_entrega", LocalDate.class)
                ));
            }
        }

        return compras;
    }
}