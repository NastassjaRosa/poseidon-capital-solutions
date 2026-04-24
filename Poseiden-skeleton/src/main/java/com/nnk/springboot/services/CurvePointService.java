package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service gérant les opérations métier sur les points de courbe ({@link CurvePoint}).
 *
 * <p>Cette classe fait le lien entre le contrôleur {@code CurveController}
 * et le repository {@code CurvePointRepository}. Elle encapsule la logique d'accès
 * aux données et centralise la gestion des erreurs pour les opérations CRUD.</p>
 */
@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;
    /**
     * Constructeur de CurvePointService.
     *
     * @param curvePointRepository le repository JPA d'accès aux données des points de courbe
     */
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }
    /**
     * Retourne la liste de tous les points de courbe enregistrés en base.
     *
     * @return une liste de {@link CurvePoint}, vide si aucun point n'existe
     */
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }
    /**
     * Sauvegarde un point de courbe en base de données.
     *
     * <p>Si le point possède déjà un identifiant, il sera mis à jour.
     * Sinon, une nouvelle entrée sera créée.</p>
     *
     * @param curvePoint le point de courbe à sauvegarder
     * @return le point de courbe sauvegardé avec son identifiant généré
     */
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }
    /**
     * Recherche un point de courbe par son identifiant.
     *
     * @param id l'identifiant du point de courbe à rechercher
     * @return le point de courbe correspondant
     * @throws IllegalArgumentException si aucun point de courbe n'est trouvé pour cet identifiant
     */
    public CurvePoint findById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        return curvePoint.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }
    /**
     * Supprime un point de courbe par son identifiant.
     *
     * @param id l'identifiant du point de courbe à supprimer
     */
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}