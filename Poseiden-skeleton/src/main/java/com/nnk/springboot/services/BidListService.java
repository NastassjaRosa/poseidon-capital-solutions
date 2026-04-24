package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service gérant les opérations métier sur les enchères ({@link BidList}).
 *
 * <p>Cette classe fait le lien entre le contrôleur {@code BidListController}
 * et le repository {@code BidListRepository}. Elle encapsule la logique d'accès
 * aux données et centralise la gestion des erreurs pour les opérations CRUD.</p>
 */
@Service
public class BidListService {

    private final BidListRepository bidListRepository;
    /**
     * Constructeur de BidListService.
     *
     * @param bidListRepository le repository JPA d'accès aux données des enchères
     */
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }
    /**
     * Retourne la liste de toutes les enchères enregistrées en base.
     *
     * @return une liste de {@link BidList}, vide si aucune enchère n'existe
     */
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }
    /**
     * Sauvegarde une enchère en base de données.
     *
     * <p>Si l'enchère possède déjà un identifiant, elle sera mise à jour.
     * Sinon, une nouvelle entrée sera créée.</p>
     *
     * @param bidList l'enchère à sauvegarder
     * @return l'enchère sauvegardée avec son identifiant généré
     */
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }
    /**
     * Recherche une enchère par son identifiant.
     *
     * @param id l'identifiant de l'enchère à rechercher
     * @return l'enchère correspondante
     * @throws IllegalArgumentException si aucune enchère n'est trouvée pour cet identifiant
     */
    public BidList findById(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        return bidList.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }
    /**
     * Supprime une enchère par son identifiant.
     *
     * @param id l'identifiant de l'enchère à supprimer
     */
    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}