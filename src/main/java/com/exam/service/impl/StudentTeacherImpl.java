package com.exam.service.impl;

import com.exam.model.user.StudentTeacher;
import com.exam.model.user.User;
import com.exam.repo.user.StudentTeacherRepository;
import com.exam.repo.user.UserRepository;
import com.exam.service.StudentTeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentTeacherImpl implements StudentTeacherService {

    @Autowired
    private StudentTeacherRepository studentTeacherRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public StudentTeacher createRelationshipBetweenStudentAndTeacher(Long studentID, Long teacherID) {
//        StudentTeacher studentTeacher ;
    User student =
        userRepository.findById(studentID).orElseThrow(() -> new IllegalStateException("User with this id not found "));
    User teacher = userRepository.findById(teacherID).orElseThrow(() -> new IllegalStateException("User with this id not found "));
        if (!studentTeacherRepository.existsByStudentAndTeacher(student, teacher)) {
            StudentTeacher studentTeacher = new StudentTeacher();
            studentTeacher.setStudent(student);
            studentTeacher.setTeacher(teacher);
            studentTeacherRepository.save(studentTeacher);
            return studentTeacher;
        }
        throw new IllegalStateException("Student Already Registered With Teacher");

    }

    @Override
    public List<User> getStudentsOfTeacher(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        List<StudentTeacher> studentTeacherList = studentTeacherRepository.findByTeacher(teacher);
        List<User> students = new ArrayList<>();

        for (StudentTeacher studentTeacher : studentTeacherList) {
            students.add(studentTeacher.getStudent());
        }

        return students;
    }

}
