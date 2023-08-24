package com.exam.model.quiz;

import com.exam.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_quiz")
public class UserQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int marks;
    private int timeDurationInMinutes;
    private boolean isCompleted;

    public UserQuiz(User user, Quiz quiz, int marks, int timeDurationInMinutes, boolean isCompleted) {
        this.user = user;
        this.quiz = quiz;
        this.marks = marks;
        this.timeDurationInMinutes = timeDurationInMinutes;
        this.isCompleted = isCompleted;
    }
}

