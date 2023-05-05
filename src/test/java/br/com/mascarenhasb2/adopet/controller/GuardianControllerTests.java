package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreatedDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.model.user.dto.UserDTO;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import br.com.mascarenhasb2.adopet.infra.exception.ErrorDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class GuardianControllerTests{
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GuardianRepository guardianRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private JacksonTester<GuardianCreationDTO> creationGuardianJson;
    @Autowired
    private JacksonTester<SingleResponseDTO> singleResponseJson;
    @Autowired
    private JacksonTester<ListResponseDTO> listOfGuardiansDetailsJson;
    @Autowired
    private JacksonTester<ErrorDTO> errorJson;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should return HTTP 200 and the data of the guardian when the guardian object was found by calling the readById(id) method")
    @WithMockUser(roles = "ADMIN")
    void testReadByIdWhenIdIsValidAndGuardianExists() throws Exception{
        var guardian = createOneGuardian();
        var expectedJson = createSingleResponseExpectedJson(
                HttpStatus.OK.value(),
                "Operação realizada com sucesso!",
                new GuardianDetailsDTO(guardian)
        );

        when(guardianRepository.getReferenceById(any())).thenReturn(guardian);
        var response = simulateGetRequest("/1");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Should return HTTP 404 and empty body when the guardian was not found by calling the readById(id) method")
    @WithMockUser(roles = "ADMIN")
    void testReadByIdWhenIdIsValidAndGuardianWasNotFound() throws Exception{
        when(guardianRepository.getReferenceById(any())).thenThrow(EntityNotFoundException.class);
        var response = simulateGetRequest("/1");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEmpty();
    }

    @Test
    @DisplayName("Should return HTTP 400 and a message in the body when the argument is invalid by calling the readById(id) method")
    @WithMockUser(roles = "ADMIN")
    void testReadByIdWhenIdIsInvalid() throws Exception{
        var response = simulateGetRequest("/a");
        var expectedErrorJson = createExpectedJsonWhenIdIsInvalid();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEqualTo(expectedErrorJson);
    }

    @Test
    @DisplayName("Should return HTTP 200 when at least one guardian exists while calling the read() method")
    @WithMockUser(roles = "ADMIN")
    void testReadWhenDataExists() throws Exception{
        var guardians = createListOfGuardians();
        String expectedJson = createExpectedJsonWhenReadIsSuccessful(guardians);

        when(guardianRepository.findAll()).thenReturn(guardians);
        var response = simulateGetRequest("");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Should return HTTP 404 when it has no guardian while calling the read() method")
    @WithMockUser(roles = "ADMIN")
    void testReadWhenItHasNoGuardian() throws Exception{
        List<Guardian> guardians = Collections.emptyList();

        when(guardianRepository.findAll()).thenReturn(guardians);
        var response = simulateGetRequest("");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEmpty();
    }

    @Test
    @DisplayName("Should return HTTP 204 when the delete operation was successful by calling the delete(id) method")
    @WithMockUser(roles = "ADMIN")
    void testDeleteByIdWhenIdIsValidAndGuardianExists() throws Exception{
        Guardian guardian = createOneGuardian();

        when(guardianRepository.existsById(any())).thenReturn(Boolean.TRUE);
        when(guardianRepository.findById(any())).thenReturn(Optional.of(guardian));
        var response = simulateDeleteRequest("/1");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEmpty();
    }

    @Test
    @DisplayName("Should return HTTP 404 when the id passed was not found by calling the delete(id) method")
    @WithMockUser(roles = "ADMIN")
    void testDeleteByIdWhenIdWasNotFound() throws Exception{
        when(guardianRepository.existsById(any())).thenReturn(Boolean.FALSE);
        var response = simulateDeleteRequest("/2");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEmpty();
    }

    @Test
    @DisplayName("Should return HTTP 201 and data of the guardian when the information is valid by calling the create() method")
    @WithMockUser(roles = "ADMIN")
    void testCreatingNewGuardianWhenInformationIsValid() throws Exception{
        var userDTO = new UserDTO(
                "breno@adopet.com",
                "123456"
        );
        var encodedPassword = passwordEncoder.encode(userDTO.password());
        var user = new User(1L, userDTO.email(), encodedPassword, Role.GUARDIAN);
        var newGuardian = new GuardianCreationDTO(
                "Breno Mascarenhas",
                "3799998888",
                "Cláudio-MG",
                userDTO,
                null,
                null
        );
        var guardian = new Guardian(newGuardian, user);
        var expectedJson = createSingleResponseExpectedJson(
                HttpStatus.CREATED.value(),
                "Tutor criado com sucesso!",
                new GuardianCreatedDTO(guardian)
        );

        when(userRepository.save(any())).thenReturn(user);
        when(guardianRepository.save(any())).thenReturn(guardian);
        var response = simulatePostRequest(newGuardian);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString(Charset.defaultCharset())).isEqualTo(expectedJson);
    }

    private Guardian createOneGuardian(){
        return new Guardian(
                1L,
                "Breno Mascarenhas Oliveira",
                "37999998888",
                "Claudio-MG",
                null,
                null,
                new User(
                        1L,
                        "breno@adopet.com",
                        "123456",
                        Role.GUARDIAN
                )
        );
    }

    private String createSingleResponseExpectedJson(int statusCode, String message, ReturnInformationDTO returnInformation) throws IOException{
        var expectedResponse = new SingleResponseDTO(
                String.valueOf(statusCode),
                message,
                returnInformation
        );
        return singleResponseJson.write(
                expectedResponse
        ).getJson();
    }

    private MockHttpServletResponse simulateGetRequest(String idTested) throws Exception{
        return mvc.perform(get("/tutores" + idTested))
                .andReturn()
                .getResponse();
    }

    private MockHttpServletResponse simulateDeleteRequest(String idTested) throws Exception{
        return mvc.perform(delete("/tutores" + idTested))
                .andReturn()
                .getResponse();
    }

    private MockHttpServletResponse simulatePostRequest(GuardianCreationDTO guardian) throws Exception{
        return mvc.perform(post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creationGuardianJson.write(
                                guardian
                        ).getJson()))
                .andReturn()
                .getResponse();
    }

    private String createExpectedJsonWhenIdIsInvalid() throws IOException{
        var expectedResponse = new ErrorDTO(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "id passed is invalid."
        );
        return errorJson.write(
                expectedResponse
        ).getJson();
    }

    private String createExpectedJsonWhenReadIsSuccessful(List<Guardian> guardians) throws IOException{
        var expectedResponse = new ListResponseDTO(
                String.valueOf(HttpStatus.OK.value()),
                "Consulta realizada com sucesso!",
                guardians.stream().map(GuardianDetailsDTO::new).toList()
        );
        return listOfGuardiansDetailsJson.write(
                expectedResponse
        ).getJson();
    }

    List<Guardian> createListOfGuardians(){
        var guardian1 = new Guardian(
                1L,
                "Breno Mascarenhas Oliveira",
                "37999998888",
                "Claudio-MG",
                null,
                null,
                new User(
                        1L,
                        "breno@adopet.com",
                        "123456",
                        Role.GUARDIAN
                )
        );
        var guardian2 = new Guardian(
                1L,
                "Nayra Rocha de Sousa",
                "37888889999",
                "Divinópolis-MG",
                null,
                null,
                new User(
                        2L,
                        "nayra@adopet.com",
                        "123456",
                        Role.GUARDIAN
                )
        );
        List<Guardian> guardians = new ArrayList<>();
        guardians.add(guardian1);
        guardians.add(guardian2);

        return guardians;
    }

}
