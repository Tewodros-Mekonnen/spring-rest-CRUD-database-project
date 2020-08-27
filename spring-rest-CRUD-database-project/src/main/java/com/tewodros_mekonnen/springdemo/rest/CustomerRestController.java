package com.tewodros_mekonnen.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tewodros_mekonnen.springdemo.entity.Customer;
import com.tewodros_mekonnen.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customers")
	public List<Customer> getCustomers() {

		return customerService.getCustomers();
	}

	@GetMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {

		Customer theCustomer = customerService.getCustomer(customerId);

		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found: " + customerId);
		}

		return theCustomer;
	}

	// adding mapping for POST /customers - add new customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {

		// in case if someone passes an id with in the JSON file, we do not want to
		// update an
		// existing customer based on the id that is passed (inside DAO, we are using
		// saveOrUpdate).
		// Therefore, we need to set id of the object
		// we pass to 0 (0==null). if 0 or null, instead of update, DAO will create new
		// customer object!
		theCustomer.setId(0);

		customerService.saveCustomer(theCustomer);

		return theCustomer;
	}

	// adding mapping for PUT /customers to update existing customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {

		// here, in-order to update an existing customer, no need to set the id to 0, as
		// it was done on POST mapping above
		customerService.saveCustomer(theCustomer);

		return theCustomer;

	}

	// adding mapping for DELETE /customers to delete a single customer
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {

		Customer tempCustomer = customerService.getCustomer(customerId);

		// throw exception if passed id does not exist...
		if (tempCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found: " + customerId);
		}

		customerService.deleteCustomer(customerId);

		return "Deleted customer id: " + customerId;
	}

}
