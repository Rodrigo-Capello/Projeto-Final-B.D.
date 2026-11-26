package uel.ProjetoFinalBD.controller;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

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
        @RequestParam(name = "abaixoDe60", defaultValue = "false") boolean abaixoDe60,
        Model model
    ) throws SQLException {
        model.addAttribute("taxas", relatorioDao.listarTaxasConclusao(abaixoDe60));
        model.addAttribute("abaixoDe60", abaixoDe60);
        return "relatorio-conclusao";
    }

    @GetMapping("/relatorios/satisfacao")
    public String exibirSatisfacao(Model model) throws SQLException {
        var linhas = relatorioDao.listarSatisfacaoPorTema();

        var notasPorTema = linhas.stream()
            .collect(Collectors.groupingBy(
                linha -> linha.tema(),
                LinkedHashMap::new,
                Collectors.toList()
            ));

        model.addAttribute("satisfacaoPorTema", linhas);
        model.addAttribute("notasPorTema", notasPorTema);
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