package com.bestdev.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bestdev.library.models.Person;
import com.bestdev.library.services.PeopleService;

@Component
public class PersonValidator implements Validator{

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass){
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(peopleService.getPersonByName(person.getName()).isPresent()){
            errors.rejectValue("name", "", "Имя занято");
        }
    }
}
