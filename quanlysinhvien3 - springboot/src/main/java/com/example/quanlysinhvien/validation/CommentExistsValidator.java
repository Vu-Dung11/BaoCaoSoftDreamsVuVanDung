//package com.example.quanlysinhvien.validation;
//
//import com.example.cruddemo.repository.CommentRepository;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.AllArgsConstructor;
//
//import java.util.UUID;
//
//@AllArgsConstructor
//public class CommentExistsValidator
//        implements ConstraintValidator<CommentIdExists, UUID> {
//
//    private CommentRepository commentRepository;
//
//    @Override
//    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
//        return commentRepository.existsById(uuid);
//    }
//}
