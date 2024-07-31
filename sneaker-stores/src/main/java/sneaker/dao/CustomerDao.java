package sneaker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sneaker.entity.Customer;


public interface CustomerDao extends JpaRepository<Customer, Long> {
	
}