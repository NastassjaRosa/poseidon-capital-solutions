package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Contrôleur gérant les pages d'accueil de l'application.
 *
 * <p>Expose deux endpoints : la page d'accueil publique ({@code /}) accessible
 * sans authentification, et la page d'accueil administrateur ({@code /admin/home})
 * qui redirige vers la liste des enchères après une connexion réussie.</p>
 */
@Controller
public class HomeController {
	/**
	 * Affiche la page d'accueil publique de l'application.
	 *
	 * <p>Cette page est accessible sans authentification et présente
	 * le logo de l'application ainsi qu'un lien vers la page de connexion.</p>
	 *
	 * @param model le modèle Thymeleaf (non utilisé ici, conservé par convention)
	 * @return la vue {@code home}
	 */
	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}
	/**
	 * Redirige l'utilisateur après une connexion réussie.
	 *
	 * <p>Cet endpoint est la cible par défaut configurée dans {@code SecurityConfig}
	 * après un login réussi ({@code defaultSuccessUrl("/admin/home", true)}).
	 * Il redirige systématiquement vers la liste des enchères.</p>
	 *
	 * @param model le modèle Thymeleaf (non utilisé ici, conservé par convention)
	 * @return une redirection vers {@code /bidList/list}
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}
}