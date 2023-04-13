package br.com.mascarenhasb2.adopet.domain.guardian;

import br.com.mascarenhasb2.adopet.domain.guardian.dto.GuardianCreationDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Guardian")
@Table(name = "guardians")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "city"})
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String city;
    private String about;
    private String photo;
    public Guardian(GuardianCreationDTO newGuardian) {
        this.name = newGuardian.name();
        this.phone = newGuardian.phone();
        this.city = newGuardian.city();
        this.about = newGuardian.about();
        this.photo = newGuardian.photo();
    }
}
