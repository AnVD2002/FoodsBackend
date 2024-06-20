package com.example.Food.Service.Foods;

import com.example.Food.Entity.Comment;
import com.example.Food.Entity.Food.Foods;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;

public class FoodSpecification {
    public static Specification<Foods> hasCategory(Integer categoryId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("foodCategory").get("foodCategoryID"), categoryId) );
    }
    public static Specification<Foods> hasNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("foodName"), "%"+name.toLowerCase()+"%");
    }
    public static Specification<Foods> hasFoodID(Integer FoodID) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("foodID"), FoodID);
    }

    public static Specification<Foods> hasAverageRating(Double averageRating) {
        return (root, query, cb) -> {
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Comment> commentRoot = subquery.from(Comment.class);
            subquery.select(cb.avg(commentRoot.get("rating")))
                    .where(cb.equal(commentRoot.get("food").get("foodID"), root.get("foodID")));
            Expression<Double> avgRating = cb.round(subquery.getSelection(), 0);
            return cb.equal(avgRating, Math.round(averageRating));
        };
    }
    public static Specification<Foods> hasPagination(Pageable pageable) {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get("foodID")));
            return cb.createQuery().getRestriction();
        };
    }
}
