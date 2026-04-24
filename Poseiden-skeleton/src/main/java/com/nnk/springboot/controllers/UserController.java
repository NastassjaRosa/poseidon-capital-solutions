package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * Contrôleur gérant les opérations CRUD sur les utilisateurs ({@link User}).
 *
 * <p>Expose les endpoints HTTP permettant d'afficher, créer, modifier et supprimer
 * des utilisateurs. Les mots de passe sont systématiquement encodés via
 * {@link PasswordEncoder} (BCrypt) avant toute persistance en base.</p>
 *
 * <p>L'accès à ce contrôleur est restreint aux utilisateurs ayant le rôle {@code ADMIN},
 * conformément à la configuration Spring Security.</p>
 */
@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    /**
     * Constructeur de UserController.
     *
     * @param userService     le service gérant la logique métier des utilisateurs
     * @param passwordEncoder l'encodeur BCrypt utilisé pour hasher les mots de passe
     */
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * Affiche la liste de tous les utilisateurs.
     *
     * @param model le modèle Thymeleaf alimenté avec la liste des utilisateurs
     * @return la vue {@code user/list}
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    /**
     * Affiche le formulaire d'ajout d'un nouvel utilisateur.
     *
     * @param user un objet vide utilisé pour lier les champs du formulaire
     * @return la vue {@code user/add}
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }
    /**
     * Valide et enregistre un nouvel utilisateur.
     *
     * <p>Si la validation échoue (champs manquants ou mot de passe ne respectant pas
     * la politique de sécurité), le formulaire d'ajout est réaffiché avec les erreurs.
     * En cas de succès, le mot de passe est encodé en BCrypt avant la persistance,
     * puis l'utilisateur est redirigé vers la liste.</p>
     *
     * @param user   l'utilisateur à valider et sauvegarder
     * @param result le résultat de la validation des contraintes
     * @param model  le modèle Thymeleaf
     * @return la vue {@code user/add} en cas d'erreur, sinon une redirection vers {@code /user/list}
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/user/list";
    }
    /**
     * Affiche le formulaire de modification d'un utilisateur existant.
     *
     * <p>Le champ mot de passe est vidé avant l'affichage pour des raisons de sécurité :
     * le hash BCrypt ne doit jamais être pré-rempli dans un formulaire.</p>
     *
     * @param id    l'identifiant de l'utilisateur à modifier
     * @param model le modèle Thymeleaf alimenté avec l'utilisateur à modifier
     * @return la vue {@code user/update}
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }
    /**
     * Valide et met à jour un utilisateur existant.
     *
     * <p>Si la validation échoue, le formulaire de modification est réaffiché.
     * En cas de succès, le nouveau mot de passe est encodé en BCrypt avant la mise à jour.</p>
     *
     * @param id     l'identifiant de l'utilisateur à mettre à jour
     * @param user   les nouvelles données de l'utilisateur
     * @param result le résultat de la validation des contraintes
     * @param model  le modèle Thymeleaf
     * @return la vue {@code user/update} en cas d'erreur, sinon une redirection vers {@code /user/list}
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                             @Valid User user,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            user.setId(id);
            return "user/update";
        }

        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return "redirect:/user/list";
    }
    /**
     * Supprime un utilisateur par son identifiant et redirige vers la liste.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return une redirection vers {@code /user/list}
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}