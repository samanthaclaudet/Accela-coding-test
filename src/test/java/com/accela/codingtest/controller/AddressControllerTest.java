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

import com.accela.codingtest.dao.IAddressDAO;
import com.accela.codingtest.dao.IPersonDAO;
import com.accela.codingtest.model.Address;
import com.accela.codingtest.model.Person;

@SpringBootTest
public class AddressControllerTest {

	@MockBean
	private IPersonDAO personDAO;
	
	@MockBean
	private IAddressDAO addressDAO;

	@Autowired
	private AddressController addressController;
	
	private List<Person> persons;
	private Address address, newAddress;

	@BeforeEach
	public void createElements() {
		Person person1 = new Person("Jane", "Doe");
		person1.setId(1);
		
		Person person2 = new Person("John", "Doe");
		person2.setId(2);
		
		address = new Address();
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
		
		newAddress = new Address();
		newAddress.setId(2);
		newAddress.setStreet("another street example");
		newAddress.setCity("another generic city");
		newAddress.setState("another random state");
		newAddress.setPostalCode("01010");
	}
	
    @Test
    public void getAddressNotFoundTest() {
        Mockito.when(addressDAO.findById((long) 3)).thenReturn(Optional.empty());
        String addressReturned = addressController.getAddress((long) 3);
        assertEquals("Address with id #3: NOT FOUND", addressReturned);
    }
	
    @Test
    public void getAddressTest() {
        Mockito.when(addressDAO.findById((long) 1)).thenReturn(Optional.of(address));
        String addressReturned = addressController.getAddress((long) 1);
        assertEquals("address: street example, 10101 generic city (random state)", addressReturned);
    }
    
    @Test
    public void getAllAddressesFromPersonTest() {
    	List<Address> addresses = new ArrayList<>();
    	addresses.add(address);
        Mockito.when(addressDAO.findByPersonId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(new PageImpl<Address>(addresses, Pageable.unpaged(), 1));
        Page<Address> addressReturned = addressController.getAllAddressesFromPerson((long) 1, Pageable.unpaged());
        assertEquals(addressReturned.getContent(), addresses);
    }
    
    @Test
    public void addAddressWithPersonNotFoundTest() {
        Mockito.when(personDAO.findById((long) 4)).thenReturn(Optional.empty());
        String addressAdded = addressController.addAddress(4, new Address());
        assertEquals("Person with id #4: NOT FOUND", addressAdded);
    }
    
    @Test
    public void addAddressTest() {
        Mockito.when(personDAO.findById((long) 2)).thenReturn(Optional.of(persons.get(1)));
        Mockito.when(addressDAO.save(newAddress)).thenReturn(newAddress);
        String addressAdded = addressController.addAddress(2, newAddress);
        assertEquals("address: another street example, 01010 another generic city (another random state) has been added successfully", addressAdded);
    }
    
    @Test
    public void updateAddressWithPersonNotFoundTest() {
        Mockito.when(personDAO.existsById((long) 4)).thenReturn(false);
        String addressUpdated = addressController.updateAddress((long) 4, (long) 2, new Address());
        assertEquals("Person with id #4: NOT FOUND", addressUpdated);
    }
    
    @Test
    public void updateAddressNotFoundTest() {
        Mockito.when(personDAO.existsById((long) 1)).thenReturn(true);
        Mockito.when(addressDAO.findById((long) 2)).thenReturn(Optional.empty());
        String addressUpdated = addressController.updateAddress((long) 1, (long) 2, new Address());
        assertEquals("Address with id #2: NOT FOUND", addressUpdated);
    }
       
    @Test
    public void updateAddressTest() {
        Mockito.when(personDAO.existsById((long) 1)).thenReturn(true);
        Mockito.when(addressDAO.findById((long) 1)).thenReturn(Optional.of(address));
        Mockito.when(addressDAO.save(Mockito.any(Address.class))).thenReturn(newAddress);
        String addressUpdated = addressController.updateAddress((long) 1, (long) 1, newAddress);
        assertEquals("address: another street example, 01010 another generic city (another random state) has been updated successfully", addressUpdated);
    }
    
    @Test
    public void deleteAddressNotFoundTest() {
        Mockito.when(addressDAO.findByIdAndPersonId((long) 2, (long) 4)).thenReturn(Optional.empty());
        String addressDeleted = addressController.deleteAddress((long) 4, (long) 2);
        assertEquals("Address with id #2 and person id #4: NOT FOUND", addressDeleted);
    }
    
    @Test
    public void deleteAddressTest() {
        Mockito.when(addressDAO.findByIdAndPersonId((long) 1, (long) 1)).thenReturn(Optional.of(address));
        String addressDeleted = addressController.deleteAddress((long) 1, (long) 1);
        assertEquals("Address with id #1 and person id #1 has been deleted successfully.", addressDeleted);
    }
}