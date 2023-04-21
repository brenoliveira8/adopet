package br.com.mascarenhasb2.adopet.domain.model.guardian.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;

public record GuardianDetailsDTO(
        Long id,
        String name,
        String phone,
        String city,
        String about,
        String photo
) implements ReturnInformationDTO {

    public GuardianDetailsDTO(Guardian guardian){
        this(guardian.getId(), guardian.getName(), guardian.getPhone(), guardian.getCity(), guardian.getAbout(), guardian.getPhoto());
    }
}
