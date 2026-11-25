package uel.ProjetoFinalBD.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uel.ProjetoFinalBD.dao.QuestaoDao;

@Controller
public class QuestaoController {

    private final QuestaoDao questaoDao = new QuestaoDao();

    @GetMapping("/questoes/nova")
    public String exibirFormulario(Model model) throws SQLException {
        model.addAttribute("temas", questaoDao.listarTemas());
        return "questao";
    }

    @PostMapping("/questoes/nova")
    public String cadastrarQuestao(
        @RequestParam("enunciado") String enunciado,
        @RequestParam("tipo") String tipo,
        @RequestParam("idTema") int idTema,
        Model model,
        RedirectAttributes redirectAttributes
    ) throws SQLException {
        var temas = questaoDao.listarTemas();
        boolean tipoValido = "escala".equals(tipo) || "texto_livre".equals(tipo);
        boolean temaValido = temas.stream().anyMatch(tema -> tema.id() == idTema);

        if (enunciado.isBlank() || !tipoValido || !temaValido) {
            model.addAttribute("erro", "Preencha o enunciado e selecione um tipo e um tema válidos.");
            model.addAttribute("temas", temas);
            return "questao";
        }

        questaoDao.cadastrarQuestao(enunciado.strip(), tipo, idTema);
        redirectAttributes.addFlashAttribute("mensagem", "Questão cadastrada com sucesso.");
        return "redirect:/questoes/nova";
    }

    @GetMapping("/questoes/vincular")
    public String exibirFormularioVinculo(Model model) throws SQLException {
        model.addAttribute("questionarios", questaoDao.listarQuestionarios());
        model.addAttribute("questoes", questaoDao.listarQuestoes());
        return "questao-vinculo";
    }

    @PostMapping("/questoes/vincular")
    public String vincularQuestao(
        @RequestParam("idQuestionario") int idQuestionario,
        @RequestParam("idQuestao") int idQuestao,
        RedirectAttributes redirectAttributes
    ) throws SQLException {
        boolean questionarioValido = questaoDao.listarQuestionarios().stream()
            .anyMatch(questionario -> questionario.id() == idQuestionario);
        boolean questaoValida = questaoDao.listarQuestoes().stream()
            .anyMatch(questao -> questao.id() == idQuestao);

        if (!questionarioValido || !questaoValida) {
            redirectAttributes.addFlashAttribute(
                "erro", "Selecione um questionário e uma questão válidos."
            );
            return "redirect:/questoes/vincular";
        }

        boolean vinculada = questaoDao.vincularAoQuestionario(idQuestionario, idQuestao);
        redirectAttributes.addFlashAttribute("mensagem", vinculada
            ? "Questão vinculada ao questionário."
            : "Essa questão já faz parte do questionário.");

        return "redirect:/questoes/vincular";
    }
}