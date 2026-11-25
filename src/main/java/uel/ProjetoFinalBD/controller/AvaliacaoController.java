package uel.ProjetoFinalBD.controller;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uel.ProjetoFinalBD.dao.AvaliacaoDao;

@Controller
public class AvaliacaoController {

    private final AvaliacaoDao avaliacaoDao = new AvaliacaoDao();

    @GetMapping("/avaliacoes/{codigo}")
    public String exibirAvaliacao(
        @PathVariable("codigo") UUID codigoDaUrl,
        Model model
    ) throws SQLException {
        var perguntas = avaliacaoDao.listarPerguntasPendentes(codigoDaUrl);

        model.addAttribute("codigoAvaliacao", codigoDaUrl);
        model.addAttribute("perguntas", perguntas);
        return "avaliacao";
    }
}