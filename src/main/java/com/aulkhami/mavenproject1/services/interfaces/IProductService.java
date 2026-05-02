package com.aulkhami.mavenproject1.services.interfaces;

import com.aulkhami.mavenproject1.models.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> getById(Integer id);

    List<Product> getAll();

    Product save(Product product);

    void update(Product product);

    void delete(Integer id);
}
