package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * Contrôleur gérant les pages liées à l'authentification.
 *
 * <p>Ce contrôleur expose les endpoints publics de connexion et de gestion des erreurs
 * d'accès. Il est accessible sans authentification, conformément à la configuration
 * définie dans {@code SecurityConfig}.</p>
 */
@Controller
@RequestMapping("/app")
public class LoginController {
    /**
     * Affiche la page de connexion de l'application.
     *
     * <p>Cette page contient le formulaire de login dont l'action pointe vers
     * {@code /app/login} en méthode POST, traitée automatiquement par Spring Security.
     * En cas d'échec, le paramètre {@code error=true} est ajouté à l'URL.
     * En cas de déconnexion réussie, le paramètre {@code logout=true} est présent.</p>
     *
     * @return le nom de la vue Thymeleaf {@code login}
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    /**
     * Affiche la page d'erreur d'accès refusé (403).
     *
     * <p>Cette page est affichée lorsqu'un utilisateur authentifié tente d'accéder
     * à une ressource pour laquelle il n'a pas les droits suffisants.
     * Spring Security redirige vers cet endpoint via la configuration
     * {@code accessDeniedPage("/app/error")} dans {@code SecurityConfig}.</p>
     *
     * @return un {@link ModelAndView} pointant vers la vue {@code 403},
     *         avec un message d'erreur explicatif
     */
    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMsg", "You are not authorized for the requested data.");
        mav.setViewName("403");
        return mav;
    }
    /**
     * Affiche la liste des articles sécurisés accessibles aux utilisateurs connectés.
     *
     * <p>Cet endpoint est protégé par Spring Security et nécessite une authentification.
     * Il retourne la vue listant les utilisateurs.</p>
     *
     * @return un {@link ModelAndView} pointant vers la vue {@code user/list}
     */
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/list");
        return mav;
    }
}