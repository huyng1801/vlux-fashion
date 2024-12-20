package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.CustomerDto;
import vn.student.vluxfashion.dto.LoginUserDto;
import vn.student.vluxfashion.dto.RegisterDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.Customer;
import vn.student.vluxfashion.model.Role;
import vn.student.vluxfashion.repository.CustomerRepository;

import java.util.List;
import java.util.Date;

import vn.student.vluxfashion.exception.InvalidCredentialsException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
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
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Customer getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
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
        return customerRepository.save(existingCustomer);
    }

    // Delete a customer
    public void deleteCustomer(Integer customerId) {
        Customer existingCustomer = getCustomerById(customerId);
        customerRepository.delete(existingCustomer);
    }

     public Customer register(RegisterDto registerDto) {
        if (customerRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        Customer newCustomer = new Customer();
        newCustomer.setFullName(registerDto.getFullName());
        newCustomer.setEmail(registerDto.getEmail());
        newCustomer.setHashPassword(passwordEncoder.encode(registerDto.getPassword()));
        newCustomer.setEmailConfirmed(false); // Default to false
        newCustomer.setPhone(registerDto.getPhone());
        newCustomer.setAddress(registerDto.getAddress());
        newCustomer.setAddress2(registerDto.getAddress2());
        newCustomer.setCity(registerDto.getCity());
        newCustomer.setCreatedAt(new Date());
        newCustomer.setUpdatedAt(new Date());
        return customerRepository.save(newCustomer);
    }

    // Login functionality
    public String login(LoginUserDto loginDto) {
        Customer customer = customerRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginDto.getEmail()));

        if (!passwordEncoder.matches(loginDto.getPassword(), customer.getHashPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        // Generate JWT token
        return jwtService.generateToken(customer, Role.CUSTOMER);
    }
}
