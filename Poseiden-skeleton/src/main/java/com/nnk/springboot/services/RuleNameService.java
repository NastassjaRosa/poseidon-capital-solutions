package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service gérant les opérations métier sur les règles ({@link RuleName}).
 *
 * <p>Cette classe fait le lien entre le contrôleur {@code RuleNameController}
 * et le repository {@code RuleNameRepository}. Elle encapsule la logique d'accès
 * aux données et centralise la gestion des erreurs pour les opérations CRUD.</p>
 *
 * <p>L'accès aux fonctionnalités de gestion des règles est restreint aux utilisateurs
 * ayant le rôle {@code ADMIN}, conformément à la configuration Spring Security.</p>
 */
@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;
    /**
     * Constructeur de RuleNameService.
     *
     * @param ruleNameRepository le repository JPA d'accès aux données des règles
     */
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }
    /**
     * Retourne la liste de toutes les règles enregistrées en base.
     *
     * @return une liste de {@link RuleName}, vide si aucune règle n'existe
     */
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }
    /**
     * Sauvegarde une règle en base de données.
     *
     * <p>Si la règle possède déjà un identifiant, elle sera mise à jour.
     * Sinon, une nouvelle entrée sera créée.</p>
     *
     * @param ruleName la règle à sauvegarder
     * @return la règle sauvegardée avec son identifiant généré
     */
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }
    /**
     * Recherche une règle par son identifiant.
     *
     * @param id l'identifiant de la règle à rechercher
     * @return la règle correspondante
     * @throws IllegalArgumentException si aucune règle n'est trouvée pour cet identifiant
     */
    public RuleName findById(Integer id) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        return ruleName.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }
    /**
     * Supprime une règle par son identifiant.
     *
     * @param id l'identifiant de la règle à supprimer
     */
    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}