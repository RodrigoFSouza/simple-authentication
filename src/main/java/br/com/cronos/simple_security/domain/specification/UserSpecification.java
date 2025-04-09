package br.com.cronos.simple_security.domain.specification;

import br.com.cronos.simple_security.domain.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterBy(String firstname, String lastname, String email, Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(firstname)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(lastname)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(email)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (enabled != null) {
                predicates.add(criteriaBuilder.equal(root.get("isEnabled"), enabled));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
} 