package com.accela.codingtest.dao;

import com.accela.codingtest.model.Address;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressDAO extends JpaRepository<Address, Long>{
	public Page<Address> findByPersonId (Long personId, Pageable pageable);
	public Optional<Address> findByIdAndPersonId (Long id, Long personId);
}