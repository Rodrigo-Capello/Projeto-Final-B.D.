INSERT INTO cliente (nome)
VALUES
    ('Lucas Cardoso'),
    ('Lucas Camargo'),
    ('Irineu Stroka'),
    ('João Pedro de Souza'),
    ('Victoria Alves');

INSERT INTO compra (data_compra, data_entrega, categoria, id_cliente)
VALUES
    ('2026-10-03', '2026-10-04', 'Cesta matinal', 1),
    ('2026-10-10', '2026-10-12', 'Caixa de presente', 2),
    ('2026-11-05', '2026-11-06', 'Caixa de presente', 1),
    ('2026-11-12', '2026-11-14', 'Cesta matinal', 3),
    ('2026-11-23', NULL, 'Cesta matinal', 4);

INSERT INTO tema (nome)
VALUES
    ('Atendimento'),
    ('Entrega'),
    ('Apresentação'),
    ('Qualidade dos produtos');

INSERT INTO questao (enunciado, tipo, id_tema)
VALUES
    ('Como você avalia o atendimento durante a compra?', 'escala', 1),
    ('Como você avalia a pontualidade da entrega?', 'escala', 2),
    ('Como você avalia a apresentação da encomenda?', 'escala', 3),
    ('Como você avalia a qualidade dos produtos?', 'escala', 4),
    ('O que podemos melhorar no atendimento?', 'texto_livre', 1),
    ('Você tem alguma sugestão sobre a entrega?', 'texto_livre', 2);

INSERT INTO questionario (titulo, descricao, data_criacao, categoria)
VALUES
    ('Satisfação - cestas matinais',
     'Pesquisa sobre a experiência de compra e entrega de cestas matinais.',
     '2026-10-01', 'Cesta matinal'),
    ('Satisfação - caixas de presente',
     'Pesquisa sobre a experiência de compra e entrega de caixas de presente.',
     '2026-10-01', 'Caixa de presente');

INSERT INTO questionario_questao (id_questionario, id_questao)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 6);

INSERT INTO avaliacao (data_envio, data_conclusao, id_compra, id_questionario)
VALUES
    ('2026-10-04 14:00', '2026-10-04 19:30', 1, 1),
    ('2026-10-12 14:00', '2026-10-13 10:15', 2, 2),
    ('2026-11-06 14:00', '2026-11-07 09:20', 3, 2),
    ('2026-11-14 14:00', NULL,                4, 1);

INSERT INTO resposta (codigo_avaliacao, id_questao, nota, comentario)
SELECT a.codigo_avaliacao, r.id_questao, r.nota, r.comentario
FROM (VALUES
    (1, 1, 5, NULL),
    (1, 2, 4, NULL),
    (1, 3, 5, NULL),
    (1, 4, 5, NULL),
    (1, 5, NULL, 'Seria bom confirmar o horário da entrega.'),
    (2, 1, 4, NULL),
    (2, 2, 3, NULL),
    (2, 3, 5, NULL),
    (2, 4, 4, NULL),
    (2, 6, NULL, 'Gostaria de receber um aviso quando o pedido saísse.'),
    (3, 1, 5, NULL),
    (3, 2, 5, NULL),
    (3, 3, 4, NULL),
    (3, 4, 5, NULL),
    (3, 6, NULL, 'A entrega chegou no horário combinado.')
) AS r(id_compra, id_questao, nota, comentario)
JOIN avaliacao a ON a.id_compra = r.id_compra;