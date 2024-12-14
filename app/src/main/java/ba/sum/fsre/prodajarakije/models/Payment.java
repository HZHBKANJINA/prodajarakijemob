package ba.sum.fsre.prodajarakije.models;
import com.google.firebase.firestore.PropertyName;

public class Payment {
    private Order order;
    private String paymentMethod;
    private String status;
    private String amount;

    public Payment(){}

    public Payment(Order order, String paymentMethod, String status, String amount) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.amount = amount;
    }

    @PropertyName("order")
    public Order getOrder() {
        return order;
    }

    @PropertyName("order")
    public void setOrder(Order order) {
        this.order = order;
    }

    @PropertyName("paymentMethod")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @PropertyName("paymentMethod")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @PropertyName("amount")
    public String getAmount() {
        return amount;
    }

    @PropertyName("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
