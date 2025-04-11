package test;
import java.util.*;
import dao.OrderProcessorRepository;
import dao.OrderProcessorRepositoryImpl;
import entity.*;
import exception.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EcomAppTest {

    OrderProcessorRepository repo = new OrderProcessorRepositoryImpl();

    @Test
    public void testCreateProductSuccess() {
        Product product = new Product(103, "TestProduct1", 1997.99, "Test Descriptions",150);
        boolean result = repo.createProduct(product);
        assertTrue(result);
    }

@Test
public void testAddToCartSuccess() throws ProductNotFoundException {
    Customer customer = new Customer(1, "Test User", "test@example.com","chennai", "test123");
    Product product = new Product(102, "Test Cart Product", 50.0, "Cart test product", 10);

    repo.createCustomer(customer); 
    repo.createProduct(product); 

    boolean result = repo.addToCart(customer, product, 2);
    assertTrue(result);
}
@Test
public void testPlaceOrderSuccess() throws ProductNotFoundException {
    Customer customer = new Customer(2, "Order User", "order@example.com","pune", "pass123");
    Product product = new Product(103, "OrderProduct", 120.0, "Test Order", 10);

    repo.createCustomer(customer);
    repo.createProduct(product);
    repo.addToCart(customer, product, 1);

    List<Map<Product, Integer>> cart = new ArrayList<>();
    Map<Product, Integer> item = new HashMap<>();
    item.put(product, 1);
    cart.add(item);

    boolean result = repo.placeOrder(customer, cart, "123 Order Lane");
    assertTrue(result);
}


@Test
public void testCustomerNotFoundException() {
    int invalidCustomerId = 99999; 
    Customer customer = new Customer();
    customer.setCustomerId(invalidCustomerId);

    Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
        repo.getOrdersByCustomer(customer); 
    });

    assertEquals("Customer with ID " + invalidCustomerId + " not found or has no orders.", exception.getMessage());
}


@Test
public void testProductNotFoundException() {
    Customer customer = new Customer(1, "Fake", "fake@x.com", "mumbai","123");
    Product invalidProduct = new Product();
    invalidProduct.setProductId(9999); 

    Exception exception = assertThrows(ProductNotFoundException.class, () -> {
        repo.addToCart(customer, invalidProduct, 1);
    });

    assertEquals("Product ID not found: 9999", exception.getMessage());
}


}
