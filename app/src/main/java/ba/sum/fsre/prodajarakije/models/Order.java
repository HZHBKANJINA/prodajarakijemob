package ba.sum.fsre.prodajarakije.models;
import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Order {
    private List<Product> products;
    private Customer customer;

    private Integer totalPrice;
    private Integer totalQuantity;

    private String status;

    public Order(){}

    public Order(List<Product> products, Customer customer, Integer totalPrice, Integer totalQuantity, String status) {
        this.products = products;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.status = status;
    }

    @PropertyName("products")
    public List<Product> getProducts() {
        return products;
    }

    @PropertyName("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @PropertyName("customer")
    public Customer getCustomer() {
        return customer;
    }

    @PropertyName("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PropertyName("totalPrice")
    public Integer getTotalPrice() {
        return totalPrice;
    }

    @PropertyName("totalPrice")
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    @PropertyName("totalQuantity")
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    @PropertyName("totalQuantity")
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("status")
    public void setStatus(String status) {
        this.status = status;
    }

}
