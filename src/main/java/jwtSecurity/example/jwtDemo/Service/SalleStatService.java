package jwtSecurity.example.jwtDemo.Service;

import jwtSecurity.example.jwtDemo.Model.SalleStat;

import java.util.List;

public interface SalleStatService {
    // Incrémente le compteur de sélection pour une salle donnée
    void incrementSelection(String salleNom);

    // Récupérer toutes les stats (nom salle + compteur)
    List<SalleStat> getAllStats();
}
