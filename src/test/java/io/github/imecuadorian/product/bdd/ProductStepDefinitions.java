package io.github.imecuadorian.product.bdd;

import io.cucumber.java.en.*;
import io.github.imecuadorian.product.controller.*;
import io.github.imecuadorian.product.dto.*;
import io.github.imecuadorian.product.mapper.*;
import io.github.imecuadorian.product.service.*;
import reactor.core.publisher.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;

public class ProductStepDefinitions {

    private ProductController controller;
    private ProductRequest request;
    private Mono<ProductResponse> createResponse;
    private Flux<ProductResponse> allResponse;
    private Mono<ProductResponse> singleResponse;
    private Mono<Void> deleteResponse;

    @Given("a valid product request")
    public void a_valid_product_request() {
        ProductService productService = mock(ProductService.class);
        ProductMapper productMapper = mock(ProductMapper.class);
        controller = new ProductController(productService, productMapper);

        request = new ProductRequest("Laptop", "Gaming laptop", 1499.99);
        ProductDto dto = new ProductDto(1L, "Laptop", "Gaming laptop", 1499.99, true);
        ProductResponse mapped = new ProductResponse(1L, "Laptop", "Gaming laptop", 1499.99, true);

        when(productMapper.fromRequest(any())).thenReturn(dto);
        when(productService.create(any())).thenReturn(Mono.just(dto));
        when(productMapper.toResponse(any())).thenReturn(mapped);
        when(productService.findAll()).thenReturn(Flux.just(dto));
        when(productService.findById(1L)).thenReturn(Mono.just(dto));
        when(productService.update(eq(1L), any())).thenReturn(Mono.just(dto));
        when(productService.delete(1L)).thenReturn(Mono.empty());
    }

    @When("the client sends a POST request to /api/products")
    public void the_client_sends_post_request() {
        createResponse = controller.create(request);
    }

    @Then("the product should be created and returned")
    public void the_product_should_be_created_and_returned() {
        ProductResponse product = createResponse.block();
        assert product != null;
        assert product.getName().equals("Laptop");
    }

    @When("the client sends a GET request to /api/products")
    public void the_client_sends_get_all() {
        allResponse = controller.findAll();
    }

    @Then("the client should receive a list of products")
    public void the_client_receives_list() {
        assert Objects.requireNonNull(allResponse.collectList().block()).size() == 1;
    }

    @When("the client sends a GET request to /api/products/{int}")
    public void the_client_sends_get_by_id(int id) {
        singleResponse = controller.findById((long) id);
    }

    @Then("the client should receive the product with ID 1")
    public void the_client_receives_single_product() {
        ProductResponse product = singleResponse.block();
        assert product != null;
        assert product.getId() == 1L;
    }

    @When("the client sends a PUT request to /api/products/{int}")
    public void the_client_sends_update(int id) {
        singleResponse = controller.update((long) id, request);
    }

    @Then("the product should be updated and returned")
    public void the_product_should_be_updated() {
        ProductResponse product = singleResponse.block();
        assert product != null;
        assert product.getName().equals("Laptop");
    }

    @When("the client sends a DELETE request to /api/products/{int}")
    public void the_client_sends_delete(int id) {
        deleteResponse = controller.delete((long) id);
    }

    @Then("the product should be deleted")
    public void the_product_is_deleted() {
        deleteResponse.block();
    }

}
