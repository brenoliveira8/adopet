package br.com.mascarenhasb2.adopet.domain.model.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private Integer number;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;
    private String complement;

}
