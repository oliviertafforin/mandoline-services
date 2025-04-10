package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.model.RecetteDb;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecetteSpecificationBuilder {

    public static Specification<RecetteDb> build(List<String> criteresSimples) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!criteresSimples.isEmpty()) {
                CriteriaBuilder.In<String> inClause = cb.in(root.get("categorie"));
                criteresSimples.forEach(inClause::value);
                predicates.add(inClause);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}