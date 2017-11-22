package com.spring_jsf_jpa.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring_jsf_jpa.dao.PersonDAOImpl;
import com.spring_jsf_jpa.entity.Person;
import com.spring_jsf_jpa.entity.PersonTemp;

@Component("personService")
@ManagedBean(name = "personService")
@SessionScoped
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDAOImpl personDAO;

	public void addPerson(Person p) {
		personDAO.addPerson(p);
	}

	public List<Person> listPersons() {
		return personDAO.listPersons();
	}

	@Override
	public void addPersonTemp(PersonTemp pt) {
		personDAO.addPersonTemp(pt);		
	}

}
