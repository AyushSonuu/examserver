package com.exam.repo.quiz;

import com.exam.model.quiz.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuizRepository extends JpaRepository<UserQuiz,Long> {}
