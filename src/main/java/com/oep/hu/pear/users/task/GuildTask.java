package com.oep.hu.pear.users.task;

import com.oep.hu.pear.users.application.GuildService;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.data.PersonRepository;
import com.oep.hu.pear.users.domain.*;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GuildTask {

    private StudentService studentService;
    private GuildService service;
    private PersonService personService;
    private PersonRepository<InternalPerson> internalPersonPersonRepository;
    private PersonRepository<Teacher> teacherPersonRepository;
    private PersonRepository<Apprentice> apprenticePersonRepository;
    private PersonRepository<Student> studentPersonRepository;


    public Guild getMyGuild(String user) {
        return ((InternalPerson) Hibernate.unproxy(this.internalPersonPersonRepository.getReferenceById(user))).getMyGuild();
    }

    public List<String> getGuildNames() {
        return this.service.getAllNames();
    }

    public Guild getById(String id) {
        return this.service.getGuildByName(id);
    }

    @Transactional
    public void addMaster(String guild, String teacherEmail) throws Exception {
        this.service.addMaster(guild, (Teacher) Hibernate.unproxy(this.teacherPersonRepository.getReferenceById(teacherEmail)));
    }

    @Transactional
    public void promoteStudentToApprentice(String guild, String studentEmail) throws Exception {
        this.personService.setStudentToApprentice(studentEmail);
    }

    @Transactional
    public void addStudent(String guild, String studentEmail) throws Exception {
        this.service.addStudent(guild, this.studentPersonRepository.getReferenceById(studentEmail));
    }

    public void createGuild(String guildName) {
        this.service.createGuild(guildName);
    }

    public void changeGuild(String email, String guild) {
        InternalPerson person = ((InternalPerson) Hibernate.unproxy(this.internalPersonPersonRepository.getReferenceById(email)));
        person.setMyGuild(this.getById(guild));
        this.internalPersonPersonRepository.save(person);
    }
}
