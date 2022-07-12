package io.github.wendellmfm.controller;

import io.github.wendellmfm.model.Customer;
import io.github.wendellmfm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/get/{id}")
    public ResponseEntity get(@PathVariable Integer id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not founded.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customer.get());
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Customer customer){
        Customer savedCustomer = customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not founded.");
        }

        customerRepository.delete(customer.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer deleted successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestBody Customer customer){

        return customerRepository.findById(id).map(customerFounded -> {
            customer.setId(customerFounded.getId());
            customerRepository.save(customer);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer updated successfully.");
        }).orElseGet( () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not founded."));
    }

    @GetMapping("/find")
    public ResponseEntity find(Customer filter){
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("cpf", ExampleMatcher.GenericPropertyMatchers.exact());

        Example example = Example.of(filter, matcher);
        List<Customer> customersList = customerRepository.findAll(example);

        if (customersList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not founded.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customersList);
    }
}
