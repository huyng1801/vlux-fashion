package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.CustomerDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.Customer;
import vn.student.vluxfashion.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerReposity;

    // Create a new customer
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setEmail(customerDto.getEmail());
        customer.setHashPassword(customerDto.getHashPassword()); // You should hash the password here
        customer.setEmailConfirmed(false); // Default to false, or set as needed
        customer.setPhone(customerDto.getPhone());
        customer.setAddress(customerDto.getAddress());
        customer.setAddress2(customerDto.getAddress2());
        customer.setCity(customerDto.getCity());
        return customerReposity.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerReposity.findAll();
    }

    // Get customer by ID
    public Customer getCustomerById(Integer customerId) {
        return customerReposity.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
    }

    // Update a customer
    public Customer updateCustomer(Integer customerId, CustomerDto customerDto) {
        Customer existingCustomer = getCustomerById(customerId);
        existingCustomer.setFullName(customerDto.getFullName());
        existingCustomer.setEmail(customerDto.getEmail());
        existingCustomer.setHashPassword(customerDto.getHashPassword()); // You should hash the password here if it's changed
        existingCustomer.setPhone(customerDto.getPhone());
        existingCustomer.setAddress(customerDto.getAddress());
        existingCustomer.setAddress2(customerDto.getAddress2());
        existingCustomer.setCity(customerDto.getCity());
        return customerReposity.save(existingCustomer);
    }

    // Delete a customer
    public void deleteCustomer(Integer customerId) {
        Customer existingCustomer = getCustomerById(customerId);
        customerReposity.delete(existingCustomer);
    }
}
