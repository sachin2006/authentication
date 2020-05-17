/**
 * 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.model.Address;

/**
 * @author sachin
 *
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

	public List<Address> findByUser(Long userId);
}
