package jwtSecurity.example.jwtDemo.Model;
import jakarta.persistence.*;
@Entity
@Table(name = "salle_stat")
public class SalleStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salle_nom", unique = true, nullable = false)
    private String salleNom;

    @Column(name = "compteur", nullable = false)
    private int compteur;

    // Constructeurs

    public SalleStat() {}

    public SalleStat(String salleNom, int compteur) {
        this.salleNom = salleNom;
        this.compteur = compteur;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public String getSalleNom() {
        return salleNom;
    }

    public void setSalleNom(String salleNom) {
        this.salleNom = salleNom;
    }

    public int getCompteur() {
        return compteur;
    }

    public void setCompteur(int compteur) {
        this.compteur = compteur;
    }
}