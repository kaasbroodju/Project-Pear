package com.oep.hu.pear.users.application;

import com.oep.hu.pear.users.data.TeacherRepository;
import com.oep.hu.pear.users.domain.Teacher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {

    private TeacherRepository repository;

    public Teacher createTeacher(String email) {
        return this.repository.save(new Teacher(email));
    }

    public Teacher getTeacher(String email) {
        return this.repository.getReferenceById(email);
    }

    public List<Teacher> getAllTeachers() {
        return this.repository.findAll();
    }

}
