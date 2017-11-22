package com.spring_jsf_jpa.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.spring_jsf_jpa.entity.Person;
import com.spring_jsf_jpa.entity.PersonTemp;

@Named(value = "personDAOImpl")
@Transactional
public class PersonDAOImpl {

	@PersistenceContext(unitName = "primaryDbEntityManagerFactory")
	private EntityManager em;

	public void addPerson(Person p) {
		em.persist(p);
	}
	
	public void addPersonTemp(PersonTemp pt) {
		em.persist(pt);
	}

	public List<Person> listPersons() {
		List<Person> personsList = em.createQuery("from Person").getResultList();
		return personsList;
	}

}
