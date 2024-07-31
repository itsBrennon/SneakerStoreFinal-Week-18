package sneaker.store.controller;
import java.util.List; 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import sneaker.store.controller.model.SneakerStoreData;
import sneaker.store.controller.model.SneakerStoreData.SneakerStoreCustomer;
import sneaker.store.controller.model.SneakerStoreData.SneakerStoreSneakerSize;
import sneaker.service.SneakerStoreService;

//This is the sneaker store controller class. The HTTP requests are mapped to this class and responses 
//are sent back to the client.

//Tells Spring that this a controller and that it takes and returns JSON
@RestController 
//Maps the URIs starting with /sneaker_store onto the SneakerStoreController. 
//All of the URIs mapped to the methods below will start with /sneaker_store
@RequestMapping("/sneaker_store") 
@Slf4j
public class SneakerStoreController {
	//Injects the SneakerStoreService bean into this field
	@Autowired
	private SneakerStoreService sneakerStoreService;
	
	
	//Maps POST requests to the method. The requests are sent to /sneaker_store 
	//The method creates/inserts a sneaker store's data into the database by calling the saveSneakerStore() method in the SneakerStoreService class
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public SneakerStoreData insertSneakerStore(@RequestBody SneakerStoreData sneakerStoreData) {
		log.info("Creating the sneaker store {}", sneakerStoreData);
		return sneakerStoreService.saveSneakerStore(sneakerStoreData);
	}
	
	//Maps PUT requests to the method. The requests are sent to /sneaker_store/{sneakerStoreId}.
	//The method updates a sneaker store's data in the database by calling the saveSneakerStore() method in the SneakerStoreService class
	@PutMapping("/{sneakerStoreId}")
	public SneakerStoreData updateSneakerStore(@PathVariable Long sneakerStoreId, @RequestBody SneakerStoreData sneakerStoreData) {
		sneakerStoreData.setSneakerStoreId(sneakerStoreId);
		log.info("Updating sneaker store {}", sneakerStoreData);
		return sneakerStoreService.saveSneakerStore(sneakerStoreData);
	}
	
	//Maps a POST request to the method. The requests are sent to /sneaker_store/{sneakerStoreId}/sneakersize.
	//The method inserts an sneakersize's data into the database by calling the saveSneakerSize() method in the SneakerStoreService class
	@PostMapping("/{sneakerStoreId}/sneakersize")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SneakerStoreSneakerSize insertSneakerSize(@PathVariable Long sneakerStoreId, @RequestBody SneakerStoreSneakerSize sneakerStoreSneakerSize) {
		log.info("Creating sneakersize {} for sneaker store with ID={}", sneakerStoreSneakerSize, sneakerStoreId);
		return sneakerStoreService.saveSneakerSize(sneakerStoreId, sneakerStoreSneakerSize);
	}
	
	//Maps a POST request to the method. The requests are sent to /sneaker_store/{sneakerStoreId}/customer.
	//The method inserts a customer's data into the database by calling the saveCustomer() method in the SneakerStoreService class
	@PostMapping("/{sneakerStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SneakerStoreCustomer insertCustomer(@PathVariable Long sneakerStoreId, @RequestBody SneakerStoreCustomer sneakerStoreCustomer) {
		log.info("Creating customer {} for sneaker store with ID={}", sneakerStoreCustomer, sneakerStoreId);
		return sneakerStoreService.saveCustomer(sneakerStoreId, sneakerStoreCustomer);
	}
	
		
	//Maps a GET request to the method. The requests are sent to /sneaker_store
	//The method retrieves all sneaker store data from the database by calling the retrieveAllSneakerStores() method in the SneakerStoreService class
	@GetMapping()
	public List<SneakerStoreData> retrieveAllSneakerStores() {
		log.info("Retrieve all sneaker stores.");
		return sneakerStoreService.retrieveAllSneakerStores();
	}
	
	
	//Maps a GET request to the method. The requests are sent to /sneaker_store/{sneakerStoreId}. 
	//The method retrieves a sneaker store's data from the database by calling the retrieveSneakerStoreById() method in the SneakerStoreService class
	@GetMapping("/{sneakerStoreId}")
	public SneakerStoreData retrieveSneakerStoreById(@PathVariable Long sneakerStoreId) {
		log.info("Retrieving sneaker store by ID={}", sneakerStoreId);
		return sneakerStoreService.retrieveSneakerStoreById(sneakerStoreId);
	}
	
	
	//Maps a DELETE request to the method. The requests are sent to /sneaker_store/{sneakerStoreId}.
	//The method deletes a sneaker store's data from the database by calling the deleteSneakerStoreById() method in the SneakerStoreService class
	@DeleteMapping("/{sneakerStoreId}")
	public Map<String, String> deleteSneakerStoreById(@PathVariable Long sneakerStoreId) {
		log.info("Deleting sneaker store with ID={}", sneakerStoreId);
		sneakerStoreService.deleteSneakerStoreById(sneakerStoreId);
		
		return Map.of("message", "Deletion of sneaker store with ID=" + sneakerStoreId 
				+ " was successful.");
	}
}