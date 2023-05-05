package br.com.mascarenhasb2.adopet.domain.model.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
        @NotBlank
        String street,
        @NotNull
        Integer number,
        @NotBlank
        String neighborhood,
        @NotBlank
        @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
        String zipCode,
        @NotBlank
        String city,
        @NotBlank
        @Pattern(regexp = "[A-Z]{2}")
        String state,
        String complement
){
}
