package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Contrôleur gérant les opérations CRUD sur les règles ({@link RuleName}).
 *
 * <p>Expose les endpoints HTTP permettant d'afficher, créer, modifier et supprimer
 * des règles métier. Les données sont validées via les annotations de contrainte
 * définies sur l'entité {@link RuleName} avant toute persistance.</p>
 *
 * <p>L'accès à ce contrôleur est restreint aux utilisateurs ayant le rôle {@code ADMIN},
 * conformément à la configuration Spring Security.</p>
 */
@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;
    /**
     * Constructeur de RuleNameController.
     *
     * @param ruleNameService le service gérant la logique métier des règles
     */
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }
    /**
     * Affiche la liste de toutes les règles.
     *
     * @param model le modèle Thymeleaf alimenté avec la liste des règles
     * @return la vue {@code ruleName/list}
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }
    /**
     * Affiche le formulaire d'ajout d'une nouvelle règle.
     *
     * @param ruleName un objet vide utilisé pour lier les champs du formulaire
     * @return la vue {@code ruleName/add}
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }
    /**
     * Valide et enregistre une nouvelle règle.
     *
     * <p>Si la validation échoue, le formulaire d'ajout est réaffiché avec les erreurs.
     * En cas de succès, la règle est persistée et l'utilisateur est redirigé vers la liste.</p>
     *
     * @param ruleName la règle à valider et sauvegarder
     * @param result   le résultat de la validation des contraintes
     * @param model    le modèle Thymeleaf
     * @return la vue {@code ruleName/add} en cas d'erreur, sinon une redirection vers {@code /ruleName/list}
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
    /**
     * Affiche le formulaire de modification d'une règle existante.
     *
     * @param id    l'identifiant de la règle à modifier
     * @param model le modèle Thymeleaf alimenté avec la règle à modifier
     * @return la vue {@code ruleName/update}
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }
    /**
     * Valide et met à jour une règle existante.
     *
     * @param id       l'identifiant de la règle à mettre à jour
     * @param ruleName les nouvelles données de la règle
     * @param result   le résultat de la validation des contraintes
     * @param model    le modèle Thymeleaf
     * @return la vue {@code ruleName/update} en cas d'erreur, sinon une redirection vers {@code /ruleName/list}
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }

        ruleName.setId(id);
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
    /**
     * Supprime une règle par son identifiant et redirige vers la liste.
     *
     * @param id    l'identifiant de la règle à supprimer
     * @param model le modèle Thymeleaf
     * @return une redirection vers {@code /ruleName/list}
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteById(id);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}