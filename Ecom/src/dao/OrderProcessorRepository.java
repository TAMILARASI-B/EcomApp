package dao;

import entity.*;
import exception.*;

import java.util.*;

public interface OrderProcessorRepository {
    boolean createProduct(Product product);
    boolean createCustomer(Customer customer);
    boolean deleteProduct(int productId);
    boolean deleteCustomer(int customerId);
    boolean addToCart(Customer customer, Product product, int quantity) throws ProductNotFoundException;
    boolean removeFromCart(Customer customer, Product product);
    List<Product> getAllFromCart(Customer customer);
    boolean placeOrder(Customer customer, List<Map<Product, Integer>> cart, String shippingAddress);
    void getOrdersByCustomer(Customer customer) throws CustomerNotFoundException;
	
}