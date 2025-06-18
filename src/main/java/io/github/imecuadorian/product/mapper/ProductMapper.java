package io.github.imecuadorian.product.mapper;

import io.github.imecuadorian.product.dto.*;
import io.github.imecuadorian.product.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product entity);

    Product toEntity(ProductDto dto);

    ProductResponse toResponse(ProductDto dto);

    default ProductDto fromRequest(ProductRequest request) {
        Product entity =
                Product.builder()
                        .name(request.name())
                        .description(request.description())
                        .price(request.price())
                        .active(true)
                        .build();
        return toDto(entity);
    }
}
