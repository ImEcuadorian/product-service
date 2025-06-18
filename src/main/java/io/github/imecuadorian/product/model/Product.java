package io.github.imecuadorian.product.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "public", name = "products")
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean active;
}
