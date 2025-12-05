//package com.example.quanlysinhvien.validation;
//
//import com.example.cruddemo.repository.PostRepository;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.AllArgsConstructor;
//
//import java.util.UUID;
//@AllArgsConstructor
//public class PostIdExistsValidator implements ConstraintValidator<PostIdExists, UUID>{
//
//    private PostRepository postRepository;
//
//    @Override
//    public boolean isValid(UUID id, ConstraintValidatorContext context) {
//        return postRepository.existsById(id);
//    }
//
//}
