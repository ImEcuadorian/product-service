package io.github.imecuadorian.product.service;

import io.github.imecuadorian.product.dto.*;
import reactor.core.publisher.*;

public interface ProductService {
    Flux<ProductDto> findAll();

    Mono<ProductDto> findById(Long id);

    Mono<ProductDto> create(ProductDto productDto);

    Mono<ProductDto> update(Long id, ProductDto productDto);

    Mono<Void> delete(Long id);
}
