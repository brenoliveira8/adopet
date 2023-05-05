package br.com.mascarenhasb2.adopet.domain.model.adoption;

import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;
import br.com.mascarenhasb2.adopet.domain.model.pet.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Adoption")
@Table(name = "adoptions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Adoption{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "adoption_date")
    private LocalDateTime adoptionDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Adoption(Guardian guardian, Pet pet){
        this.adoptionDate = LocalDateTime.now();
        this.guardian = guardian;
        this.pet = pet;
    }
}
