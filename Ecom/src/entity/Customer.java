package entity;

public class Customer {
    private int customerId;
    private String customername;
    private String email;
    private String address;
    private String password;

    public Customer(int customerId, String customername, String email, String address,String password) {
        this.customerId = customerId;
        this.customername = customername;
        this.email = email;
        this.address = address;
        this.setPassword(password);
    }

    
    public Customer() {}

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customername;
    }
    public void setName(String name) {
        this.customername = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

 
    @Override
    public String toString() {
        return "Customer [ID=" + customerId + ", Name=" + customername + ", Email=" + email + ", Address=" + address + "]";
    }


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}

