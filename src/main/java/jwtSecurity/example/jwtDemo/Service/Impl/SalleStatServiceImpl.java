package jwtSecurity.example.jwtDemo.Service.Impl;

import jwtSecurity.example.jwtDemo.Service.SalleStatService;
import jwtSecurity.example.jwtDemo.Model.SalleStat;
import jwtSecurity.example.jwtDemo.Repository.SalleStatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalleStatServiceImpl implements SalleStatService {

    private final SalleStatRepository salleStatRepository;

    public SalleStatServiceImpl(SalleStatRepository salleStatRepository) {
        this.salleStatRepository = salleStatRepository;
    }

    @Override
    public void incrementSelection(String salleNom) {
        System.out.println("Increment selection called for salle : " + salleNom);
        List<SalleStat> stats = salleStatRepository.findBySalleNom(salleNom);

        if (!stats.isEmpty()) {
            SalleStat stat = stats.get(0); // On prend le premier (ou gérer autrement)
            stat.setCompteur(stat.getCompteur() + 1);
            salleStatRepository.save(stat);
        } else {
            SalleStat stat = new SalleStat();
            stat.setSalleNom(salleNom);
            stat.setCompteur(1);
            salleStatRepository.save(stat);
        }
    }



    @Override
    public List<SalleStat> getAllStats() {
        return salleStatRepository.findAll();
    }
}
