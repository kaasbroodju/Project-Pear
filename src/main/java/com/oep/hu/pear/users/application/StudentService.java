package com.oep.hu.pear.users.application;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.users.data.StudentRepository;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository repository;

    public Student createStudent(String email) {
        return this.repository.save(new Student(email));
    }

    public Set<Form> myDrafts(String email) {
        return this.repository.getReferenceById(email).getCurrentBook().getDrafts();
//        return this.repository.findDrafts(FormStatus.CONCEPT, email);
    }

    public Student addFormToStudent(String email, Form form) {
        Student student = this.repository.getReferenceById(email);
        student.getCurrentBook().getForms().add(form);
        return this.repository.save(student);
    }

    public Student getStudentByEmail(String email) {
        return this.repository.getReferenceById(email);
    }

    public Student getStudentOfForm(Form form)  {
        return this.repository.findByBooks_Forms(form);
    }

    public long deleteFormFromBook(long formId) {
        return this.repository.deleteByBooks_Forms_Id(formId);
    }

    public List<Student> getStudentsByEmail(List<String> emails) {
        return this.repository.findAllById(emails);
    }

    public Student update(Student student) {
        return this.repository.save(student);
    }

    public List<Student> searchStudentsByEmail(String email) {
        email = "%" + email + "%";
        return this.repository.findByEmailIsLikeIgnoreCaseOrderByEmailAsc(email);
    }
}
