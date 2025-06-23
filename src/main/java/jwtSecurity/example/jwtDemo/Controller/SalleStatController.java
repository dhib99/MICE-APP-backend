package jwtSecurity.example.jwtDemo.Controller;

import jwtSecurity.example.jwtDemo.Model.SalleStat;
import jwtSecurity.example.jwtDemo.Service.SalleStatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salle-stats")
public class SalleStatController {

    private final SalleStatService salleStatService;

    public SalleStatController(SalleStatService salleStatService) {
        this.salleStatService = salleStatService;
    }

    // Incrémente le compteur d'une salle donnée (appelé à chaque sélection de salle)
    @PostMapping("/increment")
    public ResponseEntity<Void> incrementSalleSelection(@RequestParam String salleNom) {
        salleStatService.incrementSelection(salleNom);
        return ResponseEntity.ok().build();
    }

    // Récupère toutes les stats des salles (pour afficher dans le graphique)
    @GetMapping
    public ResponseEntity<List<SalleStat>> getAllStats() {
        List<SalleStat> stats = salleStatService.getAllStats();
        return ResponseEntity.ok(stats);
    }
}
