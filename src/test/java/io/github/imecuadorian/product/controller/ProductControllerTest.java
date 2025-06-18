package io.github.imecuadorian.product.controller;

import io.github.imecuadorian.product.config.*;
import io.github.imecuadorian.product.dto.*;
import io.github.imecuadorian.product.mapper.*;
import io.github.imecuadorian.product.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.reactive.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.test.web.reactive.server.*;
import reactor.core.publisher.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebFluxTest(ProductController.class)
@Import({ProductControllerTest.TestConfig.class, TestSecurityConfig.class})
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;
    private ProductRequest request;
    private ProductResponse response;
    private ProductDto dto;

    @BeforeEach
    void setup() {
        request = new ProductRequest("Camiseta", "De algodón", 10.5);
        dto =
                ProductDto.builder()
                        .id(1L)
                        .name("Camiseta")
                        .description("De algodón")
                        .price(10.5)
                        .active(true)
                        .build();
        response = new ProductResponse(1L, "Camiseta", "De algodón", 10.5, true);
    }

    @Test
    void testCreate() {
        when(productMapper.fromRequest(any())).thenReturn(dto);
        when(productService.create(any())).thenReturn(Mono.just(dto));
        when(productMapper.toResponse(any())).thenReturn(response);

        webTestClient
                .post()
                .uri("/api/products")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(1)
                .jsonPath("$.name")
                .isEqualTo("Camiseta");
    }

    @Test
    void testFindAll() {
        when(productService.findAll()).thenReturn(Flux.just(dto));
        when(productMapper.toResponse(any())).thenReturn(response);

        webTestClient
                .get()
                .uri("/api/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ProductResponse.class)
                .hasSize(1)
                .contains(response);
    }

    @Test
    void testFindById() {
        when(productService.findById(1L)).thenReturn(Mono.just(dto));
        when(productMapper.toResponse(any())).thenReturn(response);

        webTestClient
                .get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(1)
                .jsonPath("$.name")
                .isEqualTo("Camiseta");
    }

    @Test
    void testUpdate() {
        when(productMapper.fromRequest(any())).thenReturn(dto);
        when(productService.update(1L, dto)).thenReturn(Mono.just(dto));
        when(productMapper.toResponse(any())).thenReturn(response);

        webTestClient
                .put()
                .uri("/api/products/1")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(1)
                .jsonPath("$.price")
                .isEqualTo(10.5);
    }

    @Test
    void testDelete() {
        when(productService.delete(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/products/1").exchange().expectStatus().isNoContent();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }

        @Bean
        public ProductMapper productMapper() {
            return Mockito.mock(ProductMapper.class);
        }
    }
}
