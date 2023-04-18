package br.com.mascarenhasb2.adopet.domain.model.shelter;

import br.com.mascarenhasb2.adopet.domain.model.address.Address;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity (name = "Shelter")
@Table (name = "shelters")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString (of = {"id", "name"})
public class Shelter {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    @Embedded
    private Address address;
    @Embedded
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Shelter(ShelterCreationDTO newShelter, User user) {
        this.name = newShelter.name();
        this.phone = newShelter.phone();
        this.address = new Address(newShelter.address());
        this.user = user;
    }
    public void updateInformation(ShelterUpdateDTO shelter) {
        this.name = (shelter.name() != null) ? shelter.name() : this.name;
        this.phone = (shelter.phone() != null) ? shelter.phone() : this.phone;
        if(shelter.address() != null)
            this.address.updateInformation(shelter.address());
    }
}
