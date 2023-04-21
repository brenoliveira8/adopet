package br.com.mascarenhasb2.adopet.domain.model.address.dto;

import jakarta.validation.constraints.Pattern;

public record AddressUpdateDTO(
        String street,
        Integer number,
        String neighborhood,
        @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
        String zipCode,
        String city,
        @Pattern(regexp = "[A-Z]{2}")
        String state,
        String complement
) {
}
