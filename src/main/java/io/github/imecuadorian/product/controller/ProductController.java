package io.github.imecuadorian.product.controller;

import io.github.imecuadorian.product.dto.*;
import io.github.imecuadorian.product.mapper.*;
import io.github.imecuadorian.product.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return productService.create(productMapper.fromRequest(request)).map(productMapper::toResponse);
    }

    @GetMapping
    public Flux<ProductResponse> findAll() {
        return productService.findAll().map(productMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ProductResponse> findById(@PathVariable Long id) {
        return productService.findById(id).map(productMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return productService
                .update(id, productMapper.fromRequest(request))
                .map(productMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return productService.delete(id);
    }
}
