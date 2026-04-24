package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
/**
 * Service d'authentification personnalisé implémentant {@link UserDetailsService}.
 *
 * <p>Cette classe est utilisée par Spring Security pour charger les informations
 * d'un utilisateur depuis la base de données lors de la tentative de connexion.
 * Elle fait le lien entre l'entité {@link User} de l'application et le contrat
 * {@link UserDetails} attendu par Spring Security.</p>
 *
 * <p>Le rôle de l'utilisateur est transmis sous forme de {@link SimpleGrantedAuthority},
 * ce qui permet à Spring Security d'appliquer les règles d'autorisation définies
 * dans {@code SecurityConfig} (ex. : accès ADMIN uniquement à {@code /user/**}).</p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    /**
     * Constructeur de CustomUserDetailsService.
     *
     * @param userRepository le repository permettant d'accéder aux utilisateurs en base de données
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Charge un utilisateur par son nom d'utilisateur pour l'authentification Spring Security.
     *
     * <p>Cette méthode est appelée automatiquement par Spring Security lors d'une tentative
     * de connexion. Elle recherche l'utilisateur en base via son {@code username}.
     * Si l'utilisateur est trouvé, ses informations (nom, mot de passe hashé, rôle)
     * sont encapsulées dans un objet {@link UserDetails} standard de Spring Security.</p>
     *
     * <p>Le mot de passe retourné est le hash BCrypt stocké en base — Spring Security
     * se charge de comparer ce hash avec le mot de passe saisi par l'utilisateur.</p>
     *
     * @param username le nom d'utilisateur saisi dans le formulaire de connexion
     * @return un objet {@link UserDetails} contenant les informations de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec ce nom d'utilisateur
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole()))
        );
    }
}