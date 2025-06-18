package io.github.imecuadorian.product.dto;

import jakarta.validation.constraints.*;

public record ProductRequest(
        @NotBlank String name, @NotBlank String description, @Positive double price) {
}
