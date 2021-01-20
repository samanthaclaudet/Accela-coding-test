package com.accela.codingtest.dao;

import com.accela.codingtest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonDAO extends JpaRepository<Person, Long> {

}
