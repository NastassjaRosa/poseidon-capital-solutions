package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Contrôleur gérant les opérations CRUD sur les échanges ({@link Trade}).
 *
 * <p>Expose les endpoints HTTP permettant d'afficher, créer, modifier et supprimer
 * des échanges financiers. Les données sont validées via les annotations de contrainte
 * définies sur l'entité {@link Trade} avant toute persistance.</p>
 *
 * <p>Accessible à tous les utilisateurs authentifiés.</p>
 */
@Controller
public class TradeController {

    private final TradeService tradeService;
    /**
     * Constructeur de TradeController.
     *
     * @param tradeService le service gérant la logique métier des échanges
     */
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    /**
     * Affiche la liste de tous les échanges.
     *
     * @param model le modèle Thymeleaf alimenté avec la liste des échanges
     * @return la vue {@code trade/list}
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }
    /**
     * Affiche le formulaire d'ajout d'un nouvel échange.
     *
     * @param trade un objet vide utilisé pour lier les champs du formulaire
     * @return la vue {@code trade/add}
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }
    /**
     * Valide et enregistre un nouvel échange.
     *
     * <p>Si la validation échoue, le formulaire d'ajout est réaffiché avec les erreurs.
     * En cas de succès, l'échange est persisté et l'utilisateur est redirigé vers la liste.</p>
     *
     * @param trade  l'échange à valider et sauvegarder
     * @param result le résultat de la validation des contraintes
     * @param model  le modèle Thymeleaf
     * @return la vue {@code trade/add} en cas d'erreur, sinon une redirection vers {@code /trade/list}
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.save(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
    /**
     * Affiche le formulaire de modification d'un échange existant.
     *
     * @param id    l'identifiant de l'échange à modifier
     * @param model le modèle Thymeleaf alimenté avec l'échange à modifier
     * @return la vue {@code trade/update}
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }
    /**
     * Valide et met à jour un échange existant.
     *
     * @param id     l'identifiant de l'échange à mettre à jour
     * @param trade  les nouvelles données de l'échange
     * @param result le résultat de la validation des contraintes
     * @param model  le modèle Thymeleaf
     * @return la vue {@code trade/update} en cas d'erreur, sinon une redirection vers {@code /trade/list}
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }

        trade.setTradeId(id);
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
    /**
     * Supprime un échange par son identifiant et redirige vers la liste.
     *
     * @param id    l'identifiant de l'échange à supprimer
     * @param model le modèle Thymeleaf
     * @return une redirection vers {@code /trade/list}
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteById(id);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
}