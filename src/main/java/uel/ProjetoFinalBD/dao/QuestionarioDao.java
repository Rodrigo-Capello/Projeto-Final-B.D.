package uel.ProjetoFinalBD.dao;

import java.sql.SQLException;

public class QuestionarioDao {

    public void cadastrar(
            String titulo, String descricao, String categoria, String ocasiao
    ) throws SQLException {
        String sql = """
            INSERT INTO questionario (titulo, descricao, categoria, ocasiao)
            VALUES (?, ?, ?, ?)
            """;

        try (var conexao = Conexao.abrir();
             var comando = conexao.prepareStatement(sql)) {
            comando.setString(1, titulo);
            comando.setString(2, descricao);
            comando.setString(3, categoria);
            comando.setString(4, ocasiao);
            comando.executeUpdate();
        }
    }
}