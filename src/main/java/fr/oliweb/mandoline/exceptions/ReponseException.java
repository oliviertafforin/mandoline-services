package fr.oliweb.mandoline.exceptions;

import java.time.LocalDateTime;

public record ReponseException(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}
