package com.switflow.swiftFlow.Service;

import com.switflow.swiftFlow.Entity.Customer;
import com.switflow.swiftFlow.Exception.CustomerEmailAlreadyExistsException;
import com.switflow.swiftFlow.Repo.CustomerRepo;
import com.switflow.swiftFlow.Request.CustomerRequest;
import com.switflow.swiftFlow.Response.CustomerResposne;
import com.switflow.swiftFlow.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public CustomerResposne createCustomer(CustomerRequest customerRequest) {
        if (customerRequest.getCustomerName() == null || customerRequest.getCustomerName().trim().isEmpty()) {
            throw new RuntimeException("Customer name is required");
        }

        if (customerRequest.getCustomerEmail() == null || customerRequest.getCustomerEmail().trim().isEmpty()) {
            throw new RuntimeException("Customer email is required");
        }

        if (customerRepo.findByCustomerEmail(customerRequest.getCustomerEmail()).isPresent()) {
            throw new CustomerEmailAlreadyExistsException("Customer with this email already exists");
        }

        try {
            Customer customer = new Customer();
            customer.setCustomerName(customerRequest.getCustomerName());
            customer.setCompanyName(customerRequest.getCompanyName());
            customer.setCustomerEmail(customerRequest.getCustomerEmail());
            customer.setCustomerPhone(customerRequest.getCustomerPhone());
            customer.setGstNumber(customerRequest.getGstNumber());
            customer.setPrimaryAddress(customerRequest.getPrimaryAddress());
            customer.setStatus("Active");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            customer.setDateAdded(LocalDate.now().format(formatter));

            Customer savedCustomer = customerRepo.save(customer);

            CustomerResposne response = new CustomerResposne();
            response.setCustomerId(savedCustomer.getCustomerId());
            response.setCustomerName(savedCustomer.getCustomerName());
            response.setCompanyName(savedCustomer.getCompanyName());
            response.setCustomerEmail(savedCustomer.getCustomerEmail());
            response.setCustomerPhone(savedCustomer.getCustomerPhone());
            response.setGstNumber(savedCustomer.getGstNumber());
            response.setPrimaryAddress(savedCustomer.getPrimaryAddress());
            response.setStatus(savedCustomer.getStatus());
            response.setDateAdded(savedCustomer.getDateAdded());
            response.setBillingAddress(savedCustomer.getBillingAddress());
            response.setShippingAddress(savedCustomer.getShippingAddress());

            return response;
        } catch (CustomerEmailAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error creating customer: " + e.getMessage());
            throw new RuntimeException("Failed to create customer: " + e.getMessage());
        }
    }

    public List<CustomerResposne> getAllCustomers() {
        return customerRepo.findAll().stream().map(customer -> {
            CustomerResposne response = new CustomerResposne();
            response.setCustomerId(customer.getCustomerId());
            response.setCustomerName(customer.getCustomerName());
            response.setCompanyName(customer.getCompanyName());
            response.setCustomerEmail(customer.getCustomerEmail());
            response.setCustomerPhone(customer.getCustomerPhone());
            response.setGstNumber(customer.getGstNumber());
            response.setPrimaryAddress(customer.getPrimaryAddress());
            response.setStatus(customer.getStatus());
            response.setDateAdded(customer.getDateAdded());
            return response;
        }).collect(Collectors.toList());
    }

    public CustomerResposne getCustomerById(int customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        
        CustomerResposne response = new CustomerResposne();
        response.setCustomerId(customer.getCustomerId());
        response.setCustomerName(customer.getCustomerName());
        response.setCompanyName(customer.getCompanyName());
        response.setCustomerEmail(customer.getCustomerEmail());
        response.setCustomerPhone(customer.getCustomerPhone());
        response.setGstNumber(customer.getGstNumber());
        response.setPrimaryAddress(customer.getPrimaryAddress());
        response.setStatus(customer.getStatus());
        response.setDateAdded(customer.getDateAdded());
        response.setBillingAddress(customer.getBillingAddress());
        response.setShippingAddress(customer.getShippingAddress());
        return response;
    }

    public CustomerResposne updateCustomer(int customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setCompanyName(customerRequest.getCompanyName());
        customer.setCustomerEmail(customerRequest.getCustomerEmail());
        customer.setCustomerPhone(customerRequest.getCustomerPhone());
        customer.setGstNumber(customerRequest.getGstNumber());
        customer.setPrimaryAddress(customerRequest.getPrimaryAddress());
        customer.setStatus(customerRequest.getStatus());
        customer.setBillingAddress(customerRequest.getBillingAddress());
        customer.setShippingAddress(customerRequest.getShippingAddress());
        Customer updatedCustomer = customerRepo.save(customer);

        CustomerResposne response = new CustomerResposne();
        response.setCustomerId(updatedCustomer.getCustomerId());
        response.setCustomerName(updatedCustomer.getCustomerName());
        response.setCompanyName(updatedCustomer.getCompanyName());
        response.setCustomerEmail(updatedCustomer.getCustomerEmail());
        response.setCustomerPhone(updatedCustomer.getCustomerPhone());
        response.setGstNumber(updatedCustomer.getGstNumber());
        response.setPrimaryAddress(updatedCustomer.getPrimaryAddress());
        response.setStatus(updatedCustomer.getStatus());
        response.setBillingAddress(updatedCustomer.getBillingAddress());
        response.setShippingAddress(updatedCustomer.getShippingAddress());
        response.setDateAdded(updatedCustomer.getDateAdded());
        return response;

    }

    public MessageResponse deleteCustomer(int customerId) {
        customerRepo.deleteById(customerId);
        return new MessageResponse("Customer deleted successfully");
    }

}