package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service gérant les opérations métier sur les échanges ({@link Trade}).
 *
 * <p>Cette classe fait le lien entre le contrôleur {@code TradeController}
 * et le repository {@code TradeRepository}. Elle encapsule la logique d'accès
 * aux données et centralise la gestion des erreurs pour les opérations CRUD.</p>
 */
@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    /**
     * Constructeur de TradeService.
     *
     * @param tradeRepository le repository JPA d'accès aux données des échanges
     */
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }
    /**
     * Retourne la liste de tous les échanges enregistrés en base.
     *
     * @return une liste de {@link Trade}, vide si aucun échange n'existe
     */
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }
    /**
     * Sauvegarde un échange en base de données.
     *
     * <p>Si l'échange possède déjà un identifiant, il sera mis à jour.
     * Sinon, une nouvelle entrée sera créée.</p>
     *
     * @param trade l'échange à sauvegarder
     * @return l'échange sauvegardé avec son identifiant généré
     */
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }
    /**
     * Recherche un échange par son identifiant.
     *
     * @param id l'identifiant de l'échange à rechercher
     * @return l'échange correspondant
     * @throws IllegalArgumentException si aucun échange n'est trouvé pour cet identifiant
     */
    public Trade findById(Integer id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }
    /**
     * Supprime un échange par son identifiant.
     *
     * @param id l'identifiant de l'échange à supprimer
     */
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}