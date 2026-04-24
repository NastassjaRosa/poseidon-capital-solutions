package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service gérant les opérations métier sur les notations ({@link Rating}).
 *
 * <p>Cette classe fait le lien entre le contrôleur {@code RatingController}
 * et le repository {@code RatingRepository}. Elle encapsule la logique d'accès
 * aux données et centralise la gestion des erreurs pour les opérations CRUD.</p>
 */
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    /**
     * Constructeur de RatingService.
     *
     * @param ratingRepository le repository JPA d'accès aux données des notations
     */
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    /**
     * Retourne la liste de toutes les notations enregistrées en base.
     *
     * @return une liste de {@link Rating}, vide si aucune notation n'existe
     */
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
    /**
     * Sauvegarde une notation en base de données.
     *
     * <p>Si la notation possède déjà un identifiant, elle sera mise à jour.
     * Sinon, une nouvelle entrée sera créée.</p>
     *
     * @param rating la notation à sauvegarder
     * @return la notation sauvegardée avec son identifiant généré
     */
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
    /**
     * Recherche une notation par son identifiant.
     *
     * @param id l'identifiant de la notation à rechercher
     * @return la notation correspondante
     * @throws IllegalArgumentException si aucune notation n'est trouvée pour cet identifiant
     */
    public Rating findById(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    }

    /**
     * Supprime une notation par son identifiant.
     *
     * @param id l'identifiant de la notation à supprimer
     */
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}