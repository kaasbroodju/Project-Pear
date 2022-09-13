package com.oep.hu.pear.users.application;

import com.oep.hu.pear.users.data.GuildRepository;
import com.oep.hu.pear.users.domain.Apprentice;
import com.oep.hu.pear.users.domain.Guild;
import com.oep.hu.pear.users.domain.Student;
import com.oep.hu.pear.users.domain.Teacher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuildService {

    private GuildRepository repository;

    public Guild createGuild(String name) {
        return this.repository.save(new Guild(name));
    }

    // todo remove transactional on all methods
    @Transactional
    public void addMaster(String name, Teacher master) {
        Guild guild = repository.getReferenceById(name);
        guild.addPerson(master);
//        return repository.save(guild);
    }

    @Transactional
    public void addApprentice(String name, Apprentice apprentice) {
        Guild guild = this.repository.getReferenceById(name);
        guild.addPerson(apprentice);
//        this.repository.save(guild);
    }

    public void removeApprentice(String name, Apprentice apprentice) {
        Guild guild = this.repository.getReferenceById(name);
        guild.removePerson(apprentice);
//        return this.repository.save(guild);
    }

    @Transactional
    public void addStudent(String name, Student student) {
        Guild guild = this.repository.getReferenceById(name);
        guild.addPerson(student);
//        return this.repository.save(guild);
    }

    @Transactional
    public Guild removeStudent(String name, Student student) {
        Guild guild = this.repository.getReferenceById(name);
        guild.removePerson(student);
        return this.repository.save(guild);
    }

    public List<String> getAllNames() {
        return this.repository.findAll().stream().map(Guild::getName).toList();
    }

    public Guild getGuildByName(String name) {
        return this.repository.getReferenceById(name);
    }
}
