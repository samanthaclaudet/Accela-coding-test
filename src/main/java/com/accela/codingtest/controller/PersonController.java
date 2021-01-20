package com.accela.codingtest.controller;

import com.accela.codingtest.dao.IPersonDAO;
import com.accela.codingtest.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    private IPersonDAO personDAO;

    @GetMapping(value = "/count")
    public String getCountPersons() {
        return "There are currently " + personDAO.count() + " persons in the database.";
    }

    @GetMapping(value = "/persons")
    public Page<Person> getPersons(Pageable pageable) {
        return personDAO.findAll(pageable);
    }

    @GetMapping(value = "/persons/{idPerson}")
    public String getPerson(@PathVariable long idPerson) {
        Optional<Person> person = personDAO.findById(idPerson);
        return person.isPresent() ? person.get().toString() : "Person with id #" + idPerson + ": NOT FOUND";
    }
    
    @PostMapping("/persons")
    public String createPerson(@Valid @RequestBody Person person) {
    	person.setAddresses(new ArrayList<>());
    	Person personCreated = personDAO.save(person);
        return personCreated.toString() + " has been created successfully with id #" + personCreated.getId();
    }
    
    @PutMapping("/persons/{idPerson}")
    public String updatePerson(@PathVariable Long idPerson, @Valid @RequestBody Person personRequest) {
    	Optional<Person> person = personDAO.findById(idPerson);
    	if  (!person.isPresent()) {
    		return "Person with id #" + idPerson + ": NOT FOUND";
    	}
    	Person personToUpdate = person.get();
        personToUpdate.setFirstName(personRequest.getFirstName());
        personToUpdate.setLastName(personRequest.getLastName());
        return personDAO.save(personToUpdate).toString() + " has been updated successfully";
    }


    @DeleteMapping("/persons/{idPerson}")
    public String deletePerson(@PathVariable Long idPerson) {
    	Optional<Person> person = personDAO.findById(idPerson);
    	if  (!person.isPresent()) {
    		return "Person with id #" + idPerson + ": NOT FOUND";
    	}
    	Person personToUpdate = person.get();
    	personDAO.delete(personToUpdate);
        return "Person with id #" + idPerson + " has been deleted successfully.";
    }
}
