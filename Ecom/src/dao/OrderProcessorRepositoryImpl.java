package dao;

import entity.*;
import exception.*;
import util.DBConnUtil;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public  class OrderProcessorRepositoryImpl implements OrderProcessorRepository {
	
	public boolean createProduct(Product product) {
	    try (Connection conn = DBConnUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?)")) {
	        stmt.setInt(1, product.getProductId());
	        stmt.setString(2, product.getProductName());
	        stmt.setDouble(3, product.getPrice());
	        stmt.setString(4, product.getDescription());
	        stmt.setInt(5, product.getQuantity());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	        return false;
	    }
	}


    public boolean createCustomer(Customer customer) {
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO customers VALUES (?, ?, ?, ?)")
        ) {
            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM products WHERE product_id = ?")
        ) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) {
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?")
        ) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    
    @Override
    public boolean addToCart(Customer customer, Product product, int quantity) throws ProductNotFoundException {
        try (Connection conn = DBConnUtil.getConnection()) {
          
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM products WHERE product_id = ?");
            checkStmt.setInt(1, product.getProductId());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                throw new ProductNotFoundException("Product ID not found: " + product.getProductId());
            }

           
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO cart VALUES (NULL, ?, ?, ?)");
            stmt.setInt(1, customer.getCustomerId());
            stmt.setInt(2, product.getProductId());
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeFromCart(Customer customer, Product product) {
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM cart WHERE customer_id = ? AND product_id = ?")
        ) {
            stmt.setInt(1, customer.getCustomerId());
            stmt.setInt(2, product.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Product> getAllFromCart(Customer customer) {
        List<Product> cartItems = new ArrayList<>();
        String sql = "SELECT p.product_id, p.name AS product_name, p.price, p.description, p.quantity " +
                     "FROM cart c JOIN products p ON c.product_id = p.product_id " +
                     "WHERE c.customer_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer.getCustomerId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));  // Alias used here
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setQuantity(rs.getInt("quantity"));
                cartItems.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }



    public boolean placeOrder(Customer customer, List<Map<Product, Integer>> cart, String shippingAddress) {
        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement orderStmt = conn.prepareStatement("INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES (?, CURDATE(), ?, ?)", Statement.RETURN_GENERATED_KEYS);
            double total = 0;
            for (Map<Product, Integer> map : cart) {
                for (Product p : map.keySet()) {
                    total += p.getPrice() * map.get(p);
                }
            }
            orderStmt.setInt(1, customer.getCustomerId());
            orderStmt.setDouble(2, total);
            orderStmt.setString(3, shippingAddress);
            orderStmt.executeUpdate();
            ResultSet rs = orderStmt.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);
            PreparedStatement itemStmt = conn.prepareStatement("INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)");
            for (Map<Product, Integer> map : cart) {
                for (Product p : map.keySet()) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, p.getProductId());
                    itemStmt.setInt(3, map.get(p));
                    itemStmt.executeUpdate();
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) {}
        }
    }
    public void getOrdersByCustomer(Customer customer) throws CustomerNotFoundException {
        boolean customerExists = false;

        String sql = "SELECT o.order_id, o.order_date, p.name AS product_name, " +
                     "oi.quantity, p.price " +
                     "FROM orders o " +
                     "JOIN order_items oi ON o.order_id = oi.order_id " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "WHERE o.customer_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer.getCustomerId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                customerExists = true;
                int orderId = rs.getInt("order_id");
                String productName = rs.getString("product_name");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price");
                Date orderDate = rs.getDate("order_date");

                System.out.println("Order ID: " + orderId + " | Date: " + orderDate);
                System.out.println(" - " + productName + " | Qty: " + qty + " | Price: ₹" + price);
            }

            if (!customerExists) {
                throw new CustomerNotFoundException("Customer with ID " + customer.getCustomerId() + " not found or has no orders.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}