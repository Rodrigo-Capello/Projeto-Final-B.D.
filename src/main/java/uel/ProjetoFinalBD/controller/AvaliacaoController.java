package uel.ProjetoFinalBD.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uel.ProjetoFinalBD.dao.AvaliacaoDao;
import uel.ProjetoFinalBD.dao.AvaliacaoDao.RespostaEnviada;

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

    @PostMapping("/avaliacoes/{codigo}")
    public String enviarAvaliacao(
        @PathVariable("codigo") UUID codigoDaUrl,
        @RequestParam Map<String, String> campos,
        Model model,
        RedirectAttributes redirectAttributes
    ) throws SQLException {
        var perguntas = avaliacaoDao.listarPerguntasPendentes(codigoDaUrl);

        model.addAttribute("codigoAvaliacao", codigoDaUrl);
        model.addAttribute("perguntas", perguntas);

        if (perguntas.isEmpty()) {
            return "avaliacao";
        }

        var respostas = new ArrayList<RespostaEnviada>();

        for (var pergunta : perguntas) {
            String valor = campos.getOrDefault("resposta_" + pergunta.id(), "").strip();

            if ("escala".equals(pergunta.tipo())) {
                if (!valor.matches("[1-5]")) {
                    model.addAttribute("erro", "Selecione uma nota de 1 a 5 em cada questão de escala.");
                    return "avaliacao";
                }

                respostas.add(new RespostaEnviada(
                    pergunta.id(), Integer.valueOf(valor), null
                ));
            } else if ("texto_livre".equals(pergunta.tipo()) && !valor.isEmpty()) {
                respostas.add(new RespostaEnviada(pergunta.id(), null, valor));
            }
        }

        if (respostas.isEmpty()) {
            model.addAttribute("erro", "Preencha pelo menos uma resposta.");
            return "avaliacao";
        }

        if (avaliacaoDao.salvarRespostas(codigoDaUrl, respostas)) {
            redirectAttributes.addFlashAttribute("enviada", true);
        }

        return "redirect:/avaliacoes/" + codigoDaUrl;
    }
}