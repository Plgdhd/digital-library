package com.bestdev.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bestdev.library.models.Book;
import com.bestdev.library.models.Person;

@Service
public class PeopleService {
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
            .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, birthday) VALUES(?,?)", person.getName(), person.getBirthday());
    }

    public void edit(Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, birthday=? WHERE id=?", person.getName(), person.getBirthday(), person.getId());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public Optional<Person> getPersonByName(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
            .stream().findAny();
    }

    public List<Book> getBooksByPerson(int personID){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personID}, new BeanPropertyRowMapper<>(Book.class));
    }
}
