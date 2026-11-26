package uel.ProjetoFinalBD.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uel.ProjetoFinalBD.dao.QuestionarioDao;

@Controller
public class QuestionarioController {

    private final QuestionarioDao questionarioDao = new QuestionarioDao();

    @GetMapping("/questionarios/novo")
    public String exibirFormulario() {
        return "questionario";
    }

    @PostMapping("/questionarios/novo")
    public String cadastrar(
            @RequestParam("titulo") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("categoria") String categoria,
            @RequestParam("ocasiao") String ocasiao,
            Model model
    ) throws SQLException {
        if (titulo.isBlank()) {
            model.addAttribute("erro", "Informe o título do questionário.");
            return "questionario";
        }

        try {
            questionarioDao.cadastrar(
                    titulo.strip(),
                    descricao.isBlank() ? null : descricao.strip(),
                    categoria,
                    ocasiao
            );
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                model.addAttribute("erro", "Já existe um questionário para essa categoria e ocasião.");
                return "questionario";
            }
            throw e;
        }

        return "redirect:/questionarios/novo?sucesso";
    }
}