package com.oep.hu.pear.users.task;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.users.application.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class DraftsTask {

    private StudentService studentService;

    public Set<Form> myDrafts(String user) {
        return this.studentService.myDrafts(user);
    }
}
