package br.com.mascarenhasb2.adopet.domain.model.adoption.dto;

import br.com.mascarenhasb2.adopet.domain.model.adoption.Adoption;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "animal", "tutor", "data"})
public record AdoptionDetailsDTO(
        Long id,
        @JsonProperty("tutor")
        Long guardianId,
        @JsonProperty("animal")
        Long petId,
        @JsonProperty("data")
        @JsonFormat (pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime adoptionDate
) {
    public AdoptionDetailsDTO(Adoption adoption) {
        this(adoption.getId(), adoption.getGuardian().getId(), adoption.getPet().getId(), adoption.getAdoptionDate());
    }
}
