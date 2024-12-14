package ba.sum.fsre.prodajarakije.models;
import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Cart {
    private Customer customer;
    private List<Product> products;
    private int totalPrice;

    public Cart(){}

    public Cart(Customer customer, List<Product> products, int totalPrice) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    @PropertyName("customer")
    public Customer getCustomer() {
        return customer;
    }

    @PropertyName("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PropertyName("products")
    public List<Product> getProducts() {
        return products;
    }

    @PropertyName("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @PropertyName("totalPrice")
    public int getTotalPrice() {
        return totalPrice;
    }

    @PropertyName("totalPrice")
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
