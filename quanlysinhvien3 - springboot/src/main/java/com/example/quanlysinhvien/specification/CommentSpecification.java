//package com.example.quanlysinhvien.specification;
//
//import com.example.cruddemo.entity.Comment;
//import com.example.cruddemo.form.CommentFilterForm;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//
//public class CommentSpecification {
//    public static Specification<Comment> builSpec(CommentFilterForm form) {
//        return form == null ? null : new Specification<Comment>() {
//
//            @Override
//            public Predicate toPredicate(
//                    Root<Comment> root,
//                    CriteriaQuery<?> query,
//                    CriteriaBuilder builder) {
//
//                var predicates = new ArrayList<Predicate>();
//                String search = form.getSearch();
//                if (search != null) {
//                    var hasNameLike = builder.like(root.get("name"), "%" + search + "%");
//                    var email = builder.like(root.get("email"), "%" + search + "%");
//                    var predicate = builder.or(hasNameLike, email);
//
//                    predicates.add(predicate);
//                }
//
//                var postId = form.getPostId();
//                if (postId != null) {
//                    var predicate = builder.equal(root.get("post").get("id"), postId);
//                    predicates.add(predicate);
//
//                }
//
//                return builder.and(predicates.toArray(new Predicate[0]));
//
//            }
//
//        };
//    }
//}
