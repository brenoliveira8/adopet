package br.com.mascarenhasb2.adopet.domain.model.guardian;

import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Guardian")
@Table(name = "guardians")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "city"})
public class Guardian{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String city;
    private String about;
    private String photo;
    @Embedded
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Guardian(GuardianCreationDTO newGuardian, User user){
        this.name = newGuardian.name();
        this.phone = newGuardian.phone();
        this.city = newGuardian.city();
        this.about = newGuardian.about();
        this.photo = newGuardian.photo();
        this.user = user;
    }

    public void updateInformation(GuardianUpdateDTO newInformation){
        this.name = newInformation.name() != null ? newInformation.name() : this.name;
        this.phone = newInformation.phone() != null ? newInformation.phone() : this.phone;
        this.city = newInformation.city() != null ? newInformation.city() : this.city;
        this.about = newInformation.about() != null ? newInformation.about() : this.about;
        this.photo = newInformation.photo() != null ? newInformation.photo() : this.photo;
    }
}
