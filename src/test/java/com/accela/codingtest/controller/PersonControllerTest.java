package com.accela.codingtest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.accela.codingtest.dao.IPersonDAO;
import com.accela.codingtest.model.Address;
import com.accela.codingtest.model.Person;

@SpringBootTest
public class PersonControllerTest {

	@MockBean
	private IPersonDAO personDAO;

	@Autowired
	private PersonController personController;
	
	private List<Person> persons;
	private Person newPerson;

	@BeforeEach
	public void createElements() {
		Person person1 = new Person("Jane", "Doe");
		person1.setId(1);
		
		Person person2 = new Person("John", "Doe");
		person2.setId(2);
		
		Address address = new Address();
		address.setId(1);
		address.setStreet("street example");
		address.setCity("generic city");
		address.setState("random state");
		address.setPostalCode("10101");
		address.setPerson(person1);
		person1.getAddresses().add(address);
		
		persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		
		newPerson = new Person("Jack", "Smith");
    	newPerson.setId(3);
	}
	
    @Test
    public void getCountPersonsTest() {
        Mockito.when(personDAO.count()).thenReturn((long) persons.size());
        String countPersons = personController.getCountPersons();
        assertEquals("There are currently 2 persons in the database.", countPersons);
    }
    
    @Test
    public void getPersonsTest() {
        Mockito.when(personDAO.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<Person>(persons, Pageable.unpaged(), 2));
        Page<Person> personsReturned = personController.getPersons(Pageable.unpaged());
        assertEquals(personsReturned.getContent(), persons);
    }
    
    @Test
    public void getPerson1Test() {
        Mockito.when(personDAO.findById((long) 1)).thenReturn(Optional.of(persons.get(0)));
        String personReturned = personController.getPerson((long) 1);
        assertEquals("Jane Doe, addresses: [address: street example, 10101 generic city (random state)]", personReturned);
    }
    
    @Test
    public void createPersonTest() {
        Mockito.when(personDAO.save(newPerson)).thenReturn(newPerson);
        String personCreated = personController.createPerson(newPerson);
        assertEquals("Jack Smith, addresses: [unknown] has been created successfully with id #3", personCreated);
    }
    
    @Test
    public void updatePersonNotFoundTest() {
        Mockito.when(personDAO.findById((long) 4)).thenReturn(Optional.empty());
        String personUpdated = personController.updatePerson((long) 4, new Person());
        assertEquals("Person with id #4: NOT FOUND", personUpdated);
    }
    
    @Test
    public void updatePersonTest() {
        Mockito.when(personDAO.findById((long) 2)).thenReturn(Optional.of(persons.get(1)));
        Mockito.when(personDAO.save(Mockito.any(Person.class))).thenReturn(newPerson);
        String personUpdated = personController.updatePerson((long) 2, newPerson);
        assertEquals("Jack Smith, addresses: [unknown] has been updated successfully", personUpdated);
    }
    
    @Test
    public void deletePersonNotFoundTest() {
        Mockito.when(personDAO.findById((long) 4)).thenReturn(Optional.empty());
        String personDeleted = personController.deletePerson((long) 4);
        assertEquals("Person with id #4: NOT FOUND", personDeleted);
    }
    
    @Test
    public void deletePersonTest() {
        Mockito.when(personDAO.findById((long) 1)).thenReturn(Optional.of(persons.get(0)));
        String personDeleted = personController.deletePerson((long) 1);
        assertEquals("Person with id #1 has been deleted successfully.", personDeleted);
    }
}
