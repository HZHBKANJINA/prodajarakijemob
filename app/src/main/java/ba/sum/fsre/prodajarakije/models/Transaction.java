package ba.sum.fsre.prodajarakije.models;
import com.google.firebase.firestore.PropertyName;

public class Transaction {
    private Product product;
    private int quantity;
    private int totalPrice;
    private Merchant merchant;
    private Customer customer;
    private String date;
    private String status;

    public Transaction(){}

    public Transaction(Product product, int quantity, int totalPrice, Merchant merchant, Customer customer, String date, String status) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.merchant = merchant;
        this.customer = customer;
        this.date = date;
        this.status = status;
    }

    @PropertyName("product")
    public Product getProduct() {
        return product;
    }

    @PropertyName("product")
    public void setProduct(Product product) {
        this.product = product;
    }

    @PropertyName("quantity")

    public int getQuantity() {
        return quantity;
    }

    @PropertyName("quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @PropertyName("totalPrice")
    public int getTotalPrice() {
        return totalPrice;
    }

    @PropertyName("totalPrice")
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @PropertyName("merchant")
    public Merchant getMerchant() {
        return merchant;
    }

    @PropertyName("merchant")
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @PropertyName("customer")
    public Customer getCustomer() {
        return customer;
    }

    @PropertyName("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    @PropertyName("date")
    public void setDate(String date) {
        this.date = date;
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
