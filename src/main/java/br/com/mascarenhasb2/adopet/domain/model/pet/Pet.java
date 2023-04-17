package br.com.mascarenhasb2.adopet.domain.model.pet;

import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.Shelter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity (name = "Pet")
@Table (name = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString (of = {"id", "name", "behavior"})
public class Pet {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    @Enumerated (EnumType.STRING)
    private PetSize size;
    private String behavior;
    private Boolean adopted;
    private String photo;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "shelter_id")
    private Shelter shelter;
    public Pet(PetCreationDTO newPet, Shelter shelter) {
        this.name = newPet.name();
        this.age = newPet.age();
        this.size = newPet.size();
        this.behavior = newPet.behavior();
        this.adopted = false;
        this.photo = newPet.photo();
        this.shelter = shelter;
    }
    public void updateInformation(PetUpdateDTO pet, Shelter shelter) {
        this.name = (pet.name() != null) ? pet.name() : this.name;
        this.age = (pet.age() != null) ? pet.age() : this.age;
        this.size = (pet.size() != null) ? pet.size() : this.size;
        this.behavior = (pet.behavior() != null) ? pet.behavior() : this.behavior;
        this.adopted = (pet.adopted() != null) ? pet.adopted() : this.adopted;
        this.photo = (pet.photo() != null) ? pet.photo() : this.photo;
        this.shelter = (shelter != null) ? shelter : this.shelter;
    }
}
