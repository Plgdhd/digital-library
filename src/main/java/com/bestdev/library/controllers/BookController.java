package com.bestdev.library.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bestdev.library.models.Book;
import com.bestdev.library.models.Person;
import com.bestdev.library.services.BookService;
import com.bestdev.library.services.PeopleService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService; 
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService booksService, PeopleService peopleService){
        this.peopleService = peopleService;
        this.bookService = booksService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookService.show(id));

        Optional<Person> bookOwner = bookService.getOwner(id);
        
        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        }
        else{
            model.addAttribute("people", peopleService.index());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
    
    @PostMapping()
    public String create(@ModelAttribute("book") Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    //с thymeleaf patch какого то хера не работает, поэтому заменил патчи на post :/
    @PostMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                        @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.edit(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }
    @PostMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookService.setPersonToBook(id, person);
        return "redirect:/books/" + id;
    }
}
