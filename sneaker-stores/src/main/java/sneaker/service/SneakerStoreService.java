package sneaker.service;


import java.util.LinkedList; 
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sneaker.store.controller.model.SneakerStoreData;
import sneaker.store.controller.model.SneakerStoreData.SneakerStoreCustomer;
import sneaker.store.controller.model.SneakerStoreData.SneakerStoreSneakerSize;
import sneaker.dao.CustomerDao;
import sneaker.dao.SneakerSizeDao;
import sneaker.dao.SneakerStoreDao;
import sneaker.entity.Customer;
import sneaker.entity.SneakerSize;
import sneaker.entity.SneakerStore;

//Manages transactions that are performed in the SneakerStoreDao interface.

//Tells Spring that this is a service class and creates the SneakerStoreService bean 
@Service
public class SneakerStoreService {
	
	//Injects the SneakerStoreDao bean into this field
	@Autowired
	private SneakerStoreDao sneakerStoreDao;
	
	@Autowired
	private SneakerSizeDao sneakersizeDao;
	
	@Autowired CustomerDao customerDao;

	//Saves the created or updated sneaker store data in the sneaker_store table by calling the save() method in the SneakerStoreDao interface
	public SneakerStoreData saveSneakerStore(SneakerStoreData sneakerStoreData) {
		Long sneakerStoreId = sneakerStoreData.getSneakerStoreId();
		SneakerStore sneakerStore = findOrCreateSneakerStore(sneakerStoreId);
		
		copySneakerStoreFields(sneakerStore, sneakerStoreData);
		
		SneakerStore dbSneakerStore = sneakerStoreDao.save(sneakerStore);
		
		return new SneakerStoreData(dbSneakerStore);
	}

	//Sets the values of the SneakerStore fields to the values of the SneakerStoreData fields
	private void copySneakerStoreFields(SneakerStore sneakerStore, SneakerStoreData sneakerStoreData) {
		sneakerStore.setSneakerStoreId(sneakerStoreData.getSneakerStoreId());
		sneakerStore.setSneakerStoreName(sneakerStoreData.getSneakerStoreName());
		sneakerStore.setSneakerStoreAddress(sneakerStoreData.getSneakerStoreAddress());
		sneakerStore.setSneakerStoreCity(sneakerStoreData.getSneakerStoreCity());
		sneakerStore.setSneakerStoreState(sneakerStoreData.getSneakerStoreState());
		sneakerStore.setSneakerStoreZip(sneakerStoreData.getSneakerStoreZip());
		sneakerStore.setSneakerStorePhone(sneakerStoreData.getSneakerStorePhone());
	}

	//If the sneaker store ID is null, it creates a sneaker store. If the ID is not null, it calls
	//the findSneakerStoreById() method to retrieve the sneaker store data from the database. 
	private SneakerStore findOrCreateSneakerStore(Long sneakerStoreId) {
		SneakerStore sneakerStore;
		
		if (Objects.isNull(sneakerStoreId)) {
			sneakerStore = new SneakerStore();
		} else {
			sneakerStore = findSneakerStoreById(sneakerStoreId);
		}
		
		return sneakerStore;
	}

	//Retrieves sneaker store data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the SneakerStoreDao interface. If the sneaker store ID that is passed to the method does not exist in the sneaker_store table, it throws an exception.
	private SneakerStore findSneakerStoreById(Long sneakerStoreId) {
		return sneakerStoreDao.findById(sneakerStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"SneakerStore with ID=" + sneakerStoreId + " does not exist."));
	}

	//Saves the sneakersize data in the sneakersize table by calling the save() method in the SneakerSizeDao interface. 
	@Transactional(readOnly = false)
	public SneakerStoreSneakerSize saveSneakerSize(Long sneakerStoreId, SneakerStoreSneakerSize sneakerStoreSneakerSize) {
		SneakerStore sneakerStore = findSneakerStoreById(sneakerStoreId);
		SneakerSize sneakersize = findOrCreateSneakerSize(sneakerStoreId, sneakerStoreSneakerSize.getSneakerSizeId());
		copySneakerSizeFields(sneakersize, sneakerStoreSneakerSize);
		sneakersize.setSneakerStore(sneakerStore);
		sneakerStore.getSneakersize().add(sneakersize);
		
		SneakerSize dbSneakerSize = sneakersizeDao.save(sneakersize);
		
		return new SneakerStoreSneakerSize(dbSneakerSize);
	}
	
	//Sets the values of the SneakerSize fields to the values of the SneakerStoreSneakerSize fields
	public void copySneakerSizeFields(SneakerSize sneakersize, SneakerStoreSneakerSize sneakerStoreSneakerSize) {
		sneakersize.setSneakerSizeId(sneakerStoreSneakerSize.getSneakerSizeId());
		sneakersize.setSneakerSizeBaby(sneakerStoreSneakerSize.getSneakerSizeBaby());
		sneakersize.setSneakerSizeKid(sneakerStoreSneakerSize.getSneakerSizeKid());
		sneakersize.setSneakerSizeAdult(sneakerStoreSneakerSize.getSneakerSizeAdult());
	}
	
	//If the sneakersize ID is null, it creates an sneakersize. If the ID is not null, it calls
	//the findSneakerSizeById() method to retrieve the sneakersize data from the sneakersize table. 
	public SneakerSize findOrCreateSneakerSize(Long sneakerStoreId, Long sneakersizeId) {
		SneakerSize sneakersize;
		
		if (Objects.isNull(sneakersizeId)) {
			sneakersize = new SneakerSize();
		} else {
			sneakersize = findSneakerSizeById(sneakerStoreId, sneakersizeId);
		}
		
		return sneakersize;
	}
	
	//Retrieves sneakersize data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the SneakerSizeDao interface. If the sneakersize ID that is passed to the method does not exist in the sneakersize table, it throws an exception.
	//If the sneaker store ID associated with the sneakersize does not equal the sneaker store ID passed to the method, it throws an exception.
	public SneakerSize findSneakerSizeById(Long sneakerStoreId, Long sneakersizeId) {
		SneakerSize sneakersize = sneakersizeDao.findById(sneakersizeId)
			.orElseThrow(() -> new NoSuchElementException(
					"SneakerSize with ID=" + sneakersizeId + " does not exist."));
		
		if(sneakersize.getSneakerStore().getSneakerStoreId() == sneakerStoreId) {
			return sneakersize;
		} else {
			throw new IllegalArgumentException("Sneaker store with ID=" + sneakerStoreId + " does not have an sneakersize with ID=" + sneakersizeId);
		}
	}
	
	
	
	//Saves the customer data in the customer table by calling the save() method in the CustomerDao interface. 
	@Transactional(readOnly = false)
	public SneakerStoreCustomer saveCustomer(Long sneakerStoreId, SneakerStoreCustomer sneakerStoreCustomer) {
		SneakerStore sneakerStore = findSneakerStoreById(sneakerStoreId);
		Customer customer = findOrCreateCustomer(sneakerStoreId, sneakerStoreCustomer.getCustomerId()); 
		copyCustomerFields(customer, sneakerStoreCustomer);
		customer.getSneakerStores().add(sneakerStore);
		sneakerStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new SneakerStoreCustomer(dbCustomer);
	}
	
	//Sets the values of the Customer fields to the values of the SneakerStoreCustomer fields
	public void copyCustomerFields(Customer customer, SneakerStoreCustomer sneakerStoreCustomer) {
		customer.setCustomerId(sneakerStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(sneakerStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(sneakerStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(sneakerStoreCustomer.getCustomerEmail());
	}
	
	//If the customer ID is null, it creates a customer. If the ID is not null, it calls
	//the findCustomerById() method to retrieve the customer data from the customer table. 
	public Customer findOrCreateCustomer(Long sneakerStoreId, Long customerId) {
		Customer customer;
		
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(sneakerStoreId, customerId);
		}
		
		return customer;
	}
	
	//Retrieves customer data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the CustomerDao interface. If the customer ID that is passed to the method does not exist in the customer table, it throws an exception.
	//If one of the sneaker store IDs associated with the customer does not equal the sneaker store ID passed to the method, it throws an exception.
	public Customer findCustomerById(Long sneakerStoreId, Long customerId) {
		boolean sneakerStoreIdsMatch = false;
		
		Customer customer =  customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Customer with ID=" + customerId + " does not exist."));
		
		Set<SneakerStore> sneakerStores = customer.getSneakerStores();
		for (SneakerStore sneakerStore : sneakerStores) {
			if (sneakerStore.getSneakerStoreId() == sneakerStoreId) {
				sneakerStoreIdsMatch = true;
			}
		}
		
		if(sneakerStoreIdsMatch) {
			return customer;
		} else {
			throw new IllegalArgumentException("Sneaker store with ID=" + sneakerStoreId + " does not have a customer with ID=" + customerId);
		}
	}

	//Retrieves all of the sneaker store data in the sneaker_store table by calling the findAll() method in the SneakerStoreDao interface. 
	//It does not return the sneakersize and customer data associated with the sneaker stores.
	@Transactional
	public List<SneakerStoreData> retrieveAllSneakerStores() {
		List<SneakerStore> sneakerStores = sneakerStoreDao.findAll();
		List<SneakerStoreData> results = new LinkedList<>();
		
		for (SneakerStore sneakerStore : sneakerStores) {
			SneakerStoreData sneakerStoreData = new SneakerStoreData(sneakerStore);
			
			
			
			results.add(sneakerStoreData);
		}
		
		return results;
	}
	

	//Retrieves a sneaker store by calling the findSneakerStoreById() method in the SneakerStoreDao interface
	public SneakerStoreData retrieveSneakerStoreById(Long sneakerStoreId) {
		SneakerStore sneakerStore = findSneakerStoreById(sneakerStoreId);
		return new SneakerStoreData(sneakerStore);
	}
	
	
	//Deletes a sneaker store by calling the delete() method in the SneakerStoreDao interface
	public void deleteSneakerStoreById(Long sneakerStoreId) {
		SneakerStore sneakerStore = findSneakerStoreById(sneakerStoreId);
		sneakerStoreDao.delete(sneakerStore);
	}

	

	
}
