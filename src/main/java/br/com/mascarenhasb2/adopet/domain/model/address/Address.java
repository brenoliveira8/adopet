package br.com.mascarenhasb2.adopet.domain.model.address;

import br.com.mascarenhasb2.adopet.domain.model.address.dto.AddressDTO;
import br.com.mascarenhasb2.adopet.domain.model.address.dto.AddressUpdateDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address{
    private String street;
    private Integer number;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;
    private String complement;

    public Address(AddressDTO newAddress){
        this.street = newAddress.street();
        this.number = newAddress.number();
        this.neighborhood = newAddress.neighborhood();
        this.zipCode = newAddress.zipCode();
        this.city = newAddress.city();
        this.state = newAddress.state();
        this.complement = newAddress.complement();
    }

    public void updateInformation(AddressUpdateDTO address){
        this.street = (address.street() != null) ? address.street() : this.street;
        this.number = (address.number() != null) ? address.number() : this.number;
        this.neighborhood = (address.neighborhood() != null) ? address.neighborhood() : this.neighborhood;
        this.zipCode = (address.zipCode() != null) ? address.zipCode() : this.zipCode;
        this.city = (address.city() != null) ? address.city() : this.city;
        this.state = (address.state() != null) ? address.state() : this.state;
        this.complement = (address.complement() != null) ? address.complement() : this.complement;

    }
}
