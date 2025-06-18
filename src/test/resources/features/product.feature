Feature: Product operations

    Scenario: Retrieving all products
        Given a valid product request
        When the client sends a GET request to /api/products
        Then the client should receive a list of products

    Scenario: Retrieving a product by ID
        Given a valid product request
        When the client sends a GET request to /api/products/1
        Then the client should receive the product with ID 1

    Scenario: Updating a product
        Given a valid product request
        When the client sends a PUT request to /api/products/1
        Then the product should be updated and returned

    Scenario: Deleting a product
        Given a valid product request
        When the client sends a DELETE request to /api/products/1
        Then the product should be deleted
