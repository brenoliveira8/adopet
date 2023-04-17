package br.com.mascarenhasb2.adopet.domain.model.address;

import br.com.mascarenhasb2.adopet.domain.model.address.dto.AddressDTO;
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

    public Address(AddressDTO newAddress) {
        this.street = newAddress.street();
        this.number = newAddress.number();
        this.neighborhood = newAddress.neighborhood();
        this.zipCode = newAddress.zipCode();
        this.city = newAddress.city();
        this.state = newAddress.state();
        this.complement = newAddress.complement();
    }
}
