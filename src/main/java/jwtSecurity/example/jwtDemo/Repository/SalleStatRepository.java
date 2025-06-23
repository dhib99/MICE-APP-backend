package jwtSecurity.example.jwtDemo.Repository;

import jwtSecurity.example.jwtDemo.Model.SalleStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalleStatRepository extends JpaRepository<SalleStat, Long> {
    List<SalleStat> findBySalleNom(String nom);
}
