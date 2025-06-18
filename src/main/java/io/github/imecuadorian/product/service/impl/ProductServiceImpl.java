package io.github.imecuadorian.product.service.impl;

import io.github.imecuadorian.product.dto.*;
import io.github.imecuadorian.product.mapper.*;
import io.github.imecuadorian.product.repository.*;
import io.github.imecuadorian.product.service.*;
import lombok.*;
import org.springframework.stereotype.*;
import reactor.core.publisher.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Flux<ProductDto> findAll() {
        return repository.findAll().map(mapper::toDto);
    }

    @Override
    public Mono<ProductDto> findById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public Mono<ProductDto> create(ProductDto dto) {
        return repository.save(mapper.toEntity(dto)).map(mapper::toDto);
    }

    @Override
    public Mono<ProductDto> update(Long id, ProductDto dto) {
        return repository
                .findById(id)
                .flatMap(
                        existing -> {
                            existing.setName(dto.getName());
                            existing.setDescription(dto.getDescription());
                            existing.setPrice(dto.getPrice());
                            return repository.save(existing);
                        })
                .map(mapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
