# Esquema relacional

- CLIENTE (**id_cliente**, nome)
- COMPRA (**id_compra**, data_compra, data_entrega, categoria, id_cliente)
  - `id_cliente` → `CLIENTE(id_cliente)`
- AVALIAÇÃO (**codigo_avaliacao**, data_envio, data_conclusao, id_compra, id_questionario)
  - `id_compra` → `COMPRA(id_compra)`
  - `id_questionario` → `QUESTIONÁRIO(id_questionario)`
  - `id_compra` NOT NULL e UNIQUE
- QUESTIONÁRIO (**id_questionario**, titulo, descricao, data_criacao, categoria)
- TEMA (**id_tema**, nome)
- QUESTÃO (**id_questao**, enunciado, tipo, id_tema)
  - `id_tema` → `TEMA(id_tema)`
  - `id_tema` NOT NULL.
- QUESTIONARIO_QUESTAO (**id_questionario, id_questao**)
  - `id_questionario` → `QUESTIONÁRIO(id_questionario)`
  - `id_questao` → `QUESTÃO(id_questao)`
- RESPOSTA (**id_resposta**, nota, comentario, codigo_avaliacao, id_questao)
  - `codigo_avaliacao` → `AVALIAÇÃO(codigo_avaliacao)`
  - `id_questao` → `QUESTÃO(id_questao)`
  - `codigo_avaliacao` e `id_questao` NOT NULL.
  - `(codigo_avaliacao, id_questao)`  UNIQUE.
  - Uma avaliação só pode conter respostas às questões do questionário que ela usa.
  - `nota` para questões de escala e `comentario` para questões de texto livre.


  ## Justificativa 3FN

O esquema está na 3FN porque, em cada tabela, todo atributo comum depende da chave inteira, sem depender de outro atributo comum.