package com.exam.repo.user;

import com.exam.model.user.StudentTeacher;
import com.exam.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTeacherRepository extends JpaRepository<StudentTeacher,Long> {


    List<StudentTeacher> findByTeacher(User teacher);

    boolean existsByStudentAndTeacher(User student, User teacher);


}
