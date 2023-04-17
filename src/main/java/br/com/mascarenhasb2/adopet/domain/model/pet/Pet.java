package br.com.mascarenhasb2.adopet.domain.model.pet;

import br.com.mascarenhasb2.adopet.domain.model.shelter.Shelter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Pet")
@Table(name = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "name", "behavior"})
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String size;
    private String behavior;
    private String city;
    private Shelter shelter;


}
