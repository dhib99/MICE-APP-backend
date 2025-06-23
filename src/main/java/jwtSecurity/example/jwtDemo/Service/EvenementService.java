package jwtSecurity.example.jwtDemo.Service;



import jwtSecurity.example.jwtDemo.Dto.EventDto;
import jwtSecurity.example.jwtDemo.Model.Evenement;

import java.util.List;
public interface EvenementService {
    Evenement ajouterEvenement(EventDto eventDto);

    void incrementSelection(String salleNom);

    String getSalleNomById(Long salleId);

    List<Evenement> getAllEvenements();
}