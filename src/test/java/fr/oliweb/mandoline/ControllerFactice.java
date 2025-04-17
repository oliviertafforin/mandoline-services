package fr.oliweb.mandoline;

import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dummy")
public class ControllerFactice {
    @GetMapping("/not-found")
    public void triggerNotFound() {
        throw new RessourceIntrouvableException("Ressource non trouvée");
    }

    @PostMapping("/validation")
    public void triggerValidation(@Valid @RequestBody GlobalExceptionHandlerTest.InputFactice input) {
    }

    @GetMapping("/constraint")
    public void triggerConstraint(@RequestParam @Min(1) @Valid int value1, @RequestParam @Max(10) @Valid int value2) {
    }

    @PostMapping("/malformed")
    public void triggerJsonError(@RequestBody GlobalExceptionHandlerTest.InputFactice input) {
    }

    @GetMapping("/generic")
    public void triggerGeneric() {
        throw new RuntimeException("Test erreur interne");
    }


}
