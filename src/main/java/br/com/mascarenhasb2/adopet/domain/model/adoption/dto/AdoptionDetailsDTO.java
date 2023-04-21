package br.com.mascarenhasb2.adopet.domain.model.adoption.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;
import br.com.mascarenhasb2.adopet.domain.model.adoption.Adoption;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "animal", "tutor", "data"})
public record AdoptionDetailsDTO(
        Long id,
        @JsonProperty("tutor")
        String guardian,
        @JsonProperty("animal")
        String pet,
        @JsonProperty("data")
        @JsonFormat (pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime adoptionDate
) implements ReturnInformationDTO {
    public AdoptionDetailsDTO(Adoption adoption) {
        this(adoption.getId(), adoption.getGuardian().getName(), adoption.getPet().getName(), adoption.getAdoptionDate());
    }
}
