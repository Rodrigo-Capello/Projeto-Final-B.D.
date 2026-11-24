CREATE TABLE cliente (
    id_cliente INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome TEXT NOT NULL
);

CREATE TABLE compra (
    id_compra INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data_compra DATE NOT NULL,
    data_entrega DATE,
    categoria TEXT NOT NULL,
    ocasiao TEXT NOT NULL CHECK (ocasiao IN ('Normal', 'Natal', 'Dia dos Namorados')),
    id_cliente INTEGER NOT NULL REFERENCES cliente(id_cliente),
    CHECK (data_entrega >= data_compra)
);

CREATE TABLE questionario (
    id_questionario INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo TEXT NOT NULL,
    descricao TEXT,
    data_criacao DATE NOT NULL DEFAULT CURRENT_DATE,
    categoria TEXT NOT NULL CHECK (categoria IN ('Cesta matinal', 'Caixa de presente')),
    ocasiao TEXT NOT NULL CHECK (ocasiao IN ('Normal', 'Natal', 'Dia dos Namorados')),
    UNIQUE (categoria, ocasiao)
);

CREATE TABLE avaliacao (
    codigo_avaliacao UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data_envio DATE NOT NULL DEFAULT CURRENT_DATE,
    data_conclusao DATE,
    id_compra INTEGER NOT NULL UNIQUE REFERENCES compra(id_compra),
    id_questionario INTEGER NOT NULL REFERENCES questionario(id_questionario)
);

CREATE TABLE tema (
    id_tema INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome TEXT NOT NULL
);

CREATE TABLE questao (
    id_questao INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    enunciado TEXT NOT NULL,
    tipo TEXT NOT NULL CHECK (tipo IN ('escala', 'texto_livre')),
    id_tema INTEGER NOT NULL REFERENCES tema(id_tema)
);

CREATE TABLE questionario_questao (
    id_questionario INTEGER REFERENCES questionario(id_questionario),
    id_questao INTEGER REFERENCES questao(id_questao),
    PRIMARY KEY (id_questionario, id_questao)
);

CREATE TABLE resposta (
    id_resposta INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nota INTEGER,
    comentario TEXT,
    codigo_avaliacao UUID NOT NULL REFERENCES avaliacao(codigo_avaliacao),
    id_questao INTEGER NOT NULL REFERENCES questao(id_questao),
    UNIQUE (codigo_avaliacao, id_questao),
    CHECK (
        (nota IS NOT NULL AND nota BETWEEN 1 AND 5 AND comentario IS NULL)
        OR
        (nota IS NULL AND comentario IS NOT NULL)
    )
);