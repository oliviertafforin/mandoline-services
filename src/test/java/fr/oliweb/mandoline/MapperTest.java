package fr.oliweb.mandoline;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.mappers.IngredientMapper;
import fr.oliweb.mandoline.mappers.RecetteMapper;
import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.model.RecetteDb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static fr.oliweb.mandoline.mappers.RecetteMapper.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MapperTest {

    @Nested
    @DisplayName("Tests pour le mapping des objets Ingredient")
    class IngredientTest {

        private IngredientDb createIngredientDb() {
            IngredientDb db = new IngredientDb();
            db.setId(UUID.randomUUID());
            db.setNom("Ingredient Test");
            return db;
        }

        private IngredientDTO createIngredientDTO() {
            IngredientDTO dto = new IngredientDTO();
            dto.setId(UUID.randomUUID());
            dto.setNom("Ingredient Test");
            return dto;
        }

        @Test
        public void toDtoTest() {
            IngredientDb ingredientDb = createIngredientDb();
            IngredientDTO result = IngredientMapper.toDto(ingredientDb);

            assertNotNull(result);
            assertEquals(ingredientDb.getId(), result.getId());
            assertEquals(ingredientDb.getNom(), result.getNom());
        }

        @Test
        public void toDbTest() {
            IngredientDTO ingredientDTO = createIngredientDTO();
            IngredientDb result = IngredientMapper.toDb(ingredientDTO);

            assertNotNull(result);
            assertEquals(ingredientDTO.getId(), result.getId());
            assertEquals(ingredientDTO.getNom(), result.getNom());
        }

        @Test
        public void toDtoListTest() {
            IngredientDb db1 = createIngredientDb();
            IngredientDb db2 = createIngredientDb();
            List<IngredientDb> dbs = Arrays.asList(db1, db2);

            List<IngredientDTO> result = IngredientMapper.toDtoList(dbs);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(db1.getNom(), result.get(0).getNom());
            assertEquals(db2.getNom(), result.get(1).getNom());
        }

        @Test
        public void toDbListTest() {
            IngredientDTO dto1 = createIngredientDTO();
            IngredientDTO dto2 = createIngredientDTO();
            List<IngredientDTO> dtos = Arrays.asList(dto1, dto2);

            List<IngredientDb> result = IngredientMapper.toDbList(dtos);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(dto1.getNom(), result.get(0).getNom());
            assertEquals(dto2.getNom(), result.get(1).getNom());
        }

        @Test
        public void toDtoWithNullTest() {
            IngredientDTO result = IngredientMapper.toDto(null);
            assertNull(result);
        }

        @Test
        public void toDbWithNullTest() {
            IngredientDb result = IngredientMapper.toDb(null);
            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Tests pour le mapping des objets Recette")
    class RecetteTest {

        @Test
        public void toDtoTest() {
            RecetteDb recetteDb = createRecetteDb("recette test");

            RecetteDTO recetteDTO = toDto(recetteDb);

            assertNotNull(recetteDTO);
            assertEquals(recetteDb.getId(), recetteDTO.getId());
            assertEquals(recetteDb.getNom(), recetteDTO.getNom());
            assertEquals(recetteDb.getInstructions(), recetteDTO.getInstructions());
            assertEquals(recetteDb.getTemperature(), recetteDTO.getTemperature());
            assertEquals(recetteDb.getTpsPrepa(), recetteDTO.getTpsPrepa());
            assertEquals(recetteDb.getTpsCuisson(), recetteDTO.getTpsCuisson());
        }

        @Test
        public void toDbTest() {
            RecetteDTO recetteDTO = createRecetteDTO();

            RecetteDb recetteDb = toDb(recetteDTO);

            assertNotNull(recetteDb);
            assertEquals(recetteDTO.getId(), recetteDb.getId());
            assertEquals(recetteDTO.getNom(), recetteDb.getNom());
            assertEquals(recetteDTO.getInstructions(), recetteDb.getInstructions());
            assertEquals(recetteDTO.getTemperature(), recetteDb.getTemperature());
            assertEquals(recetteDTO.getTpsPrepa(), recetteDb.getTpsPrepa());
            assertEquals(recetteDTO.getTpsCuisson(), recetteDb.getTpsCuisson());
        }

        @Test
        public void toDtoListTest() {
            RecetteDb recetteDb1 = createRecetteDb("recette test 1");
            RecetteDb recetteDb2 = createRecetteDb("recette test 2");
            List<RecetteDb> recetteDbs = Arrays.asList(recetteDb1, recetteDb2);

            List<RecetteDTO> recetteDTOs = toDtoList(recetteDbs);

            assertNotNull(recetteDTOs);
            assertEquals(2, recetteDTOs.size());
            assertEquals("recette test 1", recetteDTOs.get(0).getNom());
            assertEquals("recette test 2", recetteDTOs.get(1).getNom());
        }

        @Test
        public void toDtoWithNullTest() {
            RecetteDTO recetteDTO = toDto(null);
            assertNull(recetteDTO);
        }

        @Test
        public void toDbWithNullTest() {
            RecetteDb recetteDb = toDb(null);
            assertNull(recetteDb);
        }

        private RecetteDb createRecetteDb(String nom) {
            RecetteDb recetteDb = new RecetteDb();
            recetteDb.setId(UUID.randomUUID());
            recetteDb.setNom(nom);
            recetteDb.setInstructions("Instructions");
            recetteDb.setTemperature(200);
            recetteDb.setTpsPrepa(30);
            recetteDb.setTpsCuisson(45);
            // Initialisez les autres champs nécessaires comme proprietaire, image, etc.
            return recetteDb;
        }

        private RecetteDTO createRecetteDTO() {
            RecetteDTO recetteDTO = new RecetteDTO();
            recetteDTO.setId(UUID.randomUUID());
            recetteDTO.setNom("Recette Test");
            recetteDTO.setInstructions("Instructions");
            recetteDTO.setTemperature(200);
            recetteDTO.setTpsPrepa(30);
            recetteDTO.setTpsCuisson(45);
            // Initialisez les autres champs nécessaires comme proprietaire, image, etc.
            return recetteDTO;
        }
    }
}
