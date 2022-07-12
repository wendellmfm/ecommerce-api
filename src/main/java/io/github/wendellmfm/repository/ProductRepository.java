package io.github.wendellmfm.repository;

import io.github.wendellmfm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
