-- Relatório 1 - taxa de conclusão por categoria/ocasião

SELECT
    q.categoria,
    q.ocasiao,
    COUNT(a.codigo_avaliacao) AS enviadas,
    COUNT(a.data_conclusao) AS concluidas,
	ROUND(
	    100.0 * COUNT(a.data_conclusao)
	    / COUNT(a.codigo_avaliacao),
	    1
	) AS taxa_conclusao_pct
FROM questionario q
LEFT JOIN avaliacao a ON a.id_questionario = q.id_questionario
GROUP BY q.categoria, q.ocasiao
--HAVING COUNT(a.codigo_avaliacao) > COUNT(a.data_conclusao)
ORDER BY q.categoria, q.ocasiao;


-- Relatório 2 - satisfação por ocasião e tema

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
ORDER BY qr.ocasiao, t.nome;


-- Relatório 3 - tempo de entrega por ocasião
SELECT
    c.ocasiao,
    COUNT(*) AS compras_entregues,
    ROUND(AVG(c.data_entrega - c.data_compra), 1) AS intervalo_medio_dias,
    MAX(c.data_entrega - c.data_compra) AS maior_intervalo_dias,
    ROUND(
        AVG(c.data_entrega - c.data_compra) - (
            SELECT AVG(data_entrega - data_compra)
            FROM compra
            WHERE data_entrega IS NOT NULL
        ),
        1
    ) AS diferenca_da_media_geral_dias
FROM compra c
WHERE c.data_entrega IS NOT NULL
GROUP BY c.ocasiao
ORDER BY c.ocasiao;