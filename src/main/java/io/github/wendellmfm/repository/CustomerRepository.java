package io.github.wendellmfm.repository;

import io.github.wendellmfm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByNameLike(String name);

    List<Customer> findByNameContaining(String name);

    List<Customer> findByNameOrId(String name, Integer id);

    boolean existsByName(String name);

    @Query("select c from Customer c left join fetch c.purchaseOrders where c.id = :id")
    Customer findPurchaseOrdersByCustomerHQLQuery(@Param("id") Integer id);


}
