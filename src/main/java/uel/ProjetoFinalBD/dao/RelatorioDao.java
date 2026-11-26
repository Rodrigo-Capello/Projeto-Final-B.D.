package uel.ProjetoFinalBD.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDao {

    public record TaxaConclusao(
        String categoria,
        String ocasiao,
        long enviadas,
        long concluidas,
        BigDecimal percentual
    ) {}

    public List<TaxaConclusao> listarTaxasConclusao(boolean abaixoDe60) throws SQLException {
        String sql = """
            SELECT
                q.categoria,
                q.ocasiao,
                COUNT(a.codigo_avaliacao) AS enviadas,
                COUNT(a.data_conclusao) AS concluidas,
                ROUND(
                    100.0 * COUNT(a.data_conclusao) / COUNT(a.codigo_avaliacao),
                    1
                ) AS percentual
            FROM questionario q
            LEFT JOIN avaliacao a ON a.id_questionario = q.id_questionario
            GROUP BY q.categoria, q.ocasiao
            HAVING NOT ? OR 100.0 * COUNT(a.data_conclusao) < 60 * COUNT(a.codigo_avaliacao)
            ORDER BY q.categoria, q.ocasiao
            """;

        List<TaxaConclusao> taxas = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql)
        ) {
            comando.setBoolean(1, abaixoDe60);

            try (var resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    taxas.add(new TaxaConclusao(
                        resultado.getString("categoria"),
                        resultado.getString("ocasiao"),
                        resultado.getLong("enviadas"),
                        resultado.getLong("concluidas"),
                        resultado.getBigDecimal("percentual")
                    ));
                }
            }
        }

        return taxas;
    }


    public record SatisfacaoPorTema(
        String ocasiao,
        String tema,
        long quantidadeNotas,
        BigDecimal notaMedia
    ) {}

    public List<SatisfacaoPorTema> listarSatisfacaoPorTema() throws SQLException {
        String sql = """
            SELECT
                qr.ocasiao,
                t.nome AS tema,
                COUNT(r.nota) AS quantidade_notas,
                ROUND(AVG(r.nota), 2) AS nota_media
            FROM resposta r
            JOIN questao q ON q.id_questao = r.id_questao
            JOIN tema t ON t.id_tema = q.id_tema
            JOIN avaliacao a ON a.codigo_avaliacao = r.codigo_avaliacao
            JOIN questionario qr ON qr.id_questionario = a.id_questionario
            WHERE r.nota IS NOT NULL
            GROUP BY t.id_tema, t.nome, qr.ocasiao
            ORDER BY qr.ocasiao, t.nome
            """;

        List<SatisfacaoPorTema> resultados = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql);
            var resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                resultados.add(new SatisfacaoPorTema(
                    resultado.getString("ocasiao"),
                    resultado.getString("tema"),
                    resultado.getLong("quantidade_notas"),
                    resultado.getBigDecimal("nota_media")
                ));
            }
        }

        return resultados;
    }


    public record TempoEntregaPorOcasião(
        String ocasiao,
        long comprasEntregues,
        BigDecimal intervaloMedioDias,
        int maiorIntervaloDias,
        BigDecimal diferencaDaMediaGeralDias,
        BigDecimal mediaGeralDias
    ) {}

    public List<TempoEntregaPorOcasião> listarTempoEntregaPorOcasiao() throws SQLException {
        String sql = """
            SELECT
                c.ocasiao,
                COUNT(*) AS compras_entregues,
                ROUND(AVG(c.data_entrega - c.data_compra), 1) AS intervalo_medio_dias,
                MAX(c.data_entrega - c.data_compra) AS maior_intervalo_dias,
                ROUND(AVG(c.data_entrega - c.data_compra) - geral.media, 1)
                    AS diferenca_da_media_geral_dias,
                ROUND(geral.media, 1) AS media_geral_dias
            FROM compra c
            CROSS JOIN (
                SELECT AVG(data_entrega - data_compra) AS media
                FROM compra
                WHERE data_entrega IS NOT NULL
            ) AS geral
            WHERE c.data_entrega IS NOT NULL
            GROUP BY c.ocasiao, geral.media
            ORDER BY c.ocasiao
            """;

        List<TempoEntregaPorOcasião> resultados = new ArrayList<>();

        try (
            var conexao = Conexao.abrir();
            var comando = conexao.prepareStatement(sql);
            var resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                resultados.add(new TempoEntregaPorOcasião(
                    resultado.getString("ocasiao"),
                    resultado.getLong("compras_entregues"),
                    resultado.getBigDecimal("intervalo_medio_dias"),
                    resultado.getInt("maior_intervalo_dias"),
                    resultado.getBigDecimal("diferenca_da_media_geral_dias"),
                    resultado.getBigDecimal("media_geral_dias")
                ));
            }
        }

        return resultados;
    }
}
