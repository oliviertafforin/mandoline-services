package fr.oliweb.mandoline;

import fr.oliweb.mandoline.controller.IngredientController;
import fr.oliweb.mandoline.controller.RecetteController;
import fr.oliweb.mandoline.controller.UtilisateurController;
import fr.oliweb.mandoline.model.UtilisateurDb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MandolineApplicationTests {

	@Autowired
	private IngredientController ingredientController;

	@Autowired
	private RecetteController recetteController;

	@Autowired
	private UtilisateurController utilisateurController;

	@Test
	@DisplayName("Test application context start")
	void contextLoads() {
		assertThat(ingredientController).isNotNull();
		assertThat(recetteController).isNotNull();
		assertThat(utilisateurController).isNotNull();
	}

}
