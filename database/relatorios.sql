-- Relatório 1 - taxa de conclusão por categoria/ocasião

SELECT
    q.categoria,
    q.ocasiao,
    COUNT(a.codigo_avaliacao) AS enviadas,
    COUNT(a.data_conclusao) AS concluidas,
	ROUND(
	    100.0 * COUNT(a.data_conclusao) / NULLIF(COUNT(a.codigo_avaliacao), 0),
	    1
	) AS taxa_conclusao_pct
FROM questionario q
LEFT JOIN avaliacao a ON a.id_questionario = q.id_questionario
GROUP BY q.categoria, q.ocasiao
-- HAVING 100.0 * COUNT(a.data_conclusao) < 60 * COUNT(a.codigo_avaliacao)
-- Filtro: taxa de conclusão < 60%
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
ORDER BY c.ocasiao;