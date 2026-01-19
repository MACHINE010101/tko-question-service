package com.edu.question_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Question title is required")
    @Size(min = 10, max = 500, message = "Question title must be between 10 and 500 characters")
    private String questionTitle;
    
    @NotBlank(message = "Option 1 is required")
    @Size(max = 200, message = "Option 1 must not exceed 200 characters")
    private String option1;
    
    @NotBlank(message = "Option 2 is required")
    @Size(max = 200, message = "Option 2 must not exceed 200 characters")
    private String option2;
    
    @NotBlank(message = "Option 3 is required")
    @Size(max = 200, message = "Option 3 must not exceed 200 characters")
    private String option3;
    
    @NotBlank(message = "Option 4 is required")
    @Size(max = 200, message = "Option 4 must not exceed 200 characters")
    private String option4;
    
    @NotBlank(message = "Right answer is required")
    @Size(max = 200, message = "Right answer must not exceed 200 characters")
    private String rightAnswer;
    
    @NotBlank(message = "Difficulty level is required")
    @Pattern(regexp = "^(Easy|Medium|Hard)$", message = "Difficulty level must be Easy, Medium, or Hard")
    @Column(name = "difficultylevel")  // Keep DB column name for backward compatibility
    private String difficultyLevel;
    
    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;
}
