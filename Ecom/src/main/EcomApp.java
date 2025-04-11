package main;

import dao.*;
import entity.*;
import exception.*;
import java.util.*;
public class EcomApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderProcessorRepository repo = new OrderProcessorRepositoryImpl();

        while (true) {
            System.out.println("1. Register Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Add to cart");
            System.out.println("5. View cart");
            System.out.println("6. Place order");
            System.out.println("7. View Customer Order");
            System.out.println("8. Exit");
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
    System.out.print("Enter Customer ID: ");
    int customerId = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter Customer Name: ");
    String name = sc.nextLine();
    System.out.print("Enter Customer Email: ");
    String email = sc.nextLine();
    System.out.print("Enter Customer Password: ");
    String password = sc.nextLine();
    System.out.print("Enter Customer address: ");
    String address = sc.nextLine();

    Customer c = new Customer(customerId, name, email,address, password);
   
    if (repo.createCustomer(c)) {
        System.out.println("Customer registered successfully!");
    } else {
        System.out.println("Customer registration failed.");
    }    break;
                case 2:
    System.out.print("Enter Product ID: ");
    int productId = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter Product Name: ");
    String productName = sc.nextLine();
    System.out.print("Enter Product Price: ");
    double price = sc.nextDouble();
    sc.nextLine();
    System.out.print("Enter Product Description: ");
    String description = sc.nextLine();
    System.out.print("Enter Stock Quantity: ");
    int stock = sc.nextInt();
    Product p = new Product(productId, productName, price,  description,stock);
    if (repo.createProduct(p)) {
        System.out.println("Product created successfully!");
    } else {
        System.out.println("Failed to create product.");
    }

    break;
                case 3:
    System.out.print("Enter Product ID to delete: ");
    int deleteId = sc.nextInt();
    if (repo.deleteProduct(deleteId)) {
        System.out.println("Product deleted successfully!");
    } else {
        System.out.println("Failed to delete product.");
    }

    break;
                case 4:
                    try {
                        System.out.print("Enter Customer ID: ");
                        int addCustomerId = sc.nextInt();
                        System.out.print("Enter Product ID: ");
                        int addProductId = sc.nextInt();
                        System.out.print("Enter Quantity: ");
                        int addQty = sc.nextInt();
                        Customer ac = new Customer(); ac.setCustomerId(addCustomerId);
                        Product ap = new Product(); ap.setProductId(addProductId);
                        if(repo.addToCart(ac, ap, addQty))
                        {
                        	System.out.println("Product added to cart successfully!");
                        }
                        else
                        {
                        	System.out.println("Product not added to cart successfully!");
                        }
                    } catch (exception.ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Enter Customer ID: ");
                    int viewCustomerId = sc.nextInt();
                    Customer vc = new Customer();
                    vc.setCustomerId(viewCustomerId);

                    List<Product> cartItems = repo.getAllFromCart(vc);
                    if (cartItems.isEmpty()) {
                        System.out.println("Your cart is empty or customer ID is invalid.");
                    } else {
                        System.out.println("Items in your cart:");
                        for (Product prod : cartItems) {
                            System.out.println(prod.getProductName() + " - ₹" + prod.getPrice());
                        }
                    }
                    break;

                case 6:
    System.out.print("Enter Customer ID: ");
    int orderCustomerId = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter Shipping Address: ");
    String shippingAddress = sc.nextLine();
    Customer oc = new Customer();
    oc.setCustomerId(orderCustomerId);
    List<Product> items = repo.getAllFromCart(oc);
    List<Map<Product, Integer>> orderCart = new ArrayList<>();
    for (Product prod : items) {
        Map<Product, Integer> map = new HashMap<>();
        map.put(prod, 1);
        orderCart.add(map);
    }
   if(repo.placeOrder(oc, orderCart, shippingAddress))
   {
	   System.out.println("order placed successfully!");
   }
   else
   {
	   System.out.println("order not placed successfully!");
   }
    break;
                case 7:
                    try {
                        System.out.print("Enter Customer ID: ");
                        int custId = sc.nextInt();

                        Customer customer = new Customer();
                        customer.setCustomerId(custId);

                        repo.getOrdersByCustomer(customer);
                    } catch (CustomerNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                    }
                    break;


                case 8:
                    System.exit(0);
            }
        }
       
       
    }
}
