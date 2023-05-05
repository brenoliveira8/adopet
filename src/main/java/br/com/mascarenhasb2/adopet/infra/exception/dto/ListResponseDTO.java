package br.com.mascarenhasb2.adopet.infra.exception.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;

import java.util.List;

public record ListResponseDTO(String code, String message, List<? extends ReturnInformationDTO> data){
}
