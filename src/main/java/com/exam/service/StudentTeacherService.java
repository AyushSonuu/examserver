package com.exam.service;

import com.exam.model.user.StudentTeacher;
import com.exam.model.user.User;

import java.util.List;

public interface StudentTeacherService {

    public StudentTeacher createRelationshipBetweenStudentAndTeacher(Long studentID, Long teacherID);
    public List<User> getStudentsOfTeacher(Long teacherId);

}
