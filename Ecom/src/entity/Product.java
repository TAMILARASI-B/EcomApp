package entity;

public class Product {
    private int productId;
    private String productname;
    private double price;
    private int quantity;
    private String description;
    public Product() {}

    public Product(int productId, String productname, double price, String description, int quantity) {
        this.productId = productId;
        this.productname = productname;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }


    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productname; }
    public void setProductName(String name) { this.productname = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Product [ID=" + productId + ", Name=" + productname + ", Price=" + price + ", Quantity=" + quantity + "]";
    }

	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		 this.description=description;
	}
	
}
