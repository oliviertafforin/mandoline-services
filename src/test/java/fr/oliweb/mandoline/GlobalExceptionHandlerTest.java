package fr.oliweb.mandoline;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.oliweb.mandoline.exceptions.GlobalExceptionHandler;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@WebMvcTest(ControllerFactice.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn404WhenResourceNotFound() throws Exception {
        mockMvc.perform(get("/dummy/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Ressource introuvable"))
                .andExpect(jsonPath("$.message").value("Ressource non trouvée"));
    }

    @Test
    void shouldReturn400ForValidationError() throws Exception {
        InputFactice invalidInput = new InputFactice(); // name == null
        String body = objectMapper.writeValueAsString(invalidInput);

        mockMvc.perform(post("/dummy/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation échouée"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturn400ForConstraintViolation() throws Exception {
        mockMvc.perform(get("/dummy/constraint")
                        .param("value1", "0")
                        .param("value2", "999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Violation de contrainte"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturn400ForMalformedJson() throws Exception {
        String invalidJson = "{ name: }"; // JSON invalide

        mockMvc.perform(post("/dummy/malformed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Requête invalide"))
                .andExpect(jsonPath("$.message").value("Le corps de la requête est mal formé"));
    }

    @Test
    void shouldReturn500ForGenericError() throws Exception {
        mockMvc.perform(get("/dummy/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Erreur interne"))
                .andExpect(jsonPath("$.message").value("Test erreur interne"));
    }

    static class InputFactice {
        @NotBlank
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
