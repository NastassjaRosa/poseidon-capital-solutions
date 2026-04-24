package com.nnk.springboot.config;

import com.nnk.springboot.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/**
 * Configuration principale de Spring Security pour l'application Poseidon.
 *
 * <p>Cette classe définit les règles d'authentification et d'autorisation,
 * le fournisseur d'authentification basé sur la base de données,
 * ainsi que la configuration du formulaire de connexion et de la déconnexion.</p>
 *
 * <p>L'authentification est gérée via une session HTTP (session-based authentication),
 * mécanisme par défaut de Spring Security avec {@code formLogin}.</p>
 */
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    /**
     * Constructeur de SecurityConfig.
     *
     * @param customUserDetailsService service de chargement des utilisateurs depuis la base de données
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    /**
     * Crée et expose un encodeur de mot de passe BCrypt.
     *
     * <p>BCrypt est un algorithme de hachage adaptatif qui intègre un sel automatique,
     * ce qui le rend résistant aux attaques par dictionnaire et par rainbow table.</p>
     *
     * @return une instance de {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Configure le fournisseur d'authentification basé sur les données utilisateur en base.
     *
     * <p>Le {@link DaoAuthenticationProvider} utilise le {@link CustomUserDetailsService}
     * pour charger l'utilisateur par son nom d'utilisateur, puis compare le mot de passe
     * fourni avec le hash stocké en base via le {@link PasswordEncoder}.</p>
     *
     * @return une instance configurée de {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    /**
     * Définit la chaîne de filtres de sécurité HTTP de l'application.
     *
     * <p>Les règles d'accès configurées sont les suivantes :</p>
     * <ul>
     *   <li>Les URLs publiques (page d'accueil, login, CSS, images, page d'erreur) sont accessibles sans authentification.</li>
     *   <li>Les URLs {@code /user/**} et {@code /ruleName/**} sont réservées aux utilisateurs ayant le rôle {@code ADMIN}.</li>
     *   <li>Toutes les autres URLs nécessitent une authentification.</li>
     * </ul>
     *
     * <p>Le formulaire de connexion est configuré sur {@code /app/login}.
     * En cas de succès, l'utilisateur est redirigé vers {@code /admin/home}.
     * La déconnexion est accessible via {@code /app-logout}.</p>
     *
     * <p>Les accès non autorisés sont redirigés vers la page {@code /app/error} (403).</p>
     *
     * @param http l'objet {@link HttpSecurity} fourni par Spring Security
     * @return la {@link SecurityFilterChain} construite
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/app/login", "/css/**","/images/**", "/app/error").permitAll()
                        .requestMatchers("/user/**").hasAuthority("ADMIN")
                        .requestMatchers("/ruleName/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .defaultSuccessUrl("/admin/home", true)
                        .failureUrl("/app/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                        .logoutSuccessUrl("/app/login?logout=true")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/app/error")
                );

        return http.build();
    }
}