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
public class BookService {
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
            .stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?,?,?)",book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void edit(int bookID, Book book){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?", book.getTitle(), book.getAuthor(), book.getYear(), bookID);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }


    public void setPersonToBook(int bookID, Person person){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", person.getId(), bookID);
    }

    public List<Book> getBooksByPerson(int personID){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personID}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getOwner(int bookID){ 
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id = ?", new Object[]{bookID}, new BeanPropertyRowMapper<>(Person.class))
            .stream().findAny();
    }

    public void release(int bookID){ 
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id=?", bookID);
    }



}
