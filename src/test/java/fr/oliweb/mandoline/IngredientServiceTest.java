package fr.oliweb.mandoline;

import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.repository.ImageRepository;
import fr.oliweb.mandoline.repository.IngredientRepository;
import fr.oliweb.mandoline.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IngredientServiceTest {

    @MockitoBean
    private IngredientRepository ingredientRepository;

    @MockitoBean
    private ImageRepository imageRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test d'appel au endpoint getAllIngredients")
    void shouldReturnNomIngredient() throws Exception {
        IngredientDb db = new IngredientDb();
        String nom = "testingredient";
        db.setId(UUID.randomUUID());
        db.setNom(nom);
        List<IngredientDb> ingredients = List.of(db);
        when(ingredientRepository.findAll()).thenReturn(ingredients);
        this.mockMvc.perform(get("/api/ingredient")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(nom)));
    }

    @Test
    public void testSupprimerIngredient() {
        UUID id = UUID.randomUUID();

        when(ingredientRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> ingredientService.supprimerIngredient(id));

        verify(ingredientRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSupprimerIngredientNonExistant() {
        UUID id = UUID.randomUUID();

        when(ingredientRepository.existsById(id)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ingredientService.supprimerIngredient(id);
        });

        assertEquals("Impossible de supprimer l'ingrédient car il est introuvable", exception.getMessage());
    }
}
