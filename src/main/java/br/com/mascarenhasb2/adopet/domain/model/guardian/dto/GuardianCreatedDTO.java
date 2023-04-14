package br.com.mascarenhasb2.adopet.domain.model.guardian.dto;

import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;

public record GuardianCreatedDTO(Long id, String name, String phone, String city) {
    public GuardianCreatedDTO(Guardian guardian){
        this(guardian.getId(), guardian.getName(), guardian.getPhone(), guardian.getCity());
    }
}
