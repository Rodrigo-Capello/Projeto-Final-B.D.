package uel.ProjetoFinalBD.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uel.ProjetoFinalBD.dao.RelatorioDao;

@Controller
public class RelatorioController {

    private final RelatorioDao relatorioDao = new RelatorioDao();

    @GetMapping("/relatorios")
    public String exibirMenu() {
        return "relatorios";
    }
    
    @GetMapping("/relatorios/conclusao")
    public String exibirRelatorios(
        @RequestParam(name = "apenasPendentes", defaultValue = "false") boolean apenasPendentes,
        Model model
    ) throws SQLException {
        model.addAttribute("taxas", relatorioDao.listarTaxasConclusao(apenasPendentes));
        model.addAttribute("apenasPendentes", apenasPendentes);
        model.addAttribute("satisfacaoPorTema", relatorioDao.listarSatisfacaoPorTema());
        return "relatorio-conclusao";
    }

    @GetMapping("/relatorios/satisfacao")
    public String exibirSatisfacao(Model model) throws SQLException {
        model.addAttribute("satisfacaoPorTema", relatorioDao.listarSatisfacaoPorTema());
        return "relatorio-satisfacao";
    }

    @GetMapping("/relatorios/entrega")
    public String exibirEntrega(Model model) throws SQLException {
        var temposEntrega = relatorioDao.listarTempoEntregaPorOcasiao();

        int limiteGrafico = temposEntrega.stream()
            .mapToInt(linha -> linha.maiorIntervaloDias())
            .max()
            .orElse(0) + 1;

        model.addAttribute("temposEntrega", temposEntrega);
        model.addAttribute("limiteGrafico", limiteGrafico);
        return "relatorio-entrega";
    }

}