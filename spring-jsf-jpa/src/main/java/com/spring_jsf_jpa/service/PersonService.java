package com.spring_jsf_jpa.service;

import java.util.List;

import com.spring_jsf_jpa.entity.Person;
import com.spring_jsf_jpa.entity.PersonTemp;
 
public interface PersonService {
 
    public void addPerson(Person p);
    public List<Person> listPersons();
    
    public void addPersonTemp(PersonTemp pt);
     
}