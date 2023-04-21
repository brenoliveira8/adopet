package br.com.mascarenhasb2.adopet.infra.exception.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;

public record SingleResponseDTO(String code, String message, ReturnInformationDTO data) {
}
