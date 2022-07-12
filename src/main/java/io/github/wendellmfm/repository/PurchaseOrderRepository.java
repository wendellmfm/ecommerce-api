package io.github.wendellmfm.repository;

import io.github.wendellmfm.model.Customer;
import io.github.wendellmfm.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findByCustomer(Customer customer);
}
