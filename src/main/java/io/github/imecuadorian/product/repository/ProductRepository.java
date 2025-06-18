package io.github.imecuadorian.product.repository;

import io.github.imecuadorian.product.model.*;
import org.springframework.data.repository.reactive.*;
import org.springframework.stereotype.*;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
