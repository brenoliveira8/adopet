package br.com.mascarenhasb2.adopet.domain.model.shelter;

import br.com.mascarenhasb2.adopet.domain.model.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Shelter")
@Table(name = "shelters")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "name"})
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    @Embedded
    private Address address;

}
