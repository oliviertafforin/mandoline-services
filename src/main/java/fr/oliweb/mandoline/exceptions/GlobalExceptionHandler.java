package fr.oliweb.mandoline.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static fr.oliweb.mandoline.exceptions.ExceptionMessages.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ReponseException buildErrorResponse(HttpStatus status, String error, String message) {
        return new ReponseException(LocalDateTime.now(), status.value(), error, message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ReponseException handleRessourceIntrouvableException(RessourceIntrouvableException rie) {
        logger.warn(RESSOURCE_INTROUVABLE + " : {}", rie.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, RESSOURCE_INTROUVABLE.toString(), rie.getMessage());
    }

    @ExceptionHandler(RequeteInvalideException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReponseException handleInvalidRequestException(RequeteInvalideException rie) {
        logger.warn(REQUETE_INVALIDE + " : {}", rie.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, REQUETE_INVALIDE.toString(), rie.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ReponseException handleIOException(IOException ex) {
        String message = "Erreur d'entrée/sortie : " + ex.getMessage();
        logger.error(message, ex);

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erreur serveur",
                message
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RessourceEnDoubleException.class)
    public ReponseException resourceAlreadyExistException(RessourceEnDoubleException exception)
    {
        logger.error(exception.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), "null");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReponseException handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        logger.error("Erreur de validation : {}", message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, VALIDATION_ECHOUEE.toString(), message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ReponseException handleInvalidJson(HttpMessageNotReadableException ex) {
        logger.error("JSON illisible : {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, REQUETE_INVALIDE.toString(), "Le corps de la requête est mal formé");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ReponseException handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + " : " + v.getMessage())
                .collect(Collectors.joining(", "));
        logger.error("Violation de contrainte : {}", message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, VIOLATION_CONTRAINTE.toString(), message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ReponseException handleConstraintViolation(HandlerMethodValidationException hmve) {
        String message = hmve.getValueResults()
                .stream()
                .map(v -> ((DefaultMessageSourceResolvable) v.getResolvableErrors().get(0).getArguments()[0]).getDefaultMessage() + " " + v.getResolvableErrors().get(0).getDefaultMessage())
                .collect(Collectors.joining(", "));
        logger.error("Violation de contrainte : {}", message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, VIOLATION_CONTRAINTE.toString(), message);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ReponseException handleMissingParam(MissingServletRequestParameterException ex) {
        String message = "Paramètre requis manquant : " + ex.getParameterName();
        logger.error(message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, PARAMETRE_MANQUANT.toString(), message);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ReponseException handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        String message = "Méthode " + ex.getMethod() + " non supportée pour cette URL";
        logger.error(message);
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ExceptionMessages.METHODE_NON_AUTORISEE.toString(), message);
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ReponseException maxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        logger.error("Taille d'upload max dépassée", e);
        return buildErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE, ERREUR_INTERNE.toString(), "Taille d'upload max. dépassée");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ReponseException handleGenericException(Exception ex) {
        logger.error(ERREUR_INTERNE + " : {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.ERREUR_INTERNE.toString(), ex.getMessage());
    }
}