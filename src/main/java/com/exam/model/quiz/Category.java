package com.exam.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;
    private String title;
    private String Description;

    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    private Set<Quiz> quizzes = new LinkedHashSet<Quiz>();

    public Category(String title, String description, Set<Quiz> quizzes) {
        this.title = title;
        Description = description;
        this.quizzes = quizzes;
    }
}
