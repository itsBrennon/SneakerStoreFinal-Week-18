

package sneaker.store.controller.model;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import sneaker.entity.Customer;
import sneaker.entity.SneakerSize;
import sneaker.entity.SneakerStore;

//A class where JSON will be converted to a data transfer object and vice versa

@Data
@NoArgsConstructor
public class SneakerStoreData {
	private Long sneakerStoreId;
	private String sneakerStoreName;
	private String sneakerStoreAddress;
	private String sneakerStoreCity;
	private String sneakerStoreState;
	private String sneakerStoreZip;
	private String sneakerStorePhone;
	
	
	
	
	
	private Set<SneakerStoreCustomer> customers = new HashSet<>();
	private Set<SneakerStoreSneakerSize> sneakersizes = new HashSet<>();

	//Assigns the values of the SneakerStore class fields to the SneakerStoreData class fields
	public SneakerStoreData (SneakerStore sneakerStore) {
		sneakerStoreId = sneakerStore.getSneakerStoreId();
		sneakerStoreName = sneakerStore.getSneakerStoreName();
		sneakerStoreAddress = sneakerStore.getSneakerStoreAddress();
		sneakerStoreCity = sneakerStore.getSneakerStoreCity();
		sneakerStoreState = sneakerStore.getSneakerStoreState();
		sneakerStoreZip = sneakerStore.getSneakerStoreZip();
		sneakerStorePhone = sneakerStore.getSneakerStorePhone();
		
		//The loop for customers
		for (Customer customer : sneakerStore.getCustomers()) {
			customers.add(new SneakerStoreCustomer(customer));
		}
		//The loop for sneakerSize
		for (SneakerSize sneakersize : sneakerStore.getSneakersize()) {
			sneakersizes.add(new SneakerStoreSneakerSize(sneakersize));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class SneakerStoreCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		//Assigns the values of the Customer class fields to the SneakerStoreCustomer class fields
		public SneakerStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}

	@Data
	@NoArgsConstructor
	public static class SneakerStoreSneakerSize {
		private Long sneakerSizeId;
		private String sneakerSizeBaby;
		private String sneakerSizeKid;
		private String sneakerSizeAdult;
		
		
		//Assigns the values of the SneakerSize class fields to the SneakerStoreSneakerSize class fields
		public SneakerStoreSneakerSize(SneakerSize sneakerSize) {
			sneakerSizeId = sneakerSize.getSneakerSizeId();
			sneakerSizeBaby = sneakerSize.getSneakerSizeBaby();
			sneakerSizeKid = sneakerSize.getSneakerSizeKid();
			sneakerSizeAdult = sneakerSize.getSneakerSizeAdult();
			
		}
	}
}