package br.com.mascarenhasb2.adopet.infra.exception.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreatedDTO;

import java.util.List;

public record SingleResponseDTO(String code, String message, ReturnInformationDTO data) {
}
