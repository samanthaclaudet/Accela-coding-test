package com.accela.codingtest.controller;

import com.accela.codingtest.dao.IAddressDAO;
import com.accela.codingtest.dao.IPersonDAO;
import com.accela.codingtest.model.Address;
import com.accela.codingtest.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*")
public class AddressController {

    @Autowired
    private IAddressDAO addressDAO;

    @Autowired
    private IPersonDAO personDAO;

    @GetMapping(value = "/addresses/{idAddress}")
    public String getAddress(@PathVariable long idAddress) {
        Optional<Address> address = addressDAO.findById(idAddress);
        return address.isPresent() ? address.get().toString() : "Address with id #" + idAddress + ": NOT FOUND";
    }

    @GetMapping(value = "/persons/{idPerson}/addresses")
    public Page<Address> getAllAddressesFromPerson(@PathVariable (value = "idPerson") Long idPerson, Pageable pageable) {
    	return addressDAO.findByPersonId(idPerson, pageable);
}

    @PostMapping(value = "/persons/{idPerson}/addresses")
    public String addAddress(@PathVariable (value = "idPerson") long idPerson, @Valid @RequestBody Address address) {
        Optional<Person> person = personDAO.findById(idPerson);

        if (!person.isPresent()) {
            return "Person with id #" + idPerson + ": NOT FOUND";
        }

        Person personToUpdate = person.get();
        address.setPerson(personToUpdate);
        return addressDAO.save(address).toString() + " has been added successfully";
    }

    @PutMapping("/persons/{idPerson}/addresses/{idAddress}")
    public String updateAddress(@PathVariable (value = "idPerson") Long idPerson, @PathVariable (value = "idAddress") Long idAddress, 
    		@Valid @RequestBody Address addressRequest) {
        if(!personDAO.existsById(idPerson)) {
        	return "Person with id #" + idPerson + ": NOT FOUND";
        }
        Optional<Address> address = addressDAO.findById(idAddress);
        if(!address.isPresent()) {
        	return "Address with id #" + idAddress + ": NOT FOUND";
        }

    	Address addressToUpdate = address.get();
    	addressToUpdate.setStreet(addressRequest.getStreet());
    	addressToUpdate.setCity(addressRequest.getCity());
    	addressToUpdate.setState(addressRequest.getState());
    	addressToUpdate.setPostalCode(addressRequest.getPostalCode());
        return addressDAO.save(addressToUpdate).toString() + " has been updated successfully";
    }

    @DeleteMapping("/persons/{idPerson}/addresses/{idAddress}")
    public String deleteAddress(@PathVariable (value = "idPerson") Long idPerson, @PathVariable (value = "idAddress") Long idAddress) {
        Optional<Address> address = addressDAO.findByIdAndPersonId(idAddress, idPerson);  	
        if  (!address.isPresent()) {
    		return "Address with id #" + idAddress + " and person id #" + idPerson + ": NOT FOUND";
    	}
    	addressDAO.delete(address.get());
    	return "Address with id #" + idAddress + " and person id #" + idPerson + " has been deleted successfully.";
    }
}