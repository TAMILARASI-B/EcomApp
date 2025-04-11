package entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int customerId;
    private Map<Product, Integer> items = new HashMap<>();

    public Cart(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() { return customerId; }

    public Map<Product, Integer> getItems() { return items; }

    public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setItems(Map<Product, Integer> items) {
		this.items = items;
	}

	public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public double calculateTotal() {
        return items.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    @Override
    public String toString() {
        return "Cart [Customer ID=" + customerId + ", Items=" + items + "]";
    }
}

