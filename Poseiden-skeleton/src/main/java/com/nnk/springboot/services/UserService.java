package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service gérant les opérations métier sur les utilisateurs ({@link User}).
 *
 * <p>Cette classe fait le lien entre le contrôleur {@code UserController}
 * et le repository {@code UserRepository}. Elle encapsule la logique d'accès
 * aux données et centralise la gestion des erreurs pour les opérations CRUD.</p>
 *
 * <p>L'accès à la gestion des utilisateurs est restreint aux utilisateurs ayant
 * le rôle {@code ADMIN}, conformément à la configuration Spring Security.</p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    /**
     * Constructeur de UserService.
     *
     * @param userRepository le repository JPA d'accès aux données des utilisateurs
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retourne la liste de tous les utilisateurs enregistrés en base.
     *
     * @return une liste de {@link User}, vide si aucun utilisateur n'existe
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
    /**
     * Sauvegarde un utilisateur en base de données.
     *
     * <p>Si l'utilisateur possède déjà un identifiant, il sera mis à jour.
     * Sinon, une nouvelle entrée sera créée. Le mot de passe doit être
     * préalablement encodé via {@code BCryptPasswordEncoder} avant l'appel
     * à cette méthode — cette responsabilité incombe au contrôleur.</p>
     *
     * @param user l'utilisateur à sauvegarder
     * @return l'utilisateur sauvegardé avec son identifiant généré
     */
    public User save(User user) {
        return userRepository.save(user);
    }
    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à rechercher
     * @return l'utilisateur correspondant
     * @throws IllegalArgumentException si aucun utilisateur n'est trouvé pour cet identifiant
     */
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }
    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}