INSERT INTO cliente (nome)
VALUES
    ('Ana Martins'),
    ('Bruno Ribeiro'),
    ('Camila Souza'),
    ('Diego Almeida'),
    ('Elisa Costa'),
    ('Felipe Santos'),
    ('Gabriela Lima'),
    ('Henrique Oliveira'),
    ('Isabela Rocha'),
    ('João Ferreira'),
    ('Larissa Gomes'),
    ('Marcos Vieira'),
    ('Natália Lopes'),
    ('Otávio Mendes'),
    ('Paula Cardoso'),
    ('Rafael Nunes'),
    ('Sofia Teixeira'),
    ('Thiago Barbosa'),
    ('Vitória Dias'),
    ('William Ribeiro'),
    ('Beatriz Monteiro'),
    ('Caio Duarte'),
    ('Daniela Azevedo'),
    ('Eduardo Campos'),
    ('Fernanda Melo'),
    ('Gustavo Pires'),
    ('Helena Carvalho'),
    ('Igor Batista'),
    ('Júlia Andrade'),
    ('Leonardo Moraes');

INSERT INTO compra (data_compra, data_entrega, categoria, ocasiao, id_cliente)
VALUES
    ('2026-10-02', '2026-10-05', 'Cesta matinal', 'Normal', 1),
    ('2026-10-08', '2026-10-13', 'Caixa de presente', 'Normal', 2),
    ('2026-11-03', '2026-11-05', 'Caixa de presente', 'Normal', 1),
    ('2026-11-09', '2026-11-15', 'Cesta matinal', 'Normal', 3),
    ('2026-11-22', NULL, 'Cesta matinal', 'Normal', 4),
    ('2025-12-07', '2025-12-23', 'Cesta matinal', 'Natal', 2),
    ('2025-12-13', '2025-12-23', 'Caixa de presente', 'Natal', 5),
    ('2026-06-03', '2026-06-11', 'Cesta matinal', 'Dia dos Namorados', 3),
    ('2026-06-06', '2026-06-11', 'Caixa de presente', 'Dia dos Namorados', 4),

    -- Compras 10 a 15: Natal
    ('2025-12-01', '2025-12-22', 'Cesta matinal', 'Natal', 6),
    ('2025-12-11', '2025-12-24', 'Caixa de presente', 'Natal', 7),
    ('2025-12-05', '2025-12-23', 'Cesta matinal', 'Natal', 8),
    ('2025-12-15', '2025-12-23', 'Caixa de presente', 'Natal', 9),
    ('2025-12-08', '2025-12-23', 'Cesta matinal', 'Natal', 10),
    ('2025-12-12', '2025-12-24', 'Caixa de presente', 'Natal', 11),

    -- Compras 16 a 21: Dia dos Namorados
    ('2026-06-01', '2026-06-12', 'Cesta matinal', 'Dia dos Namorados', 12),
    ('2026-06-05', '2026-06-12', 'Caixa de presente', 'Dia dos Namorados', 13),
    ('2026-06-02', '2026-06-11', 'Cesta matinal', 'Dia dos Namorados', 14),
    ('2026-06-05', '2026-06-11', 'Caixa de presente', 'Dia dos Namorados', 15),
    ('2026-05-30', '2026-06-11', 'Cesta matinal', 'Dia dos Namorados', 16),
    ('2026-06-03', '2026-06-11', 'Caixa de presente', 'Dia dos Namorados', 17),

    -- Compras 22 a 34: Normal
    ('2026-10-03', '2026-10-07', 'Cesta matinal', 'Normal', 18),
    ('2026-10-07', '2026-10-10', 'Caixa de presente', 'Normal', 19),
    ('2026-10-07', '2026-10-14', 'Cesta matinal', 'Normal', 20),
    ('2026-10-14', '2026-10-18', 'Caixa de presente', 'Normal', 21),
    ('2026-10-18', '2026-10-23', 'Cesta matinal', 'Normal', 22),
    ('2026-10-27', '2026-10-29', 'Caixa de presente', 'Normal', 23),
    ('2026-10-29', '2026-11-04', 'Cesta matinal', 'Normal', 24),
    ('2026-11-02', '2026-11-05', 'Caixa de presente', 'Normal', 25),
    ('2026-11-06', '2026-11-11', 'Cesta matinal', 'Normal', 26),
    ('2026-11-12', '2026-11-16', 'Caixa de presente', 'Normal', 27),
    ('2026-11-17', '2026-11-19', 'Cesta matinal', 'Normal', 28),
    ('2026-11-20', NULL, 'Caixa de presente', 'Normal', 29),
    ('2026-11-24', NULL, 'Cesta matinal', 'Normal', 30);

INSERT INTO tema (nome)
VALUES
    ('Atendimento'),
    ('Entrega'),
    ('Apresentação'),
    ('Qualidade dos produtos'),
    ('Outro');

INSERT INTO questao (enunciado, tipo, id_tema)
VALUES
    -- Questões iniciais (1 ~ 6)
    ('Como você avalia o atendimento durante a compra?', 'escala', 1),
    ('Como você avalia a pontualidade da entrega?', 'escala', 2),
    ('Como você avalia a apresentação da encomenda?', 'escala', 3),
    ('Como você avalia a qualidade dos produtos?', 'escala', 4),
    ('O que podemos melhorar no atendimento?', 'texto_livre', 1),
    ('Você tem alguma sugestão sobre a entrega?', 'texto_livre', 2),

    -- Atendimento (7 ~ 12)
    ('Como você avalia a clareza das informações antes da compra?', 'escala', 1),
    ('Como você avalia a rapidez no retorno às suas dúvidas?', 'escala', 1),
    ('Como você avalia a facilidade de fazer o pedido?', 'escala', 1),
    ('Como você avalia o atendimento ao solicitar uma personalização?', 'escala', 1),
    ('Que informação teria ajudado você a escolher o presente?', 'texto_livre', 1),
    ('Como foi a comunicação com a loja depois da confirmação do pedido?', 'texto_livre', 1),

    -- Entrega (13 ~ 18)
    ('Como você avalia a facilidade de combinar o horário da entrega?', 'escala', 2),
    ('Como você avalia as condições da encomenda ao chegar?', 'escala', 2),
    ('Como você avalia a comunicação sobre o andamento da entrega?', 'escala', 2),
    ('Como você avalia o cuidado da equipe na entrega do presente?', 'escala', 2),
    ('Que informação você gostaria de receber no dia da entrega?', 'texto_livre', 2),
    ('Como foi a experiência ao acompanhar a entrega da encomenda?', 'texto_livre', 2),

    -- Apresentação (19 ~ 24)
    ('Como você avalia a organização dos itens dentro da cesta?', 'escala', 3),
    ('Como você avalia o acabamento da embalagem da cesta?', 'escala', 3),
    ('Como você avalia a organização dos itens dentro da caixa de presente?', 'escala', 3),
    ('Como você avalia a apresentação da caixa para presentear?', 'escala', 3),
    ('O que você mudaria na apresentação da cesta?', 'texto_livre', 3),
    ('O que você mudaria na embalagem da caixa de presente?', 'texto_livre', 3),

    -- Qualidade dos produtos (25 ~ 30)
    ('Como você avalia o frescor dos alimentos da cesta?', 'escala', 4),
    ('Como você avalia a variedade dos itens da cesta?', 'escala', 4),
    ('Como você avalia a seleção de produtos da caixa de presente?', 'escala', 4),
    ('Como você avalia a combinação dos itens da caixa?', 'escala', 4),
    ('Que item você gostaria de encontrar em uma próxima cesta?', 'texto_livre', 4),
    ('Que produto você gostaria de encontrar em uma próxima caixa de presente?', 'texto_livre', 4),

    -- Natal (questões 31 a 33)
    ('Como você avalia a variedade de produtos natalinos na encomenda?', 'escala', 4),
    ('Como você avalia os detalhes natalinos da embalagem?', 'escala', 3),
    ('Que produto você gostaria de encontrar em uma próxima edição de Natal?', 'texto_livre', 4),

    -- Dia dos Namorados (questões 34 a 36)
    ('Como você avalia a decoração da encomenda para o Dia dos Namorados?', 'escala', 3),
    ('Quanto a seleção de produtos combinou com o presente que você queria dar?', 'escala', 4),
    ('Que opção de personalização você gostaria de ter para o Dia dos Namorados?', 'texto_livre', 1);

INSERT INTO questionario (titulo, descricao, data_criacao, categoria, ocasiao)
VALUES
    ('Satisfação - cestas matinais',
     'Pesquisa de satisfação sobre atendimento, entrega, apresentação e qualidade dos produtos da cesta matinal.',
     '2024-10-01', 'Cesta matinal', 'Normal'),

    ('Satisfação - caixas de presente',
     'Pesquisa de satisfação sobre atendimento, entrega, apresentação e qualidade dos produtos da caixa de presente.',
     '2024-10-01', 'Caixa de presente', 'Normal'),

    ('Satisfação - cestas matinais de Natal',
     'Pesquisa de satisfação sobre atendimento, entrega, apresentação e qualidade dos produtos da cesta matinal de Natal.',
     '2025-11-01', 'Cesta matinal', 'Natal'),

    ('Satisfação - caixas de presente de Natal',
     'Pesquisa de satisfação sobre atendimento, entrega, apresentação e qualidade dos produtos da caixa de presente de Natal.',
     '2025-11-01', 'Caixa de presente', 'Natal'),

    ('Satisfação - cestas matinais de Dia dos Namorados',
     'Pesquisa de satisfação sobre atendimento, entrega, apresentação e qualidade dos produtos da cesta matinal de Dia dos Namorados.',
     '2025-05-01', 'Cesta matinal', 'Dia dos Namorados'),

    ('Satisfação - caixas de presente de Dia dos Namorados',
     'Pesquisa de satisfação sobre atendimento, entrega, apresentação e qualidade dos produtos da caixa de presente de Dia dos Namorados.',
     '2025-05-01', 'Caixa de presente', 'Dia dos Namorados');

INSERT INTO questionario_questao (id_questionario, id_questao)
VALUES
    -- 1 - Cesta matinal normal
    (1, 1), (1, 2), (1, 3), (1, 4),
    (1, 5), (1, 7), (1, 18), (1, 25),

    -- 2 - Caixa de presente normal
    (2, 1), (2, 2), (2, 3), (2, 4),
    (2, 6), (2, 9), (2, 18), (2, 27),

    -- 3 - Cesta matinal de Natal
    (3, 1), (3, 2), (3, 3), (3, 4),
    (3, 13), (3, 20), (3, 26), (3, 31), (3, 32), (3, 33),

    -- 4 - Caixa de presente de Natal
    (4, 1), (4, 2), (4, 3), (4, 4),
    (4, 14), (4, 22), (4, 28), (4, 31), (4, 32), (4, 33),

    -- 5 - Cesta matinal de Dia dos Namorados
    (5, 1), (5, 2), (5, 3), (5, 4),
    (5, 8), (5, 15), (5, 19), (5, 34), (5, 35), (5, 36),

    -- 6 - Caixa de presente de Dia dos Namorados
    (6, 1), (6, 2), (6, 3), (6, 4),
    (6, 16), (6, 21), (6, 28), (6, 34), (6, 35), (6, 36);

INSERT INTO avaliacao (data_envio, data_conclusao, id_compra, id_questionario)
SELECT
    c.data_entrega,
    CASE
        WHEN c.id_compra IN (4, 15, 21, 32) THEN NULL
        ELSE c.data_entrega + 1
    END,
    c.id_compra,
    q.id_questionario
FROM compra c
JOIN questionario q
    ON q.categoria = c.categoria
   AND q.ocasiao = c.ocasiao
WHERE c.data_entrega IS NOT NULL;

INSERT INTO resposta (codigo_avaliacao, id_questao, nota, comentario)
SELECT
    a.codigo_avaliacao,
    q.id_questao,
    CASE 
        WHEN q.tipo = 'escala' THEN random(1, 5)
        END,
    CASE
        WHEN q.tipo = 'texto_livre' THEN
            CASE q.id_questao
                WHEN 5 THEN 'Seria bom receber a confirmação do pedido mais cedo.'
                WHEN 6 THEN 'Gostaria de receber uma previsão mais precisa da entrega.'
                WHEN 18 THEN 'Recebi a confirmação da entrega e a foto de como a encomenda ficou foi um ponto positivo.'
                WHEN 33 THEN 'Gostaria de encontrar biscoitos natalinos na próxima edição.'
                WHEN 36 THEN 'Gostaria de poder incluir um cartão com mensagem personalizada.'                
            END
    END
FROM avaliacao a
JOIN questionario_questao qq ON qq.id_questionario = a.id_questionario
JOIN questao q ON q.id_questao = qq.id_questao
WHERE a.data_conclusao IS NOT NULL;